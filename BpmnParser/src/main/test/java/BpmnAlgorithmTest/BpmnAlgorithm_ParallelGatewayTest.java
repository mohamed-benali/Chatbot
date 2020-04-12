package BpmnAlgorithmTest;

import main.Entity.Intent.Intents;
import main.Entity.Intent.myIntent;
import main.Entity.Parser.BpmnAlgorithm;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BpmnAlgorithm_ParallelGatewayTest {

    static BpmnModelInstance modelInstanceParallelGateway;
    static BpmnAlgorithm bpmnAlgorithmParallelGateway;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/BpmnAlgorithm";

    static String bpmnPathParallelGateway = componentTestingPath + "/diagramParallelGateway.bpmn";


    @BeforeEach
    void setUp() {
        File fileParallelGateway = new File(bpmnPathParallelGateway);
        modelInstanceParallelGateway = Bpmn.readModelFromFile(fileParallelGateway);

        bpmnAlgorithmParallelGateway = new BpmnAlgorithm(modelInstanceParallelGateway);
    }

    @AfterEach
    void tearDown() {
    }

    // TODO: Convert to Intents to string, compare the string so it tells where is the difference


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
        myIntent intent = new myIntent("StartEvent_1e53g9j", "Empleat", "Begin");
        intent.addBasicInfo("BEGIN_CONTEXT","StartEvent_1e53g9j",
                "Task_0kegxhu", "Empleat");

        myIntent intent2 = new myIntent("Task_098k69d", "Empleat", "Ship a Parcel");
        intent2.addBasicInfo("ExclusiveGateway_0t87582","ExclusiveGateway_0n01jaj",
                "Task_1pynbur", "No");
        myIntent intent2_query = new myIntent("Task_098k69d_query", "Empleat", "Ship a Parcel");
        intent2_query.addTrainingPhrase("Who Ship a Parcel");

        myIntent intent3 = new myIntent("Task_0k9iu95", "Empleat", "Fetch the items");
        intent3.addBasicInfo("Task_0kegxhu","Task_0k9iu95",
                "ExclusiveGateway_0t87582", null);
        myIntent intent3_query = new myIntent("Task_0k9iu95_query", "Empleat", "Fetch the items");
        intent3_query.addTrainingPhrase("Who Fetch the items");

        myIntent intent4 = new myIntent("Task_0kegxhu", "Empleat", "Request items");
        intent4.addBasicInfo("StartEvent_1e53g9j","Task_0kegxhu",
                "Task_0k9iu95", null);
        myIntent intent4_query = new myIntent("Task_0kegxhu_query", "Empleat", "Request items");
        intent4_query.addTrainingPhrase("Who Request items");

        myIntent intent5 = new myIntent("Task_1pynbur", "Empleat", "Deliver order");
        intent5.addBasicInfo("ExclusiveGateway_0n01jaj","Task_1pynbur",
                "EndEvent_06wuojd", null);
        myIntent intent5_query = new myIntent("Task_1pynbur_query", "Empleat", "Deliver order");
        intent5_query.addTrainingPhrase("Who Deliver order");

        myIntent intent6 = new myIntent("Task_1t8hbl4", "Empleat", "Ship a Parcel with Transport insurance");
        intent6.addBasicInfo("ExclusiveGateway_0t87582","ExclusiveGateway_0n01jaj",
                "Task_1pynbur", "Yes");
        myIntent intent6_query = new myIntent("Task_1t8hbl4_query", "Empleat", "Ship a Parcel with Transport insurance");
        intent6_query.addTrainingPhrase("Who Ship a Parcel with Transport insurance");

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
        expectedIntents.add(intent2_query);
        expectedIntents.add(intent3); //
        expectedIntents.add(intent3_query);
        expectedIntents.add(intent4); //
        expectedIntents.add(intent4_query);
        expectedIntents.add(intent5); //
        expectedIntents.add(intent5_query);
        expectedIntents.add(intent6); //
        expectedIntents.add(intent6_query);
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
        myIntent intent = new myIntent("StartEvent_1b1dcy2", "Departament", "Begin");
        intent.addBasicInfo("BEGIN_CONTEXT","StartEvent_1b1dcy2",
                "Task_1vunmit", "Departament");

        myIntent intent2 = new myIntent("Task_1vunmit", "Departament", "Get items");
        intent2.addBasicInfo("StartEvent_1b1dcy2","Task_1vunmit",
                "Task_1wuzo3e", null);
        myIntent intent2_query = new myIntent("Task_1vunmit_query", "Departament", "Get items");
        intent2_query.addTrainingPhrase("Who Get items");

        myIntent intent3 = new myIntent("Task_1wuzo3e", "Departament", "Prepare the items");
        intent3.addBasicInfo("Task_1vunmit","Task_1wuzo3e",
                "Activity_1o8a9ib", null);
        myIntent intent3_query = new myIntent("Task_1wuzo3e_query", "Departament", "Prepare the items");
        intent3_query.addTrainingPhrase("Who Prepare the items");

        myIntent intent4 = new myIntent("EndEvent_0irzcwa", "Departament", "End");
        intent4.addInputContextID("Gateway_1pv0p63");
        intent4.addOutputContextID("EndEvent_0irzcwa");
        intent4.addTrainingPhrase(null);

        expectedIntents.add(intent); //
        expectedIntents.add(intent2);
        expectedIntents.add(intent2_query);
        expectedIntents.add(intent3); //
        expectedIntents.add(intent3_query);
        expectedIntents.add(intent4); //


        myIntent intent5 = new myIntent("Activity_0941ghy", "Departament", "Update Stock");
        intent5.addBasicInfo("Activity_1o8a9ib","Activity_0941ghy",
                "Activity_114hvc6", null);
        myIntent intent5_query = new myIntent("Activity_0941ghy_query", "Departament", "Update Stock");
        intent5_query.addTrainingPhrase("Who Update Stock");

        myIntent intent6 = new myIntent("Activity_114hvc6", "Departament", "Update records");
        intent6.addBasicInfo("Activity_0941ghy","Gateway_1pv0p63",
                "EndEvent_0irzcwa", null);
        myIntent intent6_query = new myIntent("Activity_114hvc6_query", "Departament", "Update records");
        intent6_query.addTrainingPhrase("Who Update records");

        myIntent intent7 = new myIntent("Activity_1o8a9ib", "Departament", "Check stock");
        intent7.addBasicInfo("Task_1wuzo3e","Activity_1o8a9ib",
                "Activity_0941ghy", null);
        myIntent intent7_query = new myIntent("Activity_1o8a9ib_query", "Departament", "Check stock");
        intent7_query.addTrainingPhrase("Who Check stock");

        expectedIntents.add(intent5); //
        expectedIntents.add(intent5_query);
        expectedIntents.add(intent6); //
        expectedIntents.add(intent6_query);
        expectedIntents.add(intent7); //
        expectedIntents.add(intent7_query);




        return expectedIntents;
    }


}