package main.Entity.Parser;

import main.Entity.Intent.Intents;
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

    public void parse() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, SpinnerChief_SentenceParaphraserException {
        /**
        try {
            this.intents.add(bpmnAlgorithm.parse());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
        this.intents.add(bpmnAlgorithm.parse());
        this.intents.build();
    }

}
