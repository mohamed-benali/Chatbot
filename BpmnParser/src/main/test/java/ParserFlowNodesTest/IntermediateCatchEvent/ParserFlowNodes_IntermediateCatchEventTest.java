package ParserFlowNodesTest.IntermediateCatchEvent;

import main.Entity.Intent.Intents;
import main.Entity.Intent.Intent.myIntent;
import main.Entity.Parser.BpmnAlgorithm.BpmnAlgorithmImpl;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodesImpl;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserFlowNodes_IntermediateCatchEventTest {

    static BpmnModelInstance modelInstance;
    static ParserFlowNodesImpl parserFlowNodes;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/ParserFlowNodes/IntermediateCatchEvent";

    static String bpmnPath = componentTestingPath + "/ZooModified.bpmn";

    @BeforeAll
    static void setUp() {
        File file = new File(bpmnPath);
        modelInstance = Bpmn.readModelFromFile(file);
    }

    @BeforeEach
    void setUpEach() {
        parserFlowNodes = new ParserFlowNodesImpl(modelInstance);
        BpmnAlgorithmImpl.resetVisitedFlowNodesInstance(); // Reset it
    }

    @AfterAll
    static void tearDown() {
        parserFlowNodes = null;
    }


//TODO: Consider the messageFlow(collaboration) ??????
    @Test
    @DisplayName("Parse Intermediate Catch Event")
    void parseIntermediateCatchEvent() throws IOException {
        String intermediateCatchEventID = "IntermediateCatchEvent_1pyp6o5"; // Gateway que obre
        IntermediateCatchEvent intermediateCatchEvent = modelInstance.getModelElementById(intermediateCatchEventID);

        String participantID = "Participant_1fg9rvj";
        Participant participant = modelInstance.getModelElementById(participantID);

        String procesId = "Process_0m9k2qh";
        Process process = modelInstance.getModelElementById(procesId);


        Intents resultIntents = parserFlowNodes.parseIntermediateCatchEvent(participant, process, intermediateCatchEvent);

        Intents expectedIntents = createExpectedIntentsIntermediateCatchEvent();

        assertEquals(expectedIntents, resultIntents);
    }

    private Intents createExpectedIntentsIntermediateCatchEvent() throws IOException {
        Intents expectedIntents = new Intents();

        myIntent intent = new myIntent("IntermediateCatchEvent_1pyp6o5", "Visitor", "Receive ZooClub card");
        intent.addBasicInfo("Task_0w3czw9","IntermediateCatchEvent_1pyp6o5",
                "Task_0ssja9k", null);

        expectedIntents.add(intent);

        return expectedIntents;

    }


    @Test
    @DisplayName("Parse Intermediate Catch Event AFTER Closing Parallel Gateway")
    void parseIntermediateCatchEventAfterClosingParallelGateway() throws IOException {
        String intermediateCatchEventID = "IntermediateCatchEvent_1dgnju9"; // Gateway que obre
        IntermediateCatchEvent intermediateCatchEvent = modelInstance.getModelElementById(intermediateCatchEventID);

        String participantID = "Participant_1cb1ihb";
        Participant participant = modelInstance.getModelElementById(participantID);

        String procesId = "Process_1";
        Process process = modelInstance.getModelElementById(procesId);


        Intents resultIntents = parserFlowNodes.parseIntermediateCatchEvent(participant, process, intermediateCatchEvent);

        Intents expectedIntents = createExpectedIntentsIntermediateCatchEventAfterClosingParallelGateway();

        assertEquals(expectedIntents, resultIntents);
    }

    private Intents createExpectedIntentsIntermediateCatchEventAfterClosingParallelGateway() throws IOException {
        Intents expectedIntents = new Intents();

        myIntent intent = new myIntent("IntermediateCatchEvent_1dgnju9", "Zoo", "Receive payment confirmation");
        intent.addBasicInfo("ParallelGateway_1oafzb2","IntermediateCatchEvent_1dgnju9",
                "Task_02qz4bj", null);

        expectedIntents.add(intent);

        return expectedIntents;

    }


}