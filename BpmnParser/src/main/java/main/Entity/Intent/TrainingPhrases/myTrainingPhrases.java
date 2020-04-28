package main.Entity.Intent.TrainingPhrases;

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


    public myTrainingPhrases() {
        setTrainingPhrases(new TreeMap<>());
    }

    public Map<String, myTrainingPhrase> getTrainingPhrases() { return trainingPhrases; }
    public void setTrainingPhrases(Map<String, myTrainingPhrase> trainingPhrases) { this.trainingPhrases = trainingPhrases; }

    public void addNewTrainingPhrase(myTrainingPhrase myTrainingPhrase) {
        String sentence = myTrainingPhrase.getSentence().getSentence();
        trainingPhrases.put(sentence, myTrainingPhrase);
    }

    public void addNewTrainingPhrase(String newTrainingPhrase) {
        myTrainingPhrase trainingPhrase = new myTrainingPhrase(newTrainingPhrase);
        this.addNewTrainingPhrase(trainingPhrase);
    }

    public void addNewTrainingPhrase(Sentence newSentence) {
        myTrainingPhrase trainingPhrase = new myTrainingPhrase(newSentence);
        this.addNewTrainingPhrase(trainingPhrase);
    }
}
