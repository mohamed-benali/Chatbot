package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Cjt_Words;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Words;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngine;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngineImpl;

import java.util.List;

public class SpinnerChiefParserDirectSubSetImpl implements SpinnerChiefParser {
    WordEngine wordEngine;
    SpinnerChiefParser spinnerChiefParser;


    public SpinnerChiefParserDirectSubSetImpl() {
        wordEngine = new WordEngineImpl();
        spinnerChiefParser = new SpinnerChiefParserAllCombinationsImpl();
    }


    /**
     * Generates direct sentences for the words passed as parameter <br>
     * <br>
     * Example:<br>
     * {Who|That|Who else|Which} {ships|boats|delivers|cruises} {a|the|a new|some sort of} {parcel|package|courier|goods}?<br>
     * becomes: <br>
     * Who ships a parcel?<br>
     * That boats the package?<br>
     * Who else delivers a new courier<br>
     * Which cruises some sort of goods?<br>
     * @param words The words to use for the generation
     * @param similarSentences The result of all generated sentences
     * @return Returns all the sentences for the direct combinations of the the words passed as a parameter
     */
    @Override
    public Sentences generateSentences(Words words, Sentences similarSentences) {
        Cjt_Words cjt_words = wordEngine.separateWordsIntoSubSets(words, 1); // TODO: Documentar que s'ha pogut reusar
        List<Words> cjt_words_list = cjt_words.getCjt_words();
        Sentences sentences = new Sentences();
        for(Words subSetwords : cjt_words_list) {
            sentences.addSentences(spinnerChiefParser.generateSentences(subSetwords, similarSentences));
        }
        return sentences;
    }
}
