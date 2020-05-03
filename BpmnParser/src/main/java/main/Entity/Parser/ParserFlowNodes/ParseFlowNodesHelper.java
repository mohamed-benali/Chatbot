package main.Entity.Parser.ParserFlowNodes;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ParseFlowNodesHelper {
    public BpmnModelInstance modelInstance;

    private CamundaHelper camundaHelper;


    private static ParseFlowNodesHelper instance;
    public static ParseFlowNodesHelper getInstance(BpmnModelInstance modelInstance) {
        if(instance == null) instance = new ParseFlowNodesHelper(modelInstance);
        return instance;
    }

    public ParseFlowNodesHelper(BpmnModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        if(camundaHelper==null) camundaHelper = CamundaHelper.getInstance(modelInstance);
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
    public String createName(MessageFlow messageFlow, String subject) {
        String name = messageFlow.getId() + "_" + subject;
        return name;
    }

    public String createName(Participant participant, Process process, FlowNode node,
                             String textAtStart, String textAtEnd)
    {
        String name = createName(participant, process, node);
        name = textAtStart + name + textAtEnd;
        return name;
    }


    // TODO: USe some wildcard
    public List<FlowNode> convertToFlowNodeList(Collection<FlowNode> nodes) {
        int nBranques = nodes.size();
        FlowNode[] firstNodesArray = nodes.toArray(new FlowNode[nBranques]); // Primers nodes de les branques
        List<FlowNode> firstNodes = new ArrayList<FlowNode>(Arrays.asList(firstNodesArray));
        return firstNodes;
    }

    public List<SequenceFlow> convertToSequenceFlowList(Collection<SequenceFlow> nodes) {
        int nBranques = nodes.size();
        SequenceFlow[] firstNodesArray = nodes.toArray(new SequenceFlow[nBranques]); // Primers nodes de les branques
        List<SequenceFlow> firstNodes = new ArrayList<SequenceFlow>(Arrays.asList(firstNodesArray));
        return firstNodes;
    }






    /*
     *  GATEWAY opening/closing definitions
     */
    public boolean isClosingExclusiveGateway(FlowNode followingNode) {
        ModelElementType exclusiveGatewayType   = this.modelInstance.getModel().getType(ExclusiveGateway.class);
        if((followingNode.getElementType() == exclusiveGatewayType) && followingNode.getName() == null) return true;
        else return false;
    }
    public boolean isOpeningExclusiveGateway(FlowNode followingNode) {
        ModelElementType exclusiveGatewayType   = this.modelInstance.getModel().getType(ExclusiveGateway.class);
        if((followingNode.getElementType() == exclusiveGatewayType) && followingNode.getName() != null) return true;
        else return false;
    }

    public boolean isClosingParallelGateway(FlowNode followingNode) {
        ModelElementType parallelGatewayType   = this.modelInstance.getModel().getType(ParallelGateway.class);
        if(followingNode.getElementType() == parallelGatewayType){
            Collection<FlowNode> parallelGatewayIncomingflowNodes = camundaHelper.getAllIncomingFlowNodes(followingNode);
            int incomingSize = parallelGatewayIncomingflowNodes.size();
            return incomingSize > 1;
        }
        return false;
    }
    public boolean isOpeningParallelGateway(FlowNode followingNode) {
        ModelElementType parallelGatewayType   = this.modelInstance.getModel().getType(ParallelGateway.class);
        if(followingNode.getElementType() == parallelGatewayType){
            Collection<FlowNode> parallelGatewayIncomingflowNodes = camundaHelper.getAllIncomingFlowNodes(followingNode);
            int incomingSize = parallelGatewayIncomingflowNodes.size();
            return incomingSize == 1;
        }
        return false;
    }

    //region REGION: AFTER GATEWAY
    /*
     * AFTER GATEWAY
     */

    // Tells if the node is after ONLY one EMPTY exlusive gateway
    // Closing gateways dont have text
    public boolean after_closing_exclusive_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllIncomingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(isClosingExclusiveGateway(flowNode) ) return true;
        }
        return false;
    }

    // Tells if the node is after ONLY one NON EMPTY exlusive gateway
    // Opening gateways dont have text
    public boolean after_opening_exclusive_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllIncomingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(isOpeningExclusiveGateway(flowNode)) return true;
        }
        return false;
    }

    public boolean after_opening_parallel_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllIncomingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(this.isOpeningParallelGateway(flowNode)) return true;
        }
        return false;
    }

    public boolean after_closing_parallel_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllIncomingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(this.isClosingParallelGateway(flowNode)) return true;
        }
        return false;
    }
    //endregion


    //region REGION: BEFORE GATEWAY
    /*
     * BEFORE GATEWAY
     */

    // Tells if the node is before a closing exlusive gateway
    // Closing gateways dont have text
    public boolean before_closing_exclusive_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllFlowingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(isClosingExclusiveGateway(flowNode)) return true;
        }
        return false;
    }

    // Tells if the node is before an opening exlusive gateway
    // Closing gateways have text
    public boolean before_opening_exclusive_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllFlowingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(isOpeningExclusiveGateway(flowNode)) return true;
        }
        return false;
    }

    public boolean before_closing_parallel_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllFlowingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(this.isClosingParallelGateway(flowNode)) return true;
        }
        return false;
    }

    public boolean before_opening_parallel_gateway(FlowNode node) {
        Collection<FlowNode> flowNodes = camundaHelper.getAllFlowingFlowNodes(node);
        for(FlowNode flowNode : flowNodes) {
            if(this.isOpeningParallelGateway(flowNode)) return true;
        }
        return false;
    }
    //endregion


}
