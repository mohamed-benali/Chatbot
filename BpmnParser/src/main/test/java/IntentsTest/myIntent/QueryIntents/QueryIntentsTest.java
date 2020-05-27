package IntentsTest.myIntent.QueryIntents;

import main.Entity.Intent.Intents;
import main.Entity.Intent.Intent.QueryIntent.WhatObjectQueryTaskIntent;
import main.Entity.Intent.Intent.QueryIntent.WhoSubjectQueryTaskIntent;
import main.Entity.Intent.Intent.TaskIntent;
import main.Entity.Intent.Intent.myIntent;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryIntentsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    @DisplayName("Create a QueryTaskIntents")
    void createQueryTaskIntent() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        String name = "taskName";
        String subject = "Employee";
        String tasca = "Send finished documents to Marketing Department";

        myIntent taskIntent = new TaskIntent(name, subject, tasca);

        Intents resultIntents = taskIntent.buildExtraIntents();

        Intents expectedIntents = createExpected_QueryTaskIntents(name, subject, tasca);

        assertEquals(expectedIntents, resultIntents);
    }

    private Intents createExpected_QueryTaskIntents(String name, String subject, String tasca) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        Intents expectedIntents = new Intents();

        myIntent intent_WHO_SUBJECT_query = new WhoSubjectQueryTaskIntent(name , subject, tasca);
        expectedIntents.add(intent_WHO_SUBJECT_query);

        myIntent intent_WHAT_OBJECT_query = new WhatObjectQueryTaskIntent(name , subject, tasca);
        expectedIntents.add(intent_WHAT_OBJECT_query);

        return expectedIntents;
    }
}