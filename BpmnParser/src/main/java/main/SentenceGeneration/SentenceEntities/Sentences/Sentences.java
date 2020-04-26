package main.SentenceGeneration.SentenceEntities.Sentences;

import main.Entity.Intent.Intents;
import main.Entity.Intent.myIntent;

import java.util.Map;
import java.util.TreeMap;

public class Sentences {
    /**
     * First    -->     The sentence(string)
     * Second   -->     The sentence object
     *
     */
    private Map<String, Sentence> sentences;

    public Sentences() {
        setSentences(new TreeMap<>());
    }


    public Map<String, Sentence> getSentences() { return sentences; }
    public void setSentences(Map<String, Sentence> sentences) { this.sentences = sentences; }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sentences) {
            Sentences otherSentences = (Sentences) obj;
            if (this.getSentences().equals(otherSentences.getSentences())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return sentences.toString();
    }

    /*
     * ADD SENTENCES
     */

    public void addSentence(Sentence sentence) { this.sentences.put(sentence.getSentence(), sentence); }

    public void addSentence(String sentence) {
        Sentence newSentence = new Sentence(sentence);
        this.addSentence(newSentence);
    }

    public void addSentences(Map<String, Sentence> sentences) {
        for(Map.Entry<String, Sentence> entry : sentences.entrySet()) {
            this.addSentence(entry.getValue());
        }
    }

    public void addSentences(Sentences sentences) { this.addSentences(sentences.getSentences()); }




}
