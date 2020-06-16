package main.Entity.Parser;

import main.Entity.Intent.Intents;
import main.Entity.Parser.BpmnAlgorithm.BpmnAlgorithm;
import main.Entity.Parser.BpmnAlgorithm.BpmnAlgorithmImpl;
import main.Exceptions.NotBpmnTypeException;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.Exceptions.SpinnerChief_SentenceParaphraserException;


import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.io.File;
import java.io.IOException;


public class Parser {
    public static void println(String s) { System.out.println(s); }

    public BpmnModelInstance modelInstance;
    public Intents intents;

    public BpmnAlgorithm bpmnAlgorithm;

    /*
     * CONSTRUCTORS
     */
    public Parser(String bpmnPath) throws IOException, NotBpmnTypeException {
        this.intents = Intents.getInstance();

        // read a model from a file
        File file = new File(bpmnPath);
        if(!file.exists()) throw new IOException("Doesn't exist a file with the path: " + bpmnPath);

        this.modelInstance = Bpmn.readModelFromFile(file);

        this.bpmnAlgorithm = new BpmnAlgorithmImpl(this.modelInstance);
    }

    /*
     * GETTERS & SETTERS
     */
    public Intents getIntents() { return this.intents; }



    /*
     * Parser
     */

    public void parse() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, SpinnerChief_SentenceParaphraserException {
        /**
        try {
            this.intents.add(bpmnAlgorithm.parse());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
        this.intents.add(bpmnAlgorithm.parse());
        this.intents.build(modelInstance);
    }

    public void translateIntoDialogFlow() throws Exception {
        this.getIntents().translateIntoDialogFlow();
    }
}
