package main.SentenceGeneration.SentenceAnalyzer;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SimpleSentenceAnalysis;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SentenceAnalyzerTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }


    @Test
    @DisplayName("Analyze sentence correctly (Predicate, Object, Complement)")
    void analyzeSentence() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Send finished documents to Marketing Department.";
        SentenceAnalyzer sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();

        SentenceAnalysis resultSentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);

        SentenceAnalysis expectedSentenceAnalysis;
        expectedSentenceAnalysis = new SimpleSentenceAnalysis("send", "verb",
                "infinitive", null,
                "finished documents", "plural",
                "to marketing department", "singular");

        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }

    @Test
    @DisplayName("Analyze sentence correctly: Request Items")
    void analyzeSentence2() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Request Items";
        SentenceAnalyzer sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();

        SentenceAnalysis resultSentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);

        // TODO: Request is matched as a noun by freeling, i dont know if this is correct
        SentenceAnalysis expectedSentenceAnalysis = new SimpleSentenceAnalysis("request", "noun",
                                                                            null, "singular",
                                                                            "items", "plural",
                                                                            null, null);

        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }

    @Test
    @DisplayName("Analyze sentence correctly: Chase the monkey")
    void analyzeSentence3() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Chase the monkey";
        SentenceAnalyzer sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();

        SentenceAnalysis resultSentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);

        // TODO: Chase is matched as a noun by freeling, i dont know if this is correct
        SentenceAnalysis expectedSentenceAnalysis = new SimpleSentenceAnalysis("chase", "noun",
                null, "singular",
                "the monkey", "singular",
                null, null);

        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }

    @Test
    @DisplayName("Analyze sentence correctly: Receive payment confirmation")
    void analyzeSentence4() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Receive payment confirmation";
        SentenceAnalyzer sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();

        SentenceAnalysis resultSentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);

        SentenceAnalysis expectedSentenceAnalysis = new SimpleSentenceAnalysis("receive", "verb",
                "infinitive", null,
                "payment confirmation", "singular",
                null, null);

        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }

    @Test
    @DisplayName("Analyze sentence correctly: Receive Zoo's card")
    void analyzeSentence5() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Receive Zoo's card";
        SentenceAnalyzer sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();

        SentenceAnalysis resultSentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);

        SentenceAnalysis expectedSentenceAnalysis = new SimpleSentenceAnalysis("receive", "verb",
                "infinitive", null,
                "zoo 's card", "singular",
                null, null);

        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }

    @Test
    @DisplayName("Analyze sentence correctly: Charge zoo account")
    void analyzeSentence6() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Charge zoo account";
        SentenceAnalyzer sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();

        SentenceAnalysis resultSentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);

        SentenceAnalysis expectedSentenceAnalysis = new SimpleSentenceAnalysis("charge", "noun",
                null, "singular",
                "zoo account", "singular",
                null, null);

        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }

}