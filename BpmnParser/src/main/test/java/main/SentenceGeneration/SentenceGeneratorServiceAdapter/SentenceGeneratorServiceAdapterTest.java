package main.SentenceGeneration.SentenceGeneratorServiceAdapter;

import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SimpleSentenceAnalysis;
import main.Exceptions.NoFreelingKeyException;
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
    @DisplayName("Generate simple sentence correctly with  (Subject=Mary, Predicate=chase, Object=the monkey)")
    void generateSimpleSentenceWith_Subject_Verb_Object() throws NoFreelingKeyException {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis("chase", "noun",
                null, "singular",
                "the monkey", "singular",
                null, null);

        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String subject = "Mary";
        String generatedSentence = sentenceGeneratorServiceAdapter.generateSimpleSentence(sentenceAnalysis, subject);

        String expectedGeneratedSentence = "Mary chases the monkey.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generate simple sentence correctly with (Subject, Verb, Object, Complement)")
    void generateSimpleSentenceWith_Subject_Verb_Object_Complement() throws NoFreelingKeyException {
        SentenceAnalysis sentenceAnalysis;
        sentenceAnalysis = new SimpleSentenceAnalysis("send", "verb",
                                                    "infinitive", null,
                                                    "finished documents", "plural",
                                                    "to marketing department", "singular");

        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String subject = "Mary";
        String generatedSentence = sentenceGeneratorServiceAdapter.generateSimpleSentence(sentenceAnalysis, subject);

        String expectedGeneratedSentence = "Mary sends finished documents to marketing department.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generate simple sentence correctly with: Request items")
    void generateSimpleSentenceWith_Subject_Verb_Object_2() throws NoFreelingKeyException {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis("request", "noun",
                                                                        null, "singular",
                                                                        "items", "plural",
                                                                        null, null);

        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String subject = "Employee";
        String generatedSentence = sentenceGeneratorServiceAdapter.generateSimpleSentence(sentenceAnalysis, subject);

        String expectedGeneratedSentence = "Employee requests items.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

}