import main.Entity.Parser.ParserFlowNodes;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.ModelInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ParserFlowNodesTest {

    ParserFlowNodes parserFlowNodes;

    @BeforeEach
    void setUp() {
        String packagePath = "./src/main/java/main";
        String bpmnPath = packagePath + "/../Data/diagram.bpmn";
        File file = new File(bpmnPath);
        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);

        parserFlowNodes = new ParserFlowNodes(modelInstance);
    }

    @AfterEach
    void tearDown() {
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
    void getSourceSubject() {
    }

    @Test
    void getTargetSubject() {
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