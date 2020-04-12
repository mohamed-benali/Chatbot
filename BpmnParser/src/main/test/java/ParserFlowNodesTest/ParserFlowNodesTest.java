package ParserFlowNodesTest;

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

class ParserFlowNodesTest {

    static BpmnModelInstance modelInstance;
    static ParserFlowNodes parserFlowNodes;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/ParserFlowNodes";

    static String bpmnPath = componentTestingPath + "/diagram.bpmn";

    @BeforeAll
    static void setUp() {
        File file = new File(bpmnPath);
        modelInstance = Bpmn.readModelFromFile(file);
        parserFlowNodes = new ParserFlowNodes(modelInstance);
    }

    @AfterAll
    static void tearDown() {
    }


    @Test
    @DisplayName("...")
    void doNothing() {

    }




}