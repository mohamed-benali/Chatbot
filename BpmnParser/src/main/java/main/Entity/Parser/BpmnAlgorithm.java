package main.Entity.Parser;

import main.Entity.Intent.BeginIntent_Special;
import main.Entity.Intent.myIntent;
import main.Entity.Intent.Intents;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BpmnAlgorithm {
    public static void println(String s) { System.out.println(s); }

    public Intents intents;
    public BpmnModelInstance modelInstance; //TODO: Create a Wrapper class of modelInstance, so can create custom methods

    public ParserFlowNodes parserFlowNodes;



    /*
     * CONSTRUCTORS
     */
    public BpmnAlgorithm(BpmnModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        this.intents = new Intents();
        this.parserFlowNodes = new ParserFlowNodes(modelInstance);
    }




    /*
     * Parser
     */

    public Intents parse() throws IOException {
        // Participants
        List<String> participantNames = new ArrayList<String>();
        Collection<Participant> ParticipantInstances = modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : ParticipantInstances) {
            this.intents.add(this.parseParticipant(participant));

            participantNames.add(participant.getName());
        }
        //TODO: Add contexts    beginIntent.addDeleteContexts(???)

        // Begin intent
        BeginIntent_Special beginIntentSpecial = new BeginIntent_Special();
        beginIntentSpecial.setParticipantNames(participantNames);
        this.intents.add(beginIntentSpecial);

        // Collaboration
        Collection<MessageFlow> messageFlowIntsances = modelInstance.getModelElementsByType(MessageFlow.class);
        for(MessageFlow messageFlow : messageFlowIntsances) {
            myIntent sourceIntent = this.parserFlowNodes.parseSourceMessageFlow(messageFlow);
            myIntent targetIntent = this.parserFlowNodes.parseTargetMessageFlow(messageFlow);

            this.intents.insertAfterIntent (sourceIntent, messageFlow.getSource().getId());
            this.intents.insertBeforeIntent(targetIntent, messageFlow.getTarget().getId());
        }

        return this.intents;
    }



    private Intents parseParticipant(Participant participant) throws IOException {
        Process process = participant.getProcess();
        Intents intents = this.parseProcess(participant, process);
        return intents;
    }

    private Intents parseProcess(Participant participant, Process process) throws IOException {
        Intents intents = new Intents();

        Collection<StartEvent> startEvents = process.getChildElementsByType(StartEvent.class);
        for(StartEvent startEvent : startEvents) { //  Just one?
            intents.add(this.parseFlowNode(participant, process, startEvent) ); // Each node parses itself and parses outgoing nodes
        }

        return intents;
    }

    /*
     * Each node parses itself, then, calls recursively the function to parse outgoing nodes
     */
    private Intents parseFlowNode(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        ModelElementType startEventType         = this.modelInstance.getModel().getType(StartEvent.class);
        ModelElementType taskType               = this.modelInstance.getModel().getType(Task.class);
        ModelElementType exclusiveGatewayType   = this.modelInstance.getModel().getType(ExclusiveGateway.class);
        ModelElementType ParallelGatewayType    = this.modelInstance.getModel().getType(ParallelGateway.class);
        ModelElementType messageFlowType        = this.modelInstance.getModel().getType(MessageFlow.class);
        ModelElementType sequenceFlowType       = this.modelInstance.getModel().getType(SequenceFlow.class);
        ModelElementType endEventType           = this.modelInstance.getModel().getType(EndEvent.class);


        if     (node.getElementType() == startEventType)       intents.add(this.parserFlowNodes.parseStartEvent      (participant, process, node));
        else if(node.getElementType() == taskType)             intents.add(this.parserFlowNodes.parseTask            (participant, process, node));
        else if(node.getElementType() == exclusiveGatewayType) intents.add(this.parserFlowNodes.parseExclusiveGateway(participant, process, node));
        else if(node.getElementType() == ParallelGatewayType) {
            // After the execution of "parseParallelGateway", *node* changes to the closing parallelGateway
            Intents parsedIntents = this.parserFlowNodes.parseParallelGateway(participant, process, node);
            intents.add(parsedIntents);
        }
        else if(node.getElementType() == messageFlowType)      intents.add(this.parserFlowNodes.parseMessageFlow     (participant, process, node));
        else if(node.getElementType() == endEventType)         intents.add(this.parserFlowNodes.parseEndEvent        (participant, process, node));
        else intents.add(this.parserFlowNodes.parseFlowNode(participant, process, node));

        // Recursively parse the outgoing flownodes.
        Collection<FlowNode> flowNodes = parserFlowNodes.getRelevantFlowingFlowNodes(node); // Get following flow nodes(task, gateway, etc) of a FlowNode.
        for(FlowNode flowNode: flowNodes) {
            intents.add(this.parseFlowNode(participant, process, flowNode));
        }

        return intents;
    }
}
