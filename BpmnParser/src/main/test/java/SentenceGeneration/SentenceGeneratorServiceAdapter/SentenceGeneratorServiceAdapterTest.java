package SentenceGeneration.SentenceGeneratorServiceAdapter;

import SentenceGeneration.Entity.SentenceAnalysis.SentenceAnalysis;
import SentenceGeneration.Entity.SentenceAnalysis.SimpleSentenceAnalysis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SentenceGeneratorServiceAdapterTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }


    @Test
    @DisplayName("Generate simple sentence correctly with (Subject, Verb, Object")
    void generateSimpleSentenceWith_Subject_Verb_Object() {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis( "chase",
                                                                        "the monkey",
                                                                        null);

        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String subject = "Mary";
        String generatedSentence = sentenceGeneratorServiceAdapter.generateSimpleSentence(sentenceAnalysis, subject);

        String expectedGeneratedSentence = "Mary chases the monkey.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generate simple sentence correctly with (Subject, Verb, Object, Complement)")
    void generateSimpleSentenceWith_Subject_Verb_Object_Complement() {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis( "send",
                                                                        "finished documents",
                                                                        "to marketing department");

        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String subject = "Mary";
        String generatedSentence = sentenceGeneratorServiceAdapter.generateSimpleSentence(sentenceAnalysis, subject);

        String expectedGeneratedSentence = "Mary sends finished documents to marketing department.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generate simple sentence correctly with: Request items")
    void generateSimpleSentenceWith_Subject_Verb_Object_2() {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis( "request",
                "items",
                null);

        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String subject = "Employee";
        String generatedSentence = sentenceGeneratorServiceAdapter.generateSimpleSentence(sentenceAnalysis, subject);

        String expectedGeneratedSentence = "Employee requests items";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

}