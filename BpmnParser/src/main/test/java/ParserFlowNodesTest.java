import main.Entity.Parser.ParserFlowNodes;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.MessageFlow;


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