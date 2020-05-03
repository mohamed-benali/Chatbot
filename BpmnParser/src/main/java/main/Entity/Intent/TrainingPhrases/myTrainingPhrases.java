package main.Entity.Intent.TrainingPhrases;

import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

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

    private boolean hasNullTrainingPhrase;


    //region REGION: Override(equals, toString)
    @Override
    public String toString() {
        String result = trainingPhrases.toString();
        result += "\n";
        result += "Has Null Training Phrase: " + hasNullTrainingPhrase();
        result += "\n";
        result += "\n";
        return  result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof myTrainingPhrases) {
            myTrainingPhrases otherTrainingPhrases = (myTrainingPhrases) o;
            if (this.getTrainingPhrases().equals(otherTrainingPhrases.getTrainingPhrases() ) &&
             this.hasNullTrainingPhrase() == otherTrainingPhrases.hasNullTrainingPhrase() )
                return true;
        }
        return false;
    }

    public myTrainingPhrases() {
        setTrainingPhrases(new TreeMap<>());
        setHasNullTrainingPhrase(false);
    }
    //endregion

    public Map<String, myTrainingPhrase> getTrainingPhrases() { return trainingPhrases; }
    public void setTrainingPhrases(Map<String, myTrainingPhrase> trainingPhrases) { this.trainingPhrases = trainingPhrases; }

    public List<String> getTrainingPhrasesList() {
        return new ArrayList<>(this.getTrainingPhrases().keySet());
    }

    //region REGION: Has null training Phrase
    /**
     * Tells if it has a null Training Phrase. Its true if it's so.
     * TODO: Its a workaround, so i should see how i can include a null key on a map.
     */
    public boolean hasNullTrainingPhrase() { return hasNullTrainingPhrase; }
    public void setHasNullTrainingPhrase(boolean hasNullTrainingPhrase) { this.hasNullTrainingPhrase = hasNullTrainingPhrase; }
    //endregion



    //region REGION: Add Training Phrase
    public void addTrainingPhrase(myTrainingPhrase myTrainingPhrase) {
        String sentence = myTrainingPhrase.getSentence().getSentence();
        if(sentence == null) setHasNullTrainingPhrase(true);
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

    private void addAllTrainingPhrases(Sentences trainingPhrases) {
        this.addAllTrainingPhrases(trainingPhrases.getSentencesList());
    }

    //endregion


    //region REGION: AddAll Builded Training Phrases

    /**
     * Adds the paraphrased sentences corresponding to the parameter {@code key} to the trainingPhrase identified by {@code key}
     * @param key The key corresponding to the Training phrase that will add the {@code paraphrasedSentences.getByKey(key);}
     * @param paraphrasedSentences All the paraphrased sentences
     */
    private void addAllTrainingPhrasesToKeyAsBuilded(String key, ParaphrasedSentences paraphrasedSentences) {
        myTrainingPhrase myTrainingPhrase = getTrainingPhrases().get(key);
        myTrainingPhrase.addSimilarSentences(paraphrasedSentences.getByKey(key));
    }





    //endregion

    public void clear() { this.getTrainingPhrases().clear(); }


    /**
     * Gets the builded paraphrased sentences of all trainingPhrases
     * <br>
     * This means that gets the paraphrased(similar) training phrases of all trainingPhrases
     * <br>
     * PRE CONDITION: All the trainingPhrases are already paraphrased
     * <br>
     * @return Returns the paraphrased training sentences
     */
    public List<String> getBuildedTrainingPhrases() {
        List<String> sentences = new ArrayList<>();
        for(Map.Entry<String, myTrainingPhrase> entry : trainingPhrases.entrySet()) {
            List<String> paraphrasedSentences = entry.getValue().getBuildedParaphrasedSentences();
            sentences.addAll(paraphrasedSentences);
        }
        return sentences;
    }

    public void buildTrainingPhrases() {

    }

    /**
     * Updates the trainingPhrases with the parameter {@code paraphrasedSentences}
     * <br>
     * Concretely, adds the similar sentence for each sentence existing in attribute {@code this.trainingPhrases} and parameter {@code paraphrasedSentences}
     * @param paraphrasedSentences Paraphrased Sentences. Each sentence has associated similar sentences.
     */
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
        for(Map.Entry<String, myTrainingPhrase> entry : trainingPhrases.entrySet()) {
            String key = entry.getKey();
            //myTrainingPhrase trainingPhrase = entry.getValue();
            if(paraphrasedSentences.containsKey(key)) this.addAllTrainingPhrasesToKeyAsBuilded(key, paraphrasedSentences);
        }
    }




}
