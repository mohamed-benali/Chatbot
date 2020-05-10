package main.SentenceGeneration.SentenceBuilder;

import main.SentenceGeneration.SentenceAnalyzer.FreelingSimpleSentenceAnalyzerImpl;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceAnalyzer.SentenceAnalyzer;
import main.SentenceGeneration.SentenceGeneratorServiceAdapter.SentenceGeneratorServiceAdapter;
import main.SentenceGeneration.SentenceGeneratorServiceAdapter.SentenceGenerator_SimpleNLG_AdapterImpl;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;


import java.io.IOException;
import java.util.List;

public class SentenceBuilderImpl implements SentenceBuilder {


    SentenceAnalyzer sentenceAnalyzer;
    SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter;

    /**
     * Default constructor for sentenceAnalyzer: FreelingSimpleSentenceAnalyzerImpl()<br>
     * Default constructor for SentenceGeneratorServiceAdapter: SentenceGenerator_SimpleNLG_AdapterImpl()
     */
    public SentenceBuilderImpl() {
        sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();
        sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
    }

    /**
     * Custom constructor. It will use the parameters to define the behaviour
     * @param sentenceAnalyzer sentence Analyzer that will be used
     * @param sentenceGeneratorServiceAdapter Sentence Generator that will be used
     */
    public SentenceBuilderImpl(SentenceAnalyzer sentenceAnalyzer,
                               SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter) {
        this.sentenceAnalyzer = sentenceAnalyzer;
        this.sentenceGeneratorServiceAdapter = sentenceGeneratorServiceAdapter;
    }

    @Override
    public String buildSentence(String sentence, String subject) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        SentenceAnalysis sentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);
        String generatedSentence = sentenceGeneratorServiceAdapter.generateSimpleSentence(sentenceAnalysis, subject);
        return generatedSentence;
    }


    @Override
    public String buildSentence(String sentence, String sourceSubject, String targetSubject) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {

        String joinedSentence = sentence + " to the " + targetSubject;

        SentenceAnalysis sentenceAnalysis = sentenceAnalyzer.analyzeSentence(joinedSentence);
        String generatedSentence = sentenceGeneratorServiceAdapter.generateSimpleSentence(sentenceAnalysis, sourceSubject);
        return generatedSentence;
    }


    @Override
    public String buildWhoSubjectSentence(String sentence) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        SentenceAnalysis sentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);
        String generatedSentence = sentenceGeneratorServiceAdapter.generateWhoSubjectSentence(sentenceAnalysis);
        return generatedSentence;
    }

    @Override
    public String buildWhatObjectSentence(String sentence, String subject ) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        SentenceAnalysis sentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);
        String generatedSentence = sentenceGeneratorServiceAdapter.generateWhatObjectSentence(sentenceAnalysis, subject);
        return generatedSentence;
    }

    @Override
    public String buildWhatObjectSentence_WorkAround_Without_Does(String sentence, String subject) throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        SentenceAnalysis sentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);
        String generatedSentence = sentenceGeneratorServiceAdapter.generateWhatObjectSentence_WorkAround_Without_Does(sentenceAnalysis, subject);
        return generatedSentence;
    }


}
