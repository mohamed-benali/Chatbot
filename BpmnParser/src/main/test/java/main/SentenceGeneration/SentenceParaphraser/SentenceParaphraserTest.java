package main.SentenceGeneration.SentenceParaphraser;

import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentence;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChief_SentenceParaphraserImpl;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class SentenceParaphraserTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }





    @Test
    @DisplayName("Paraphrase sentence: Who requests the items?")
    void generateSentences() throws IOException, InterruptedException, SpinnerChief_SentenceParaphraserException {

        List<String> sentences = new ArrayList<>();
        String sentence = "Who requests the items?";
        sentences.add(sentence);

        SentenceParaphraser sentenceParaphraser = new SpinnerChief_SentenceParaphraserImpl() ;

        ParaphrasedSentences similarSentences = sentenceParaphraser.paraphraseSentence(sentences);

        ParaphrasedSentences expectedGeneratedSentences = generateExpectedSimilarSententces(sentence);

        assertEquals(expectedGeneratedSentences, similarSentences);
    }

    private ParaphrasedSentences generateExpectedSimilarSententces(String sentence) {
        ParaphrasedSentences expectedSentences = new ParaphrasedSentences();
        // {Who|That|Who else} requests {the items|the things}?

        Sentences sentences = new Sentences();
        sentences.addSentence("Who requests the items?");
        sentences.addSentence("Who requests the things?");

        sentences.addSentence("That requests the items?");
        sentences.addSentence("That requests the things?");

        sentences.addSentence("Who else requests the items?");
        sentences.addSentence("Who else requests the things?");

        expectedSentences.addMultipleParaphrasedSentencesToNewSentence(sentence, sentences);

        return expectedSentences;
    }




    @Test
    @DisplayName("Paraphrase sentence Backtracking: Who requests the items?") // TODO: Do it
    void generateSentencesBacktracking() throws IOException, InterruptedException, SpinnerChief_SentenceParaphraserException {

        List<String> sentences = new ArrayList<>();
        String sentence = "Who requests the items?";
        sentences.add(sentence);

        SentenceParaphraser sentenceParaphraser = new SpinnerChief_SentenceParaphraserImpl() ;

        ParaphrasedSentences similarSentences = sentenceParaphraser.paraphraseSentence(sentences);

        ParaphrasedSentences expectedGeneratedSentences = generateExpectedSimilarSententces(sentence);

        assertEquals(expectedGeneratedSentences, similarSentences);
    }

    private ParaphrasedSentences generateExpectedSentencesBacktracking(String sentence) {
        ParaphrasedSentences expectedSentences = new ParaphrasedSentences();
        // {Who|That|Who else} requests {the items|the things}?

        Sentences sentences = new Sentences();
        sentences.addSentence("Who requests the items?");
        sentences.addSentence("Who requests the things?");

        sentences.addSentence("That requests the items?");
        sentences.addSentence("That requests the things?");

        sentences.addSentence("Who else requests the items?");
        sentences.addSentence("Who else requests the things?");

        expectedSentences.addMultipleParaphrasedSentencesToNewSentence(sentence, sentences);

        return expectedSentences;
    }


}