package main.Entity.Intent;

import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
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
        return getTrainingPhrases().getTrainingPhrasesList();
    }
}
