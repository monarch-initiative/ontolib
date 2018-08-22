package org.monarchinitiative.phenol.io.obo.go;


import org.monarchinitiative.phenol.base.PhenolException;
import org.monarchinitiative.phenol.io.obo.OboOntologyLoader;
import org.monarchinitiative.phenol.ontology.data.Ontology;

import java.io.File;

public class GoOboParser {

  private final File oboFile;

  private final boolean debug;

  public GoOboParser(File oboFile, boolean debug) {
    this.oboFile = oboFile;
    this.debug = debug;
  }
  public GoOboParser(File oboFile) {
    this(oboFile,false);
  }


  public Ontology parse() throws PhenolException {
    final OboOntologyLoader loader = new OboOntologyLoader(oboFile);
    Ontology ontology = loader.load();
    if (debug) {
      System.err.println(String.format("Parsed a total of %d MP terms",ontology.countAllTerms()));
    }

    return ontology;
  }

}
