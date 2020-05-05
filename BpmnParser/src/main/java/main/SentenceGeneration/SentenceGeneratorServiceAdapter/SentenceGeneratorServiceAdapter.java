package main.SentenceGeneration.SentenceGeneratorServiceAdapter;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.Exceptions.NoFreelingKeyException;

public interface SentenceGeneratorServiceAdapter {
    String generateSimpleSentence(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException;

    String generateWhoSubjectSentence(SentenceAnalysis sentenceAnalysis) throws NoFreelingKeyException;

    String generateWhatObjectSentence(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException;
}
