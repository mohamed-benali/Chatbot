package main.Entity.Parser;

import main.Entity.Intent;
import main.Entity.Intents;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.List;

public class ParserFlowNodes {
    public static void println(String s) { System.out.println(s); }

    public BpmnModelInstance modelInstance;

    public ParserFlowNodes(BpmnModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }


    public Intents parseStartEvent(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        StartEvent startEvent = modelInstance.getModelElementById(node.getId());

        return intents;
    }

    public Intents parseTask(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        Task task = modelInstance.getModelElementById(node.getId());

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
