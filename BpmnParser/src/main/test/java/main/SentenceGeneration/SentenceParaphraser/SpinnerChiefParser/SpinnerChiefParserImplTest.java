package main.SentenceGeneration.SentenceParaphraser.SpinnerChiefParser;

import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SentenceParaphraser;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParser;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChiefParser.SpinnerChiefParserImpl;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChief_SentenceParaphraserImpl;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SpinnerChiefParserImplTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }



    @Test
    @DisplayName("Parse one sentence: {Who|That|Who else} requests {the items|the things}?") // TODO: Do it
    void generateSentencesBacktracking() {

        String sentenceToParse = "{Who|That|Who else} requests {the items|the things}?";

        SpinnerChiefParser spinnerChiefParser = new SpinnerChiefParserImpl() ;
        Sentences similarSentences = spinnerChiefParser.parseSentence(sentenceToParse);

        Sentences expectedGeneratedSentences = generateExpectedSimilarSententces();

        assertEquals(expectedGeneratedSentences, similarSentences);
    }

    private Sentences generateExpectedSimilarSententces() {
        // {Who|That|Who else} requests {the items|the things}?

        Sentences expectedSentences = new Sentences();
        expectedSentences.addSentence("Who requests the items?");
        expectedSentences.addSentence("Who requests the things?");

        expectedSentences.addSentence("That requests the items?");
        expectedSentences.addSentence("That requests the things?");

        expectedSentences.addSentence("Who else requests the items?");
        expectedSentences.addSentence("Who else requests the things?");

        return expectedSentences;

    }








    @Test
    @DisplayName("Parse one sentence: {Who|That|Who else|Which} {updates|up-dates|improvements|revisions} {the|the particular|typically the|this} {stock|share|inventory|investment}?") // TODO: Do it
    void generateSentencesBacktracking2() {

        String sentenceToParse = "{Who|That|Who else|Which} {updates|up-dates|improvements|revisions} {the|the particular|typically the|this} {stock|share|inventory|investment}?";

        SpinnerChiefParser spinnerChiefParser = new SpinnerChiefParserImpl() ;
        Sentences similarSentences = spinnerChiefParser.parseSentence(sentenceToParse);

        Sentences expectedGeneratedSentences = generateExpectedSimilarSententces2();

        assertEquals(expectedGeneratedSentences, similarSentences);
    }

    private Sentences generateExpectedSimilarSententces2() {
        // {Who|That|Who else} requests {the items|the things}?

        Sentences expectedSentences = new Sentences();
        expectedSentences.addSentence("Who requests the items?");
        expectedSentences.addSentence("Who requests the things?");
        expectedSentences.addSentence("Who requests the items?");
        expectedSentences.addSentence("Who requests the things?");

        expectedSentences.addSentence("That requests the items?");
        expectedSentences.addSentence("That requests the things?");

        expectedSentences.addSentence("Who else requests the items?");
        expectedSentences.addSentence("Who else requests the things?");

        return expectedSentences;

    }



}


