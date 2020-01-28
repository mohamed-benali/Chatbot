package main.Entity;

import java.util.List;

public class Intent {
    private String subject; // Who does it
    private String task;    // What is done
    private List<Intent> inputIntents;  // Intents before
    private List<Intent> outputIntents; // Intents after
    private List<String> trainingPhrases; // Training phrases

    /*
     * GETTERS & SETTERS
     */
    public String getSubject()             { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getTask()          { return task; }
    public void setTask(String task) { this.task = task; }

    public List<Intent> getInputIntents()                  { return inputIntents; }
    public void setInputIntents(List<Intent> inputIntents) { this.inputIntents = inputIntents; }

    public List<Intent> getOutputIntents()                   { return outputIntents; }
    public void setOutputIntents(List<Intent> outputIntents) { this.outputIntents = outputIntents; }

    public List<String> getTrainingPhrases()                     { return trainingPhrases; }
    public void setTrainingPhrases(List<String> trainingPhrases) { this.trainingPhrases = trainingPhrases; }


    /*
     * Methods
     */
}
