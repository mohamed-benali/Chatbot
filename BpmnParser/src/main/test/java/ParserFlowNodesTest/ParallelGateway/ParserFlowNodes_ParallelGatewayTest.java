package ParserFlowNodesTest.ParallelGateway;

import main.Entity.Intent.Intents;
import main.Entity.Intent.myIntent;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodes;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserFlowNodes_ParallelGatewayTest {

    static BpmnModelInstance modelInstanceParallelGateway;
    static ParserFlowNodes parserFlowNodesParallelGateway;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/ParserFlowNodes/ParallelGateway";

    static String bpmnPathParallelGateway = componentTestingPath + "/diagramParallelGateway.bpmn";

    @BeforeAll
    static void setUp() {
        File fileParallelGateway = new File(bpmnPathParallelGateway);
        modelInstanceParallelGateway = Bpmn.readModelFromFile(fileParallelGateway);
        parserFlowNodesParallelGateway = new ParserFlowNodes(modelInstanceParallelGateway);
    }

    @AfterAll
    static void tearDown() {
    }


    @Test
    @DisplayName("Gets the closing parallel gateway of a gateway")
    void getclosingParallelGateway() {
        String parallelGatewayID = "Gateway_1tig0cb"; // Gateway que obre
        ParallelGateway parallelGateway = modelInstanceParallelGateway.getModelElementById(parallelGatewayID);

        ParallelGateway closingParallelGateway = parserFlowNodesParallelGateway.getClosingParallelGateway(parallelGateway);

        String expectedClosingParallelGatewayID = "Gateway_1pv0p63";
        assertEquals(expectedClosingParallelGatewayID, closingParallelGateway.getId());
    }


    @Test
    @DisplayName("The Node is changed to the closing Gateway") // So it can keep parsing from there
    void parseParallelGateway_node_is_changed_to_the_closingGateway() throws IOException {
        String parallelGatewayID = "Gateway_1tig0cb"; // Gateway que obre
        ParallelGateway parallelGateway = modelInstanceParallelGateway.getModelElementById(parallelGatewayID);

        String participantID = "Participant_0yo93vp";
        Participant participant = modelInstanceParallelGateway.getModelElementById(participantID);

        String procesId = "Process_0flyrvp";
        Process process = modelInstanceParallelGateway.getModelElementById(procesId);

        List<FlowNode> workAroundChangeNodeInsideMethod = new ArrayList<FlowNode>();
        workAroundChangeNodeInsideMethod.add(parallelGateway);

        Intents resultIntents = parserFlowNodesParallelGateway.parseParallelGateway(participant, process, workAroundChangeNodeInsideMethod);
        FlowNode node = workAroundChangeNodeInsideMethod.get(0); // TODO: Test it Should be the parallel gateway

        String expectedNodeID = "Gateway_1pv0p63"; // Closing Parallel Gateway

        assertEquals(expectedNodeID, node.getId());
    }

    @Test
    @DisplayName("Parse parallel gateway")
    void parseParallelGateway() throws IOException {
        String parallelGatewayID = "Gateway_1tig0cb"; // Gateway que obre
        ParallelGateway parallelGateway = modelInstanceParallelGateway.getModelElementById(parallelGatewayID);

        String participantID = "Participant_0yo93vp";
        Participant participant = modelInstanceParallelGateway.getModelElementById(participantID);

        String procesId = "Process_0flyrvp";
        Process process = modelInstanceParallelGateway.getModelElementById(procesId);


        List<FlowNode> workAroundChangeNodeInsideMethod = new ArrayList<FlowNode>();
        workAroundChangeNodeInsideMethod.add(parallelGateway);

        Intents resultIntents = parserFlowNodesParallelGateway.parseParallelGateway(participant, process, workAroundChangeNodeInsideMethod);
        FlowNode node = workAroundChangeNodeInsideMethod.get(0);


        Intents expectedIntents = createExpectedIntentsParallelGateway();

        assertEquals(expectedIntents, resultIntents);
    }

    private Intents createExpectedIntentsParallelGateway() throws IOException {
        Intents expectedIntents = new Intents();

        myIntent intent2 = new myIntent("Activity_0941ghy", "Departament", "Update Stock");
        intent2.addBasicInfo("Activity_1o8a9ib","Activity_0941ghy",
                "Activity_114hvc6", null);
        myIntent intent2_query = new myIntent("Activity_0941ghy_query", "Departament", "Update Stock");
        intent2_query.addTrainingPhrase("Who Update Stock");

        myIntent intent3 = new myIntent("Activity_114hvc6", "Departament", "Update records");
        intent3.addBasicInfo("Activity_0941ghy","Gateway_1pv0p63",
                "EndEvent_0irzcwa", null); // TODO: OutputIntentID EndEvent
        myIntent intent3_query = new myIntent("Activity_114hvc6_query", "Departament", "Update records");
        intent3_query.addTrainingPhrase("Who Update records");

        myIntent intent4 = new myIntent("Activity_1o8a9ib", "Departament", "Check stock");
        intent4.addBasicInfo("Task_1wuzo3e","Activity_1o8a9ib", // TODO: Put the correct input Context(task instead of gateway)
                "Activity_0941ghy", null);
        myIntent intent4_query = new myIntent("Activity_1o8a9ib_query", "Departament", "Check stock");
        intent4_query.addTrainingPhrase("Who Check stock");

        expectedIntents.add(intent2);
        expectedIntents.add(intent2_query);
        expectedIntents.add(intent3); //
        expectedIntents.add(intent3_query);
        expectedIntents.add(intent4); //
        expectedIntents.add(intent4_query);


        return expectedIntents;

    }


}