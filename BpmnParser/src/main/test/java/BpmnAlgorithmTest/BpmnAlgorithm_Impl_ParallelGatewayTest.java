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

import static org.junit.jupiter.api.Assertions.assertEquals;

class BpmnAlgorithm_Impl_ParallelGatewayTest {

    static BpmnModelInstance modelInstanceParallelGateway;
    static BpmnAlgorithmImpl bpmnAlgorithmParallelGateway;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/BpmnAlgorithm";

    static String bpmnPathParallelGateway = componentTestingPath + "/diagramParallelGateway.bpmn";

    @BeforeAll
    static void setUp() {
        File fileParallelGateway = new File(bpmnPathParallelGateway);
        modelInstanceParallelGateway = Bpmn.readModelFromFile(fileParallelGateway);
    }

    @BeforeEach
    void setUpEach() {
        bpmnAlgorithmParallelGateway = new BpmnAlgorithmImpl(modelInstanceParallelGateway);
    }

    @AfterEach
    void tearDown() {
    }


    /*
     * diagramParallelGateway.bmpn
     */

    @Test // TODO: Store the solutions from a json file, so its easier and cleaner to make them
    @DisplayName("Parse participant Empleat from diagramParallelGateway.bpmn")
    void parseParticipant_empleat_diagramParallelGatewayBPMN() throws IOException {
        Intents resultIntents = new Intents();
        Participant ParticipantEmpleat = modelInstanceParallelGateway.getModelElementById("Participant_0uhnto1");
        resultIntents.add(bpmnAlgorithmParallelGateway.parseParticipant(ParticipantEmpleat));

        Intents expectedIntents = createExpectedIntentsEmpleat_diagramParallelGateway_BPMN();

        assertEquals(expectedIntents, resultIntents);

    }

    private Intents createExpectedIntentsEmpleat_diagramParallelGateway_BPMN() throws IOException {
        Intents expectedIntents = new Intents();

        // StartEvent
        myIntent intent = new myIntent("StartEvent_1e53g9j", "Employee", "Begin");
        intent.addBasicInfo("BEGIN_CONTEXT","StartEvent_1e53g9j",
                "Task_0kegxhu", "Employee");

        myIntent intent2 = new myIntent("Task_098k69d", "Employee", "Ship a Parcel");
        intent2.addBasicInfo("ExclusiveGateway_0t87582","ExclusiveGateway_0n01jaj",
                "Task_1pynbur", "No");

        myIntent intent3 = new myIntent("Task_0k9iu95", "Employee", "Fetch the items");
        intent3.addBasicInfo("Task_0kegxhu","Task_0k9iu95",
                "ExclusiveGateway_0t87582", null);

        myIntent intent4 = new myIntent("Task_0kegxhu", "Employee", "Request items");
        intent4.addBasicInfo("StartEvent_1e53g9j","Task_0kegxhu",
                "Task_0k9iu95", null);

        myIntent intent5 = new myIntent("Task_1pynbur", "Employee", "Deliver the order");
        intent5.addBasicInfo("ExclusiveGateway_0n01jaj","Task_1pynbur",
                "EndEvent_06wuojd", null);

        myIntent intent6 = new myIntent("Task_1t8hbl4", "Employee", "Ship a Parcel with Transport insurance");
        intent6.addBasicInfo("ExclusiveGateway_0t87582","ExclusiveGateway_0n01jaj",
                "Task_1pynbur", "Yes");

        myIntent intent7 = new myIntent("ExclusiveGateway_0t87582", "Employee", "Price over 100?");
        intent7.addBasicInfo("Task_0k9iu95","ExclusiveGateway_0t87582",
                "Task_1t8hbl4", null);
        intent7.addOutputIntentID("Task_098k69d");

        myIntent intent8 = new myIntent("EndEvent_06wuojd", "Employee", "End");
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
    @DisplayName("Parse participant Departament from diagramParallelGateway.bpmn")
    void parseParticipant_departament_diagramParallelGatewayBPMN() throws IOException {
        Intents resultIntents = new Intents();
        Participant ParticipantEmpleat = modelInstanceParallelGateway.getModelElementById("Participant_0yo93vp");
        resultIntents.add(bpmnAlgorithmParallelGateway.parseParticipant(ParticipantEmpleat));

        Intents expectedIntents = createExpectedIntentsDepartament_diagramParallelGateway_BPMN();

        assertEquals(expectedIntents, resultIntents);

    }

    // TODO: Put the correct intents
    private Intents createExpectedIntentsDepartament_diagramParallelGateway_BPMN() throws IOException {
        Intents expectedIntents = new Intents();

        // StartEvent
        myIntent intent = new myIntent("StartEvent_1b1dcy2", "Department", "Begin");
        intent.addBasicInfo("BEGIN_CONTEXT","StartEvent_1b1dcy2",
                "Task_1vunmit", "Department");

        myIntent intent2 = new myIntent("Task_1vunmit", "Department", "Get the items");
        intent2.addBasicInfo("StartEvent_1b1dcy2","Task_1vunmit",
                "Task_1wuzo3e", null);

        myIntent intent3 = new myIntent("Task_1wuzo3e", "Department", "Prepare the items");
        intent3.addBasicInfo("Task_1vunmit","Task_1wuzo3e",
                "Activity_1o8a9ib", null);


        myIntent intent4 = new myIntent("EndEvent_0irzcwa", "Department", "End");
        intent4.addInputContextID("Gateway_1pv0p63");
        intent4.addOutputContextID("EndEvent_0irzcwa");
        intent4.addTrainingPhrase(null);

        expectedIntents.add(intent); //
        expectedIntents.add(intent2);
        expectedIntents.add(intent3); //
        expectedIntents.add(intent4); //


        myIntent intent5 = new myIntent("Activity_0941ghy", "Department", "Update the Stock");
        intent5.addBasicInfo("Activity_1o8a9ib","Activity_0941ghy",
                "Activity_114hvc6", null);

        myIntent intent6 = new myIntent("Activity_114hvc6", "Department", "Update the records");
        intent6.addBasicInfo("Activity_0941ghy","Gateway_1pv0p63",
                "EndEvent_0irzcwa", null);

        myIntent intent7 = new myIntent("Activity_1o8a9ib", "Department", "Check the stock");
        intent7.addBasicInfo("Task_1wuzo3e","Activity_1o8a9ib",
                "Activity_0941ghy", null);

        expectedIntents.add(intent5); //
        expectedIntents.add(intent6); //
        expectedIntents.add(intent7); //




        return expectedIntents;
    }


}