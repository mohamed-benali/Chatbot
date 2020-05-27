package main.Entity.Intent.Intent;

import main.Entity.Intent.Intents;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
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
    protected myIntent createBasicIntentInstance(String name, String subject, String task) throws IOException {
        return new StartIntent(name, subject, task);
    }


    @Override
    public Intents buildExtraIntents() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        Intents intents = new Intents();
        intents.add(super.buildExtraIntents());
        return intents;
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
