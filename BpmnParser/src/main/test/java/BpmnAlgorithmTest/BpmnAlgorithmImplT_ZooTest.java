package BpmnAlgorithmTest;

import main.Entity.Intent.Intent.BeginIntent_Special;
import main.Entity.Intent.Intent.myIntent;
import main.Entity.Intent.Intents;
import main.Entity.Parser.BpmnAlgorithm.BpmnAlgorithmImpl;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BpmnAlgorithmImplT_ZooTest {

    static BpmnModelInstance modelInstance;
    static BpmnAlgorithmImpl bpmnAlgorithm;

    /*
     * PATHS
     */
    static String packageTestingPath = "./src/main/test";
    static String componentTestingPath = packageTestingPath + "/testing_files/BpmnAlgorithm";

    static String bpmnPath = componentTestingPath + "/ZooModified.bpmn";

    @BeforeAll
    static void setUp() {
        File file = new File(bpmnPath);
        modelInstance = Bpmn.readModelFromFile(file);
    }

    @BeforeEach
    void setUpEach() {
        bpmnAlgorithm = new BpmnAlgorithmImpl(modelInstance);
    }

    @AfterEach
    void tearDown() {
    }

    /*
     * diagram.bmpn
     */

    @Test // TODO: Store the solutions from a json file, so its easier and cleaner to make them
    @DisplayName("Parse ZooModified.bpmn")
    void parseZooModifiedBPMN() throws IOException {
        Intents resultIntents = new Intents();
        resultIntents.add(bpmnAlgorithm.parse());

        Intents expectedIntents = createExpectedIntentsZooModifiedBPMN();

        assertEquals(expectedIntents, resultIntents);
        //assertEquals("expectedIntents", resultIntents.createCode());

    }


    private Intents createExpectedIntentsZooModifiedBPMN() throws IOException {
        Intents expectedIntents = new Intents();

        myIntent intentActivity_17nmxuc = new myIntent("Activity_17nmxuc","Bank","Send payment confirmation to the Zoo");
        intentActivity_17nmxuc.addBasicInfo("Task_0oz8hjr","Task_0oz8hjr",
                "Activity_17nmxuc","EndEvent_0fwg27l",null);
        expectedIntents.add(intentActivity_17nmxuc);

        BeginIntent_Special beginiIntentSpecialBEGIN_CONTEXT = new BeginIntent_Special();
        List<String> participantNames = new ArrayList<>();
        participantNames.add("Zoo");
        participantNames.add("Bank");
        participantNames.add("Visitor");
        beginiIntentSpecialBEGIN_CONTEXT.setParticipantNames(participantNames);
        expectedIntents.add(beginiIntentSpecialBEGIN_CONTEXT);

        myIntent intentEndEvent_0fwg27l = new myIntent("EndEvent_0fwg27l","Bank","End");
        intentEndEvent_0fwg27l.addBasicInfo("Activity_17nmxuc","Activity_17nmxuc",
                "EndEvent_0fwg27l",null,null);
        expectedIntents.add(intentEndEvent_0fwg27l);

        myIntent intentEndEvent_0ueqhec = new myIntent("EndEvent_0ueqhec","Visitor","End");
        intentEndEvent_0ueqhec.addBasicInfo("Task_0ssja9k","Task_0ssja9k",
                "EndEvent_0ueqhec",null,null);
        expectedIntents.add(intentEndEvent_0ueqhec);

        myIntent intentEndEvent_1pqhyp2 = new myIntent("EndEvent_1pqhyp2","Zoo","End");
        intentEndEvent_1pqhyp2.addBasicInfo("Task_0j4npun","ParallelGateway_0jj7fu7",
                "EndEvent_1pqhyp2",null,null);
        expectedIntents.add(intentEndEvent_1pqhyp2);

        myIntent intentExclusiveGateway_0ajb73w = new myIntent("ExclusiveGateway_0ajb73w","Visitor","Individual membership?");
        intentExclusiveGateway_0ajb73w.addBasicInfo("Task_17x20z3","Task_17x20z3",
                "ExclusiveGateway_0ajb73w","Task_064spq4",null);
        expectedIntents.add(intentExclusiveGateway_0ajb73w);

        myIntent intentIntermediateCatchEvent_1dgnju9 = new myIntent("IntermediateCatchEvent_1dgnju9","Zoo","Receive payment confirmation");
        intentIntermediateCatchEvent_1dgnju9.addBasicInfo("Task_1qrcrh7","ParallelGateway_1oafzb2",
                "IntermediateCatchEvent_1dgnju9","Task_02qz4bj",null);
        expectedIntents.add(intentIntermediateCatchEvent_1dgnju9);

        myIntent intentIntermediateCatchEvent_1pyp6o5 = new myIntent("IntermediateCatchEvent_1pyp6o5","Visitor","Receive ZooClub card");
        intentIntermediateCatchEvent_1pyp6o5.addBasicInfo("Task_0w3czw9","Task_0w3czw9",
                "IntermediateCatchEvent_1pyp6o5","Task_0ssja9k",null);
        expectedIntents.add(intentIntermediateCatchEvent_1pyp6o5);

        myIntent intentStartEvent_0vpq959 = new myIntent("StartEvent_0vpq959","Bank","Receive payment request");
        intentStartEvent_0vpq959.addBasicInfo(null,"BEGIN_CONTEXT",
                "StartEvent_0vpq959","Task_0420rk1","Bank");
        expectedIntents.add(intentStartEvent_0vpq959);

        myIntent intentStartEvent_13penc1 = new myIntent("StartEvent_13penc1","Zoo","Receive visitor information");
        intentStartEvent_13penc1.addBasicInfo(null,"BEGIN_CONTEXT",
                "StartEvent_13penc1","Task_0qrzi4y","Zoo");
        expectedIntents.add(intentStartEvent_13penc1);

        myIntent intentStartEvent_1yl25iv = new myIntent("StartEvent_1yl25iv","Visitor","Start");
        intentStartEvent_1yl25iv.addBasicInfo(null,"BEGIN_CONTEXT",
                "StartEvent_1yl25iv","Task_17x20z3","Visitor");
        expectedIntents.add(intentStartEvent_1yl25iv);

        myIntent intentTask_02qz4bj = new myIntent("Task_02qz4bj","Zoo","Deliver ZooClub card to the visitor");
        intentTask_02qz4bj.addBasicInfo("IntermediateCatchEvent_1dgnju9","IntermediateCatchEvent_1dgnju9",
                "Task_02qz4bj","Task_1gst9f9",null);
        expectedIntents.add(intentTask_02qz4bj);

        myIntent intentTask_0420rk1 = new myIntent("Task_0420rk1","Bank","Process payment information");
        intentTask_0420rk1.addBasicInfo("StartEvent_0vpq959","StartEvent_0vpq959",
                "Task_0420rk1","Task_0oz8hjr",null);
        expectedIntents.add(intentTask_0420rk1);

        myIntent intentTask_064spq4 = new myIntent("Task_064spq4","Visitor","Prepare personal information");
        intentTask_064spq4.addBasicInfo("ExclusiveGateway_0ajb73w","ExclusiveGateway_0ajb73w",
                "ExclusiveGateway_06m442q","Task_0wrm9ya","Yes");
        expectedIntents.add(intentTask_064spq4);

        myIntent intentTask_0j4npun = new myIntent("Task_0j4npun","Zoo","Contact ZooClub magazine");
        intentTask_0j4npun.addBasicInfo("Task_1gst9f9","Task_1gst9f9",
                "ParallelGateway_0jj7fu7","EndEvent_1pqhyp2",null);
        expectedIntents.add(intentTask_0j4npun);

        myIntent intentTask_0oih0sg = new myIntent("Task_0oih0sg","Zoo","Send payment request to the bank");
        intentTask_0oih0sg.addBasicInfo("Task_106edn4","Task_106edn4",
                "Task_0oih0sg","Task_1qrcrh7",null);
        expectedIntents.add(intentTask_0oih0sg);

        myIntent intentTask_0oz8hjr = new myIntent("Task_0oz8hjr","Bank","Charge zoo account");
        intentTask_0oz8hjr.addBasicInfo("Task_0420rk1","Task_0420rk1",
                "Task_0oz8hjr","Activity_17nmxuc",null);
        expectedIntents.add(intentTask_0oz8hjr);

        myIntent intentTask_0qrzi4y = new myIntent("Task_0qrzi4y","Zoo","Enter information into the system");
        intentTask_0qrzi4y.addBasicInfo("StartEvent_13penc1","StartEvent_13penc1",
                "Task_0qrzi4y","Task_106edn4",null);
        expectedIntents.add(intentTask_0qrzi4y);

        myIntent intentTask_0ssja9k = new myIntent("Task_0ssja9k","Visitor","Go home");
        intentTask_0ssja9k.addBasicInfo("IntermediateCatchEvent_1pyp6o5","IntermediateCatchEvent_1pyp6o5",
                "Task_0ssja9k","EndEvent_0ueqhec",null);
        expectedIntents.add(intentTask_0ssja9k);

        myIntent intentTask_0w3czw9 = new myIntent("Task_0w3czw9","Visitor","Wait for card");
        intentTask_0w3czw9.addBasicInfo("Task_0wrm9ya","Task_0wrm9ya",
                "Task_0w3czw9","IntermediateCatchEvent_1pyp6o5",null);
        expectedIntents.add(intentTask_0w3czw9);

        myIntent intentTask_0wrm9ya = new myIntent("Task_0wrm9ya","Visitor","Send information to the ZooClub department");
        intentTask_0wrm9ya.addBasicInfo("Task_064spq4","ExclusiveGateway_06m442q",
                "Task_0wrm9ya","Task_0w3czw9",null);
        expectedIntents.add(intentTask_0wrm9ya);

        myIntent intentTask_106edn4 = new myIntent("Task_106edn4","Zoo","Send request to billing department");
        intentTask_106edn4.addBasicInfo("Task_0qrzi4y","Task_0qrzi4y",
                "Task_106edn4","Task_0oih0sg",null);
        expectedIntents.add(intentTask_106edn4);

        myIntent intentTask_14zetua = new myIntent("Task_14zetua","Visitor","Prepare family's information");
        intentTask_14zetua.addBasicInfo("ExclusiveGateway_0ajb73w","ExclusiveGateway_0ajb73w",
                "ExclusiveGateway_06m442q","Task_0wrm9ya","No");
        expectedIntents.add(intentTask_14zetua);

        myIntent intentTask_17x20z3 = new myIntent("Task_17x20z3","Visitor","Decide individual or family ticket");
        intentTask_17x20z3.addBasicInfo("StartEvent_1yl25iv","StartEvent_1yl25iv",
                "Task_17x20z3","ExclusiveGateway_0ajb73w",null);
        expectedIntents.add(intentTask_17x20z3);

        myIntent intentTask_1gst9f9 = new myIntent("Task_1gst9f9","Zoo","Pass information to Marketing department");
        intentTask_1gst9f9.addBasicInfo("Task_02qz4bj","Task_02qz4bj",
                "Task_1gst9f9","Task_0j4npun",null);
        expectedIntents.add(intentTask_1gst9f9);

        myIntent intentTask_1qrcrh7 = new myIntent("Task_1qrcrh7","Zoo","Wait for payment");
        intentTask_1qrcrh7.addBasicInfo("Task_0oih0sg","Task_0oih0sg",
                "ParallelGateway_1oafzb2","IntermediateCatchEvent_1dgnju9",null);
        expectedIntents.add(intentTask_1qrcrh7);






        return expectedIntents;
    }




}