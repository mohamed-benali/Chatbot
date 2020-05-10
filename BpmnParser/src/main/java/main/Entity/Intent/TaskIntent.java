package main.Entity.Intent;

import main.Entity.Intent.QueryIntent.QueryTaskIntent;
import main.Entity.Intent.QueryIntent.WhatObjectQueryTaskIntent;
import main.Entity.Intent.QueryIntent.WhoSubjectQueryTaskIntent;
import main.Enums.DefaultTrainingPhraseType;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.List;

public class TaskIntent extends myIntent {
    public TaskIntent(String name, String subject, String tasca) throws IOException {
        super(name, subject, tasca);
    }

    @Override // TODO: other type of Querys
    public Intents buildExtraIntents() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        Intents intents = new Intents();

        String id = this.getId();
        String task = this.getTask();
        String subject = this.getSubject();

        myIntent whoSubjectQueryTaskIntent = new WhoSubjectQueryTaskIntent(id, subject, task);
        intents.add(whoSubjectQueryTaskIntent);

        myIntent whatObjectQueryTaskIntent = new WhatObjectQueryTaskIntent(id, subject, task);
        intents.add(whatObjectQueryTaskIntent);

        return intents;
    }

    @Override
    protected Sentences buildTrainingPhrasesToParaphrase() {
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
        trainingPhrases.setDefaultTrainingPhraseType(DefaultTrainingPhraseType.NEXT_TYPE);
    }
}
