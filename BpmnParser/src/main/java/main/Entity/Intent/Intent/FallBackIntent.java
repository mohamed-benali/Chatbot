package main.Entity.Intent.Intent;

import main.Entity.Intent.Intent.myIntent;
import main.Entity.Intent.Intents;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FallBackIntent extends myIntent {
    public static String DEFAULT_FALLBACK_INTENT = "DEFAULT_FALLBACK_INTENT";

    public FallBackIntent() throws IOException { super(DEFAULT_FALLBACK_INTENT); }
    public FallBackIntent(String name) throws IOException { super(name+"_FALLBACK_INTENT"); }



    @Override
    public String createCode() {
        String varName = "defaultFallbackIntent" + this.getName();
        StringBuilder response = new StringBuilder();
        /*
        response.append("FallBackIntent ").append(varName).append(" = new FallBackIntent(")
                .append(quote).append(getName()).append(quote).append(");").append("\n");*/
        response.append("FallBackIntent ").append(varName).append(" = new FallBackIntent();").append("\n");
        return response.toString();
    }


    protected List<String> makeResponse() {
        List<String> responses = new ArrayList<String>();
        String response = "Sorry, i didn't understand";
        responses.add(response);

        return responses;
    }

    @Override
    public boolean isFallbackIntent() {
        return true;
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
        return new ArrayList<>();
    }

    @Override
    protected void setDefaultNullTrainingPhrase() {
        trainingPhrases.setDefaultTrainingPhraseType(null);
        trainingPhrases.setHasNullTrainingPhrase(false);
    }
}
