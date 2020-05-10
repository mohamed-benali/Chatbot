package main.SentenceGeneration.SentenceBuilder;

import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SentenceAnalysis;
import main.SentenceGeneration.SentenceEntities.SentenceAnalysis.SimpleSentenceAnalysis;
import main.SentenceGeneration.SentenceGeneratorServiceAdapter.SentenceGeneratorServiceAdapter;
import main.SentenceGeneration.SentenceGeneratorServiceAdapter.SentenceGenerator_SimpleNLG_AdapterImpl;
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








    //region REGION: WHO_SUBJECT
    @Test
    @DisplayName("Generates a WHO_SUBJECT sentence: Who chases the monkey?.")
    void generateSentence6() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "chase the monkey";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildWhoSubjectSentence(sentence);


        String expectedGeneratedSentence = "Who chases the monkey?";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generates a WHO_SUBJECT sentence: Who sends finished documents to marketing department?.")
    void generateSentence7() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Send finished documents to Marketing Department";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildWhoSubjectSentence(sentence);

        String expectedGeneratedSentence = "Who sends finished documents to marketing department?";

        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("Generates a WHO_SUBJECT sentence: Who ships a parcel?")
    void generateSentence8() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Ship a Parcel";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildWhoSubjectSentence(sentence);


        String expectedGeneratedSentence = "Who ships a parcel?";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }
    //endregion

    @Test
    @DisplayName("Generates a WHAT_OBJECT sentence: What does Mary chase?")
    void generateWHAT_OBJECT_Sentence() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Chase the monkey";
        String subject = "Mary";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildWhatObjectSentence(sentence, subject);


        String expectedGeneratedSentence = "What does Mary chase?";


        assertEquals(expectedGeneratedSentence, generatedSentence);
    }
    

    @Test
    @DisplayName("Generates a WHAT_OBJECT sentence: What does Mary send to marketing department?")
    void generateWHAT_OBJECT_Sentence2() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Send finished documents to Marketing Department";
        String subject = "Mary";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildWhatObjectSentence(sentence, subject);

        String expectedGeneratedSentence = "What does Mary send to marketing department?";

        assertEquals(expectedGeneratedSentence, generatedSentence);
    }

    @Test
    @DisplayName("WorkAround! Generates a WHAT_OBJECT sentence: What sends Mary to marketing department?")
    void generateWorkAround_WHAT_OBJECT_Sentence2() throws InterruptedException, SentenceAnalyzerException, IOException, NoFreelingKeyException {
        String sentence = "Send finished documents to Marketing Department";
        String subject = "Mary";

        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl() ;
        String generatedSentence = sentenceBuilder.buildWhatObjectSentence_WorkAround_Without_Does(sentence, subject);

        String expectedGeneratedSentence = "What sends Mary to marketing department?";

        assertEquals(expectedGeneratedSentence, generatedSentence);
    }



}