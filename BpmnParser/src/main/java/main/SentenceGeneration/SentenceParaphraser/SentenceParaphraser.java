package main.SentenceGeneration.SentenceParaphraser;

import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;

import java.io.IOException;
import java.util.List;

public interface SentenceParaphraser {
    /**
     * Paraphrases all the sentences of {@code sentences}
     * <br><br>
     * PRE CONDITION: The sentences are not null correct sentences
     * <br
     * @param sentences Sentences to paraphrase
     * @return For each sentence, returns it's paraphrased sentences
     */
    ParaphrasedSentences paraphraseSentences(List<String> sentences) throws InterruptedException, SpinnerChief_SentenceParaphraserException, IOException;
}
