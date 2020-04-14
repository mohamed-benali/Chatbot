package ParserFlowNodesTest;

import main.Entity.Parser.ParserFlowNodes.ParserFlowNodes;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.MessageFlow;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CamundaHelperTest {

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
    }

    @BeforeEach
    void setUpEach() {
        parserFlowNodes = new ParserFlowNodes(modelInstance);
    }

    @AfterAll
    static void tearDown() {
    }


    @Test
    @DisplayName("Gets the subject of the source of a messageFlow")
    void getSourceSubject() {
        String messageFlowID = "MessageFlow_0awsr48";
        MessageFlow messageFlow = modelInstance.getModelElementById(messageFlowID);
        String sourceSubject = parserFlowNodes.getCamundaHelper().getSourceSubject(messageFlow);

        assertEquals("Empleat", sourceSubject);
    }

    @Test
    @DisplayName("Gets the subject of the target of a messageFlow")
    void getTargetSubject() {
        String messageFlowID = "MessageFlow_0awsr48";
        MessageFlow messageFlow = modelInstance.getModelElementById(messageFlowID);
        String targetSubject = parserFlowNodes.getCamundaHelper().getTargetSubject(messageFlow);

        assertEquals("Departament", targetSubject);
    }

    

}