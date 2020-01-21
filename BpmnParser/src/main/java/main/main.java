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

    public static void main(String[] args) {
        String packagePath = "./src/main/java/main";
        String bpmnPath = packagePath + "/../Data/diagram.bpmn";


        // read a model from a file
        File file = new File(bpmnPath);
        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);


        Collection<Task> taskInstances = modelInstance.getModelElementsByType(Task.class);

        for(Task task : taskInstances) {
            String name = task.getName();
            System.out.println("Name: " + name);
        }

    }
}
