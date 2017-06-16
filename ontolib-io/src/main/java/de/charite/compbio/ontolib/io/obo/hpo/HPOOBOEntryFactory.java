package de.charite.compbio.ontolib.io.obo.hpo;

import com.google.common.collect.Lists;
import de.charite.compbio.ontolib.formats.hpo.HPORelationQualifier;
import de.charite.compbio.ontolib.formats.hpo.HPOTerm;
import de.charite.compbio.ontolib.formats.hpo.HPOTermRelation;
import de.charite.compbio.ontolib.io.obo.OBOImmutableOntologyLoader;
import de.charite.compbio.ontolib.io.obo.OBOOntologyEntryFactory;
import de.charite.compbio.ontolib.io.obo.Stanza;
import de.charite.compbio.ontolib.io.obo.StanzaEntry;
import de.charite.compbio.ontolib.io.obo.StanzaEntryAltID;
import de.charite.compbio.ontolib.io.obo.StanzaEntryComment;
import de.charite.compbio.ontolib.io.obo.StanzaEntryCreatedBy;
import de.charite.compbio.ontolib.io.obo.StanzaEntryCreationDate;
import de.charite.compbio.ontolib.io.obo.StanzaEntryDef;
import de.charite.compbio.ontolib.io.obo.StanzaEntryDisjointFrom;
import de.charite.compbio.ontolib.io.obo.StanzaEntryID;
import de.charite.compbio.ontolib.io.obo.StanzaEntryIntersectionOf;
import de.charite.compbio.ontolib.io.obo.StanzaEntryIsA;
import de.charite.compbio.ontolib.io.obo.StanzaEntryIsObsolete;
import de.charite.compbio.ontolib.io.obo.StanzaEntryName;
import de.charite.compbio.ontolib.io.obo.StanzaEntryRelationship;
import de.charite.compbio.ontolib.io.obo.StanzaEntrySubset;
import de.charite.compbio.ontolib.io.obo.StanzaEntrySynonym;
import de.charite.compbio.ontolib.io.obo.StanzaEntryType;
import de.charite.compbio.ontolib.io.obo.StanzaEntryUnionOf;
import de.charite.compbio.ontolib.ontology.data.ImmutableTermID;
import de.charite.compbio.ontolib.ontology.data.ImmutableTermSynonym;
import de.charite.compbio.ontolib.ontology.data.ImmutableTermXRef;
import de.charite.compbio.ontolib.ontology.data.TermID;
import de.charite.compbio.ontolib.ontology.data.TermSynonym;
import de.charite.compbio.ontolib.ontology.data.TermSynonymScope;
import de.charite.compbio.ontolib.ontology.data.TermXRef;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * Factory class for constructing {@link HPOTerm} and {@link HPOTermRelation} objects from
 * {@link Stanza} objects for usage in {@link OBOOntologyEntryFactory}.
 * 
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
class HPOOBOEntryFactory implements OBOOntologyEntryFactory<HPOTerm, HPOTermRelation> {

  /**
   * Mapping from string representation of term ID to {@link TermID}.
   * 
   * All occuring termIDs must be previously registered into this map before calling any of this
   * object's functions. This happens in {@link OBOImmutableOntologyLoader}.
   */
  private SortedMap<String, ImmutableTermID> termIDs = null;

  /** ID of next relation. */
  private int nextRelationID = 1;

  @Override
  public void setTermIDs(SortedMap<String, ImmutableTermID> termIDs) {
    this.termIDs = termIDs;
  }

