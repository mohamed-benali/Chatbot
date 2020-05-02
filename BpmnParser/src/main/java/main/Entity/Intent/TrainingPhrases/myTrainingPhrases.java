package main.Entity.Intent.TrainingPhrases;

import main.Entity.Intent.myIntent;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class myTrainingPhrases {
    /**
     * KEY --> Sentence
     * VALUE --> TrainingPhrase
     */
    private Map<String, myTrainingPhrase> trainingPhrases;

    /**
     * Tells if it has a null Training Phrase. Its true if it's so.
     * TODO: Its a workaround, so i should see how i can include a null key on a map.
     */
    private boolean hasNullTrainingPhrase;


    @Override
    public String toString() {
        return trainingPhrases.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof myTrainingPhrases) {
            myTrainingPhrases otherTrainingPhrases = (myTrainingPhrases) o;
            if (this.getTrainingPhrases().equals(otherTrainingPhrases.getTrainingPhrases())) return true;
        }
        return false;
    }

    public myTrainingPhrases() {
        setTrainingPhrases(new TreeMap<>());
        hasNullTrainingPhrase = false;
    }

    public Map<String, myTrainingPhrase> getTrainingPhrases() { return trainingPhrases; }
    public void setTrainingPhrases(Map<String, myTrainingPhrase> trainingPhrases) { this.trainingPhrases = trainingPhrases; }



    //region REGION: Add Training Phrase
    public void addTrainingPhrase(myTrainingPhrase myTrainingPhrase) {
        String sentence = myTrainingPhrase.getSentence().getSentence();
        if(sentence == null) hasNullTrainingPhrase = true;
        else trainingPhrases.put(sentence, myTrainingPhrase);
    }

    public void addTrainingPhrase(String newTrainingPhrase) {
        myTrainingPhrase trainingPhrase = new myTrainingPhrase(newTrainingPhrase);
        this.addTrainingPhrase(trainingPhrase);
    }

    public void addTrainingPhrase(Sentence newSentence) {
        myTrainingPhrase trainingPhrase = new myTrainingPhrase(newSentence);
        this.addTrainingPhrase(trainingPhrase);
    }
    //endregion


    //region REGION: AddAll Training Phrases
    public void addAllTrainingPhrases(List<String> trainingPhrases) {
        for (String trainingPhrase : trainingPhrases) this.addTrainingPhrase(trainingPhrase);
    }


    public void addAllTrainingPhrases(myTrainingPhrases trainingPhrases) {
        this.addAllTrainingPhrases(trainingPhrases.getTrainingPhrases());
    }

    private void addAllTrainingPhrases(Map<String, myTrainingPhrase> trainingPhrases) {
        for(Map.Entry<String, myTrainingPhrase> entry : trainingPhrases.entrySet()) {
            this.addTrainingPhrase(entry.getValue());
        }
    }
    //endregion

    public void clear() { this.getTrainingPhrases().clear(); }


    public List<String> getBuildedTrainingPhrases() {
        List<String> sentences = new ArrayList<>();
        for(Map.Entry<String, myTrainingPhrase> entry : trainingPhrases.entrySet()) {
            List<String> paraphrasedSentences = entry.getValue().getBuildedParaphrasedSentences();
            sentences.addAll(paraphrasedSentences);
        }
        return sentences;
    }
}
