import main.Entity.Parser.ParserFlowNodes;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.MessageFlow;


import org.camunda.bpm.model.bpmn.instance.Task;
import org.junit.jupiter.api.*;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ParserFlowNodesTest {

    static BpmnModelInstance modelInstance;
    static ParserFlowNodes parserFlowNodes;

    @BeforeAll
    static void setUp() {
        String packageTestingPath = "./src/main/test";
        String bpmnPath = packageTestingPath + "/testing_files/ParserFlowNodes/diagram.bpmn";
        File file = new File(bpmnPath);
        modelInstance = Bpmn.readModelFromFile(file);

        parserFlowNodes = new ParserFlowNodes(modelInstance);
    }

    @AfterAll
    static void tearDown() {
    }




    @Test
    void getAllFlowingFlowNodes() {
    }

    @Test
    void getAllIncomingFlowNodes() {
    }

    @Test
    void getRelevantFlowingFlowNodes() {
    }

    @Test
    void getRelevantIncomingFlowNodes() {
    }

    @Test
    @DisplayName("Gets the subject of the source of a messageFlow")
    void getSourceSubject() {
        String messageFlowID = "MessageFlow_0awsr48";
        MessageFlow messageFlow = modelInstance.getModelElementById(messageFlowID);
        String sourceSubject = parserFlowNodes.getSourceSubject(messageFlow);

        assertEquals("Empleat", sourceSubject);
    }

    @Test
    @DisplayName("Gets the subject of the target of a messageFlow")
    void getTargetSubject() {
        String messageFlowID = "MessageFlow_0awsr48";
        MessageFlow messageFlow = modelInstance.getModelElementById(messageFlowID);
        String targetSubject = parserFlowNodes.getTargetSubject(messageFlow);

        assertEquals("Departament", targetSubject);
    }

    @Test
    void createName() {
    }

    @Test
    @DisplayName("IS NOT before an exclusive gateway")
    void is_not_before_exclusive_gateway() {
        String taskID = "Task_0kegxhu"; // Request items
        Task task = modelInstance.getModelElementById(taskID);
        boolean is_before_gateway = parserFlowNodes.before_exlusive_gateway(task);
        assertFalse(is_before_gateway);
    }

    @Test
    @DisplayName("IS before an exclusive gateway")
    void is_before_exclusive_gateway() {
        String taskID = "Task_098k69d"; // Ship a Parcel
        Task task = modelInstance.getModelElementById(taskID);
        boolean is_before_gateway = parserFlowNodes.before_exlusive_gateway(task);
        assertTrue(is_before_gateway);
    }

    @Test
    @DisplayName("IS NOT after an exclusive gateway")
    void is_not_after_exclusive_gateway() {
        String taskID = "Task_0kegxhu"; // Request items
        Task task = modelInstance.getModelElementById(taskID);
        boolean is_after_gateway = parserFlowNodes.after_exclusive_gateway(task);
        assertFalse(is_after_gateway);
    }

    @Test
    @DisplayName("IS after an exclusive gateway")
    void is_after_exclusive_gateway() {
        String taskID = "Task_1pynbur"; // Deliver Order
        Task task = modelInstance.getModelElementById(taskID);
        boolean is_after_gateway = parserFlowNodes.after_exclusive_gateway(task);
        assertTrue(is_after_gateway);
    }

    @Test
    void parseFlowNode() {
    }

    @Test
    void parseStartEvent() {
    }

    @Test
    void parseTask() {
    }

    @Test
    void parseExclusiveGateway() {
    }

    @Test
    void parseMessageFlow() {
    }

    @Test
    void parseEndEvent() {
    }
}