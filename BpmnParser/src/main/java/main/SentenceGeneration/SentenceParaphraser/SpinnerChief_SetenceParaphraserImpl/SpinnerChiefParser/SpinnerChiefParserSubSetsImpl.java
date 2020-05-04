package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Cjt_Words;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.SingleWord;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Word;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Words;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngine;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngineImpl;

import java.util.List;

public class SpinnerChiefParserSubSetsImpl implements SpinnerChiefParser {

    WordEngine wordEngine;
    SpinnerChiefParserAllCombinationsImpl spinnerChiefParserAllCombinationsImpl;
    /**
     * Size of the wordsubset
     */
    int sizeSubset;

    public SpinnerChiefParserSubSetsImpl(int sizeSubset) {
        wordEngine = new WordEngineImpl();
        spinnerChiefParserAllCombinationsImpl = new SpinnerChiefParserAllCombinationsImpl();
        this.sizeSubset = sizeSubset;
    }


    /**
     * Generates subsets of the combinations for words <br>
     * <br>
     * Example:<br>
     * {Who|That|Who else|Which} {ships|boats|delivers|cruises} {a|the|a new|some sort of} {parcel|package|courier|goods}?<br>
     * becomes all the cominations of: <br>
     * {Who|That} {ships|boats} {a|the} {parcel|package}<br>
     * and <br>
     * {Who else|Which} {delivers|cruises} {a new|some sort of} {courier|goods}?
     * @param words The words to use for the backtracking
     * @param similarSentences The result of all generated sentences
     * @return Returns all the sentences for the combinations of the subsets
     */
    @Override
    public Sentences generateSentences(Words words, Sentences similarSentences) {
        Cjt_Words cjt_words = wordEngine.separateWordsIntoSubSets(words, sizeSubset);
        List<Words> cjt_words_list = cjt_words.getCjt_words();
        Sentences sentences = new Sentences();
        for(Words subSetwords : cjt_words_list) {
            sentences.addSentences(spinnerChiefParserAllCombinationsImpl.generateSentences(subSetwords, similarSentences));
        }
        return sentences;
    }


}
