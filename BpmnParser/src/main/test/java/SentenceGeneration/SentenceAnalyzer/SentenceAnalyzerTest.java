package SentenceGeneration.SentenceAnalyzer;

import SentenceGeneration.Entity.SentenceAnalysis.SentenceAnalysis;
import SentenceGeneration.Entity.SentenceAnalysis.SimpleSentenceAnalysis;
import SentenceGeneration.SentenceAnalyzer.SentenceAnalyzer;
import SentenceGeneration.SentenceAnalyzer.FreelingSimpleSentenceAnalyzerImpl;
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
    void analyzeSentence() throws InterruptedException, SentenceAnalyzerException, IOException {
        String sentence = "Send finished documents to Marketing Department.";
        SentenceAnalyzer sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();

        SentenceAnalysis resultSentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);

        SentenceAnalysis expectedSentenceAnalysis = new SimpleSentenceAnalysis( "send",
                                                                                "finished documents",
                                                                                "to marketing department");

        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }

    @Test
    @DisplayName("Analyze sentence correctly: Request Items")
    void analyzeSentence2() throws InterruptedException, SentenceAnalyzerException, IOException {
        String sentence = "Request Items";
        SentenceAnalyzer sentenceAnalyzer = new FreelingSimpleSentenceAnalyzerImpl();

        SentenceAnalysis resultSentenceAnalysis = sentenceAnalyzer.analyzeSentence(sentence);

        SentenceAnalysis expectedSentenceAnalysis = new SimpleSentenceAnalysis( "request",
                "items",
                null);

        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }

}