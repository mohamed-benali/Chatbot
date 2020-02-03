package main.Entity.Intent;

import java.util.ArrayList;
import java.util.List;

public class Intent {
    public static void println(String s) { System.out.println(s); }


    private String name; // Nom del Intent, IDENTIFICADOR
    private String subject; // Who does it
    private String task;    // What is done

    private Intents inputIntents;  // Intents before

    private Intents outputIntents; // Intents after

    private List<String> trainingPhrases; // Training phrases

    public Intent() {
        inputIntents = new Intents();
        outputIntents = new Intents();
        trainingPhrases = new ArrayList<String>();
    }

    public Intent(String name) {
        this.name = name;

        inputIntents = new Intents();
        outputIntents = new Intents();
        trainingPhrases = new ArrayList<String>();
    }

    public Intent(String name, String subject, String task) {
        this.name = name;
        this.subject = subject;
        this.task = task;

        inputIntents = new Intents();
        outputIntents = new Intents();
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

    public Intents getInputIntents()                  { return inputIntents; }
    public void setInputIntents(Intents inputIntents) { this.inputIntents = inputIntents; }
    public void addInputIntent(Intent intent) { inputIntents.add(intent); }
    public void addEmptyInputIntent(String intentID) { inputIntents.add_null_intent(intentID); }


    public Intents getOutputIntents()                   { return outputIntents; }
    public void setOutputIntents(Intents outputIntents) { this.outputIntents = outputIntents; }
    public void addOutputIntent(Intent intent) { outputIntents.add(intent); }
    public void addEmptyOutputIntent(String intentID) { outputIntents.add_null_intent(intentID); }


    public List<String> getTrainingPhrases()                     { return trainingPhrases; }
    public void setTrainingPhrases(List<String> trainingPhrases) { this.trainingPhrases = trainingPhrases; }
    public void addTrainingPhrase(String trainingPhrase) { trainingPhrases.add(trainingPhrase); }






    /*
     * Methods
     */

    public void print() {
        println("INTENT");
        println("Name:    " + this.getName());
        println("Subject: " + this.getSubject());
        println("Task:    " + this.getTask());
        println("Input Intents:");
        inputIntents.printIDs();
        println("Output Intents:");
        outputIntents.printIDs();
        println("Training Phrases:");
        for(String trainingPhrase : this.trainingPhrases) {
            println(trainingPhrase);
        }

        println("");
        println("");
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

    private String makeResponse() {
        return this.getSubject() + " " + this.getTask();
    }
}
