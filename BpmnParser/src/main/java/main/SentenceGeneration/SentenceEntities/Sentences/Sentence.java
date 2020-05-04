package main.SentenceGeneration.SentenceEntities.Sentences;

import main.Entity.Intent.myIntent;

public class Sentence {

    protected String sentence;

    public Sentence(String sentence) {
        this.sentence = sentence;
    }

    /**
     * Gets the sentence
     * @return Returns the sentence
     */
    public String getSentence() { return sentence; }
    public void setSentence(String sentence) { this.sentence = sentence; }



    @Override
    public boolean equals(Object o) {
        if (o instanceof Sentence) {
            Sentence otherSentence = (Sentence) o;
            if (this.getSentence().equals(otherSentence.getSentence() ) ) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String result = "";
        result += "\n";
        result += "Sentence:    " + this.getSentence() + "\n";
        result += "\n";
        result += "\n";

        return result;
    }
}