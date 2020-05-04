package IntentsTest.TrainingPhrases;

import main.Entity.Intent.Intents;
import main.Entity.Intent.TrainingPhrases.myTrainingPhrases;
import main.Entity.Intent.myIntent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainingPhrasesTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    @DisplayName("Check equals method")
    void checkEqualsMethod() throws IOException {
        myTrainingPhrases myTrainingPhrases = new myTrainingPhrases();
        myTrainingPhrases.addTrainingPhrase("hello");

        myTrainingPhrases myTrainingPhrases2 = new myTrainingPhrases();
        myTrainingPhrases2.addTrainingPhrase("hello");


        assertEquals(myTrainingPhrases, myTrainingPhrases2);
    }
}