package main.Entity.Parser.ParserFlowNodes;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CamundaHelper {
    public BpmnModelInstance modelInstance;

    private static CamundaHelper instance;
    public static CamundaHelper getInstance(BpmnModelInstance modelInstance) {
        if(instance == null) instance = new CamundaHelper(modelInstance);
        return instance;
    }

    public CamundaHelper(BpmnModelInstance modelInstance) {
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

    // Get following flow nodes(task, gateway, etc) of a FlowNode as List.
    public List<FlowNode> getAllFlowingFlowNodesAsList(FlowNode node) {
        List<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            followingFlowNodes.add(sequenceFlow.getTarget());
        }
        return followingFlowNodes;
    }

    // Get previous flow nodes(task, gateway, etc) of a FlowNode.
    public Collection<FlowNode> getAllIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            followingFlowNodes.add(sequenceFlow.getSource());
        }
        return followingFlowNodes;
    }

    // Get previous flow nodes(task, gateway, etc) of a FlowNode  as List
    public List<FlowNode> getAllIncomingFlowNodesAsList(FlowNode node) {
        List<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            followingFlowNodes.add(sequenceFlow.getSource());
        }
        return followingFlowNodes;
    }

    // Get following flow nodes(task, gateway, etc) of a FlowNode.
    // Ignores a flownode if it is a closing exclusive/parallel gateway
    public Collection<FlowNode> getRelevantFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            FlowNode followingNode = sequenceFlow.getTarget();
            ParseFlowNodesHelper parseFlowNodesHelper = ParseFlowNodesHelper.getInstance(modelInstance);
            if( parseFlowNodesHelper.isClosingExclusiveGateway(followingNode ) ||
                parseFlowNodesHelper.isClosingParallelGateway(followingNode) )
                followingFlowNodes.addAll(this.getRelevantFlowingFlowNodes(followingNode));
            else followingFlowNodes.add(followingNode);
        }
        return followingFlowNodes;
    }

    // Get following flow nodes(task, gateway, etc) of a FlowNode.
    // Ignores a flownode if it is a closing exclusive gateway
    public Collection<FlowNode> getRelevantFlowingFlowNodesPlusClosingParallelGateway(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            FlowNode followingNode = sequenceFlow.getTarget();
            ParseFlowNodesHelper parseFlowNodesHelper = ParseFlowNodesHelper.getInstance(modelInstance);
            if( parseFlowNodesHelper.isClosingExclusiveGateway(followingNode ) )
                followingFlowNodes.addAll(this.getRelevantFlowingFlowNodes(followingNode));
            else followingFlowNodes.add(followingNode);
        }
        return followingFlowNodes;
    }

    // Get previous flow nodes(task, gateway, etc) of a FlowNode.
    // Ignores a flownode if theu are closingExclusiveGateway
    public Collection<FlowNode> getRelevantIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> incomingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            FlowNode incomingNode = sequenceFlow.getSource();
            ParseFlowNodesHelper parseFlowNodesHelper = ParseFlowNodesHelper.getInstance(modelInstance);
            if(parseFlowNodesHelper.isClosingExclusiveGateway(incomingNode) )
                incomingFlowNodes.addAll(this.getRelevantIncomingFlowNodes(incomingNode));
            else incomingFlowNodes.add(incomingNode);
        }
        return incomingFlowNodes;
    }

    // Gives the source subject (ex:"Departament", "Empleat") of the message flow
    public String getSourceSubject(MessageFlow messageFlow) {
        String sourceSubject = null;

        InteractionNode interactionNode = messageFlow.getSource();
        String nodeID = interactionNode.getId();

        FlowNode flowNode = this.modelInstance.getModelElementById(nodeID);
        org.camunda.bpm.model.bpmn.instance.Process process = (org.camunda.bpm.model.bpmn.instance.Process) flowNode.getParentElement();
        String processID = process.getId();

        Collection<Participant> participants = this.modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : participants) {
            if(processID.equals(participant.getProcess().getId() ) ) sourceSubject = participant.getName();
        }

        return sourceSubject;
    }

    // Gives the target subject (ex:"Departament", "Empleat") of the message flow
    public String getTargetSubject(MessageFlow messageFlow) {
        String targetSubject = null;

        InteractionNode interactionNode = messageFlow.getTarget();
        String nodeID = interactionNode.getId();

        FlowNode flowNode = this.modelInstance.getModelElementById(nodeID);
        org.camunda.bpm.model.bpmn.instance.Process process = (Process) flowNode.getParentElement();
        String processID = process.getId();

        Collection<Participant> participants = this.modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : participants) {
            if(processID.equals(participant.getProcess().getId() ) ) targetSubject = participant.getName();
        }

        return targetSubject;
    }
}
