package main.Entity.Intent.TrainingPhrases;

import main.Entity.Intent.myIntent;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;

import java.util.List;

public class myTrainingPhrase { // TODO: Podria ser una sub clase de sentence

    private Sentence sentence;
    private ParaphrasedSentences paraphrasedSentences;

    public myTrainingPhrase(String sentence) {
        this.setSentence(new Sentence(sentence));
        this.setParaphrasedSentences(new ParaphrasedSentences());
    }

    public myTrainingPhrase(Sentence sentence) {
        this.setSentence(sentence);
        this.setParaphrasedSentences(new ParaphrasedSentences());
    }

    @Override
    public String toString() {
        String result = "";
        result += sentence.toString();
        result += "\n";
        result += paraphrasedSentences.toString();
        result += "\n";
        result += "\n";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof myTrainingPhrase) {
            myTrainingPhrase otherTrainingPhrase = (myTrainingPhrase) o;
            if (this.getSentence().equals(otherTrainingPhrase.getSentence()) &&
                    this.getParaphrasedSentences().equals(otherTrainingPhrase.getParaphrasedSentences() ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Normal Sentence (Yes, No, null[Next],etc)
     */
    public Sentence getSentence() { return sentence; }
    public void setSentence(Sentence sentence) { this.sentence = sentence; }

    /**
     * Similar sentences of {@code sentence}
     */
    public ParaphrasedSentences getParaphrasedSentences() { return paraphrasedSentences; }
    public void setParaphrasedSentences(ParaphrasedSentences paraphrasedSentences) { this.paraphrasedSentences = paraphrasedSentences; }

    public List<String> getBuildedParaphrasedSentences() {
        return paraphrasedSentences.getParaphrasedSentencesList();
    }
}
