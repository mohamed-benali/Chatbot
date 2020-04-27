package main.SentenceGeneration.SentenceParaphraser;

import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceParaphraserTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }


    @Test
    @DisplayName("Paraphrase a sentence: Who requests the items?.") //TODO: Fix the DisplayName
    void generateSentence() throws IOException, InterruptedException, SpinnerChief_SentenceParaphraserException {
        String sentence = "Who requests the items?\nWho requests the items?\n" +
                "Who requests the items?\nWho requests the items?\nWho requests the items?";
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


    // TODO: Create a paraphraseSentences passing a List¿?,, and inside i plice the \n
    @Test
    @DisplayName("Paraphrase a list of sentences") //TODO: Fix the DisplayName
    void generateSentences() throws IOException, InterruptedException, SpinnerChief_SentenceParaphraserException {

        List<String> sentences = new ArrayList<>(); // TODO: Put diferent sentences
        sentences.add("Who requests the items?");
        sentences.add("Who requests the items?");
        sentences.add("Who requests the items?");
        sentences.add("Who requests the items?");
        sentences.add("Who requests the items?");

        SentenceParaphraser sentenceParaphraser = new SpinnerChief_SentenceParaphraserImpl() ;
        Sentences similarSentences = sentenceParaphraser.paraphraseSentence(sentences);

        Sentences expectedGeneratedSentences = generateExpectedSimilarSententces();

        assertEquals(expectedGeneratedSentences, similarSentences);
    }


}