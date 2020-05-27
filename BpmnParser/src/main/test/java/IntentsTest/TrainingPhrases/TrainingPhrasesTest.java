package IntentsTest.TrainingPhrases;

import main.Entity.Intent.Intent.TrainingPhrases.NullTrainingPhrase;
import main.Entity.Intent.Intent.TrainingPhrases.myTrainingPhrases;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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


    @Test
    @DisplayName("Check copy method")
    void checkCopyMethod() throws IOException {
        myTrainingPhrases myTrainingPhrases = new myTrainingPhrases();
        myTrainingPhrases.addTrainingPhrase("hello");
        myTrainingPhrases.addTrainingPhrase("hello3");
        myTrainingPhrases.addTrainingPhrase("hello2");

        myTrainingPhrases.setHasNullTrainingPhrase(true);

        myTrainingPhrases copy = myTrainingPhrases.copy();

        assertEquals(myTrainingPhrases, copy);
    }

    @Test
    @DisplayName("Check copy method inmutability resitant")
    void checkCopyMethodInmutabilityResistant() throws IOException {
        myTrainingPhrases myTrainingPhrases = new myTrainingPhrases();
        myTrainingPhrases.addTrainingPhrase("hello");
        myTrainingPhrases.addTrainingPhrase("hello3");
        myTrainingPhrases.addTrainingPhrase("hello2");

        myTrainingPhrases copy = myTrainingPhrases.copy();
        copy.addTrainingPhrase("t");

        assertNotEquals(myTrainingPhrases, copy);
    }
}