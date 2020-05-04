package main.Entity.Intent;

import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
import main.Enums.DefaultTrainingPhraseType;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceBuilder.SentenceBuilder;
import main.SentenceGeneration.SentenceBuilder.SentenceBuilderImpl;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollaborationIntent extends myIntent {

    private String sourceSubject;
    private String targetSubject;

    public CollaborationIntent(String name, String sourceSubject, String targetSubject, String task) throws IOException {
        super(name);
        this.setTask(task);

        this.setSourceSubject(sourceSubject);
        this.setTargetSubject(targetSubject);

        //this.addOutputContextID(BEGIN_CONTEXT);

        //this.addTrainingPhrase("Next"); // S'indica en el metode setDefaultNullTrainingPhrase, d'aquesta classe
        setDefaultNullTrainingPhrase();
    }

    public String getSourceSubject() { return sourceSubject; }
    public void setSourceSubject(String sourceSubject) { this.sourceSubject = sourceSubject; }

    public String getTargetSubject() { return targetSubject; }
    public void setTargetSubject(String targetSubject) { this.targetSubject = targetSubject; }



    public void print() {
        println("INTENT");
        println("Name:    " + this.getName());
        println("Subject Source: " + this.getSourceSubject());
        println("Subject Target: " + this.getTargetSubject());
        println("Task:    " + this.getTask());
        println("Input Intents:");
        printIDs(inputContexts);
        println("Output Intents:");
        printIDs(outputIntents);
        println("Training Phrases:");
        println(trainingPhrases.toString());

        println("");
        println("");
    }

    protected List<String> makeResponse() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        List<String> responses = new ArrayList<String>();

        String sentence = this.getTask();
        String sourceSubject = this.getSourceSubject();
        String targetSubject = this.getTargetSubject();

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl();
        String response = sentenceBuilder.buildSentence(sentence, sourceSubject, targetSubject);

        responses.add(response);
        return responses;
    }



    @Override // TODO  Query
    public Intents buildExtraIntents() {
        return new Intents();
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
        trainingPhrases.setHasNullTrainingPhrase(true);
        trainingPhrases.setDefaultTrainingPhraseType(DefaultTrainingPhraseType.NEXT_TYPE);
    }
}
