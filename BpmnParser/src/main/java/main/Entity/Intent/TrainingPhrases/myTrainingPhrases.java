package main.Entity.Intent.TrainingPhrases;

import main.Enums.DefaultTrainingPhraseType;
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

    private NullTrainingPhrase nullTrainingPhrase;



    public myTrainingPhrases() {
        setTrainingPhrases(new TreeMap<>());
        setNullTrainingPhrase(new NullTrainingPhrase());
    }


    //region REGION: Override(equals, toString)
    @Override
    public String toString() {
        String result = trainingPhrases.toString();
        result += "\n";
        result += getNullTrainingPhrase().toString();
        result += "\n";
        result += "\n";
        return  result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof myTrainingPhrases) {
            myTrainingPhrases otherTrainingPhrases = (myTrainingPhrases) o;
            if (this.getTrainingPhrases().equals(otherTrainingPhrases.getTrainingPhrases() ) &&
             this.getNullTrainingPhrase().equals(otherTrainingPhrases.getNullTrainingPhrase()))
                return true;
        }
        return false;
    }


    //endregion

    //region REGION: Getters and setters
    public Map<String, myTrainingPhrase> getTrainingPhrases() { return trainingPhrases; }
    public void setTrainingPhrases(Map<String, myTrainingPhrase> trainingPhrases) { this.trainingPhrases = trainingPhrases; }

    public NullTrainingPhrase getNullTrainingPhrase() { return nullTrainingPhrase; }
    public void setNullTrainingPhrase(NullTrainingPhrase nullTrainingPhrase) { this.nullTrainingPhrase = nullTrainingPhrase; }


    /**
     * Gets the training phrases without considering if it has a Null Training Phrase. Therefore, the null trainigPhrase is not included in the returned response.
     * @return Returns the training phrases without the null Training Phrase
     */
    public List<String> getTrainingPhrasesList() {
        return new ArrayList<>(this.getTrainingPhrases().keySet());
    }

    //region REGION: Null training Phrase
    /**
     * Tells if it has a null Training Phrase. Its true if it's so.
     * TODO: Its a workaround, so i should see how i can include a null key on a map.
     */
    public boolean hasNullTrainingPhrase() { return this.getNullTrainingPhrase().hasNullTrainingPhrase(); }
    public void setHasNullTrainingPhrase(boolean hasNullTrainingPhrase) { this.getNullTrainingPhrase().setHasNullTrainingPhrase(hasNullTrainingPhrase); }

    public DefaultTrainingPhraseType getDefaultTrainingPhraseType() { return this.getNullTrainingPhrase().getDefaultTrainingPhraseType(); }
    public void setDefaultTrainingPhraseType(DefaultTrainingPhraseType defaultTrainingPhraseType) { this.getNullTrainingPhrase().setDefaultTrainingPhraseType(defaultTrainingPhraseType); }
    //endregion

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
     * @param paraphrasedSentences All the paraphrased sentences of {@code sentence}
     */
    public void addAllTrainingPhrasesToKeyAsBuilded(String key, ParaphrasedSentences paraphrasedSentences) {
        myTrainingPhrase myTrainingPhrase = getTrainingPhrases().get(key);
        myTrainingPhrase.addSimilarSentences(paraphrasedSentences.getByKey(key));
    }

/*  /**
     * Adds the paraphrased Next sentences corresponding to the parameter {@code sentence} to the new trainingPhrase identified by {@code sentence}
     * @param sentence The sentence corresponding to the Training phrase that will add the {@code paraphrasedSentences.getByKey(sentence);}
     * @param paraphrasedSentences All the paraphrased sentences of {@code sentence}
     *//*
    private void addAllNextTrainingPhrasesAsBuilded(String sentence, ParaphrasedSentences paraphrasedSentences) {
        myTrainingPhrase myTrainingPhrase = new myTrainingPhrase(sentence);
        myTrainingPhrase.addSimilarSentences(paraphrasedSentences.getByKey(sentence));
        this.getTrainingPhrases().put(sentence, myTrainingPhrase);
    }*/
    //endregion

    public void clear() { this.getTrainingPhrases().clear(); }


    /**
     * Gets the builded paraphrased sentences of all training Phrases,
     * PLUS the sentences corresponding to the null training Phrase(if they exist) <br>
     * This means that gets the paraphrased(similar) training phrases of all training Phrases<br>
     * <br>
     * PRE CONDITION: All the training Phrases are already paraphrased. <br>
     * PRE CONDITION: The null training phrase info has been set previously. Specifically this means that
     * The type of the null and the attribute indicating if it has a null sentence, have been set before calling this method <br>
     * @return Returns the paraphrased training sentences PLUS the sentences corresponding to the null trainingPhrase(if they exist)
     */
    public List<String> getBuildedTrainingPhrases() {
        List<String> sentencesList = new ArrayList<>();
        for(Map.Entry<String, myTrainingPhrase> entry : trainingPhrases.entrySet()) {
            List<String> paraphrasedSentences = entry.getValue().getBuildedParaphrasedSentences();
            sentencesList.addAll(paraphrasedSentences);
        }
        sentencesList.addAll(this.getNullTrainingPhrase().getTrainingPhrases());
        return sentencesList;
    }

    public void buildTrainingPhrases() {

    }

    /**
     * Updates the trainingPhrases with the parameter {@code paraphrasedSentences} without considering a possible null training phrase
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
