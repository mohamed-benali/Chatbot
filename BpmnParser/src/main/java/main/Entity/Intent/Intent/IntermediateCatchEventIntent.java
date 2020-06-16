package main.Entity.Intent.Intent;


import main.Entity.Intent.Intents;
import main.Entity.Intent.Intent.QueryIntent.WhatObjectQueryTaskIntent;
import main.Entity.Intent.Intent.QueryIntent.WhoSubjectQueryTaskIntent;
import main.Enums.DefaultTrainingPhraseType;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.io.IOException;
import java.util.List;

public class IntermediateCatchEventIntent extends myIntent {
    public IntermediateCatchEventIntent(String name, String subject, String tasca) throws IOException {
        super(name, subject, tasca);
    }

    @Override
    protected myIntent createBasicIntentInstance(String name, String subject, String task) throws IOException {
        return new IntermediateCatchEventIntent(name, subject, task);
    }

    @Override // TODO: other type of Querys
    public Intents buildExtraIntents(BpmnModelInstance modelInstance) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        Intents intents = new Intents();

        String id = this.getId();
        String task = this.getTask();
        String subject = this.getSubject();

        intents.add(super.buildExtraIntents(modelInstance));

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
