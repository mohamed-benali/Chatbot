package main.Entity.Intent;

import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.List;

public class TaskIntent extends myIntent {
    public TaskIntent(String name, String subject, String tasca) throws IOException {
        super(name, subject, tasca);
    }

    @Override // TODO: Query
    protected Intents buildExtraIntents() {
        return new Intents();
    }

    @Override
    protected Sentences buildTrainingPhrases() {
        Sentences sentences = new Sentences();
        sentences.addSentences(this.getTrainingPhrases().getTrainingPhrasesList());
        if(this.getTrainingPhrases().hasNullTrainingPhrase()) {
            Sentences nextSentences = myTrainingPhrase.getNextTrainingPhrases();
            sentences.addSentences(nextSentences);
        }
        return sentences;
    }

    @Override
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
        this.getTrainingPhrases().updateTrainingPhrases(paraphrasedSentences);
    }

    @Override
    protected List<String> getBuildedTrainingPhrases() {

    }
}
