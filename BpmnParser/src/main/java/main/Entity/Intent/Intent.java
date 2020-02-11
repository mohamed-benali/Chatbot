package main.Entity.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Intent {
    public static void println(String s) { System.out.println(s); }


    protected String name; // Nom del Intent, IDENTIFICADOR
    protected String subject; // Who does it
    protected String task;    // What is done

    protected List<String> inputIntents;  // Intents before

    protected List<String> outputIntents; // Intents after

    protected List<String> trainingPhrases; // Training phrases

    public Intent() {
        inputIntents = new ArrayList<String>();
        outputIntents = new ArrayList<String>();
        trainingPhrases = new ArrayList<String>();
    }

    public Intent(String name) {
        this.name = name;

        inputIntents = new ArrayList<String>();
        outputIntents = new ArrayList<String>();
        trainingPhrases = new ArrayList<String>();
    }

    public Intent(String name, String subject, String task) {
        this.name = name;
        this.subject = subject;
        this.task = task;

        inputIntents = new ArrayList<String>();
        outputIntents = new ArrayList<String>();
        trainingPhrases = new ArrayList<String>();
    }

    /*
     * GETTERS & SETTERS
     */
    public String getId() { return this.getName(); }

    public String getName()          { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject()             { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getTask()          { return task; }
    public void setTask(String task) { this.task = task; }

   /*
    * INPUT
    */
    public List<String> getInputIntents()                  { return this.inputIntents; }
    public void setInputIntents(List<String> inputIntents) { this.inputIntents = inputIntents; }

    public void addInputIntentID(String intent) { this.inputIntents.add(intent); }
    // TODO: Update the Outgoing intents of the targetInputIntents?? OR leave it to "void insertIntent(...)" from Intents.java
    // TODO: Requieres the modelInstance(singleton???)
    public void addInputIntentIDs(List<String> intents) { this.inputIntents.addAll(intents); }


    public void clearInputIntents() { inputIntents.clear(); }

    /*
     * OUTPUT
     */
    public List<String> getOutputIntents()                   { return this.outputIntents; }
    public void setOutputIntents(List<String> outputIntents) { this.outputIntents = outputIntents; }

    public void addOutputIntentID(String intent) { this.outputIntents.add(intent); }
    // TODO: Update the incoming intents of the sourceOutputIntents
    // TODO: Requieres the modelInstance(singleton???)
    public void addOutputIntentIDs(List<String> intents) { this.outputIntents.addAll(intents); }

    public void clearOutputIntents() { outputIntents.clear(); }

    /*
     * TRAINING PHRASES
     */
    public List<String> getTrainingPhrases()                     { return this.trainingPhrases; }
    public void setTrainingPhrases(List<String> trainingPhrases) { this.trainingPhrases = trainingPhrases; }
    public void addTrainingPhrase(String trainingPhrase) { this.trainingPhrases.add(trainingPhrase); }
    public void addTrainingPhrases(List<String> trainingPhrases) { this.trainingPhrases.addAll(trainingPhrases); }
    public void clearTrainingPhrases() { trainingPhrases.clear(); }






    /*
     * Methods
     */

    public void print() {
        println("INTENT");
        println("Name:    " + this.getName());
        println("Subject: " + this.getSubject());
        println("Task:    " + this.getTask());
        println("Input Intents:");
        printIDs(inputIntents);
        println("Output Intents:");
        printIDs(outputIntents);
        println("Training Phrases:");
        for(String trainingPhrase : this.trainingPhrases) {
            println(trainingPhrase);
        }

        println("");
        println("");
    }

    public void printIDs(List<String> list) {
        for(String id : list) {
            println(id);
        }
    }

    public void translateIntoDialogFlow() {
        // DialogFlow info
        String title = this.getName();

        // Contexts
        //this.getInputIntents();
        //this.getOutputIntents();

        //this.getTrainingPhrases();

        String response = this.makeResponse();
    }

    protected String makeResponse() {
        return this.getSubject() + " " + this.getTask();
    }


}
