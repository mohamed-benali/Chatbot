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
class SpinnerChief_SentenceParaphraserImplTest {

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
        // {Who|That|Who else|Which} requests {the items|the things|the products|those items}?

        Sentences sentences = new Sentences();
        sentences.addSentence("Who requests the items?");
        sentences.addSentence("Who requests the things?");
        sentences.addSentence("Who requests the products?");
        sentences.addSentence("Who requests those items?");

        sentences.addSentence("That requests the items?");
        sentences.addSentence("That requests the things?");
        sentences.addSentence("That requests the products?");
        sentences.addSentence("That requests those items?");

        sentences.addSentence("Who else requests the items?");
        sentences.addSentence("Who else requests the things?");
        sentences.addSentence("Who else requests the products?");
        sentences.addSentence("Who else requests those items?");

        sentences.addSentence("Which requests the items?");
        sentences.addSentence("Which requests the things?");
        sentences.addSentence("Which requests the products?");
        sentences.addSentence("Which requests those items?");

        expectedSentences.addMultipleParaphrasedSentences(sentence, sentences);

        return expectedSentences;
    }




    @Test
    @DisplayName("Paraphrase one sentence: {Who|That|Who else} requests {the items|the things}?") // TODO: Do it
    void generateSentencesBacktracking() throws IOException, InterruptedException, SpinnerChief_SentenceParaphraserException {

        String sentenceKey = "Who requests the items?";
        String sentenceToParse = "{Who|That|Who else} requests {the items|the things}?";

        SpinnerChief_SentenceParaphraserImpl sentenceParaphraser = new SpinnerChief_SentenceParaphraserImpl() ;
        ParaphrasedSentences similarSentences = sentenceParaphraser.paraphraseOneSentence(sentenceToParse, sentenceKey);

        ParaphrasedSentences expectedGeneratedSentences = generateExpectedSentencesBacktracking(sentenceKey);

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

        expectedSentences.addMultipleParaphrasedSentences(sentence, sentences);

        return expectedSentences;
    }


}