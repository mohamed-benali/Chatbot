package SentenceGeneration.SentenceGeneratorServiceAdapter;

import SentenceGeneration.Entity.SentenceAnalysis.SentenceAnalysis;

public interface SentenceGeneratorServiceAdapter {
    String generateSimpleSentence(SentenceAnalysis sentenceAnalysis, String subject);
}
