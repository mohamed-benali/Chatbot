package IntentsTest;

import com.google.cloud.dialogflow.v2.Intent;
import main.Entity.Intent.Intents;
import main.Entity.Intent.myIntent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IntentsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    @DisplayName("Check equals method")
    void checkEqualsMethod() throws IOException {
        Intents intents = new Intents();
        myIntent intent = new myIntent("name", "subject", "task");
        intents.add(intent);

        Intents intents2 = new Intents();
        myIntent intent2 = new myIntent("name", "subject", "task");
        intents2.add(intent2);


        assertEquals(intents, intents2);
    }
}