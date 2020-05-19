package BpmnAlgorithmTest;

import main.Entity.Intent.Intents;
import main.Entity.Intent.myIntent;
import main.Entity.Parser.BpmnAlgorithm.BpmnAlgorithmImpl;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BpmnAlgorithmImplTest {

    static BpmnModelInstance modelInstance;
    static BpmnAlgorithmImpl bpmnAlgorithm;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/BpmnAlgorithm";

    static String bpmnPath = componentTestingPath + "/diagram.bpmn";

    @BeforeAll
    static void setUp() {
        File file = new File(bpmnPath);
        modelInstance = Bpmn.readModelFromFile(file);
    }

    @BeforeEach
    void setUpEach() {
        bpmnAlgorithm = new BpmnAlgorithmImpl(modelInstance);
    }

    @AfterEach
    void tearDown() {
    }

    // TODO: Convert to Intents to string, compare the string so it tells where is the difference

    /*
     * diagram.bmpn
     */

    @Test // TODO: Store the solutions from a json file, so its easier and cleaner to make them
    @DisplayName("Parse participant Empleat from diagram.bpmn")
    void parseParticipant_empleat_diagramBPMN() throws IOException {
        Intents resultIntents = new Intents();
        Participant ParticipantEmpleat = modelInstance.getModelElementById("Participant_0uhnto1");
        resultIntents.add(bpmnAlgorithm.parseParticipant(ParticipantEmpleat));

        Intents expectedIntents = createExpectedIntentsEmpleat_diagram_BPMN();

        assertEquals(expectedIntents, resultIntents);

    }

    private Intents createExpectedIntentsEmpleat_diagram_BPMN() throws IOException {
        Intents expectedIntents = new Intents();

        // StartEvent
        myIntent intent = new myIntent("StartEvent_1e53g9j", "Empleat", "Begin");
        intent.addBasicInfo("BEGIN_CONTEXT","StartEvent_1e53g9j",
                "Task_0kegxhu", "Empleat");

        myIntent intent2 = new myIntent("Task_098k69d", "Empleat", "Ship a Parcel");
        intent2.addBasicInfo("ExclusiveGateway_0t87582","ExclusiveGateway_0n01jaj",
                "Task_1pynbur", "No");

        myIntent intent3 = new myIntent("Task_0k9iu95", "Empleat", "Fetch the items");
        intent3.addBasicInfo("Task_0kegxhu","Task_0k9iu95",
                "ExclusiveGateway_0t87582", null);

        myIntent intent4 = new myIntent("Task_0kegxhu", "Empleat", "Request items");
        intent4.addBasicInfo("StartEvent_1e53g9j","Task_0kegxhu",
                "Task_0k9iu95", null);

        myIntent intent5 = new myIntent("Task_1pynbur", "Empleat", "Deliver order");
        intent5.addBasicInfo("ExclusiveGateway_0n01jaj","Task_1pynbur",
                "EndEvent_06wuojd", null);

        myIntent intent6 = new myIntent("Task_1t8hbl4", "Empleat", "Ship a Parcel with Transport insurance");
        intent6.addBasicInfo("ExclusiveGateway_0t87582","ExclusiveGateway_0n01jaj",
                "Task_1pynbur", "Yes");

        myIntent intent7 = new myIntent("ExclusiveGateway_0t87582", "Empleat", "Price over 100?");
        intent7.addBasicInfo("Task_0k9iu95","ExclusiveGateway_0t87582",
                "Task_1t8hbl4", null);
        intent7.addOutputIntentID("Task_098k69d");

        myIntent intent8 = new myIntent("EndEvent_06wuojd", "Empleat", "End");
        intent8.addInputContextID("Task_1pynbur");
        intent8.addOutputContextID("EndEvent_06wuojd");
        intent8.addTrainingPhrase(null);

        expectedIntents.add(intent); //
        expectedIntents.add(intent2);
        expectedIntents.add(intent3); //
        expectedIntents.add(intent4); //
        expectedIntents.add(intent5); //
        expectedIntents.add(intent6); //
        expectedIntents.add(intent7); //
        expectedIntents.add(intent8); //

        return expectedIntents;
    }




    @Test // TODO: Store the solutions from a json file, so its easier and cleaner to make them
    @DisplayName("Parse participant Departament from diagram.bpmn")
    void parseParticipant_departament_diagramBPMN() throws IOException {
        Intents resultIntents = new Intents();
        Participant ParticipantEmpleat = modelInstance.getModelElementById("Participant_0yo93vp");
        resultIntents.add(bpmnAlgorithm.parseParticipant(ParticipantEmpleat));

        Intents expectedIntents = createExpectedIntentsDepartament_diagram_BPMN();

        Map<String, myIntent> resultIntentsMap = resultIntents.getIntents(); // String: name(identificador)
        Map<String, myIntent> expectedIntentsMap = expectedIntents.getIntents(); // String: name(identificador)
        assertEquals(expectedIntentsMap, resultIntentsMap);

        //assertEquals(expectedIntents, resultIntents);

    }

    private Intents createExpectedIntentsDepartament_diagram_BPMN() throws IOException {
        Intents expectedIntents = new Intents();

        // StartEvent
        myIntent intent = new myIntent("StartEvent_1b1dcy2", "Departament", "Begin");
        intent.addBasicInfo("BEGIN_CONTEXT","StartEvent_1b1dcy2",
                "Task_1vunmit", "Departament");

        myIntent intent2 = new myIntent("Task_1vunmit", "Departament", "Get items");
        intent2.addBasicInfo("StartEvent_1b1dcy2","Task_1vunmit",
                "Task_1wuzo3e", null);

        myIntent intent3 = new myIntent("Task_1wuzo3e", "Departament", "Prepare the items");
        intent3.addBasicInfo("Task_1vunmit","Task_1wuzo3e",
                "EndEvent_0irzcwa", null);

        myIntent intent4 = new myIntent("EndEvent_0irzcwa", "Departament", "End");
        intent4.addInputContextID("Task_1wuzo3e");
        intent4.addOutputContextID("EndEvent_0irzcwa");
        intent4.addTrainingPhrase(null);

        expectedIntents.add(intent); //
        expectedIntents.add(intent2);
        expectedIntents.add(intent3); //
        expectedIntents.add(intent4); //


        return expectedIntents;
    }




}