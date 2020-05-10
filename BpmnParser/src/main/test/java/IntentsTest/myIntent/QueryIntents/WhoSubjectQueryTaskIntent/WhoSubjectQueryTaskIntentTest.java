package IntentsTest.myIntent.QueryIntents.WhoSubjectQueryTaskIntent;

import main.Entity.Intent.QueryIntent.QueryTaskIntent;
import main.Entity.Intent.QueryIntent.WhoSubjectQueryTaskIntent;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WhoSubjectQueryTaskIntentTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    @DisplayName("Response of WhoSubject_QueryTaskIntent: Employee sends finished documents to marketing department")
    void createQueryTaskIntent() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        String name = "taskName";
        String subject = "Employee";
        String tasca = "Send finished documents to Marketing Department";

        QueryTaskIntent intent_WHO_SUBJECT_query = new WhoSubjectQueryTaskIntent(name , subject, tasca);
        List<String> resultResponse = intent_WHO_SUBJECT_query.makeResponse();

        List<String> expectedResponse = new ArrayList<>();
        expectedResponse.add("Employee sends finished documents to marketing department.");

        assertEquals(expectedResponse, resultResponse);
    }

}