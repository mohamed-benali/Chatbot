package main.SentenceGeneration.SentenceParaphraser;

import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;

import java.io.IOException;
import java.util.List;

public interface SentenceParaphraser {
    ParaphrasedSentences paraphraseSentence(List<String> sentences) throws InterruptedException, SpinnerChief_SentenceParaphraserException, IOException;
}
