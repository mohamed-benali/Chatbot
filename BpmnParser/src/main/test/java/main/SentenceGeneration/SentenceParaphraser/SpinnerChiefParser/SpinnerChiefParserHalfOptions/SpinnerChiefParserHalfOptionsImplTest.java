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

        expectedSentences.addSentence("That updates the particular stock?");
        expectedSentences.addSentence("Which updates this stock?");
        expectedSentences.addSentence("Who else updates typically the stock?");
        expectedSentences.addSentence("Who improvements the inventory?");
        expectedSentences.addSentence("Who revisions the investment?");
        expectedSentences.addSentence("Who up-dates the share?");
        expectedSentences.addSentence("Who updates the stock?");


        return expectedSentences;

    }



}


