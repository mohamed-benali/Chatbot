package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Words;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngine;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngineImpl;

public interface SpinnerChiefParser {
    /**
     * Generates the sentences of sentenceToParse depending on the implementation of SpinnerChiefParser
     * @param sentenceToParse Sentence to parse
     * @return Returns the sentences of sentenceToParse depending on the implementation of SpinnerChiefParser
     */
    default Sentences parseSentence(String sentenceToParse){
        WordEngine wordEngine = new WordEngineImpl();

        Words words = wordEngine.readWords(sentenceToParse);

        Sentences similarSentences = new Sentences();
        return generateSentences(words, similarSentences);
    }


    /**
     * Generates the sentences of words depending on the implementation of SpinnerChiefParser
     * @param words The word that will be parsed
     * @param similarSentences
     * @return
     */
    Sentences generateSentences(Words words, Sentences similarSentences);


}
