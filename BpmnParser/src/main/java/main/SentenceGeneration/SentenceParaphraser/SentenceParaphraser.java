package main.SentenceGeneration.SentenceParaphraser;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;

public interface SentenceParaphraser {
    Sentences paraphraseSentence(String sentence) throws IOException, InterruptedException;
}
