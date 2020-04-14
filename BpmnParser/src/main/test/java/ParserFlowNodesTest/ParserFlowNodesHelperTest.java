package ParserFlowNodesTest;

import main.Entity.Parser.ParserFlowNodes.ParseFlowNodesHelper;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodes;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.MessageFlow;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserFlowNodesHelperTest {

    static BpmnModelInstance modelInstance;
    static ParseFlowNodesHelper parseFlowNodesHelper;


    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/ParserFlowNodes";

    static String bpmnPath = componentTestingPath + "/diagramParallelGateway.bpmn";

    @BeforeAll
    static void setUp() {
        File file = new File(bpmnPath);
        modelInstance = Bpmn.readModelFromFile(file);
    }

    @BeforeEach
    void setUpEach() {
        parseFlowNodesHelper = new ParseFlowNodesHelper(modelInstance);
    }

    @AfterAll
    static void tearDown() {
    }


    /*
     * AFTER GATEWAY
     */

    @Test
    @DisplayName("FlowNode is after a closing exclusive gateway")
    void after_closing_exclusive_gateway() {
        String taskID = "Task_1pynbur"; // Deliver order
        FlowNode node = modelInstance.getModelElementById(taskID);
        boolean result = parseFlowNodesHelper.after_closing_exclusive_gateway(node);
        assertTrue(result);
    }

    @Test
    @DisplayName("FlowNode is after a opening exclusive gateway")
    void after_opening_exclusive_gateway() {
        String taskID = "Task_098k69d"; // Ship a Parcel
        FlowNode node = modelInstance.getModelElementById(taskID);
        boolean result = parseFlowNodesHelper.after_opening_exclusive_gateway(node);
        assertTrue(result);
    }

    @Test
    @DisplayName("FlowNode is after a closing parallel gateway")
    void after_closing_parallel_gateway() {
        String flowNodeID = "EndEvent_0irzcwa"; // End
        FlowNode node = modelInstance.getModelElementById(flowNodeID);
        boolean result = parseFlowNodesHelper.after_closing_parallel_gateway(node);
        assertTrue(result);
    }

    @Test
    @DisplayName("FlowNode is after a opening parallel gateway")
    void after_opening_parallel_gateway() {
        String taskID = "Activity_1o8a9ib"; // Chech stock
        FlowNode node = modelInstance.getModelElementById(taskID);
        boolean result = parseFlowNodesHelper.after_opening_parallel_gateway(node);
        assertTrue(result);
    }


    /*
     * BEFORE GATEWAY
     */

    @Test
    @DisplayName("FlowNode is before a closing exclusive gateway")
    void before_closing_exclusive_gateway() {
        String taskID = "Task_098k69d"; // Ship a Parcel
        FlowNode node = modelInstance.getModelElementById(taskID);
        boolean result = parseFlowNodesHelper.before_closing_exclusive_gateway(node);
        assertTrue(result);
    }

    @Test
    @DisplayName("FlowNode is before a opening exclusive gateway")
    void before_opening_exclusive_gateway() {
        String taskID = "Task_0k9iu95"; // Fetch the items
        FlowNode node = modelInstance.getModelElementById(taskID);
        boolean result = parseFlowNodesHelper.before_opening_exclusive_gateway(node);
        assertTrue(result);
    }

    @Test
    @DisplayName("FlowNode is before a closing parallel gateway")
    void before_closing_parallel_gateway() {
        String flowNodeID = "Activity_0941ghy"; // Update the stock
        FlowNode node = modelInstance.getModelElementById(flowNodeID);
        boolean result = parseFlowNodesHelper.before_closing_parallel_gateway(node);
        assertTrue(result);
    }

    @Test
    @DisplayName("FlowNode is before a opening parallel gateway")
    void before_opening_parallel_gateway() {
        String taskID = "Task_1wuzo3e"; // Prepare the items
        FlowNode node = modelInstance.getModelElementById(taskID);
        boolean result = parseFlowNodesHelper.before_opening_parallel_gateway(node);
        assertTrue(result);
    }



    /*
        @Test
    @DisplayName("IS NOT before an exclusive gateway")
    void is_not_before_exclusive_gateway() {
        String taskID = "Task_0kegxhu"; // Request items
        Task task = modelInstance.getModelElementById(taskID);
        boolean is_before_gateway = parserFlowNodes.before_exlusive_gateway(task);
        assertFalse(is_before_gateway);
    }


    @Test
    @DisplayName("IS NOT after an exclusive gateway")
    void is_not_after_exclusive_gateway() {
        String taskID = "Task_0kegxhu"; // Request items
        Task task = modelInstance.getModelElementById(taskID);
        boolean is_after_gateway = parserFlowNodes.after_exclusive_gateway(task);
        assertFalse(is_after_gateway);
    }
     */
}