package main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine;

import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Cjt_Words;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.Words;

public interface WordEngine {
    Words readWords(String sentenceToParse);

    /**
     * Creates the separated words<br>
     * If the size of all the words's options isn't the same, then those who have less than the maximum(words's options size)
     * use their last options to replace the non-exisiting ones.
     *
     * Example of general usage: If words is <br>
     * {Who|That|Who else|Which} {ships|boats|delivers|cruises} {a|the|a new|some sort of} {parcel|package|courier|goods}?<br>
     * and sizeSubset is 1 <br>
     * then the generated words are: <br>
     * {Who} {ships} {a} {parcel}?<br>
     * {That} {boats} {the} {package}<br>
     * {Who else} {delivers} {a new} {courier}<br>
     * {Which} {cruises} {some sort of} {goods}?<br>
     * @param words Words to be separated into subsets
     * @param sizeSubset Number of options of a Word into the subset.
     * @return Returns the separated words.
     */
    Cjt_Words separateWordsIntoSubSets(Words words, int sizeSubset);

    /**
     * Reduces the options of the words
     * @param words
     * @return Returns the words generated with the reduction
     */
    Cjt_Words reduceToHalfWordOptions(Words words);
}
