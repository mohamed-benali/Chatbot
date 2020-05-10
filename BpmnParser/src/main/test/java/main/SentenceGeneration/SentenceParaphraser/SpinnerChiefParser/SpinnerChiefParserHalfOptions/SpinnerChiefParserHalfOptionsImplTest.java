package main.SentenceGeneration.SentenceParaphraser.SpinnerChiefParser.SpinnerChiefParserHalfOptions;

import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParser;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParserHalfOptionsImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SpinnerChiefParserHalfOptionsImplTest {
    SpinnerChiefParser spinnerChiefParser;

    @BeforeEach
    void setUp() {
        spinnerChiefParser = new SpinnerChiefParserHalfOptionsImpl();
    }

    @AfterEach
    void tearDown() {

    }



    @Test
    @DisplayName("Parse one sentence: {Who|That|Who else|Which} {updates|up-dates|improvements|revisions} {the|the particular|typically the|this} {stock|share|inventory|investment}?")
    void generateSentencesBacktracking2() {

        String sentenceToParse =    "{Who|That|Who else|Which} " +
                                    "{updates|up-dates|improvements|revisions} " +
                                    "{the|the particular|typically the|this} " +
                                    "{stock|share|inventory|investment}?";

        Sentences similarSentences = spinnerChiefParser.parseSentence(sentenceToParse);

        Sentences expectedGeneratedSentences = generateExpectedSimilarSententces2();

        assertEquals(expectedGeneratedSentences, similarSentences);
    }

    //{Who|That|Who else|Which} {updates|up-dates|improvements|revisions} {the|the particular|typically the|this} {stock|share|inventory|investment}?"
    private Sentences generateExpectedSimilarSententces2() {

        Sentences expectedSentences = new Sentences();

        // Sub set 1
        expectedSentences.addSentence("Who updates the stock?");
        expectedSentences.addSentence("Who updates the share?");
        expectedSentences.addSentence("Who updates the particular stock?");
        expectedSentences.addSentence("Who updates the particular share?");
        expectedSentences.addSentence("Who up-dates the stock?");
        expectedSentences.addSentence("Who up-dates the share?");
        expectedSentences.addSentence("Who up-dates the particular stock?");
        expectedSentences.addSentence("Who up-dates the particular share?");
        expectedSentences.addSentence("That updates the stock?");
        expectedSentences.addSentence("That updates the share?");
        expectedSentences.addSentence("That updates the particular stock?");
        expectedSentences.addSentence("That updates the particular share?");
        expectedSentences.addSentence("That up-dates the stock?");
        expectedSentences.addSentence("That up-dates the share?");
        expectedSentences.addSentence("That up-dates the particular stock?");
        expectedSentences.addSentence("That up-dates the particular share?");

        // Sub set 2
        expectedSentences.addSentence("Who else improvements typically the inventory?");
        expectedSentences.addSentence("Who else improvements typically the investment?");
        expectedSentences.addSentence("Who else improvements this inventory?");
        expectedSentences.addSentence("Who else improvements this investment?");
        expectedSentences.addSentence("Who else revisions typically the inventory?");
        expectedSentences.addSentence("Who else revisions typically the investment?");
        expectedSentences.addSentence("Who else revisions this inventory?");
        expectedSentences.addSentence("Who else revisions this investment?");
        expectedSentences.addSentence("Which improvements typically the inventory?");
        expectedSentences.addSentence("Which improvements typically the investment?");
        expectedSentences.addSentence("Which improvements this inventory?");
        expectedSentences.addSentence("Which improvements this investment?");
        expectedSentences.addSentence("Which revisions typically the inventory?");
        expectedSentences.addSentence("Which revisions typically the investment?");
        expectedSentences.addSentence("Which revisions this inventory?");
        expectedSentences.addSentence("Which revisions this investment?");

        return expectedSentences;

    }



}


