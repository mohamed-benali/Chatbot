package main.Entity.Parser;

import main.Entity.Intents;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.ArrayList;
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
     * HELPERS
     */

    // Get following flow nodes(task, gateway, etc) of a FlowNode.
    public Collection<FlowNode> getFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            followingFlowNodes.add(sequenceFlow.getTarget());
        }
        return followingFlowNodes;
    }

    /*
     * Parser
     */

    public Intents parse() {
        Collection<Participant> ParticipantInstances = modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : ParticipantInstances) {
            this.intents.add(this.parseParticipant(participant));
        }

        return this.intents;
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
     *
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
        else if(node.getElementType() == sequenceFlowType)     intents.add(this.parserFlowNodes.parseSequenceFlow    (participant, process, node));
        else if(node.getElementType() == endEventType)         intents.add(this.parserFlowNodes.parseEndEvent        (participant, process, node));

        Collection<FlowNode> flowNodes = getFlowingFlowNodes(node); // Get following flow nodes(task, gateway, etc) of a FlowNode.
        for(FlowNode flowNode: flowNodes) {
            intents.add(this.parseFlowNode(participant, process, flowNode));
        }

        return intents;
    }
}
