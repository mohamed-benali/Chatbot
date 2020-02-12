package main.Entity.Intent;

public class CollaborationIntent extends Intent {

    private String sourceSubject;
    private String targetSubject;

    public CollaborationIntent(String name, String sourceSubject, String targetSubject, String task) {
        super(name);
        this.setTask(task);

        this.setSourceSubject(sourceSubject);
        this.setTargetSubject(targetSubject);

    }

    public String getSourceSubject() { return sourceSubject; }
    public void setSourceSubject(String sourceSubject) { this.sourceSubject = sourceSubject; }

    public String getTargetSubject() { return targetSubject; }
    public void setTargetSubject(String targetSubject) { this.targetSubject = targetSubject; }



    public void print() {
        println("INTENT");
        println("Name:    " + this.getName());
        println("Subject Source: " + this.getSourceSubject());
        println("Subject Target: " + this.getTargetSubject());
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

    public void translateIntoDialogFlow() {
        // DialogFlow info
        String title = this.getName();

        // Contexts
        //this.getInputIntents();
        //this.getOutputIntents();

        //this.getTrainingPhrases();

        String response = this.makeResponse();
    }

}
