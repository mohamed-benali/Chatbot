package main.Entity.Intent;

import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
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

        this.addTrainingPhrase("Next");
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
    protected Intents buildExtraIntents() {
        return null;
    }

    // TODO
    protected Sentences buildTrainingPhrases() {
        /*
        Sentences sentences = new Sentences();
        sentences.addSentences(this.getTrainingPhrases().getTrainingPhrasesList());
        if(this.getTrainingPhrases().hasNullTrainingPhrase()) {
            Sentences nextSentences = myTrainingPhrase.getNextTrainingPhrases();
            sentences.addSentences(nextSentences);
        }
        return sentences;
        */
         return null;
    }


    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {
        return makeResponse();
    }
}
