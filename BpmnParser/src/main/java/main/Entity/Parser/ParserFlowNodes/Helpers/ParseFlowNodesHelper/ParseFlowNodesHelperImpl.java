package main.Entity.Parser.ParserFlowNodes.Helpers.ParseFlowNodesHelper;

import main.Entity.Parser.ParserFlowNodes.Helpers.CamundaHelper.CamundaHelper;
import main.Entity.Parser.ParserFlowNodes.Helpers.CamundaHelper.CamundaHelperImpl;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ParseFlowNodesHelperImpl implements ParseFlowNodesHelper {
    public BpmnModelInstance modelInstance;

    private CamundaHelper camundaHelper;

    public ParseFlowNodesHelperImpl(BpmnModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        this.camundaHelper = new CamundaHelperImpl(modelInstance, this);
    }

    @Override
    public CamundaHelper getCamundaHelper() {
        return camundaHelper;
    }

    //region REGION: createName

    // Creates the name(id) for the Intent
    @Override
    public String createName(Participant participant, Process process, FlowNode node) {
        //String name = participant.getName() + ;
        String name = node.getId();
        return name;
    }
    @Override
    public String createName(MessageFlow messageFlow, String subject) {
        String name = messageFlow.getId() + "_" + subject;
        return name;
    }

    @Override
    public String createName(Participant participant, Process process, FlowNode node,
                             String textAtStart, String textAtEnd)
    {
        String name = createName(participant, process, node);
        name = textAtStart + name + textAtEnd;
        return name;
    }
    //endregion


    //region REGION: ConvertToList
    // TODO: USe some wildcard
    @Override
    public List<FlowNode> convertToFlowNodeList(Collection<FlowNode> nodes) {
        int nBranques = nodes.size();
        FlowNode[] firstNodesArray = nodes.toArray(new FlowNode[nBranques]); // Primers nodes de les branques
        List<FlowNode> firstNodes = new ArrayList<FlowNode>(Arrays.asList(firstNodesArray));
        return firstNodes;
    }

    @Override
    public List<SequenceFlow> convertToSequenceFlowList(Collection<SequenceFlow> nodes) {
        int nBranques = nodes.size();
        SequenceFlow[] firstNodesArray = nodes.toArray(new SequenceFlow[nBranques]); // Primers nodes de les branques
        List<SequenceFlow> firstNodes = new ArrayList<SequenceFlow>(Arrays.asList(firstNodesArray));
        return firstNodes;
    }
    //endregion






    //region REGION: Opening/Closing gateway definitions

    @Override
    public boolean isClosingExclusiveGateway(FlowNode followingNode) {
        ModelElementType exclusiveGatewayType   = this.modelInstance.getModel().getType(ExclusiveGateway.class);
        if((followingNode.getElementType() == exclusiveGatewayType) && followingNode.getName() == null) return true;
        else return false;
    }
    @Override
    public boolean isOpeningExclusiveGateway(FlowNode followingNode) {
        ModelElementType exclusiveGatewayType   = this.modelInstance.getModel().getType(ExclusiveGateway.class);
        if((followingNode.getElementType() == exclusiveGatewayType) && followingNode.getName() != null) return true;
        else return false;
    }

    @Override
    public boolean isClosingParallelGateway(FlowNode followingNode) {
        ModelElementType parallelGatewayType   = this.modelInstance.getModel().getType(ParallelGateway.class);

        if(followingNode.getElementType() == parallelGatewayType){
            Collection<FlowNode> parallelGatewayIncomingflowNodes = camundaHelper.getAllIncomingFlowNodes(followingNode);
            int incomingSize = parallelGatewayIncomingflowNodes.size();
            return incomingSize > 1;
        }
        return false;
    }
    @Override
    public boolean isOpeningParallelGateway(FlowNode followingNode) {
        ModelElementType parallelGatewayType   = this.modelInstance.getModel().getType(ParallelGateway.class);
        if(followingNode.getElementType() == parallelGatewayType){
            Collection<FlowNode> parallelGatewayIncomingflowNodes = camundaHelper.getAllIncomingFlowNodes(followingNode);
            int incomingSize = parallelGatewayIncomingflowNodes.size();
            return incomingSize == 1;
        }
        return false;
    }
    //endregion

    //region REGION: AFTER GATEWAY

    @Override
    public boolean after_closing_exclusive_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllIncomingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(isClosingExclusiveGateway(flowNode) ) return true;
        }
        return false;
    }

    @Override
    public boolean after_opening_exclusive_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllIncomingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(isOpeningExclusiveGateway(flowNode)) return true;
        }
        return false;
    }
    @Override
    public boolean after_opening_parallel_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllIncomingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(this.isOpeningParallelGateway(flowNode)) return true;
        }
        return false;
    }
    @Override
    public boolean after_closing_parallel_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllIncomingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(this.isClosingParallelGateway(flowNode)) return true;
        }
        return false;
    }
    //endregion


    //region REGION: BEFORE GATEWAY

    @Override
    public boolean before_closing_exclusive_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllFlowingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(isClosingExclusiveGateway(flowNode)) return true;
        }
        return false;
    }


    @Override
    public boolean before_opening_exclusive_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllFlowingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(isOpeningExclusiveGateway(flowNode)) return true;
        }
        return false;
    }
    @Override
    public boolean before_closing_parallel_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllFlowingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(this.isClosingParallelGateway(flowNode)) return true;
        }
        return false;
    }
    @Override
    public boolean before_opening_parallel_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllFlowingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(this.isOpeningParallelGateway(flowNode)) return true;
        }
        return false;
    }
    //endregion


}
