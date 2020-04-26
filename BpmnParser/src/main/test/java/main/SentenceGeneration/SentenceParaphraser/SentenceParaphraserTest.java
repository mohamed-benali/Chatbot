package main.SentenceGeneration.SentenceParaphraser;

import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceParaphraserTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }


    @Test
    @DisplayName("Paraphrase a sentence: Request items.")
    void generateSentence() throws IOException, InterruptedException {
        String sentence = "Who requests the items?";
        //        String sentence = "Who requests the items?";  // TODO: Test after

        SentenceParaphraser sentenceParaphraser = new SpinnerChief_SentenceParaphraserImpl() ;
        Sentences similarSentences = sentenceParaphraser.paraphraseSentence(sentence);

        Sentences expectedGeneratedSentences = generateExpectedSimilarSententces();

        assertEquals(expectedGeneratedSentences, similarSentences);
    }

    private Sentences generateExpectedSimilarSententces() {
        Sentences expectedSentences = new Sentences();
        // {Who|That|Who else} requests {the items|the things}?
        expectedSentences.addSentence("Who requests the items?");
        expectedSentences.addSentence("Who requests the things?");

        expectedSentences.addSentence("That requests the items?");
        expectedSentences.addSentence("That requests the things?");

        expectedSentences.addSentence("Who else requests the items?");
        expectedSentences.addSentence("Who else requests the things?");

        return expectedSentences;
    }


}