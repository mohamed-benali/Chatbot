package main.SentenceGeneration.SentenceEntities.Sentences;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ParaphrasedSentences {
    /**
     * First    -->     The sentence(string)
     * Second   -->     Una llista de sentencias similars de la sentencia key
     */
    private Map<String, Sentences > paraphrasedSentences;



    public ParaphrasedSentences() {
        paraphrasedSentences = new TreeMap<>();
    }

    public Map<String, Sentences> getParaphrasedSentences() { return paraphrasedSentences; }
    public void setParaphrasedSentences(Map<String, Sentences> paraphrasedSentences) { this.paraphrasedSentences = paraphrasedSentences; }



    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParaphrasedSentences) {
            ParaphrasedSentences otherSentences = (ParaphrasedSentences) obj;
            if (this.getParaphrasedSentences().equals(otherSentences.getParaphrasedSentences())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return paraphrasedSentences.toString();
    }




    /**
     *
     * @param key Sentence
     * @param paraphrasedSentences Paraphrased Sentences of {@code key}
     */
    public void setParaphrasedSentencesOfSentence(String key, Sentences paraphrasedSentences) { this.paraphrasedSentences.put(key, paraphrasedSentences); }
    public Sentences getParaphrasedSentencesOfSentence(String key) { return this.paraphrasedSentences.get(key); }



    //region REGION: Adding sentences

    //region REGION: ADD PARAPHRASED SENTENCES
    /**
     * For each Entry of {@code paraphrasedSentences} is checked if the key exists
     * If the key exists, the sentences are added to the existing sentences
     * If the key doesn't exist, a new entry is created with the sentences asociated to that key
     * @param paraphrasedSentences Paraphrased sentences
     */
    public void addParaphrasedSentences(ParaphrasedSentences paraphrasedSentences) {
        this.addParaphrasedSentences(paraphrasedSentences.getParaphrasedSentences());
    }

    /**
     * For each Entry of {@code paraphrasedSentences} is checked if the key exists
     * If the key exists, the sentences are added to the existing sentences
     * If the key doesn't exist, a new entry is created with the sentences asociated to that key
     * @param paraphrasedSentences Paraphrased sentences
     */
    public void addParaphrasedSentences(Map<String, Sentences> paraphrasedSentences) {
        for(Map.Entry<String, Sentences> entry : paraphrasedSentences.entrySet()) {
            String key = entry.getKey();
            Sentences sentencesToAdd = entry.getValue();
            this.addMultipleParaphrasedSentences(key, sentencesToAdd);
        }
    }

    /**
     * If the {@code key} exists, the {@code sentencesToAdd} are added to the existing sentences
     * If the {@code key}  doesn't exist, a new entry is created with the {@code sentencesToAdd}
     * @param key The key
     * @param sentencesToAdd The sentences to add to the key
     */
    public void addMultipleParaphrasedSentences(String key, Sentences sentencesToAdd) {
        if(this.getParaphrasedSentences().containsKey(key)) {
            this.addMultipleParaphrasedSentencesToExistingSentence(key, sentencesToAdd);
        }
        else {
            this.addMultipleParaphrasedSentencesToNewSentence(key, sentencesToAdd);
        }
    }
    //endregion

    //region REGION: ADD NEW SENTENCES
    /*
     *
     */

    public void addSentence(Sentence sentence) {
        Sentences newSentences = new Sentences();
        newSentences.addSentence(sentence);
        this.paraphrasedSentences.put(sentence.getSentence(), newSentences);
    }

    public void addSentence(String sentence) {
        Sentence newSentence = new Sentence(sentence);
        this.addSentence(newSentence);
    }

    private void addMultipleParaphrasedSentencesToNewSentence(String key, Sentences sentences) {
        this.paraphrasedSentences.put(key, new Sentences());
        this.addMultipleParaphrasedSentencesToExistingSentence(key, sentences);
    }



    //endregion

    //region REGION: ADD ONE SENTENCE to existing key
    public void addOneParaphrasedSentenceToExistingSentence(String key, String sentenceToAdd) {
        Sentences sentences = this.paraphrasedSentences.get(key);
        sentences.addSentence(sentenceToAdd);
        //TODO: Hauria de ser suficient perque es una referencia
    }

    public void addOneParaphrasedSentenceToExistingSentence(String key, Sentence sentenceToAdd) {
        Sentences sentences = this.paraphrasedSentences.get(key);
        sentences.addSentence(sentenceToAdd);
        //TODO: Hauria de ser suficient perque es una referencia
    }

    //endregion

    //region REGION: ADD MULTIPLE SENTENCES to existing key
    private void addMultipleParaphrasedSentencesToExistingSentence(String key, Map<String, Sentence> sentencesToAdd) {
        Sentences sentences = this.paraphrasedSentences.get(key);
        sentences.addSentences(sentencesToAdd);
        //TODO: Hauria de ser suficient perque es una referencia
    }

    private void addMultipleParaphrasedSentencesToExistingSentence(String key, Sentences sentencesToAdd) {
        Sentences sentences = this.paraphrasedSentences.get(key);
        sentences.addSentences(sentencesToAdd);
        //TODO: Hauria de ser suficient perque es una referencia
    }

    /*
    public void addMultipleParaphrasedSentences(String key, Sentences sentencesToAdd) { // TODO!!

    }*/

    //endregion

    //endregion



}
