package IntentsTest.myIntent.QueryIntents.WhatObjectQueryTaskIntent;

import main.Entity.Intent.Intents;
import main.Entity.Intent.QueryIntent.WhoSubjectQueryTaskIntent;
import main.Entity.Intent.TaskIntent;
import main.Entity.Intent.myIntent;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WhatObjectQueryTaskIntentTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    @DisplayName("Create a WHO_SUBJECT query task intent")
    void createQueryTaskIntent() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        String name = "taskName";
        String subject = "Employee";
        String tasca = "Ship a Parcel";

        myIntent taskIntent = new TaskIntent(name, subject, tasca);

        Intents resultIntents = taskIntent.buildExtraIntents();

        Intents expectedIntents = createExpected_WHO_SUBJECT_QueryTaskIntents(name, subject, tasca);

        assertEquals(expectedIntents, resultIntents);
    }

    private Intents createExpected_WHO_SUBJECT_QueryTaskIntents(String name, String subject, String tasca) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        Intents expectedIntents = new Intents();

        myIntent intent_query = new WhoSubjectQueryTaskIntent(name , subject, tasca);
        expectedIntents.add(intent_query);

        return expectedIntents;
    }
}