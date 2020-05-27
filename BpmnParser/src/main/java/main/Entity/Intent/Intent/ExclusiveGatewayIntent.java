package main.Entity.Intent.Intent;

import main.Entity.Intent.Intent.myIntent;
import main.Entity.Intent.Intents;
import main.Enums.DefaultTrainingPhraseType;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExclusiveGatewayIntent extends myIntent {
    public ExclusiveGatewayIntent() throws IOException {
    }

    public ExclusiveGatewayIntent(String name) throws IOException {
        super(name);
    }

    public ExclusiveGatewayIntent(String name, String subject, String task) throws IOException {
        super(name, subject, task);
    }

    @Override
    protected myIntent createBasicIntentInstance(String name, String subject, String task) throws IOException {
        return new ExclusiveGatewayIntent(name, subject, task);
    }



    protected List<String> makeResponse() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        List<String> responses = new ArrayList<String>();

        String response = this.getTask();

        responses.add(response);
        return responses;
    }



    @Override
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
        trainingPhrases.setDefaultTrainingPhraseType(DefaultTrainingPhraseType.NEXT_TYPE);
    }
}
