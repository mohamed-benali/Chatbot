package main.SentenceGeneration.SentenceGeneratorServiceAdapter;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.Exceptions.NoFreelingKeyException;

public interface SentenceGeneratorServiceAdapter {
    String generateSimpleSentence(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException;
}
