package IntentsTest.Intents;

import main.Entity.Intent.Intents;
import main.Entity.Intent.Intent.myIntent;
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

    protected String quotes(String s) {
        char quote = '"';
        if(s==null || s.equals("")) return null;
        else return quote + s + quote;
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

    @Test
    @DisplayName("Check Create code method")
    void checkCreateCodesMethod() throws IOException {
        Intents intents = new Intents();
        myIntent intent = new myIntent("name", "subject", "task");
        intent.addBasicInfo("rgrfrfr","BEGIN_CONTEXT","StartEvent_1yl25iv",
                "Task_0kegxhu", null);

        intents.add(intent);

        myIntent intent2 = new myIntent("name2", "subject2", "task2");
        intent2.addBasicInfo("rgrfrfr2","BEGIN_CONTEXT2","StartEvent_1yl25iv2",
                null, null);
        intent2.clearOutputIntents();

        intents.add(intent2);


        String resultCode = intents.createCode();

        String expectedCode = expectedCode();

        assertEquals(expectedCode,resultCode);
    }

    private String expectedCode() {
        String expectedCode;
        expectedCode = "myIntent intentname = new myIntent("+quotes("name") +","+quotes("subject") + "," +quotes("task") + ");";
        expectedCode += "\n";
        expectedCode+="intentname.addBasicInfo("+quotes("rgrfrfr") + "," + quotes("BEGIN_CONTEXT") + "," + "\n" +
                quotes("StartEvent_1yl25iv") + "," + quotes("Task_0kegxhu") + "," + "null" + ");" + "\n";
        expectedCode += "\n";

        expectedCode += "myIntent intentname2 = new myIntent("+quotes("name2") +","+quotes("subject2") + "," +quotes("task2") + ");";
        expectedCode += "\n";
        expectedCode+="intentname2.addBasicInfo("+quotes("rgrfrfr2") + "," + quotes("BEGIN_CONTEXT2") + "," +  "\n" +
                quotes("StartEvent_1yl25iv2") + "," + "null" + "," + "null" + ");" + "\n";
        expectedCode += "\n";

        return expectedCode;
    }


}