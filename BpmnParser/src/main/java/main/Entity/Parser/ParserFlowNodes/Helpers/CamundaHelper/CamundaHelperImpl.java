package main.Entity.Parser.ParserFlowNodes.Helpers.CamundaHelper;

import main.Entity.Parser.ParserFlowNodes.Helpers.ParseFlowNodesHelper.ParseFlowNodesHelper;
import main.Entity.Parser.ParserFlowNodes.Helpers.ParseFlowNodesHelper.ParseFlowNodesHelperImpl;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CamundaHelperImpl implements CamundaHelper{
    public BpmnModelInstance modelInstance;
    public ParseFlowNodesHelper parseFlowNodesHelper;

    public CamundaHelperImpl(BpmnModelInstance modelInstance, ParseFlowNodesHelper parseFlowNodesHelper) {
        this.modelInstance = modelInstance;
        this.parseFlowNodesHelper = parseFlowNodesHelper;
    }


    /*
     * HELPERS(Camunda BPMN)
     */


    //region REGION Incoming FlowNodes
    @Override
    public Collection<FlowNode> getAllIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            followingFlowNodes.add(sequenceFlow.getSource());
        }
        return followingFlowNodes;
    }

    @Override
    public List<FlowNode> getAllIncomingFlowNodesAsList(FlowNode node) {
        List<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            followingFlowNodes.add(sequenceFlow.getSource());
        }
        return followingFlowNodes;
    }

    @Override
    public Collection<FlowNode> getRelevantIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> incomingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            FlowNode incomingNode = sequenceFlow.getSource();
            if(parseFlowNodesHelper.isClosingExclusiveGateway(incomingNode) )
                incomingFlowNodes.addAll(this.getRelevantIncomingFlowNodes(incomingNode));
            else incomingFlowNodes.add(incomingNode);
        }
        return incomingFlowNodes;
    }
    //endregion

    //region REGION: Following FlowNodes
    @Override
    public Collection<FlowNode> getAllFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            followingFlowNodes.add(sequenceFlow.getTarget());
            // TODO: Consider if the next flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the following nodes are the following of the gateway,
        }
        return followingFlowNodes;
    }

    @Override
    public List<FlowNode> getAllFlowingFlowNodesAsList(FlowNode node) {
        List<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            followingFlowNodes.add(sequenceFlow.getTarget());
        }
        return followingFlowNodes;
    }

    @Override
    public Collection<FlowNode> getRelevantFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            FlowNode followingNode = sequenceFlow.getTarget();
            if( parseFlowNodesHelper.isClosingExclusiveGateway(followingNode ) ||
                parseFlowNodesHelper.isClosingParallelGateway(followingNode) )
                followingFlowNodes.addAll(this.getRelevantFlowingFlowNodes(followingNode));
            else followingFlowNodes.add(followingNode);
        }
        return followingFlowNodes;
    }

    @Override
    public Collection<FlowNode> getRelevantFlowingFlowNodesPlusClosingParallelGateway(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            FlowNode followingNode = sequenceFlow.getTarget();
            if( parseFlowNodesHelper.isClosingExclusiveGateway(followingNode ) )
                followingFlowNodes.addAll(this.getRelevantFlowingFlowNodes(followingNode));
            else followingFlowNodes.add(followingNode);
        }
        return followingFlowNodes;
    }
    //endregion




    //region get Source/Target Subject
    @Override
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

    @Override
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
    //endregion
}
