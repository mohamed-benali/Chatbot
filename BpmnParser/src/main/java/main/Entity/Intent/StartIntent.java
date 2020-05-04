package main.Entity.Intent;

import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
import main.Enums.DefaultTrainingPhraseType;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.List;

public class StartIntent extends myIntent {
    public static String BEGIN_CONTEXT = "BEGIN_CONTEXT"; // TODO: Replicated on "BeginIntent" class
                                                          // Should make some type of global access(Singleton)

    public StartIntent(String name, String subject, String tasca) throws IOException {
        super(name, subject, tasca);
        this.addInputContextID(BEGIN_CONTEXT);
        this.addTrainingPhrase(subject);
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
