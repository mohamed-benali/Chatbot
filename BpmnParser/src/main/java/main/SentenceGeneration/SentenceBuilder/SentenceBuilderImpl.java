package main.SentenceGeneration.SentenceBuilder;

import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceAnalyzer.FreelingSimpleSentenceAnalyzerImpl;
import main.SentenceGeneration.SentenceAnalyzer.SentenceAnalyzer;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceGeneratorServiceAdapter.SentenceGeneratorServiceAdapter;
import main.SentenceGeneration.SentenceGeneratorServiceAdapter.SentenceGenerator_SimpleNLG_AdapterImpl;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceParaphraser.SentenceParaphraser;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChief_SentenceParaphraserImpl;

import java.io.IOException;
import java.util.List;

public class SentenceBuilderImpl implements SentenceBuilder {


    SentenceAnalyzer sentenceAnalyzer;
    SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter;
    SentenceParaphraser sentenceParaphraser;


    public SentenceBuilderImpl() {
        sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();
        sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        sentenceParaphraser = new SpinnerChief_SentenceParaphraserImpl();
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
    public ParaphrasedSentences paraphraseSentences(List<String> trainingPhrasesToParaphrase) throws InterruptedException, SpinnerChief_SentenceParaphraserException, IOException {
        return sentenceParaphraser.paraphraseSentence(trainingPhrasesToParaphrase);
    }


}
