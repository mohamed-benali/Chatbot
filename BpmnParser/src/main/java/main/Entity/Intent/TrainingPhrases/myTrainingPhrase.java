package main.Entity.Intent.TrainingPhrases;

import main.Entity.Intent.myIntent;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.util.List;

public class myTrainingPhrase { // TODO: Podria ser una sub clase de sentence

    private Sentence sentence;
    private Sentences similarSentences;//TODO: Should be Sentences

    public myTrainingPhrase(String sentence) {
        this.setSentence(new Sentence(sentence));
        this.setSimilarSentences(new Sentences());
    }

    public myTrainingPhrase(Sentence sentence) {
        this.setSentence(sentence);
        this.setSimilarSentences(new Sentences());
    }

    //region REGION: Singleton Training Phrases(Next)
    public static Sentences getNextTrainingPhrases() {
        Sentences sentences = new Sentences();
        sentences.addSentence("Next");
        sentences.addSentence("Following");
        return sentences;
    }
    //endregion

    //region REGION: Override(toString, equals)
    @Override
    public String toString() {
        String result = "";
        result += sentence.toString();
        result += "\n";
        result += similarSentences.toString();
        result += "\n";
        result += "\n";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof myTrainingPhrase) {
            myTrainingPhrase otherTrainingPhrase = (myTrainingPhrase) o;
            if (this.getSentence().equals(otherTrainingPhrase.getSentence()) &&
                    this.getSimilarSentences().equals(otherTrainingPhrase.getSimilarSentences() ) )
            {
                return true;
            }
        }
        return false;
    }
    //endregion

    /**
     * Normal Sentence (Yes, No, null[Next],etc)
     */
    public Sentence getSentence() { return sentence; }
    public void setSentence(Sentence sentence) { this.sentence = sentence; }

    /**
     * Similar sentences of {@code sentence}
     */
    public Sentences getSimilarSentences() { return similarSentences; }
    public void setSimilarSentences(Sentences similarSentences) { this.similarSentences = similarSentences; }


    public void addSimilarSentences(Sentences sentences) {
        this.getSimilarSentences().addSentences(sentences);
    }

    /**
     * Gets the builded paraphrased(similar) sentences
     * <br>
     * This means that gets the paraphrased(similar) training phrases.
     * <br>
     * PRE CONDITION: The training phrases are already paraphrased
     * <br>
     * @return Returns the training phrases already paraphrased
     */
    public List<String> getBuildedParaphrasedSentences() {
        return getSimilarSentences().getSentencesList();
    }



}
