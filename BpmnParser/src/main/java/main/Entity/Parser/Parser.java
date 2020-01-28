package main.Entity.Parser;

import main.Entity.Intents;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;


public class Parser {
    public static void println(String s) { System.out.println(s); }

    public BpmnModelInstance modelInstance;
    public Intents intents;

    public BpmnAlgorithm bpmnAlgorithm;

    /*
     * CONSTRUCTORS
     */
    public Parser(String bpmnPath) {
        this.intents = new Intents();

        // read a model from a file
        File file = new File(bpmnPath);
        this.modelInstance = Bpmn.readModelFromFile(file);

        this.bpmnAlgorithm = new BpmnAlgorithm(this.modelInstance);
    }

    /*
     * GETTERS & SETTERS
     */
    public Intents getIntents() { return this.intents; }



    /*
     * Parser
     */

    public void parse() {
        this.intents.add(bpmnAlgorithm.parse());
    }

}
