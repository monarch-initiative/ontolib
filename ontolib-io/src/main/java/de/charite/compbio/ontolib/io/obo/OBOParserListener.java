package de.charite.compbio.ontolib.io.obo;

import com.google.common.collect.Lists;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.DbXRefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.DbXRefListContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.EolComment2Context;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.ExtWordContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.HeaderContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.HeaderKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.InstanceStanzaKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueAltIDContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueAutoGeneratedByContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueCommentContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueConsiderContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueCreatedByContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueCreationDateContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueDataVersionContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueDateContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueDefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueDefaultRelationshipIdPrefixContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueDisjointFromContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueDomainContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueFormatVersionContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueGenericContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIDContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIdMappingContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIdspaceContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueImportContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueInstanceOfContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIntersectionOfContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueInverseOfContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsAContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsAnonymousContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsAntisymmetricContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsCyclicContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsMetadataContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsObsoleteContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsReflexiveContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsSymmetricContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueIsTransitiveContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueNameContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueRangeContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueRelationshipContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueRemarkContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueReplacedByContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueSavedByContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueSubsetContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueSubsetdefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueSynonymContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueSynonymtypedefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueTransitiveOverContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueUnionOfContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.KeyValueXRefContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.StanzaContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.TermStanzaKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.TrailingModifierContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.TrailingModifierKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParser.TypedefStanzaKeyValueContext;
import de.charite.compbio.ontolib.io.obo.parser.Antlr4OBOParserBaseListener;
import de.charite.compbio.ontolib.ontology.data.TermSynonymScope;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

// TODO: validate the cardinality of entries in header/stanza
// TODO: using Optional<> for optioanl entries would greatly simplify downstream processing

