package main.Entity.Parser.ParserFlowNodes.Helpers.CamundaHelper;

import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.MessageFlow;

import java.util.Collection;
import java.util.List;

public interface CamundaHelper {

    //region REGION: Incoming FowNodes
    /**
     * Get all previous flow nodes(task, gateway, etc) of a FlowNode.
     * @param Node The node to process
     * @return Returns all the previous flow nodes of the flownNode passed as a parameter
     */
    Collection<FlowNode> getAllIncomingFlowNodes(FlowNode Node);

    /**
     * Get all previous flow nodes(task, gateway, etc) of a FlowNode  as List
     * @param node The node to process
     * @return Returns the previous flow nodes, as a List, of the flownNode passed as a parameter
     */
    List<FlowNode> getAllIncomingFlowNodesAsList(FlowNode node);

    /**
     * Get relevant previous flow nodes(task, gateway, etc) of a FlowNode.
     * Ignores a flownode if it's a closingExclusiveGateway
     * @param node The node to process
     * @return Returns the relevant previous flow nodes of the node passed as a parameter
     */
    Collection<FlowNode> getRelevantIncomingFlowNodes(FlowNode node);
    //endregion

    //region REGION: Following FlowNodes
    /**
     * Get all following flow nodes(task, gateway, etc) of a FlowNode.
     * @param node The node to process
     * @return Returns all the following nodes of a flowNode
     */
    Collection<FlowNode> getAllFlowingFlowNodes(FlowNode node);

    /**
     * Get all following flow nodes(task, gateway, etc) of a FlowNode as List.
     * @param node The node to process
     * @return Returns all the following nodes of a flowNode as list
     */
    List<FlowNode> getAllFlowingFlowNodesAsList(FlowNode node);

    /**
     * Get relevant following flow nodes(task, gateway, etc) of a FlowNode.
     * Ignores a flownode if it is a closing exclusive/parallel gateway
     * @param node The node to process
     * @return Returns the relevant following nodes of a flowNode
     */
    Collection<FlowNode> getRelevantFlowingFlowNodes(FlowNode node);

    /**
     * Get following flow nodes(task, gateway, etc) of a FlowNode.
     * Ignores a flownode if it is a closing exclusive gateway
     * @param node The node to process
     * @return Returns the relevant following nodes of a flowNode plus closing parallel gateways
     */
    Collection<FlowNode> getRelevantFlowingFlowNodesPlusClosingParallelGateway(FlowNode node);
    //endregion


    //region REGION: get Source/Target Subject
    /**
     * Gives the source subject (ex:"Departament", "Empleat") of the message flow
     * @param messageFlow The message flow to process
     * @return
     */
    String getSourceSubject(MessageFlow messageFlow);

    /**
     * Gives the target subject (ex:"Departament", "Empleat") of the message flow
     * @param messageFlow The message flow to process
     * @return
     */
    String getTargetSubject(MessageFlow messageFlow);
    //endregion

}
