package main.SentenceGeneration.SentenceParaphraser.SpinnerChiefParser.SpinnerChiefParserDirectSubSet;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParser;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParserAllCombinationsImpl;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParserDirectSubSetImpl;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParserSubSetsImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SpinnerChiefParserDirectSubSetImplTest {
    SpinnerChiefParser spinnerChiefParser;

    @BeforeEach
    void setUp() {
        spinnerChiefParser = new SpinnerChiefParserDirectSubSetImpl();
    }

    @AfterEach
    void tearDown() {

    }



    @Test
    @DisplayName("Parse one sentence: {Who|That|Who else} requests {the items|the things}?") // TODO: Do it
    void generateSentencesBacktracking() {

        String sentenceToParse = "{Who|That|Who else} requests {the items|the things}?";

        Sentences similarSentences = spinnerChiefParser.parseSentence(sentenceToParse);

        Sentences expectedGeneratedSentences = generateExpectedSimilarSententces();

        assertEquals(expectedGeneratedSentences, similarSentences);
    }

    private Sentences generateExpectedSimilarSententces() {
        // {Who|That|Who else} requests {the items|the things}?

        Sentences expectedSentences = new Sentences();
        expectedSentences.addSentence("Who requests the items?");
        expectedSentences.addSentence("That requests the things?");
        expectedSentences.addSentence("Who else requests the items?");

        return expectedSentences;

    }








    @Test
    @DisplayName("Parse one sentence: {Who|Who else} {updates|up-dates} {the|the particular|this} {stock|inventory}?")
    void generateSentencesBacktracking2() {

        String sentenceToParse = "{Who|Who else} {updates|up-dates} {the|the particular|this} {stock|inventory}?";
        Sentences similarSentences = spinnerChiefParser.parseSentence(sentenceToParse);

        Sentences expectedGeneratedSentences = generateExpectedSimilarSententces2();

        assertEquals(expectedGeneratedSentences, similarSentences);
    }

    private Sentences generateExpectedSimilarSententces2() {
        // {Who|That|Who else} requests {the items|the things}?

        Sentences expectedSentences = new Sentences();

        expectedSentences.addSentence("Who updates the stock?");
        expectedSentences.addSentence("Who updates this stock?");
        expectedSentences.addSentence("Who else up-dates the particular inventory?");


        return expectedSentences;

    }



}


