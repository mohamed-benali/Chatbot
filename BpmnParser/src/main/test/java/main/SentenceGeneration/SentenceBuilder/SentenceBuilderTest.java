package main.SentenceGeneration.SentenceBuilder;

import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceBuilderTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }


    @Test
    @DisplayName("Generates a sentence: Employee sends finished documents to marketing department.")
    void generateSentence() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Send finished documents to Marketing Department";
        String subject = "Employee";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildSentence(sentence, subject);


        String expectedGeneratedSentence = "Employee sends finished documents to marketing department.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generates a sentence: Employee requests items.")
    void generateSentence2() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Request items";
        String subject = "Employee";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildSentence(sentence, subject);


        String expectedGeneratedSentence = "Employee requests items.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generates a sentence: Employee fetches items.")
    void generateSentence3() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Fetch items";
        String subject = "Employee";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildSentence(sentence, subject);


        String expectedGeneratedSentence = "Employee fetches items.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generates a sentence: Employee gets the items.")
    void generateSentence4() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Get the items";
        String subject = "Employee";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildSentence(sentence, subject);


        String expectedGeneratedSentence = "Employee gets the items.";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generates a Collaboration sentence: Send the request to the Department.")
    void generateSentence5() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Send the request";
        String sourceSubject = "Employee";
        String targetSubject ="Department";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildSentence(sentence, sourceSubject, targetSubject);

        String expectedGeneratedSentence = "Employee sends the request to the department.";

        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

}