  @Override
  public HPOTerm constructTerm(Stanza stanza) {
    final TermID id =
        termIDs.get(this.<StanzaEntryID>getCardinalityOneEntry(stanza, StanzaEntryType.ID).getId());

    final String name =
        this.<StanzaEntryName>getCardinalityOneEntry(stanza, StanzaEntryType.NAME).getName();

    final List<TermID> altTermIDs;
    final List<StanzaEntry> altEntryList = stanza.getEntryByType().get(StanzaEntryType.ALT_ID);
    if (altEntryList == null) {
      altTermIDs = Lists.newArrayList();
    } else {
      altTermIDs = altEntryList.stream().map(e -> termIDs.get(((StanzaEntryAltID) e).getAltID()))
          .collect(Collectors.toList());
    }

    final StanzaEntryDef defEntry =
        this.<StanzaEntryDef>getCardinalityZeroOrOneEntry(stanza, StanzaEntryType.DEF);
    final String definition = (defEntry == null) ? null : defEntry.getText();

    final StanzaEntryComment commentEntry =
        this.<StanzaEntryComment>getCardinalityZeroOrOneEntry(stanza, StanzaEntryType.COMMENT);
    final String comment = (commentEntry == null) ? null : commentEntry.getText();

    final List<String> subsets;
    final List<StanzaEntry> subsetEntryList = stanza.getEntryByType().get(StanzaEntryType.SUBSET);
    if (subsetEntryList == null) {
      subsets = Lists.newArrayList();
    } else {
      subsets = subsetEntryList.stream().map(e -> ((StanzaEntrySubset) e).getName())
          .collect(Collectors.toList());
    }

    final List<TermSynonym> synonyms;
    final List<StanzaEntry> synonymEntryList = stanza.getEntryByType().get(StanzaEntryType.SYNONYM);
    if (synonymEntryList == null) {
      synonyms = Lists.newArrayList();
    } else {
      synonyms = synonymEntryList.stream().map(e -> {
        final StanzaEntrySynonym s = (StanzaEntrySynonym) e;

        final String value = s.getText();
        final TermSynonymScope scope = s.getTermSynonymScope();
        final String synonymTypeName = s.getSynonymTypeName();
        final List<TermXRef> termXRefs = s.getDbXRefList().getDbXRefs().stream()
            .map(xref -> new ImmutableTermXRef(termIDs.get(xref.getName()), xref.getDescription()))
            .collect(Collectors.toList());

        return new ImmutableTermSynonym(value, scope, synonymTypeName, termXRefs);
      }).collect(Collectors.toList());
    }

    final StanzaEntryIsObsolete isObsoleteEntry = this.<
        StanzaEntryIsObsolete>getCardinalityZeroOrOneEntry(stanza, StanzaEntryType.IS_OBSOLETE);
    final boolean obsolete = (isObsoleteEntry == null) ? false : isObsoleteEntry.getValue();

    final StanzaEntryCreatedBy createdByEntry =
        this.<StanzaEntryCreatedBy>getCardinalityZeroOrOneEntry(stanza, StanzaEntryType.CREATED_BY);
    final String createdBy = (createdByEntry == null) ? null : createdByEntry.getCreator();

    final StanzaEntryCreationDate creationDateEntry = this.<
        StanzaEntryCreationDate>getCardinalityZeroOrOneEntry(stanza, StanzaEntryType.CREATION_DATE);
    final String creationDate = (creationDateEntry == null) ? null : creationDateEntry.getValue();

    return new HPOTerm(id, altTermIDs, name, definition, comment, subsets, synonyms, obsolete,
        createdBy, creationDate);
  }

  /**
   * Extract cardinality one entry (=tag) of type <code>type</code> from <code>stanza</code>.
   * 
   * @param stanza {@link Stanza} to get {@link StanzaEntry} from.
   * @param type {@link StanzaEntryType} to use.
   * @return Resulting {@link StanzaEntry}, properly cast.
   */
  @SuppressWarnings("unchecked")
  protected <E extends StanzaEntry> E getCardinalityOneEntry(Stanza stanza, StanzaEntryType type) {
    final List<StanzaEntry> typeEntries = stanza.getEntryByType().get(type);
    if (typeEntries == null || typeEntries.size() != 1) {
      throw new RuntimeException(type + " tag must have cardinality 1 but was " + typeEntries.size()
          + " (" + stanza + ")");
    }
    return (E) typeEntries.get(0);
  }

  /**
   * Extract cardinality zero or one entry (=tag) of type <code>type</code> from
   * <code>stanza</code>.
   * 
   * @param stanza {@link Stanza} to get {@link StanzaEntry} from.
   * @param type {@link StanzaEntryType} to use.
   * @return Resulting {@link StanzaEntry}, properly cast, or <code>null</code>.
   */
  @SuppressWarnings("unchecked")
  protected <E extends StanzaEntry> E getCardinalityZeroOrOneEntry(Stanza stanza,
      StanzaEntryType type) {
    final List<StanzaEntry> typeEntries = stanza.getEntryByType().get(type);
    if (typeEntries == null) {
      return null;
    } else if (typeEntries.size() != 1) {
      throw new RuntimeException(type + " tag must have cardinality <= 1 but was "
          + typeEntries.size() + " (" + stanza + ")");
    } else {
      return (E) typeEntries.get(0);
    }
  }

  @Override
  public HPOTermRelation constructTermRelation(Stanza stanza, StanzaEntryIsA stanzaEntry) {
    final TermID sourceID =
        termIDs.get(this.<StanzaEntryID>getCardinalityOneEntry(stanza, StanzaEntryType.ID).getId());
    final TermID destID = termIDs.get(stanzaEntry.getId());
    return new HPOTermRelation(sourceID, destID, nextRelationID++, HPORelationQualifier.IS_A);
  }

  @Override
  public HPOTermRelation constructTermRelation(Stanza stanza, StanzaEntryDisjointFrom stanzaEntry) {
    throw new UnsupportedOperationException();
  }

  @Override
  public HPOTermRelation constructTermRelation(Stanza stanza, StanzaEntryUnionOf stanzaEntry) {
    throw new UnsupportedOperationException();
  }

  @Override
  public HPOTermRelation constructTermRelation(Stanza stanza,
      StanzaEntryIntersectionOf stanzaEntry) {
    throw new UnsupportedOperationException();
  }

  @Override
  public HPOTermRelation constructTermRelation(Stanza stanza, StanzaEntryRelationship stanzaEntry) {
    throw new UnsupportedOperationException();
  }

}
