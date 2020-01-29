package main.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Intent {
    private String name; // Nom del Intent, IDENTIFICADOR
    private String subject; // Who does it
    private String task;    // What is done

    private Map<String,Intent> inputIntents;  // Intents before

    private Map<String,Intent> outputIntents; // Intents after


    private List<String> trainingPhrases; // Training phrases



    public Intent() {
        inputIntents = new TreeMap<String, Intent>();
        outputIntents = new TreeMap<String, Intent>();
        trainingPhrases = new ArrayList<String>();
    }

    public Intent(String name) {
        this.name = name;

        inputIntents = new TreeMap<String, Intent>();
        outputIntents = new TreeMap<String, Intent>();
        trainingPhrases = new ArrayList<String>();
    }

    public Intent(String name, String subject, String task) {
        this.name = name;
        this.subject = subject;
        this.task = task;

        inputIntents = new TreeMap<String, Intent>();
        outputIntents = new TreeMap<String, Intent>();
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

    public Map<String,Intent> getInputIntents()                  { return inputIntents; }
    public void setInputIntents(Map<String,Intent> inputIntents) { this.inputIntents = inputIntents; }
    public void addInputIntent(Intent intent) { inputIntents.put(intent.getName(), intent); }
    public void addEmptyInputIntent(String intentID) { inputIntents.put(intentID, null); }


    public Map<String,Intent> getOutputIntents()                   { return outputIntents; }
    public void setOutputIntents(Map<String,Intent> outputIntents) { this.outputIntents = outputIntents; }
    public void addOutputIntent(Intent intent) { outputIntents.put(intent.getName(), intent); }
    public void addEmptyOutputIntent(String intentID) { inputIntents.put(intentID, null); }


    public List<String> getTrainingPhrases()                     { return trainingPhrases; }
    public void setTrainingPhrases(List<String> trainingPhrases) { this.trainingPhrases = trainingPhrases; }
    public void addTrainingPhrase(String trainingPhrase) { trainingPhrases.add(trainingPhrase); }






    /*
     * Methods
     */
}
