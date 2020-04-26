package main.SentenceGeneration.SentenceBuilder;

import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;

import java.io.IOException;

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
}
