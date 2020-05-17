package main.Entity.Parser.ParserFlowNodes.ParserFlowNodesDTO;

import main.Entity.Intent.Intents;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.camunda.bpm.model.bpmn.instance.Process;

public class ParserFlowNodesDTO {
    private Participant participant;
    private Process process;
    private FlowNode flowNode;
    private Intents intents;

    public ParserFlowNodesDTO(Participant participant, Process process, FlowNode node) {
        this.setParticipant(participant);
        this.setProcess(process);
        this.setFlowNode(node);
        this.setIntents(new Intents());
    }

    public Participant getParticipant() { return participant; }
    public void setParticipant(Participant participant) { this.participant = participant; }

    public Process getProcess() { return process; }
    public void setProcess(Process process) { this.process = process; }

    public FlowNode getFlowNode() { return flowNode; }
    public void setFlowNode(FlowNode flowNode) { this.flowNode = flowNode; }

    public Intents getIntents() { return intents; }
    public void setIntents(Intents intents) { this.intents = intents; }
}