/**
 * Master <code>ParseTreeListener</code> to use for OBO parsing.
 *
 * <p>
 * This class itself uses the listener pattern for customizing behaviour of storing the header and
 * collecting all stanzas or an event-based implementation using {@link OBOParseResultListener}.
 * </p>
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
class OBOParserListener extends Antlr4OBOParserBaseListener {

  /** Internal listener for notifying on parsed parts. */
  private final OBOParseResultListener listener;

  /**
   * Constructor.
   *
   * @param listener {@link OBOParseResultListener} to use.
   */
  public OBOParserListener(OBOParseResultListener listener) {
    this.listener = listener;
  }

  /** Map nodes to Objects with Map&lt;ParseTree, Object&gt;. */
  private ParseTreeProperty<Object> values = new ParseTreeProperty<>();

  /**
   * Set associated <code>value</code> for the given {@link ParseTree} <code>node</code>.
   * 
   * @param node The {@link ParseTree} <code>node</code> to set the value for.
   * @param value The value to put for the given {@link ParseTree} <code>node</code>.
   */
  private void setValue(ParseTree node, Object value) {
    values.put(node, value);
  }

  /**
   * Retrieve object stored for <code>node</code>.
   *
   * <p>
   * Note that this just has package level access such that this class can be conveniently tested.
   * </p>
   *
   * @param node The {@link ParseTree} node to get the value for.
   * @return The value associated with <code>node</code>.
   */
  Object getValue(ParseTree node) {
    return values.get(node);
  }

  /**
   * Flush the {@link #values} mapping for allowing garbage collection.
   */
  void clearValues() {
    values = new ParseTreeProperty<>();
  }

  /** Current {@link StanzaType}. */
  private StanzaType stanzaType = null;

  /**
   * List of current stanza's {@link StanzaEntry} objects.
   *
   * <p>
   * This has package level visibility for testing purposes.
   * </p>
   */
  List<StanzaEntry> stanzaKeyValues = null;

  /**
   * Called on entering <code>stanza</code> rule.
   */
  @Override
  public void enterHeader(HeaderContext ctx) {
    // Create new list of key/value pairs
    stanzaKeyValues = new ArrayList<StanzaEntry>();
  }

  /**
   * Called on leaving <code>header</code>.
   */
  @Override
  public void exitHeader(HeaderContext ctx) {
    // Notify listener about being done with the header parsing
    if (listener != null) {
      listener.parsedHeader(Header.create(stanzaKeyValues));
    }

    // Zap stanza type and list of key/value pairs
    stanzaType = null;
    stanzaKeyValues = null;
    // Clear mapping of parse tree nodes
    clearValues();
  }

  /**
   * Called on leaving <code>stanza</code> rule.
   */
  @Override
  public void exitStanza(StanzaContext ctx) {
    // Extract and store current stanza type
    if (ctx.termStanza() != null) {
      stanzaType = StanzaType.TERM;
    } else if (ctx.typedefStanza() != null) {
      stanzaType = StanzaType.TYPEDEF;
    } else if (ctx.instanceStanza() != null) {
      stanzaType = StanzaType.INSTANCE;
    } else {
      // nop, header is a fake stanza
    }

    // Construct Stanza object and notify listener
    if (listener != null) {
      listener.parsedStanza(Stanza.create(stanzaType, stanzaKeyValues));
    }

    // Zap stanza type and list of key/value pairs
    stanzaType = null;
    stanzaKeyValues = null;
    // Clear all values attached to parse tree nodes
    clearValues();
  }

  /**
   * Called on entering <code>stanza</code> rule.
   */
  @Override
  public void enterStanza(StanzaContext ctx) {
    // Create new list of key/value pairs
    stanzaKeyValues = new ArrayList<StanzaEntry>();
  }

  /**
   * Called on leaving <code>termKeyValue</code>, moves child <code>keyValue*</code> to this
   * {@link ParseTree}'s map entry.
   */
  @Override
  public void exitTermStanzaKeyValue(TermStanzaKeyValueContext ctx) {
    setValue(ctx, getValue(ctx.getChild(0)));
    stanzaKeyValues.add((StanzaEntry) getValue(ctx.getChild(0)));
  }

  /**
   * Called on leaving <code>instanceKeyValue</code>, moves child <code>keyValue*</code> to this
   * {@link ParseTree}'s map entry.
   */
  @Override
  public void exitInstanceStanzaKeyValue(InstanceStanzaKeyValueContext ctx) {
    setValue(ctx, getValue(ctx.getChild(0)));
    stanzaKeyValues.add((StanzaEntry) getValue(ctx.getChild(0)));
  }

  /**
   * Called on leaving <code>typedefKeyValue</code>, moves child <code>keyValue*</code> to this
   * {@link ParseTree}'s map entry.
   */
  @Override
  public void exitTypedefStanzaKeyValue(TypedefStanzaKeyValueContext ctx) {
    setValue(ctx, getValue(ctx.getChild(0)));
    stanzaKeyValues.add((StanzaEntry) getValue(ctx.getChild(0)));
  }

  /**
   * Called on leaving <code>headerKeyValue</code>, moves child <code>keyValue*</code> to this
   * {@link ParseTree}'s map entry.
   */
  @Override
  public void exitHeaderKeyValue(HeaderKeyValueContext ctx) {
    setValue(ctx, getValue(ctx.getChild(0)));
    stanzaKeyValues.add((StanzaEntry) getValue(ctx.getChild(0)));
  }

  /** Called on leaving <code>keyValueID</code> rule. */
  @Override
  public void exitKeyValueID(KeyValueIDContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryID(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueName</code> rule. */
  @Override
  public void exitKeyValueName(KeyValueNameContext ctx) {
    final String name = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryName(name, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsAnonymous</code> rule. */
  @Override
  public void exitKeyValueIsAnonymous(KeyValueIsAnonymousContext ctx) {
    final boolean isAnonymous = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsAnonymous(isAnonymous, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsAltID</code> rule. */
  @Override
  public void exitKeyValueAltID(KeyValueAltIDContext ctx) {
    final String altID = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryAltID(altID, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDef</code> rule. */
  @Override
  public void exitKeyValueDef(KeyValueDefContext ctx) {
    final String text = OBOEscapeUtils.unescape(ctx.QuotedString().getText());
    final DBXRefList dbXRefList = (DBXRefList) getValue(ctx.dbXRefList());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDef(text, dbXRefList, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueComment</code> rule. */
  @Override
  public void exitKeyValueComment(KeyValueCommentContext ctx) {
    final String text = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryComment(text, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSubset</code> rule. */
  @Override
  public void exitKeyValueSubset(KeyValueSubsetContext ctx) {
    final String name = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySubset(name, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSynonym</code> rule. */
  @Override
  public void exitKeyValueSynonym(KeyValueSynonymContext ctx) {
    final String text = OBOEscapeUtils.unescape(ctx.QuotedString().getText());
    final TermSynonymScope termSynonymScope =
        TermSynonymScope.valueOf(ctx.ScopeIdentifier().getText());
    final String scopeTypeName = OBOEscapeUtils.unescape(trimmedEmptyToNull(ctx.extWord()));
    final DBXRefList dbXRefList = (DBXRefList) getValue(ctx.dbXRefList());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySynonym(text, termSynonymScope, scopeTypeName, dbXRefList,
        trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueXRef</code> rule. */
  @Override
  public void exitKeyValueXRef(KeyValueXRefContext ctx) {
    final DBXRef dbXRef = (DBXRef) getValue(ctx.dbXRef());
    final TrailingModifier trailingModifier = dbXRef.getTrailingModifier();
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryXRef(dbXRef, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsA</code> rule. */
  @Override
  public void exitKeyValueIsA(KeyValueIsAContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.extWord().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsA(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIntersectionOf</code> rule. */
  @Override
  public void exitKeyValueIntersectionOf(KeyValueIntersectionOfContext ctx) {
    final String relationshipType;
    final String id;
    if (ctx.extWord().size() == 1) {
      relationshipType = null;
      id = OBOEscapeUtils.unescape(ctx.extWord(0).getText());
    } else {
      relationshipType = OBOEscapeUtils.unescape(ctx.extWord(0).getText());
      id = OBOEscapeUtils.unescape(ctx.extWord(1).getText());
    }
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIntersectionOf(relationshipType, id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueUnionOf</code> rule. */
  @Override
  public void exitKeyValueUnionOf(KeyValueUnionOfContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.extWord().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryUnionOf(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDisjointFrom</code> rule. */
  @Override
  public void exitKeyValueDisjointFrom(KeyValueDisjointFromContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.extWord().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDisjointFrom(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueRelationship</code> rule. */
  @Override
  public void exitKeyValueRelationship(KeyValueRelationshipContext ctx) {
    final String relationshipType;
    final List<String> ids;
    if (ctx.extWord().size() == 2) {
      relationshipType = OBOEscapeUtils.unescape(ctx.extWord(0).getText());
      ids = Lists.newArrayList(OBOEscapeUtils.unescape(ctx.extWord(1).getText()));
    } else {
      relationshipType = OBOEscapeUtils.unescape(ctx.extWord(0).getText());
      ids = ctx.extWord().subList(1, ctx.extWord().size()).stream()
          .map(t -> OBOEscapeUtils.unescape(t.getText())).collect(Collectors.toList());
    }
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryRelationship(relationshipType, ids, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsObsolete</code> rule. */
  @Override
  public void exitKeyValueIsObsolete(KeyValueIsObsoleteContext ctx) {
    final boolean isObsolete = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsObsolete(isObsolete, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueReplacedBy</code> rule. */
  @Override
  public void exitKeyValueReplacedBy(KeyValueReplacedByContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryReplacedBy(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueConsider</code> rule. */
  @Override
  public void exitKeyValueConsider(KeyValueConsiderContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryConsider(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueCreatedBy</code> rule. */
  @Override
  public void exitKeyValueCreatedBy(KeyValueCreatedByContext ctx) {
    final String creator = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryCreatedBy(creator, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueCreationDate</code> rule. */
  @Override
  public void exitKeyValueCreationDate(KeyValueCreationDateContext ctx) {
    final String date = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryCreationDate(date, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueGeneric</code> rule. */
  @Override
  public void exitKeyValueGeneric(KeyValueGenericContext ctx) {
    final String tag = OBOEscapeUtils.unescape(ctx.GenericStanzaTag().getText());
    final String text = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryGeneric(tag, text, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDomain</code> rule. */
  @Override
  public void exitKeyValueDomain(KeyValueDomainContext ctx) {
    final String value = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDomain(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueRange</code> rule. */
  @Override
  public void exitKeyValueRange(KeyValueRangeContext ctx) {
    final String value = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryRange(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueInverseOf</code> rule. */
  @Override
  public void exitKeyValueInverseOf(KeyValueInverseOfContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryInverseOf(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueTransitiveOver</code> rule. */
  @Override
  public void exitKeyValueTransitiveOver(KeyValueTransitiveOverContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryTransitiveOver(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsCyclic</code> rule. */
  @Override
  public void exitKeyValueIsCyclic(KeyValueIsCyclicContext ctx) {
    final boolean isCyclic = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsCyclic(isCyclic, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsReflexive</code> rule. */
  @Override
  public void exitKeyValueIsReflexive(KeyValueIsReflexiveContext ctx) {
    final boolean isReflexive = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsReflexive(isReflexive, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsSymmetric</code> rule. */
  @Override
  public void exitKeyValueIsSymmetric(KeyValueIsSymmetricContext ctx) {
    final boolean isSymmetric = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsSymmetric(isSymmetric, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsAntisymmetric</code> rule. */
  @Override
  public void exitKeyValueIsAntisymmetric(KeyValueIsAntisymmetricContext ctx) {
    final boolean isAntisymmetric = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsAntisymmetric(isAntisymmetric, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsTransitive</code> rule. */
  @Override
  public void exitKeyValueIsTransitive(KeyValueIsTransitiveContext ctx) {
    final boolean isTransitive = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsTransitive(isTransitive, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIsMetadata</code> rule. */
  @Override
  public void exitKeyValueIsMetadata(KeyValueIsMetadataContext ctx) {
    final boolean isMetadata = ctx.BooleanValue().getText().equals("true");
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIsMetadata(isMetadata, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueInstanceOf</code> rule. */
  @Override
  public void exitKeyValueInstanceOf(KeyValueInstanceOfContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryInstanceOf(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueFormatVersion</code> rule. */
  @Override
  public void exitKeyValueFormatVersion(KeyValueFormatVersionContext ctx) {
    final String value = ctx.stringValue().getText();
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryFormatVersion(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDataVersion</code> rule. */
  @Override
  public void exitKeyValueDataVersion(KeyValueDataVersionContext ctx) {
    final String value = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDataVersion(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDate</code> rule. */
  @Override
  public void exitKeyValueDate(KeyValueDateContext ctx) {
    final String value = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDate(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSavedBy</code> rule. */
  @Override
  public void exitKeyValueSavedBy(KeyValueSavedByContext ctx) {
    final String value = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySavedBy(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueAutogeneratedBy</code> rule. */
  @Override
  public void exitKeyValueAutoGeneratedBy(KeyValueAutoGeneratedByContext ctx) {
    final String creator = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryAutoGeneratedBy(creator, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSubsetdef</code> rule. */
  @Override
  public void exitKeyValueSubsetdef(KeyValueSubsetdefContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.extWord().getText());
    final String description = OBOEscapeUtils.unescape(ctx.QuotedString().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySubsetdef(id, description, trailingModifier, comment));
  }

  /** Called on entering <code>keyValueImport</code> rule. */
  @Override
  public void exitKeyValueImport(KeyValueImportContext ctx) {
    final String value = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryImport(value, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueSynonymTypedef</code> rule. */
  @Override
  public void exitKeyValueSynonymtypedef(KeyValueSynonymtypedefContext ctx) {
    final String synonymTypeName = OBOEscapeUtils.unescape(ctx.extWord().getText());
    final String description = OBOEscapeUtils.unescape(ctx.QuotedString().getText());
    final TermSynonymScope termSynonymScope;
    if (ctx.ScopeIdentifier() != null) {
      termSynonymScope = TermSynonymScope.valueOf(ctx.ScopeIdentifier().getText());
    } else {
      termSynonymScope = null;
    }
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntrySynonymtypedef(synonymTypeName, description, termSynonymScope,
        trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueDefaultRelationshipIdspace</code> rule. */
  @Override
  public void exitKeyValueIdspace(KeyValueIdspaceContext ctx) {
    final String localIDSpace = OBOEscapeUtils.unescape(ctx.extWord(0).getText());
    final String remoteIDSpace = OBOEscapeUtils.unescape(ctx.extWord(1).getText());
    final String description;
    if (ctx.QuotedString() != null) {
      description = OBOEscapeUtils.unescape(ctx.QuotedString().getText());
    } else {
      description = null;
    }
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIdspace(localIDSpace, remoteIDSpace, description, trailingModifier,
        comment));
  }

  /** Called on leaving <code>keyValueDefaultRelationshipPrefix</code> rule. */
  @Override
  public void exitKeyValueDefaultRelationshipIdPrefix(
      KeyValueDefaultRelationshipIdPrefixContext ctx) {
    final String id = OBOEscapeUtils.unescape(ctx.extWord().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryDefaultRelationshipIdPrefix(id, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueIdMapping</code> rule. */
  @Override
  public void exitKeyValueIdMapping(KeyValueIdMappingContext ctx) {
    final String sourceID = OBOEscapeUtils.unescape(ctx.extWord(0).getText());
    final String targetID = OBOEscapeUtils.unescape(ctx.extWord(1).getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryIDMapping(sourceID, targetID, trailingModifier, comment));
  }

  /** Called on leaving <code>keyValueRemark</code> rule. */
  @Override
  public void exitKeyValueRemark(KeyValueRemarkContext ctx) {
    final String text = OBOEscapeUtils.unescape(ctx.stringValue().getText());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());
    final String comment = trimmedEmptyToNull(ctx.eolComment2());

    setValue(ctx, new StanzaEntryRemark(text, trailingModifier, comment));
  }

  /**
   * Called on leaving <code>dbXRefList</code> rule, construct {@link DBXRef} object.
   */
  @Override
  public void exitDbXRefList(DbXRefListContext ctx) {
    final DBXRefList dbXRefList = new DBXRefList();
    for (DbXRefContext xrcCtx : ctx.dbXRef()) {
      dbXRefList.addDBXRef((DBXRef) getValue(xrcCtx));
    }

    setValue(ctx, dbXRefList);
  }

  /**
   * Called on leaving <code>dbXRef</code> rule, construct {@link DBXRef} object.
   */
  @Override
  public void exitDbXRef(DbXRefContext ctx) {
    final String name = OBOEscapeUtils.unescape(ctx.extWord().getText());
    final String description = (ctx.QuotedString() == null)
        ? null
        : OBOEscapeUtils.unescape(ctx.QuotedString().getText().trim());
    final TrailingModifier trailingModifier = (TrailingModifier) getValue(ctx.trailingModifier());

    setValue(ctx, new DBXRef(name, description, trailingModifier));
  }

  /**
   * Called on leaving <code>trailingModifier</code> rule, construct {@link TrailingModifier}
   * object.
   */
  @Override
  public void exitTrailingModifier(TrailingModifierContext ctx) {
    final TrailingModifier tm = new TrailingModifier();
    for (TrailingModifierKeyValueContext kvCtx : ctx.trailingModifierKeyValue()) {
      if (kvCtx.QuotedString() != null) {
        tm.addKeyValue(OBOEscapeUtils.unescape(kvCtx.Word(0).getText().trim()),
            OBOEscapeUtils.unescape(kvCtx.QuotedString().getText().trim()));
      } else {
        tm.addKeyValue(OBOEscapeUtils.unescape(kvCtx.Word(0).getText().trim()),
            OBOEscapeUtils.unescape(kvCtx.Word(1).getText().trim()));
      }
    }

    setValue(ctx, tm);
  }

  /**
   * Convert <code>null</code> and (trimmed) empty string to <code>null</code>.
   * 
   * @param s <code>String</code> to convert
   * @return Trimmed value of <code>s</code> or <code>null</code>.
   */
  private static String trimmedEmptyToNull(String s) {
    if (s == null) {
      return null;
    } else {
      s = s.trim();
      if (s.isEmpty()) {
        return null;
      } else {
        return s.trim();
      }
    }
  }

  /** Overload of {@link #trimmedEmptyToNull(String)} that handles {@link EolComment2Context}. */
  private static String trimmedEmptyToNull(EolComment2Context x) {
    if (x == null || x.Comment2() == null) {
      return null;
    } else {
      final String text = x.Comment2().getText();
      final int pos = text.indexOf('!');
      return trimmedEmptyToNull(text.substring(pos + 1, text.length()).trim());
    }
  }

  /** Overload of {@link #trimmedEmptyToNull(String)} that handles {@link ExtWordContext}. */
  private static String trimmedEmptyToNull(ExtWordContext n) {
    if (n == null) {
      return null;
    } else {
      return trimmedEmptyToNull(n.getText());
    }
  }

}