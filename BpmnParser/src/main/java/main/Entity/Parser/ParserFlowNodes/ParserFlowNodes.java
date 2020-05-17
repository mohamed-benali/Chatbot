package main.Entity.Parser.ParserFlowNodes;

import main.Entity.Intent.Intents;
import main.Entity.Intent.myIntent;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodesDTO.ParserFlowNodesDTO;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.MessageFlow;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.io.IOException;

public interface ParserFlowNodes {
    Intents parseFlowNode(Participant participant, Process process, FlowNode node) throws IOException;
    void parseFlowNode(ParserFlowNodesDTO parserFlowNodesDTO) throws IOException;

    myIntent parseTargetMessageFlow(MessageFlow messageFlow) throws IOException;
    myIntent parseSourceMessageFlow(MessageFlow messageFlow) throws IOException;

}
