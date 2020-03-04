package org.monarchinitiative.phenol.io.annotations.hpo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import org.monarchinitiative.phenol.annotations.hpo.HpoAnnotationEntry;
import org.monarchinitiative.phenol.annotations.hpo.HpoAnnotationModel;
import org.monarchinitiative.phenol.base.HpoAnnotationModelException;
import org.monarchinitiative.phenol.base.PhenolRuntimeException;
import org.monarchinitiative.phenol.ontology.data.Ontology;
import org.monarchinitiative.phenol.ontology.data.TermId;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class coordinates writing out the {@code phenotype.hpoa}, the so-called "big file", which is
 * aggregated from the ca. 7000 small files.
 *
 * @author <a href="mailto:peter.robinson@jax.org">Peter Robinson</a>
 */
public class PhenotypeDotHpoaFileWriter {
  /**
   * List of all of the {@link HpoAnnotationModel} objects from our annotations (OMIM and DECIPHER).
   */
  private final List<HpoAnnotationModel> internalAnnotFileList;
  /**
   * List of all of the {@link HpoAnnotationModel} objects derived from the Orphanet XML file.
   */
  private final List<HpoAnnotationModel> orphanetSmallFileList;

  private final Multimap<TermId, HpoAnnotationEntry> inheritanceMultiMap;
  /** tolerant mode (update obsolete term ids if possible) */
  private final boolean tolerant;

  /**
   * Usually "phenotype.hpoa", but may also include path.
   */
  private final String outputFileName;
  private final static String EMPTY_STRING = "";
  private final Ontology ontology;
  /**
   * Number of annotated Orphanet entries.
   */
  private int n_orphanet;
  private int n_decipher;
  private int n_omim;
  /**
   * Number of database sources that could not be identified (should be zero!).
   */
  private int n_unknown;

  private Map<String, String> ontologyMetaInfo;
  /** The path to the directory where the small files, e.g. OMIM-600301.tab, live. */
  private final File smallFileDirectory;
  /** The en_product4_HPO.xml file .*/
  private final File orphaPhenotypeXMLfile;
  /** The en_product9_ages.xml file. */
  private final File orphaInheritanceXMLfile;


  /**
   * @param ont                     reference to HPO ontology
   * @param smallFileDirectoryPath  List of annotation models for data from the HPO small files
   * @param orphaPhenotypeXMLpath,  path to
   * @param orphaInheritanceXMLpath List of inheritance annotations for Orphanet data
   * @param outpath                 path of the outfile (usually {@code phenotype.hpoa})
   */
  public static PhenotypeDotHpoaFileWriter factory(Ontology ont,
                                                   String smallFileDirectoryPath,
                                                   String orphaPhenotypeXMLpath,
                                                   String orphaInheritanceXMLpath,
                                                   String outpath,
                                                   boolean toler) {

    return new PhenotypeDotHpoaFileWriter(ont,
      smallFileDirectoryPath,
      orphaPhenotypeXMLpath,
      orphaInheritanceXMLpath,
      outpath,
      toler);

  }
  /**
   * @param ont                     reference to HPO ontology
   * @param smallFileDirectoryPath  List of annotation models for data from the HPO small files
   * @param orphaPhenotypeXMLpath,  path to
   * @param orphaInheritanceXMLpath List of inheritance annotations for Orphanet data
   * @param outpath                 path of the outfile (usually {@code phenotype.hpoa})
   */
  private PhenotypeDotHpoaFileWriter(Ontology ont,
                                     String smallFileDirectoryPath,
                                     String orphaPhenotypeXMLpath,
                                     String orphaInheritanceXMLpath,
                                     String outpath,
                                     boolean toler) {
    Objects.requireNonNull(ont);
    this.ontology = ont;
    Objects.requireNonNull(outpath);
    this.outputFileName = outpath;
    smallFileDirectory = new File(smallFileDirectoryPath);
    if (!smallFileDirectory.exists()) {
      throw new PhenolRuntimeException("Could not find " + smallFileDirectoryPath
        + " (We were expecting the directory with the HPO disease annotation files");
    }
    if (!smallFileDirectory.isDirectory()) {
      throw new PhenolRuntimeException(smallFileDirectoryPath
        + " is not a directory (We were expecting the directory with the HPO disease annotation files");
    }
    orphaPhenotypeXMLfile = new File(orphaPhenotypeXMLpath);
    if (!orphaPhenotypeXMLfile.exists()) {
      throw new PhenolRuntimeException("Could not find " + orphaPhenotypeXMLfile
        + " (We were expecting the path to en_product4_HPO.xml");
    }
    orphaInheritanceXMLfile = new File(orphaInheritanceXMLpath);
    if (!orphaInheritanceXMLfile.exists()) {
      throw new PhenolRuntimeException("Could not find " + orphaInheritanceXMLpath
        + " (We were expecting the path to en_product9_ages.xml");
    }
    this.tolerant = toler;
    HpoAnnotationFileIngestor annotationFileIngestor =
      new HpoAnnotationFileIngestor(smallFileDirectory.getAbsolutePath(), ont);
    this.internalAnnotFileList = annotationFileIngestor.getSmallFileEntries();
    // 2. Get the Orphanet Inheritance Annotations
    OrphanetInheritanceXMLParser inheritanceXMLParser =
      new OrphanetInheritanceXMLParser(orphaInheritanceXMLfile.getAbsolutePath(), ontology);
    this.inheritanceMultiMap = inheritanceXMLParser.getDisease2inheritanceMultimap();

    OrphanetXML2HpoDiseaseModelParser orphaParser =
      new OrphanetXML2HpoDiseaseModelParser(this.orphaPhenotypeXMLfile.getAbsolutePath(), ontology, tolerant);
    //List<HpoAnnotationModel> prelimOrphaDiseaseList = orphaParser.getOrphanetDiseaseModels();
    Map<TermId,HpoAnnotationModel> prelimOrphaDiseaseMap = orphaParser.getOrphanetDiseaseMap();
    ImmutableList.Builder<HpoAnnotationModel> builder = new ImmutableList.Builder<>();

    for (TermId diseaseId : prelimOrphaDiseaseMap.keySet()) {
      HpoAnnotationModel model = prelimOrphaDiseaseMap.get(diseaseId);
      if (this.inheritanceMultiMap.containsKey(diseaseId)) {
        Collection<HpoAnnotationEntry> inheritanceEntryList = this.inheritanceMultiMap.get(diseaseId);
        HpoAnnotationModel mergedModel = model.mergeWithInheritanceAnnotations(inheritanceEntryList);
        prelimOrphaDiseaseMap.put(diseaseId,mergedModel); // replace with model that has inheritance
      }
    }

    this.orphanetSmallFileList = new ArrayList<>(prelimOrphaDiseaseMap.values());
    setOntologyMetadata(ont.getMetaInfo());
    setNumberOfDiseasesForHeader();
  }

