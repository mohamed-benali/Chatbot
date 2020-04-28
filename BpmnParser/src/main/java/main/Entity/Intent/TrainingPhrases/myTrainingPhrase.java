package main.Entity.Intent.TrainingPhrases;

import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;

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
}
