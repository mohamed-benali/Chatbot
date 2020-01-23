package main;

import org.camunda.bpm.model.bpmn.*;

import org.camunda.bpm.model.bpmn.instance.Task;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.xml.ModelInstance;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.*;

import java.io.File;
import java.util.Collection;




public class main {

    public static void println(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        String packagePath = "./src/main/java/main";
        String bpmnPath = packagePath + "/../Data/diagram.bpmn";


        // read a model from a file
        File file = new File(bpmnPath);
        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);


        Collection<StartEvent> StartEventInstances = modelInstance.getModelElementsByType(StartEvent.class);
        println("\nStartEvents");
        for(StartEvent startEvent : StartEventInstances) {
            String name = startEvent.getName();
            println("Name: " + name);
        }

        println("\nParticipants");
        Collection<Participant> ParticipantInstances = modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : ParticipantInstances) {
            String name = participant.getName();
            if(name != null) println("Name: " + name);
        }


        println("\nMessageFlow");
        Collection<MessageFlow> messageFlowInstances = modelInstance.getModelElementsByType(MessageFlow.class);
        for(MessageFlow messageFlow : messageFlowInstances) {
            String source = messageFlow.getSource().getId();
            String target = messageFlow.getTarget().getId();
            println("source: " + source + "   " + "target: " + target);
        }

        println("\nTasks");
        Collection<Task> taskInstances = modelInstance.getModelElementsByType(Task.class);
        for(Task task : taskInstances) {
            String name = task.getName();
            println("Name: " + name);
        }

        println("\nSequenceFlow");
        Collection<SequenceFlow> sequenceFlowInstances = modelInstance.getModelElementsByType(SequenceFlow.class);
        for(SequenceFlow sequenceFlow : sequenceFlowInstances) {
            String name = sequenceFlow.getName();
            if(name != null) println("Name: " + name);
        }
        println("\nExclusiveGateway");
        Collection<ExclusiveGateway> exclusiveGatewayInstances = modelInstance.getModelElementsByType(ExclusiveGateway.class);
        for(ExclusiveGateway exclusiveGateway : exclusiveGatewayInstances) {
            String name = exclusiveGateway.getName();
            if(name != null) println("Name: " + name);
        }



        Collection<EndEvent> EndEventInstances = modelInstance.getModelElementsByType(EndEvent.class);
        println("\nEnd");
        for(EndEvent endEvent : EndEventInstances) {
            String name = endEvent.getName();
            println("Name: " + name);
        }

    }
}
