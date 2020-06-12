package main.Entity.Parser.BpmnAlgorithm;

import main.Entity.Intent.Intent.myIntent;
import main.Entity.Intent.Intents;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BackIntent extends myIntent {
    public static String BACK_INTENT = "BACK_INTENT";
    public static String TRAINING_PHRASE = "BACK";


    public BackIntent() throws IOException {
        super(BACK_INTENT);
        this.addTrainingPhrase(TRAINING_PHRASE);
    }



    public BackIntent(String name) throws IOException { super(name+"_BACK_INTENT"); }





    @Override
    public String createCode() {
        String varName = "backIntent" + this.getName();
        StringBuilder response = new StringBuilder();
        /*
        response.append("FallBackIntent ").append(varName).append(" = new FallBackIntent(")
                .append(quote).append(getName()).append(quote).append(");").append("\n");*/
        response.append("BackIntent ").append(varName).append(" = new BackIntent();").append("\n");
        return response.toString();
    }


    protected List<String> makeResponse() {
        List<String> responses = new ArrayList<String>();
        String response = "This message should not appear when using the server. Instead, should appear the context before.";
        responses.add(response);

        return responses;
    }

    @Override
    public boolean isFallbackIntent() {
        return false;
    }



    @Override
    public Intents buildExtraIntents() {
        return new Intents();
    }

    @Override
    protected Sentences buildTrainingPhrasesToParaphrase() {
        return new Sentences();
    }

    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {
        return getTrainingPhrases().getTrainingPhrasesList();
    }

    @Override
    protected void setDefaultNullTrainingPhrase() {
        trainingPhrases.setDefaultTrainingPhraseType(null);
        trainingPhrases.setHasNullTrainingPhrase(false);
    }
}
