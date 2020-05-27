package main.Entity.Intent.Intent.TrainingPhrases;

import main.Enums.DefaultTrainingPhraseType;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import org.apache.ibatis.jdbc.Null;

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



    //region REGION: Override(toString, equals) + copy
    @Override
    public String toString() {
        String result = "";
        result += "Has Null Training Phrase: " + hasNullTrainingPhrase;
        result += "\n";
        if(getDefaultTrainingPhraseType()!=null) result += getDefaultTrainingPhraseType().toString();
        else result += "Training Phrase Type: " + getDefaultTrainingPhraseType();
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

    public NullTrainingPhrase copy() {
        NullTrainingPhrase copy = new NullTrainingPhrase();
        copy.setHasNullTrainingPhrase(this.hasNullTrainingPhrase());
        copy.setDefaultTrainingPhraseType(this.getDefaultTrainingPhraseType());
        return copy;
    }
    //endregion
}
