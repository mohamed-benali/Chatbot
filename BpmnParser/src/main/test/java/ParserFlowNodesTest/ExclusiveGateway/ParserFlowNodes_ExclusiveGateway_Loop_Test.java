package ParserFlowNodesTest.ExclusiveGateway;

import main.Entity.Intent.Intents;
import main.Entity.Intent.myIntent;
import main.Entity.Parser.BpmnAlgorithm;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodes;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserFlowNodes_ExclusiveGateway_Loop_Test {

    static BpmnModelInstance modelInstance;
    static ParserFlowNodes parserFlowNodes;

    static BpmnAlgorithm bpmnAlgorithm;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/ParserFlowNodes/ExclusiveGateway";

    static String bpmnPath = componentTestingPath + "/diagramLoop.bpmn";

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

    @AfterAll
    static void tearDown() {
    }


    @Test
    @DisplayName("Parse Participant Empleat with Exclusive gateway with a loop")
    void parseDiagramWithExclusiveGatewayWithLoop() throws IOException {
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
                "Task_0kegxhu", "Empleat");

        myIntent intent2 = new myIntent("Task_098k69d", "Empleat", "Ship a Parcel");
        intent2.addBasicInfo("ExclusiveGateway_0t87582","ExclusiveGateway_0n01jaj",
                "Task_1pynbur", "No");
        myIntent intent2_query = new myIntent("Task_098k69d_query", "Empleat", "Ship a Parcel");
        intent2_query.addTrainingPhrase("Who Ship a Parcel");

        myIntent intent3 = new myIntent("Task_0k9iu95", "Empleat", "Fetch the items");
        intent3.addBasicInfo("Gateway_0bqrxen","Task_0k9iu95",
                "ExclusiveGateway_0t87582", null);
        myIntent intent3_query = new myIntent("Task_0k9iu95_query", "Empleat", "Fetch the items");
        intent3_query.addTrainingPhrase("Who Fetch the items");

        myIntent intent4 = new myIntent("Task_0kegxhu", "Empleat", "Request items");
        intent4.addBasicInfo("StartEvent_1e53g9j","Gateway_0bqrxen",
                "Task_0k9iu95", null);
        myIntent intent4_query = new myIntent("Task_0kegxhu_query", "Empleat", "Request items");
        intent4_query.addTrainingPhrase("Who Request items");

        myIntent intent5 = new myIntent("Task_1pynbur", "Empleat", "Deliver order");
        intent5.addBasicInfo("ExclusiveGateway_0n01jaj","Task_1pynbur",
                "Gateway_00ar3ma", null);
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
        intent8.addInputContextID("Gateway_00ar3ma");
        intent8.addOutputContextID("EndEvent_06wuojd");
        intent8.addTrainingPhrase("Yes");

        myIntent intent9 = new myIntent("Gateway_00ar3ma", "Empleat", "Enough items?");
        intent9.addBasicInfo("Task_1pynbur","Gateway_00ar3ma",
                "EndEvent_06wuojd", null);
        intent9.addOutputIntentID("Task_0k9iu95"); // TODO: Should be Task_0k9iu95_Gateway_00ar3ma ?


        /**************************************/
        myIntent intent3Extra = new myIntent("Task_0k9iu95_Gateway_00ar3ma", "Empleat", "Fetch the items");
        intent3Extra.addBasicInfo("Gateway_00ar3ma","Task_0k9iu95",
                "ExclusiveGateway_0t87582", "No");

        /**************************************/

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

        expectedIntents.add(intent8);
        expectedIntents.add(intent3Extra);
        expectedIntents.add(intent9);

        return expectedIntents;





    }


}