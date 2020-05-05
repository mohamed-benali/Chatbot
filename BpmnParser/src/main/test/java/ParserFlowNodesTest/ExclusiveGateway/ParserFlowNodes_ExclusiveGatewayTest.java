package ParserFlowNodesTest.ExclusiveGateway;

import main.Entity.Intent.Intents;
import main.Entity.Intent.myIntent;
import main.Entity.Parser.BpmnAlgorithm;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodes;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.lang.Process;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserFlowNodes_ExclusiveGatewayTest {

    static BpmnModelInstance modelInstance;
    static ParserFlowNodes parserFlowNodes;

    static BpmnAlgorithm bpmnAlgorithm;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/ParserFlowNodes/ExclusiveGateway";

    static String bpmnPath = componentTestingPath + "/exclusiveGateway_If_without_else.bpmn";

    @BeforeAll
    static void setUp() {
        File file = new File(bpmnPath);
        modelInstance = Bpmn.readModelFromFile(file);
    }

    @BeforeEach
    void setUpEach() {
        parserFlowNodes = new ParserFlowNodes(modelInstance);

        bpmnAlgorithm = new BpmnAlgorithm(modelInstance);
    }

    @AfterEach
    void tearDown() { }


    @Test
    @DisplayName("Parse Diagram with Exclusive gateway without any task between the opening and closing exclusive gateway(in one branch)")
    void parseDiagramWithExclusiveGatewayWithoutAnyTaskBetweenOpeningAndClosingExlusiveGateway() throws IOException {
        String exclusiveGatewayID = "ExclusiveGateway_0t87582"; // Gateway que obre
        ExclusiveGateway exclusiveGateway = modelInstance.getModelElementById(exclusiveGatewayID);

        String participantID = "Participant_0uhnto1";
        Participant participant = modelInstance.getModelElementById(participantID);

        Intents resultIntents = bpmnAlgorithm.parseParticipant(participant);

        Intents expectedIntents = createExpectedIntentsExclusiveGateway();

        assertEquals(expectedIntents, resultIntents);
    }



    private Intents createExpectedIntentsExclusiveGateway() throws IOException {
        Intents expectedIntents = new Intents();

        // StartEvent
        myIntent intent = new myIntent("StartEvent_1e53g9j", "Empleat", "Begin");
        intent.addBasicInfo("BEGIN_CONTEXT","StartEvent_1e53g9j",
                "Task_0k9iu95", "Empleat");

        myIntent intent3 = new myIntent("Task_0k9iu95", "Empleat", "Fetch the items");
        intent3.addBasicInfo("StartEvent_1e53g9j","Task_0k9iu95",
                "ExclusiveGateway_0t87582", null);

        myIntent intent5 = new myIntent("Task_1pynbur", "Empleat", "Deliver order");
        intent5.addBasicInfo("ExclusiveGateway_0n01jaj","Task_1pynbur",
                "EndEvent_06wuojd", null);

        /**************************************/
        myIntent intent5Extra = new myIntent("Task_1pynbur_ExclusiveGateway_0t87582", "Empleat", "Deliver order");
        intent5Extra.addBasicInfo("ExclusiveGateway_0t87582","Task_1pynbur",
                "EndEvent_06wuojd", "No");
        /**************************************/


        myIntent intent6 = new myIntent("Task_1t8hbl4", "Empleat", "Ship a Parcel with Transport insurance");
        intent6.addBasicInfo("ExclusiveGateway_0t87582","ExclusiveGateway_0n01jaj",
                "Task_1pynbur", "Yes");

        myIntent intent7 = new myIntent("ExclusiveGateway_0t87582", "Empleat", "Price over 100?");
        intent7.addBasicInfo("Task_0k9iu95","ExclusiveGateway_0t87582",
                "Task_1t8hbl4", null);
        intent7.addOutputIntentID("Task_1pynbur"); // TODO: Should be Task_1pynbur_ExclusiveGateway_0t87582 ¿?

        myIntent intent8 = new myIntent("EndEvent_06wuojd", "Empleat", "End");
        intent8.addInputContextID("Task_1pynbur");
        intent8.addOutputContextID("EndEvent_06wuojd");
        intent8.addTrainingPhrase(null);

        expectedIntents.add(intent); //
        expectedIntents.add(intent3); //
        expectedIntents.add(intent5); //
        expectedIntents.add(intent6); //
        expectedIntents.add(intent7); //
        expectedIntents.add(intent8); //

        expectedIntents.add(intent5Extra);

        return expectedIntents;

    }


}