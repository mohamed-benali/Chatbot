package ParserFlowNodesTest;

import main.Entity.Parser.ParserFlowNodes.ParserFlowNodesImpl;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;


import org.junit.jupiter.api.*;


import java.io.File;

class ParserFlowNodesTest {

    static BpmnModelInstance modelInstance;
    static ParserFlowNodesImpl parserFlowNodes;

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
    }

    @BeforeEach
    void setUpEach() {
        parserFlowNodes = new ParserFlowNodesImpl(modelInstance);
    }

    @AfterAll
    static void tearDown() {
    }


    @Test
    @DisplayName("...")
    void doNothing() {

    }




}