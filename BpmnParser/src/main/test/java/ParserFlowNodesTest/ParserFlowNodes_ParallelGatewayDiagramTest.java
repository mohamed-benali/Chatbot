package ParserFlowNodesTest;

import main.Entity.Parser.ParserFlowNodes.ParserFlowNodes;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodesImpl;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserFlowNodes_ParallelGatewayDiagramTest {

    static BpmnModelInstance modelInstanceParallelGateway;
    static ParserFlowNodesImpl parserFlowNodesParallelGateway;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/ParserFlowNodes";

    static String bpmnPathParallelGateway = componentTestingPath + "/diagramParallelGateway.bpmn";

    @BeforeAll
    static void setUp() {
        File fileParallelGateway = new File(bpmnPathParallelGateway);
        modelInstanceParallelGateway = Bpmn.readModelFromFile(fileParallelGateway);
    }

    @BeforeEach
    void setUpEach() {
        parserFlowNodesParallelGateway = new ParserFlowNodesImpl(modelInstanceParallelGateway);
    }

    @AfterEach
    void tearDown() {
    }


    /*
     * Input Context
     */

    @Test
    @DisplayName("Gets the input intent IDs of a TASK after a Task")
    void getInputIntentIDs_Of_FlowNode_after_task() {
        String taskID = "Task_1wuzo3e"; // Prepare the items
        Task flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> inputIntentsIds = parserFlowNodesParallelGateway.getInputContextIDs(flowNode);

        List<String> expectedInputIntentsIds = new ArrayList<String>();
        expectedInputIntentsIds.add("Task_1vunmit"); // Get items

        assertEquals(expectedInputIntentsIds, inputIntentsIds);
    }

    @Test
    @DisplayName("Gets the input intents IDs of a TASK after Closing exclusive Gateway")
    void getInputIntentIDs_Of_FlowNode_after_closing_exclusive_gateway() {
        String taskID = "Task_1pynbur"; // Deliver order
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> inputIntentsIds = parserFlowNodesParallelGateway.getInputContextIDs(flowNode);

        List<String> expectedInputIntentsIds = new ArrayList<String>();
        expectedInputIntentsIds.add("ExclusiveGateway_0n01jaj"); // Closing gateway

        assertEquals(expectedInputIntentsIds, inputIntentsIds);
    }

    @Test
    @DisplayName("Gets the input intents IDs of a TASK after an Opening exclusive Gateway")
    void getInputIntentIDs_Of_FlowNode_after_opening_exclusive_gateway() {
        String taskID = "Task_1t8hbl4"; // Ship a Parcel with Transport insurance
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> inputIntentsIds = parserFlowNodesParallelGateway.getInputContextIDs(flowNode);

        List<String> expectedInputIntentsIds = new ArrayList<String>();
        expectedInputIntentsIds.add("ExclusiveGateway_0t87582"); // Opening gateway

        assertEquals(expectedInputIntentsIds, inputIntentsIds);
    }

    @Test
    @DisplayName("Gets the input intents IDs of a EndEvent after Closing parallel Gateway")
    void getInputIntentIDs_Of_FlowNode_after_closing_parallel_gateway() {
        String endEventID = "EndEvent_0irzcwa"; // End
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(endEventID);

        List<String> inputIntentsIds = parserFlowNodesParallelGateway.getInputContextIDs(flowNode);

        List<String> expectedInputIntentsIds = new ArrayList<String>();
        expectedInputIntentsIds.add("Gateway_1pv0p63"); // Closing parallel gateway

        assertEquals(expectedInputIntentsIds, inputIntentsIds);
    }


    // TODO: Possibilitat: Al parsejear un flowNode(o potser nomes task) dintre un parrallel gateway, donar
    // TODO: els inputs i output context correctes
    @Test
    @DisplayName("Gets the input context IDs of a Task(FIRST BRANCH) after an Opening parallel Gateway")
    void getInputIntentIDs_Of_FlowNode_after_opening_parallel_gateway() {
        String endEventID = "Activity_1o8a9ib"; // Check stock
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(endEventID);

        List<String> inputIntentsIds = parserFlowNodesParallelGateway.getInputContextIDs(flowNode);

        List<String> expectedInputIntentsIds = new ArrayList<String>();
        expectedInputIntentsIds.add("Task_1wuzo3e"); // "Prepare the items", Task before Opening parallel gateway

        assertEquals(expectedInputIntentsIds, inputIntentsIds);
    }



    /*
     * Output Context
     */

    @Test
    @DisplayName("Gets the output context IDs of a TASK before a Task")
    void getOutputContextIDs_Of_FlowNode_before_task() {
        String taskID = "Task_1vunmit"; // Get items
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputContextIds = parserFlowNodesParallelGateway.getOutputContextIDs(flowNode);

        List<String> expectedOutputContextIds = new ArrayList<String>();
        expectedOutputContextIds.add("Task_1vunmit"); // Get items

        assertEquals(expectedOutputContextIds, outputContextIds);
    }

    @Test
    @DisplayName("Gets the output context IDs of a TASK before an Opening Exclusive Gateway")
    void getOutputContextIDs_Of_FlowNode_before_opening_exclusive_gateway() {
        String taskID = "Task_0k9iu95"; // Fetch the items
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputContextIds = parserFlowNodesParallelGateway.getOutputContextIDs(flowNode);

        List<String> expectedOutputContextIds = new ArrayList<String>();
        expectedOutputContextIds.add("Task_0k9iu95"); // Fetch the items

        assertEquals(expectedOutputContextIds, outputContextIds);
    }

    @Test
    @DisplayName("Gets the output context IDs of a TASK before a closing Exclusive Gateway")
    void getOutputContextIDs_Of_FlowNode_before_closing_exclusive_gateway() {
        String taskID = "Task_1t8hbl4"; // Ship a Parcel with Transport insurance
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputContextIds = parserFlowNodesParallelGateway.getOutputContextIDs(flowNode);

        List<String> expectedOutputContextIds = new ArrayList<String>();
        expectedOutputContextIds.add("ExclusiveGateway_0n01jaj"); // Closing exlclusve gateway

        assertEquals(expectedOutputContextIds, outputContextIds);
    }

    @Test
    @DisplayName("Gets the output context IDs of a TASK before an Opening Parallel Gateway")
    void getOutputContextIDs_Of_FlowNode_before_opening_parallel_gateway() {
        String taskID = "Task_1wuzo3e"; // Prepare the items
        Task task = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputContextIds = parserFlowNodesParallelGateway.getOutputContextIDs(task);

        List<String> expectedOutputContextIds = new ArrayList<String>();
        expectedOutputContextIds.add("Task_1wuzo3e"); // Prepare the items

        assertEquals(expectedOutputContextIds, outputContextIds);
    }

    // TODO: Possibilitat: Al parsejear un flowNode(o potser nomes task) dintre un parallel gateway, donar
    // TODO: els inputs i output context correctes

    // Nomes es correcte si no es la ultima branca
    @Test
    @DisplayName("Gets the output context IDs of a TASK(not the last branch) before a closing Parallel Gateway")
    void getOutputContextIDs_Of_FlowNode_before_closing_parallel_gateway() {
        String taskID = "Activity_0941ghy"; // Update stock
        Task task = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputContextIds = parserFlowNodesParallelGateway.getOutputContextIDs(task);

        List<String> expectedOutputContextIds = new ArrayList<String>();
        expectedOutputContextIds.add("Activity_0941ghy"); // Closing parallel gateway

        assertEquals(expectedOutputContextIds, outputContextIds);
    }


    /*
     * Output Intents
     */

    @Test
    @DisplayName("Gets the output intent ID of a TASK before a TASK")
    void getOutputIntentIDs_Of_FlowNode_before_a_task() {
        String taskID = "Task_1vunmit"; // Get items
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputIntentIds = parserFlowNodesParallelGateway.getOutputIntentIDs(flowNode);

        List<String> expectedOutpuIntentIds = new ArrayList<String>();
        expectedOutpuIntentIds.add("Task_1wuzo3e"); // Prepare the items

        assertEquals(expectedOutpuIntentIds, outputIntentIds);
    }

    @Test
    @DisplayName("Gets the output intent ID of a TASK before a closing Exclusive Gateway")
    void getOutputIntentIDs_Of_FlowNode_before_closing_exclusive_gateway() {
        String taskID = "Task_1t8hbl4"; // Ship a Parcel with Transport insurance
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputIntentIds = parserFlowNodesParallelGateway.getOutputIntentIDs(flowNode);

        List<String> expectedOutpuIntentIds = new ArrayList<String>();
        expectedOutpuIntentIds.add("Task_1pynbur"); // Deliver Order

        assertEquals(expectedOutpuIntentIds, outputIntentIds);
    }

    @Test
    @DisplayName("Gets the output intent ID of a TASK before an opening Parallel Gateway")
    void getOutputIntentIDs_Of_FlowNode_before_opening_parallel_gateway() {
        String taskID = "Task_1wuzo3e"; // Prepare the items
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputIntentIds = parserFlowNodesParallelGateway.getOutputIntentIDs(flowNode);

        List<String> expectedOutpuIntentIds = new ArrayList<String>();
        expectedOutpuIntentIds.add("Activity_1o8a9ib"); // Check stock

        assertEquals(expectedOutpuIntentIds, outputIntentIds);
    }

    // TODO: REMEMBER THAT IN THE PARALLEL GATEWAY PARSING, THERE IS A POST PROCESSING OF THE EDGING FLOWNODES
    // SO THE BEST WAY TO TEST IT, IS TO DO IT WITH THE FULL PARALLEL GATEWAY
    @Test
    @DisplayName("Gets the output intent ID of a TASK before an closing Parallel Gateway")
    void getOutputIntentIDs_Of_FlowNode_before_closing_parallel_gateway() {
        String taskID = "Activity_114hvc6"; // Update records
        FlowNode flowNode = modelInstanceParallelGateway.getModelElementById(taskID);

        List<String> outputIntentIds = parserFlowNodesParallelGateway.getOutputIntentIDs(flowNode);

        List<String> expectedOutpuIntentIds = new ArrayList<String>();
        expectedOutpuIntentIds.add("EndEvent_0irzcwa"); //

        assertEquals(expectedOutpuIntentIds, outputIntentIds);
    }

}