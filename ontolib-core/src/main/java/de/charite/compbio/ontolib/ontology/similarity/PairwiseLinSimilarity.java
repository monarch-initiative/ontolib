package de.charite.compbio.ontolib.ontology.similarity;

import de.charite.compbio.ontolib.ontology.data.Term;
import de.charite.compbio.ontolib.ontology.data.TermID;
import de.charite.compbio.ontolib.ontology.data.TermRelation;
import java.util.Map;

/**
 * Implementation of pairwise Lin similarity.
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 * @author <a href="mailto:sebastian.koehler@charite.de">Sebastian Koehler</a>
 */
final class PairwiseLinSimilarity<T extends Term, R extends TermRelation>
    implements
      PairwiseSimilarity {

  /** Internally used pairwise Resnik similarity. */
  private final PairwiseResnikSimilarity<T, R> pairwiseResnik;

  /**
   * Construct with inner {@link PairwiseResnikSimilarity}.
   * 
   * @param pairwiseResnik Inner {@link PairwiseResnikSimilarity} to use.
   */
  public PairwiseLinSimilarity(PairwiseResnikSimilarity<T, R> pairwiseResnik) {
    this.pairwiseResnik = pairwiseResnik;
  }

  @Override
  public double computeScore(TermID query, TermID target) {
    final Map<TermID, Double> termToIC = pairwiseResnik.getTermToIC();
    final double resnikScore = this.pairwiseResnik.computeScore(query, target);
    return (2.0 * resnikScore) / (termToIC.get(query) + termToIC.get(target));
  }

}