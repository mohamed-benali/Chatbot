package main.Entity.Parser;

import main.Entity.Intent;
import main.Entity.Intents;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class ParserFlowNodes {
    public static void println(String s) { System.out.println(s); }

    public BpmnModelInstance modelInstance;

    public ParserFlowNodes(BpmnModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }


    /*
     * HELPERS(Camunda BPMN)
     */

    // Get following flow nodes(task, gateway, etc) of a FlowNode.
    public Collection<FlowNode> getFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            if(sequenceFlow.getName() != null) followingFlowNodes.add(sequenceFlow.getTarget());
            // TODO: Consider if the next flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the following nodes are the following of the gateway,
        }
        return followingFlowNodes;
    }

    // Get previous flow nodes(task, gateway, etc) of a FlowNode.
    public Collection<FlowNode> getIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            followingFlowNodes.add(sequenceFlow.getSource());
            // TODO: Consider if the incoming flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the incoming nodes are the incoming of that gateway,
        }
        return followingFlowNodes;
    }

    /*
     * AUXILIAR METHODS
     */

    public String createName(Participant participant, Process process, FlowNode node) {
        //String name = participant.getName() + ;
        String name = node.getId();
        return name;
    }


    /*
     * PARSE FLOW NODES
     */


    public Intents parseStartEvent(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        StartEvent startEvent = modelInstance.getModelElementById(node.getId());

        return intents;
    }

    public Intents parseTask(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        Task task = modelInstance.getModelElementById(node.getId());

        Collection<SequenceFlow> incomingSequenceFlow = task.getIncoming();
        Collection<SequenceFlow> outgoingSequenceFlow = task.getOutgoing();

        Collection<FlowNode> incomingFlowNodes = getIncomingFlowNodes(task);
        Collection<FlowNode> outgoingFlowNodes = getFlowingFlowNodes(task);

        String name = createName(participant, process, task); // The name is the identificator
        String subject = participant.getName();
        String tasca = task.getName();

        Intent intent = new Intent(name, subject, tasca);

        for(SequenceFlow sequenceFlow : incomingSequenceFlow) intent.addTrainingPhrase(sequenceFlow.getName());

        for(FlowNode flowNode : incomingFlowNodes) intent.addEmptyInputIntent (createName(participant, process, flowNode));
        for(FlowNode flowNode : outgoingFlowNodes) intent.addEmptyOutputIntent(createName(participant, process, flowNode));

        // TODO: LOOK AT BpmnAlgorithm.java on function: public Intents parse()
        // TODO: CONSIDER THAT IDEA




        return intents;
    }

    public Intents parseExclusiveGateway(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        ExclusiveGateway exclusiveGateway = modelInstance.getModelElementById(node.getId());

        return intents;
    }

    public Intents parseMessageFlow(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        MessageFlow messageFlow = modelInstance.getModelElementById(node.getId());

        return intents;
    }

    public Intents parseSequenceFlow(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        SequenceFlow sequenceFlow = modelInstance.getModelElementById(node.getId());

        return intents;
    }

    public Intents parseEndEvent(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        EndEvent endEvent = modelInstance.getModelElementById(node.getId());

        return intents;
    }
}
