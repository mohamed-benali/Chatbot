package main.Entity.Intent.TrainingPhrases;

import main.Enums.DefaultTrainingPhraseType;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.util.List;


public class NullTrainingPhrase {
    private boolean hasNullTrainingPhrase;
    private DefaultTrainingPhraseType defaultTrainingPhraseType;

    public NullTrainingPhrase() {
        setHasNullTrainingPhrase(false);
        setDefaultTrainingPhraseType(null);
    }


    public boolean hasNullTrainingPhrase() { return hasNullTrainingPhrase; }
    public void setHasNullTrainingPhrase(boolean hasNullTrainingPhrase) { this.hasNullTrainingPhrase = hasNullTrainingPhrase; }

    public DefaultTrainingPhraseType getDefaultTrainingPhraseType() { return defaultTrainingPhraseType; }
    public void setDefaultTrainingPhraseType(DefaultTrainingPhraseType defaultTrainingPhraseType) { this.defaultTrainingPhraseType = defaultTrainingPhraseType; }


    public List<String> getTrainingPhrases() {
        Sentences sentences = new Sentences();
        if(hasNullTrainingPhrase()) sentences.addSentences(myTrainingPhrase.getDefaultTrainingPhrases(getDefaultTrainingPhraseType()));
        return sentences.getSentencesList();
    }



    //region REGION: Override(toString, equals)
    @Override
    public String toString() {
        String result = "";
        result += "Has Null Training Phrase: " + hasNullTrainingPhrase;
        result += "\n";
        result += getDefaultTrainingPhraseType().toString();
        result += "\n";
        result += "\n";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NullTrainingPhrase) {
            NullTrainingPhrase otherNullTrainingPhrase = (NullTrainingPhrase) o;
            if (this.hasNullTrainingPhrase() == otherNullTrainingPhrase.hasNullTrainingPhrase() &&
                    this.getDefaultTrainingPhraseType() == otherNullTrainingPhrase.getDefaultTrainingPhraseType() )
            {
                return true;
            }
        }
        return false;
    }
    //endregion
}
