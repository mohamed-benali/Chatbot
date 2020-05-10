package main.SentenceGeneration.SentenceGeneratorServiceAdapter;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.Exceptions.NoFreelingKeyException;

public interface SentenceGeneratorServiceAdapter {
    String generateSimpleSentence(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException;

    String generateWhoSubjectSentence(SentenceAnalysis sentenceAnalysis) throws NoFreelingKeyException;

    String generateWhatObjectSentence(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException;


    /**
     * WorkAround analisis of WhatObject(the workaround is that its used What_Subject instead of What_object, and
     * the object and the subject are swapped. Therefore, does is not included
     * @param sentenceAnalysis Analisis of "Send the documents"
     * @param subject "Mary"
     * @return What sends Mary?
     * @throws NoFreelingKeyException
     */
    String generateWhatObjectSentence_WorkAround_Without_Does(SentenceAnalysis sentenceAnalysis, String subject) throws NoFreelingKeyException;
}
