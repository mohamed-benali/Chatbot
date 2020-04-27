package main.SentenceGeneration.SentenceParaphraser;

import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.List;

public interface SentenceParaphraser {
    Sentences paraphraseSentence(String sentence) throws IOException, InterruptedException, SpinnerChief_SentenceParaphraserException;

    Sentences paraphraseSentence(List<String> sentences) throws InterruptedException, SpinnerChief_SentenceParaphraserException, IOException;
}
