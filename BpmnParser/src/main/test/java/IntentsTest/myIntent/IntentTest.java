package IntentsTest.myIntent;

import main.Entity.Intent.Intent.myIntent;
import main.Entity.Intent.Intents;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IntentTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    @DisplayName("Check copy method")
    void checkCopyMethod() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        myIntent intent = new myIntent("name", "subject", "task");
        intent.addInputContextID("ic");
        intent.addInputContextID("ic2");
        intent.addOutputContextID("oc2");
        intent.addOutputContextID("oc1");
        intent.addOutputContextID("oc3");
        intent.addOutputIntentID("oi5");
        intent.addOutputIntentID("oi2");
        intent.addOutputIntentID("oi3");
        intent.addOutputIntentID("oi1");
        intent.addTrainingPhrase("tp");
        intent.addTrainingPhrase("tp43");
        intent.addTrainingPhrase("tp2");


        myIntent copy = intent.copy();

        assertEquals(intent,copy);
    }


    @Test
    @DisplayName("Check copy method  inmutability resitant")
    void checkCopyMethodInmutabilityResistant() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        myIntent intent = new myIntent("name", "subject", "task");
        intent.addInputContextID("ii");
        intent.addTrainingPhrase("tp");


        myIntent copy = intent.copy();
        copy.addInputContextID("ii2");

        assertNotEquals(intent,copy);
    }


    @Test
    @DisplayName("Build Extra Intents")
    void buildExtraIntents() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        myIntent intent = new myIntent("name", "subject", "task");
        intent.addInputContextID("ic");
        intent.addInputContextID("ic2");
        intent.addOutputContextID("oc2");
        intent.addOutputContextID("oc1");
        intent.addOutputContextID("oc3");
        intent.addOutputIntentID("oi5");
        intent.addOutputIntentID("oi2");
        intent.addOutputIntentID("oi3");
        intent.addOutputIntentID("oi1");
        intent.addTrainingPhrase("tp");
        intent.addTrainingPhrase("tp43");
        intent.addTrainingPhrase("tp2");


        myIntent copy = intent.copy();

        assertEquals(intent,copy);
    }







}