package SentenceGeneration.SentenceGenerator;

import SentenceGeneration.Entity.SentenceAnalysis.SentenceAnalysis;
import SentenceGeneration.Entity.SentenceAnalysis.SimpleSentenceAnalysis;
import main.Exceptions.SentenceAnalyzerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceGeneratorTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

/*
    @Test
    @DisplayName("Analyze sentence correctly (Predicate, Object, Complement)")
    void generateSentence() throws InterruptedException, SentenceAnalyzerException, IOException {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis( "send",
                                                                        "finished documents",
                                                                        "to marketing department");

        SentenceGenerator = new SentenceGeneratorImpl();




        assertEquals(expectedSentenceAnalysis, resultSentenceAnalysis);
    }
*/
}