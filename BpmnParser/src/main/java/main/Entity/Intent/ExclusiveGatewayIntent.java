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

public class ExclusiveGatewayIntent extends myIntent {
    public ExclusiveGatewayIntent() throws IOException {
    }

    public ExclusiveGatewayIntent(String name) throws IOException {
        super(name);
    }

    public ExclusiveGatewayIntent(String name, String subject, String task) throws IOException {
        super(name, subject, task);
    }

    protected List<String> makeResponse() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        List<String> responses = new ArrayList<String>();

        String response = this.getTask();

        responses.add(response);
        return responses;
    }



    @Override
    protected Intents buildExtraIntents() {
        return null;
    }

    @Override
    protected Sentences buildTrainingPhrases() {
        return null;
    }

    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {
        return this.getTrainingPhrases().getTrainingPhrasesList(); // TODO: Pillar el null com a Next?
    }
}
