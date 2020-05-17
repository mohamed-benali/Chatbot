package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Cjt_Words;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Word;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Words;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngine;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngineImpl;

import java.util.List;

public class SpinnerChiefParserHalfOptionsImpl implements main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParser {
    WordEngine wordEngine;
    SpinnerChiefParser spinnerChiefParser;

    public SpinnerChiefParserHalfOptionsImpl() {
        wordEngine = new WordEngineImpl();
        spinnerChiefParser = new SpinnerChiefParserDirectSubSetImpl();
        //spinnerChiefParser = new SpinnerChiefParserAllCombinationsImpl();
    }

    public SpinnerChiefParserHalfOptionsImpl(SpinnerChiefParser spinnerChiefParser) {
        wordEngine = new WordEngineImpl();
        this.spinnerChiefParser = spinnerChiefParser;
    }


    /**
     * Divides the words into half of the options<br>
     * Example:
     * {Who|That|Who else|Which} {ships|boats|delivers|cruises} {a|the|a new|some sort of} {parcel|package|courier|goods} <br>
     * Becames:<br>
     * {Who|That|Who else|Which} ships {a|the|a new|some sort of} parcel<br>
     * and <br>
     * Who {ships|boats|delivers|cruises} a {parcel|package|courier|goods} <br>
     * Then, these are parsed(By default with direct Subset Parsing(see the strategy of {@link SpinnerChiefParserDirectSubSetImpl})
     * @param words The words that will be parsed
     * @param similarSentences
     * @return
     */
    @Override
    public Sentences generateSentences(Words words, Sentences similarSentences) {
        Cjt_Words cjt_words = wordEngine.reduceToHalfWordOptions(words);
        List<Words> cjt_words_list = cjt_words.getCjt_words();
        Sentences sentences = new Sentences();
        for(Words subSetwords : cjt_words_list) {
            sentences.addSentences(spinnerChiefParser.generateSentences(subSetwords, similarSentences));
        }
        return sentences;
    }
}
