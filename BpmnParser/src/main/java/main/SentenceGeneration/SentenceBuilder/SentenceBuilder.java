package main.SentenceGeneration.SentenceBuilder;

import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;

import java.io.IOException;
import java.util.List;

public interface SentenceBuilder {


    /**
     *
     * @param sentence The sentence without the subject
     * @param subject The subject
     * @return Returns a sentence where the {@code sentence} and {@code subject} are joined
     */
    String buildSentence(String sentence, String subject) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException;

    /**
     *
     * @param sentence The sentence without the subject
     * @param sourceSubject Source Subject: Will act as the subject
     * @param targetSubject Target Subject: Will act as the complement
     * @return Returns a sentence where the {@code sentence} and {@code targetSubject} are joined with {@code sourceSubject}
     */
    String buildSentence(String sentence, String sourceSubject, String targetSubject) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException;


    /**
     * Builds a question where is asked who does what is done in the {@code sentence} passed as a parameter<br>
     * <br>
     * Specifically, the function analyzes the sentence, and builds the question.<br>
     * <br>
     *  For example, if {@code sentence} is "chase the monkey"
     *  the method returns "Who chases the monkey?"
     * @param sentence The sentence without the subject
     * @return Returns a sentence where is asked who does what is done in the {@code sentence} passed as a parameter
     */
    String buildWhoSubjectSentence(String sentence) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException;

    /**
     * Builds a question where is asked what does the subject<br>
     * <br>
     * Specifically, the function analyzes the sentence, and builds the question.<br>
     * <br>
     * @param sentence The sentence without the subject
     * @param subject The subject
     * @return Returns a sentence where is asked what does the subject
     * @throws InterruptedException
     * @throws SentenceAnalyzerException
     * @throws NoFreelingKeyException
     * @throws IOException
     */
    String buildWhatObjectSentence(String sentence, String subject) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException;


    /**
     * WorkAround analisis of WhatObject(the workaround is that its used What_Subject instead of What_object, and
     * the object and the subject are swapped. Therefore, does is not included.
     * @param sentence Ex: "Send the documents"
     * @param subject Ex: "Mary"
     * @return "What sends Mary?"
     */
    String buildWhatObjectSentence_WorkAround_Without_Does(String sentence, String subject) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException;
}
