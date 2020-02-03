package main.Entity.Parser;

import main.Entity.Intent.Intent;
import main.Entity.Intent.Intents;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.Collection;

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
    public Collection<FlowNode> getAllFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            followingFlowNodes.add(sequenceFlow.getTarget());
            // TODO: Consider if the next flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the following nodes are the following of the gateway,
        }
        return followingFlowNodes;
    }

    // Get previous flow nodes(task, gateway, etc) of a FlowNode.
    public Collection<FlowNode> getAllIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            followingFlowNodes.add(sequenceFlow.getSource());
            // TODO: Consider if the incoming flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the incoming nodes are the incoming of that gateway,
        }
        return followingFlowNodes;
    }

    // Get following flow nodes(task, gateway, etc) of a FlowNode.
    // Ignores a flownode if it hasnt a Name.
    public Collection<FlowNode> getRelevantFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            FlowNode followingNode = sequenceFlow.getTarget();
            if(followingNode.getName() != null) followingFlowNodes.add(followingNode);
            else followingFlowNodes.addAll(this.getRelevantFlowingFlowNodes(followingNode));

            // TODO: Consider if the next flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the following nodes are the following of the gateway,
        }
        return followingFlowNodes;
    }

    // Get previous flow nodes(task, gateway, etc) of a FlowNode.
    // Ignores a flownode if it hasnt a Name.
    public Collection<FlowNode> getRelevantIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> incomingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            FlowNode incomingNode = sequenceFlow.getSource();
            if(incomingNode.getName() != null) incomingFlowNodes.add(incomingNode);
            else incomingFlowNodes.addAll(this.getRelevantIncomingFlowNodes(incomingNode));
            // TODO: Consider if the incoming flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the incoming nodes are the incoming of that gateway,
        }
        return incomingFlowNodes;
    }


    /*
     * AUXILIAR METHODS
     */
    // Creates the name(id) for the Intent
    public String createName(Participant participant, Process process, FlowNode node) {
        //String name = participant.getName() + ;
        String name = node.getId();
        return name;
    }


    /*
     * PARSE FLOW NODES
     */



    public Intents parseFlowNode(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        Collection<SequenceFlow> incomingSequenceFlow = node.getIncoming();
        Collection<SequenceFlow> outgoingSequenceFlow = node.getOutgoing();

        Collection<FlowNode> incomingFlowNodes = getRelevantIncomingFlowNodes(node);
        Collection<FlowNode> outgoingFlowNodes = getRelevantFlowingFlowNodes(node);

        String name = createName(participant, process, node); // The name is the identificator
        String subject = participant.getName();
        String tasca = node.getName();

        Intent intent = new Intent(name, subject, tasca);

        for(SequenceFlow sequenceFlow : incomingSequenceFlow) intent.addTrainingPhrase(sequenceFlow.getName());

        for(FlowNode flowNode : incomingFlowNodes) intent.addEmptyInputIntent (createName(participant, process, flowNode));
        for(FlowNode flowNode : outgoingFlowNodes) intent.addEmptyOutputIntent(createName(participant, process, flowNode));


        intents.add(intent);

        return intents;
    }

    public Intents parseStartEvent(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        StartEvent startEvent = modelInstance.getModelElementById(node.getId());
        intents.add(this.parseFlowNode(participant, process, startEvent));
        return intents;
    }

    public Intents parseTask(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        Task task = modelInstance.getModelElementById(node.getId());

        intents.add(this.parseFlowNode(participant, process, node));

        // TODO: LOOK AT BpmnAlgorithm.java on function: public Intents parse()
        // TODO: CONSIDER THAT IDEA

        return intents;
    }



    public Intents parseExclusiveGateway(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        ExclusiveGateway exclusiveGateway = modelInstance.getModelElementById(node.getId());
        intents.add(this.parseFlowNode(participant, process, exclusiveGateway));

        return intents;
    }

    public Intents parseMessageFlow(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        MessageFlow messageFlow = modelInstance.getModelElementById(node.getId());

        return intents;
    }

    public Intents parseEndEvent(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        EndEvent endEvent = modelInstance.getModelElementById(node.getId());
        intents.add(this.parseFlowNode(participant, process, endEvent));

        return intents;
    }
}
