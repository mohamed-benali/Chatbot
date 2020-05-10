package main.SentenceGeneration.SentenceParaphraser.SpinnerChiefParser.Word.WordEngine;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParser;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParserAllCombinationsImpl;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.Entities.*;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngine;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.Word.WordEngine.WordEngineImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class WordEngineImplTest {

    WordEngine wordEngine;

    @BeforeEach
    void setUp() {
        wordEngine = new WordEngineImpl();
    }

    @AfterEach
    void tearDown() {

    }



    @Test
    @DisplayName("Parse one sentence: {Who|That|Who else} requests {the items|the things}?") // TODO: Do it
    void generateSentencesBacktracking() {
        MultipleWord multipleWord = new MultipleWord();
        multipleWord.add("Who");
        multipleWord.add("That");
        multipleWord.add("Who else");
        SingleWord singleWord = new SingleWord(" requests ");
        MultipleWord multipleWord2 = new MultipleWord();
        multipleWord2.add("the items");
        multipleWord2.add("the things");
        Words words = new Words();
        words.add(multipleWord);
        words.add(singleWord);
        words.add(multipleWord2);

        int sizeSubset = 2;
        Cjt_Words resultCjt_words = wordEngine.separateWordsIntoSubSets(words, sizeSubset);

        Cjt_Words expectedGeneratedCjt_Words = generateExpectedCjt_Words();

        assertEquals(expectedGeneratedCjt_Words, resultCjt_words);
    }

    private Cjt_Words generateExpectedCjt_Words() {
        // {Who|That|Who else} requests {the items|the things}?
        MultipleWord multipleWord = new MultipleWord();
        multipleWord.add("Who");
        multipleWord.add("That");
        SingleWord singleWord = new SingleWord(" requests ");
        MultipleWord multipleWord2 = new MultipleWord();
        multipleWord2.add("the items");
        multipleWord2.add("the things");
        Words words = new Words();
        words.add(multipleWord);
        words.add(singleWord);
        words.add(multipleWord2);


        MultipleWord multipleWord3 = new MultipleWord();
        multipleWord3.add("Who");
        multipleWord3.add("Who else");
        SingleWord singleWord2 = new SingleWord(" requests ");
        MultipleWord multipleWord4 = new MultipleWord();
        multipleWord4.add("the items");
        multipleWord4.add("the things");
        Words words2 = new Words();
        words2.add(multipleWord3);
        words2.add(singleWord2);
        words2.add(multipleWord4);

        Cjt_Words expectedCjt_words = new Cjt_Words();
        expectedCjt_words.add(words);
        expectedCjt_words.add(words2);

        return expectedCjt_words;

    }








    @Test
    @DisplayName("Parse one sentence: {Who|Who else} {updates|up-dates} {the|the particular|this} {stock|inventory}?")
    void generateSentencesBacktracking2() {
        MultipleWord multipleWord = new MultipleWord();
        multipleWord.add("Who");
        multipleWord.add("Who else");
        MultipleWord multipleWord2 = new MultipleWord();
        multipleWord2.add("updates");
        multipleWord2.add("up-dates");
        MultipleWord multipleWord3 = new MultipleWord();
        multipleWord3.add("the");
        multipleWord3.add("the particular");
        multipleWord3.add("this");
        MultipleWord multipleWord4 = new MultipleWord();
        multipleWord4.add("stock");
        multipleWord4.add("inventory");
        Words words = new Words();
        words.add(multipleWord);
        words.add(multipleWord2);
        words.add(multipleWord3);
        words.add(multipleWord4);

        int sizeSubset = 2;
        Cjt_Words resultCjt_words = wordEngine.separateWordsIntoSubSets(words, sizeSubset);

        Cjt_Words expectedGeneratedCjt_Words = generateExpectedCjt_Words2();

        assertEquals(expectedGeneratedCjt_Words, resultCjt_words);
    }

    private Cjt_Words generateExpectedCjt_Words2() {
        // {Who|Who else} {updates|up-dates} {the|the particular|this} {stock|inventory}?
        MultipleWord multipleWord = new MultipleWord();
        multipleWord.add("Who");
        multipleWord.add("Who else");
        MultipleWord multipleWord2 = new MultipleWord();
        multipleWord2.add("updates");
        multipleWord2.add("up-dates");
        MultipleWord multipleWord3 = new MultipleWord();
        multipleWord3.add("the");
        multipleWord3.add("the particular");
        MultipleWord multipleWord4 = new MultipleWord();
        multipleWord4.add("stock");
        multipleWord4.add("inventory");
        Words words = new Words();
        words.add(multipleWord);
        words.add(multipleWord2);
        words.add(multipleWord3);
        words.add(multipleWord4);

        MultipleWord multipleWord5 = new MultipleWord();
        multipleWord5.add("Who");
        multipleWord5.add("Who else");
        MultipleWord multipleWord6 = new MultipleWord();
        multipleWord6.add("updates");
        multipleWord6.add("up-dates");
        MultipleWord multipleWord7 = new MultipleWord();
        multipleWord7.add("the");
        multipleWord7.add("this");
        MultipleWord multipleWord8 = new MultipleWord();
        multipleWord8.add("stock");
        multipleWord8.add("inventory");
        Words words2 = new Words();
        words2.add(multipleWord5);
        words2.add(multipleWord6);
        words2.add(multipleWord7);
        words2.add(multipleWord8);

        Cjt_Words expectedCjt_words = new Cjt_Words();
        expectedCjt_words.add(words);
        expectedCjt_words.add(words2);

        return expectedCjt_words;

    }











    @Test //TODO Test it better(search for the blank space, which are translated as blank spaces)
    @DisplayName("Parse one sentence, Half strategy: {Who|Who else} {updates|up-dates} {the|the particular|this} {stock|inventory}?")
    void generateSentencesBacktracking3() {
        MultipleWord multipleWord = new MultipleWord();
        multipleWord.add("Who");
        multipleWord.add("Who else");
        MultipleWord multipleWord2 = new MultipleWord();
        multipleWord2.add("updates");
        multipleWord2.add("up-dates");
        MultipleWord multipleWord3 = new MultipleWord();
        multipleWord3.add("the");
        multipleWord3.add("the particular");
        multipleWord3.add("this");
        MultipleWord multipleWord4 = new MultipleWord();
        multipleWord4.add("stock");
        multipleWord4.add("inventory");
        Words words = new Words();
        words.add(multipleWord);
        words.add(multipleWord2);
        words.add(multipleWord3);
        words.add(multipleWord4);

        Cjt_Words resultCjt_words = wordEngine.reduceToHalfWordOptions(words);

        Cjt_Words expectedGeneratedCjt_Words = generateExpectedCjt_Words3();

        assertEquals(expectedGeneratedCjt_Words, resultCjt_words);
    }

    private Cjt_Words generateExpectedCjt_Words3() {
        // {Who|Who else} {updates|up-dates} {the|the particular|this} {stock|inventory}?
        SingleWord singleWord = new SingleWord("Who");
        MultipleWord multipleWord2 = new MultipleWord();
        multipleWord2.add("updates");
        multipleWord2.add("up-dates");
        SingleWord singleWord2 = new SingleWord("the");
        MultipleWord multipleWord4 = new MultipleWord();
        multipleWord4.add("stock");
        multipleWord4.add("inventory");
        Words words = new Words();
        words.add(singleWord);
        words.add(multipleWord2);
        words.add(singleWord2);
        words.add(multipleWord4);

        MultipleWord multipleWord5 = new MultipleWord();
        multipleWord5.add("Who");
        multipleWord5.add("Who else");
        SingleWord singleWord3 = new SingleWord("updates");
        MultipleWord multipleWord7 = new MultipleWord();
        multipleWord7.add("the");
        multipleWord7.add("the particular");
        multipleWord7.add("this");
        SingleWord singleWord4 = new SingleWord("stock");

        Words words2 = new Words();
        words2.add(multipleWord5);
        words2.add(singleWord3);
        words2.add(multipleWord7);
        words2.add(singleWord4);

        Cjt_Words expectedCjt_words = new Cjt_Words();
        expectedCjt_words.add(words);
        expectedCjt_words.add(words2);

        return expectedCjt_words;

    }

}


