package main.Entity.Parser;

import main.Entity.Intent.Intent;
import main.Entity.Intent.Intents;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.Collection;

public class BpmnAlgorithm {
    public static void println(String s) { System.out.println(s); }

    public Intents intents;
    public BpmnModelInstance modelInstance;

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

    public Intents parse() {
        Collection<Participant> ParticipantInstances = modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : ParticipantInstances) {
            this.intents.add(this.parseParticipant(participant));
        }

        Collection<MessageFlow> messageFlowIntsances = modelInstance.getModelElementsByType(MessageFlow.class);
        for(MessageFlow messageFlow : messageFlowIntsances) {
            this.parseMessageFlow(messageFlow); // this.intents es actualitzada dintre la funcio
        }

        return this.intents;
    }

    private void parseMessageFlow(MessageFlow messageFlow) {
        String sourceID = messageFlow.getSource().getId();
        String targetID = messageFlow.getTarget().getId();

        FlowNode sourceNode = this.modelInstance.getModelElementById(sourceID);
        FlowNode targetNode = this.modelInstance.getModelElementById(targetID);


        String name = messageFlow.getId();
        String sourceSubject = this.getSourceSubject(messageFlow);
        String targetSubject = this.getTargetSubject(messageFlow);
        String task = messageFlow.getName();
        Intent intent = new CollaborationIntent(name, sourceSubject, targetSubject, task);

        this.intents.insertIntent(intent, sourceID, targetID);
    }

    private Intents parseParticipant(Participant participant) {
        Process process = participant.getProcess();
        Intents intents = this.parseProcess(participant, process);
        return intents;
    }

    private Intents parseProcess(Participant participant, Process process) {
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
    private Intents parseFlowNode(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        ModelElementType startEventType         = this.modelInstance.getModel().getType(StartEvent.class);
        ModelElementType taskType               = this.modelInstance.getModel().getType(Task.class);
        ModelElementType exclusiveGatewayType   = this.modelInstance.getModel().getType(ExclusiveGateway.class);
        ModelElementType messageFlowType        = this.modelInstance.getModel().getType(MessageFlow.class);
        ModelElementType sequenceFlowType       = this.modelInstance.getModel().getType(SequenceFlow.class);
        ModelElementType endEventType           = this.modelInstance.getModel().getType(EndEvent.class);



        if     (node.getElementType() == startEventType)       intents.add(this.parserFlowNodes.parseStartEvent      (participant, process, node));
        else if(node.getElementType() == taskType)             intents.add(this.parserFlowNodes.parseTask            (participant, process, node));
        else if(node.getElementType() == exclusiveGatewayType) intents.add(this.parserFlowNodes.parseExclusiveGateway(participant, process, node));
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