  /**
   * In the header of the {@code phenotype.hpoa} file, we write the
   * number of OMIM, Orphanet, and DECIPHER entries. This is calculated
   * here (except for Orphanet).
   */
  private void setNumberOfDiseasesForHeader() {

    this.n_decipher = 0;
    this.n_omim = 0;
    this.n_unknown = 0;
    for (HpoAnnotationModel diseaseModel : internalAnnotFileList) {
      if (diseaseModel.isOMIM()) n_omim++;
      else if (diseaseModel.isDECIPHER()) n_decipher++;
      else n_unknown++;
    }
    this.n_orphanet = orphanetSmallFileList.size();
  }


  private void setOntologyMetadata(Map<String, String> meta) {
    this.ontologyMetaInfo = meta;
  }

  private String getDate() {
    Date dNow = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    return ft.format(dNow);
  }

  /**
   * Output the {@code phenotype.hpoa} file on the basis of the "small files" and the Orphanet XML file.
   *
   * @throws IOException if we cannot write to file.
   */
  public void outputBigFile() throws IOException {
    String description = String.format("#description: HPO annotations for rare diseases [%d: OMIM; %d: DECIPHER; %d ORPHANET]", n_omim, n_decipher, n_orphanet);
    if (n_unknown > 0)
      description = String.format("%s -- warning: %d entries could not be assigned to a database", description, n_unknown);
    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
    writer.write(description + "\n");
    writer.write(String.format("#date: %s\n", getDate()));
    writer.write("#tracker: https://github.com/obophenotype/human-phenotype-ontology\n");
    if (ontologyMetaInfo.containsKey("data-version")) {
      writer.write(String.format("#HPO-version: %s\n", ontologyMetaInfo.get("data-version")));
    }
    if (ontologyMetaInfo.containsKey("saved-by")) {
      writer.write(String.format("#HPO-contributors: %s\n", ontologyMetaInfo.get("saved-by")));
    }
    int n = 0;
    writer.write(getHeaderLine() + "\n");
    for (HpoAnnotationModel smallFile : this.internalAnnotFileList) {
      List<HpoAnnotationEntry> entryList = smallFile.getEntryList();
      for (HpoAnnotationEntry entry : entryList) {
        try {
          String bigfileLine = entry.toBigFileLine(ontology);
          writer.write(bigfileLine + "\n");
        } catch (HpoAnnotationModelException e) {
          System.err.println(String.format("[ERROR] with entry (%s) skipping line: %s",e.getMessage(),entry.getLineNoTabs()));
        }
        n++;
      }
    }
    System.out.println("[INFO] We output a total of " + n + " big file lines from internal HPO Annotation files");
    int m = 0;
    Set<TermId> seenInheritance = new HashSet<>();
    for (HpoAnnotationModel smallFile : this.orphanetSmallFileList) {
      List<HpoAnnotationEntry> entryList = smallFile.getEntryList();
      for (HpoAnnotationEntry entry : entryList) {
        try {
          String bigfileLine = entry.toBigFileLine(ontology);
          writer.write(bigfileLine + "\n");
        } catch (HpoAnnotationModelException e) {
          System.err.println(String.format("[ERROR] with entry (%s) skipping line: %s",e.getMessage(),entry.getLineNoTabs()));
        }
        m++;
      }
    }
    System.out.println("We output a total of " + m + " big file lines from the Orphanet Annotation files");
    System.out.println("Total output lines was " + (n + m));
    writer.close();
  }

  /**
   * @return Header line for the big file (indicating column names for the data).
   */
  static String getHeaderLine() {
    String[] fields = {"DatabaseID",
      "DiseaseName",
      "Qualifier",
      "HPO_ID",
      "Reference",
      "Evidence",
      "Onset",
      "Frequency",
      "Sex",
      "Modifier",
      "Aspect",
      "Biocuration"};
    return String.join("\t", fields);
  }


}