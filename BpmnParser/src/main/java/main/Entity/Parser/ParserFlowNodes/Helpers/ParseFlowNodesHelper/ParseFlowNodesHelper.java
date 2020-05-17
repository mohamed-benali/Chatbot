package main.Entity.Parser.ParserFlowNodes.Helpers.ParseFlowNodesHelper;

import main.Entity.Parser.ParserFlowNodes.Helpers.CamundaHelper.CamundaHelper;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.Collection;
import java.util.List;

public interface ParseFlowNodesHelper {

    CamundaHelper getCamundaHelper();




    String createName(Participant participant, Process process, FlowNode flowNode);
    String createName(Participant participant, Process process, FlowNode nextNode, String textAtStart, String textAtEnd);
    String createName(MessageFlow messageFlow, String sourceSubject);

    List<SequenceFlow> convertToSequenceFlowList(Collection<SequenceFlow> sequenceFlowsCollection);
    List<FlowNode> convertToFlowNodeList(Collection<FlowNode> followingNodes);

    /**
     * Tells if the node is after, at least, one closing exlusive gateway
     * As a design decision, closing exclusive gateways dont have text
     * @param node The node to evaluate
     * @return Returns if the node passed as a parameter is after a closing exclusive gateway
     */
    boolean after_closing_exclusive_gateway(FlowNode node);
    boolean after_closing_parallel_gateway(FlowNode node);

    /**
     * Tells if the node is after, at least, one opening exlusive gateway
     * As a design decision, opening exclusive gateways has text
     * @param node The node to evaluate
     * @return Returns if the node passed as a parameter is after an opening exclusive gateway
     */
    boolean after_opening_exclusive_gateway(FlowNode node);
    boolean after_opening_parallel_gateway(FlowNode node);



    /**
     * Tells if the node is before an opening exclusive gateway
     * As a design decision, opening exclusive gateways has text
     * @param node The node to evaluate
     * @return Returns if the node passed as a parameter is before an opening exclusive gateway
     */
    boolean before_opening_exclusive_gateway(FlowNode node);
    boolean before_opening_parallel_gateway(FlowNode node);

    /**
     * Tells if the node is before a closing exlusive gateway
     * As a design decision, closing exclusive gateways dont have text
     * @param node The node to evaluate
     * @return Returns if the node passed as a parameter is before a closing exclusive gateway
     */
    boolean before_closing_exclusive_gateway(FlowNode node);
    boolean before_closing_parallel_gateway(FlowNode node);

    /**
     * Tells if the node is an opening exclusive gateway
     * As a design decision, opening exclusive gateways have text
     * @param node The node to evaluate
     * @return Returns if the node passed as a parameter is a closing exclusive gateway
     */
    boolean isOpeningExclusiveGateway(FlowNode node);

    /**
     * Tells if the node is an opening parallel gateway
     * As a design decision, a node is a opening parallel gateways if(followingNode.getElementType() == parallelGatewayType)
     * and incoming flow nodes is greater than 1
     * @param node The node to evaluate
     * @return Returns if the node passed as a parameter is an opening parallel gateway
     */
    boolean isOpeningParallelGateway(FlowNode node);

    /**
     * Tells if the node is an closing exclusive gateway
     * As a design decision, a closing exclusive gateways dont has text
     * @param node The node to evaluate
     * @return Returns if the node passed as a parameter is a closing exclusive gateway
     */
    boolean isClosingExclusiveGateway(FlowNode node);

    /**
     * Tells if the node is an closing parallel gateway
     * As a design decision, a node is a closing parallel gateways if(followingNode.getElementType() == parallelGatewayType)
     * and incoming flow nodes equals 1
     * @param node The node to evaluate
     * @return Returns if the node passed as a parameter is a closing parallel gateway
     */
    boolean isClosingParallelGateway(FlowNode node);

}
