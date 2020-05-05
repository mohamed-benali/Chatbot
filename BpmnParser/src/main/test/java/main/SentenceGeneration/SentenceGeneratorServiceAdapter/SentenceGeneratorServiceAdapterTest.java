package main.SentenceGeneration.SentenceGeneratorServiceAdapter;

import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceBuilder.SentenceBuilder;
import main.SentenceGeneration.SentenceBuilder.SentenceBuilderImpl;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SimpleSentenceAnalysis;
import main.Exceptions.NoFreelingKeyException;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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



    @Test
    @DisplayName("Generates a WHO_SUBJECT sentence: with (Verb, Object, Complement).")
    void generateWHO_SUBJECT_Sentence2() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis("send", "verb",
                "infinitive", null,
                "finished documents", "plural",
                "to marketing department", "singular");

        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String generatedSentence = sentenceGeneratorServiceAdapter.generateWhoSubjectSentence(sentenceAnalysis);

        String expectedGeneratedSentence = "Who sends finished documents to marketing department?";

        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generates a WHO_SUBJECT sentence: Mary chases the monkey.")
    void generateWHO_SUBJECT_Sentence() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis("chase", "noun",
                null, "singular",
                "the monkey", "singular",
                null, null);

        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String generatedSentence = sentenceGeneratorServiceAdapter.generateWhoSubjectSentence(sentenceAnalysis);

        String expectedGeneratedSentence = "Who chases the monkey?";

        assertEquals(expectedGeneratedSentence, generatedSentence);
    }









    @Test
    @DisplayName("Generates a WHAT_OBJECT sentence: with (Subject, Verb, Object, Complement).")
    void generateWHO_SUBJECT_Sentence3() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis("send", "verb",
                "infinitive", null,
                "finished documents", "plural",
                "to marketing department", "singular");

        String subject = "Mary";
        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String generatedSentence = sentenceGeneratorServiceAdapter.generateWhatObjectSentence(sentenceAnalysis, subject);

        String expectedGeneratedSentence = "What does Mary send to marketing department?";

        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generates a WHAT_OBJECT sentence: What does Mary chase?")
    void generateWHAT_OBJECT_Sentence() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        SentenceAnalysis sentenceAnalysis = new SimpleSentenceAnalysis("chase", "noun",
                null, "singular",
                "the monkey", "singular",
                null, null);

        String subject = "Mary";
        SentenceGeneratorServiceAdapter sentenceGeneratorServiceAdapter = new SentenceGenerator_SimpleNLG_AdapterImpl();
        String generatedSentence = sentenceGeneratorServiceAdapter.generateWhatObjectSentence(sentenceAnalysis, subject);

        String expectedGeneratedSentence = "What does Mary chase?";

        assertEquals(expectedGeneratedSentence, generatedSentence);
    }



}