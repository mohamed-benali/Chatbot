package main.Entity.Intent.Intent.QueryIntent;

import main.Entity.Intent.Intent.TaskIntent;
import main.Entity.Intent.Intent.myIntent;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WhatObjectQueryTaskIntent extends QueryTaskIntent {
    public WhatObjectQueryTaskIntent(String name, String subject, String tasca) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        super(name+"_WHAT_OBJECT", subject, tasca);

        // TODO: Create here the training Sentences? For now here
        String whatObjectSentence_workAround_without_does = this.getSentenceBuilder().buildWhatObjectSentence_WorkAround_Without_Does(this.getTask(), subject);
        this.getTrainingPhrases().addTrainingPhrase(whatObjectSentence_workAround_without_does);

        String whatObjectSentence = this.getSentenceBuilder().buildWhatObjectSentence(this.getTask(), subject);
        this.getTrainingPhrases().addTrainingPhrase(whatObjectSentence);

        // TODO: Create here the sentenceAnalysis? For now here
        SentenceAnalysis sentenceAnalysis =this.getSentenceAnalyzer().analyzeSentence(tasca);
        this.setSentenceAnalysis(sentenceAnalysis);
    }

    @Override
    protected myIntent createBasicIntentInstance(String name, String subject, String task) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        return new WhatObjectQueryTaskIntent(name, subject, task);
    }

    @Override
    public List<String> makeResponse() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException { // TODO: Better responses Make better responses, Subject Verb bla bla bla?
        List<String> responses = new ArrayList<>();
        /*
        String response = this.getSentenceAnalysis().getObject();
        responses.add(response);*/
        String subject = this.getSubject();
        String response = this.getSentenceBuilder().buildSentence(this.getTask(), subject);
        responses.add(response);

        return responses;
    }

    @Override
    protected Sentences buildTrainingPhrasesToParaphrase() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        Sentences sentences = new Sentences();
        sentences.addSentences(this.getTrainingPhrases().getTrainingPhrasesList());
        return sentences;
    }

    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
        this.getTrainingPhrases().updateTrainingPhrases(paraphrasedSentences);
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {
        this.setDefaultNullTrainingPhrase();
        return this.getTrainingPhrases().getBuildedTrainingPhrases();
    }

    @Override
    protected void setDefaultNullTrainingPhrase() {
        getTrainingPhrases().setHasNullTrainingPhrase(false);
        getTrainingPhrases().setDefaultTrainingPhraseType(null);
    }
}
