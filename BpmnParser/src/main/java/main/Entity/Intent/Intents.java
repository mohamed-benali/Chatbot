package main.Entity.Intent;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Intents {
    public static void println(String s) { System.out.println(s); }

    private Map<String, Intent> intents; // String: name(identificador)

    /*
     * CONSTRUCTORS
     */
    public Intents() {
        intents = new TreeMap<String, Intent>();
    }

    /*
     * GETTERS & SETTERS
     */
    public Map<String, Intent> getIntents() { return this.intents; }
    public void setIntents(Map<String, Intent> intents) { this.intents = intents; }

    /*
     * Add intents
     */
    public void add(Intent intent) { this.intents.put(intent.getId(), intent); }

    public void add(Map<String,Intent> intents) {
        for(Map.Entry<String,Intent> entry : intents.entrySet()) {
            this.add(entry.getValue());
        }
    }
    public void add(Intents intents) {
        this.add(intents.getIntents());
    }

    public void add_null_intent(String intentID) { this.intents.put(intentID, null); }

    /*
     * Inserts an intent after the intent identified by *intentId*
     */
    public void insertAfterIntent(Intent intent, String intentId) {
        Intent sourceIntent = this.intents.get(intentId); // Outgoing

        List<String> sourceOutputIntents = sourceIntent.getOutputIntents();
        intent.addOutputIntentIDs(sourceOutputIntents);
        for (String outputIntentID : intent.getOutputIntents() ) {
            Intent outputIntent = this.intents.get(outputIntentID);
            outputIntent.addInputIntentID(intent.getId());
            outputIntent.removeInputIntentID(intentId);
        }

        sourceIntent.clearOutputIntents();
        sourceIntent.addOutputIntentID(intent.getId());
        intent.addInputIntentID(sourceIntent.getId());

        this.add(intent);
    }

    /*
     * Inserts an intent before the intent identified by *intentId*
     */
    public void insertBeforeIntent(Intent intent, String intentId) {
        Intent targetIntent = this.intents.get(intentId); // Incoming
        List<String> targetInputIntents = targetIntent.getInputIntents();
        intent.addInputIntentIDs(targetInputIntents);
        for (String inputIntentID : intent.getInputIntents() ) {
            Intent inputIntent = this.intents.get(inputIntentID);
            inputIntent.addOutputIntentID(intent.getId());
            inputIntent.removeOutputIntentID(intentId);
        }

        targetIntent.clearInputIntents();
        targetIntent.addInputIntentID(intent.getId());
        intent.addOutputIntentID(targetIntent.getId());

        this.add(intent);
    }


    /*
     * PRINT Intents
     */
    public void print() {
        for(Map.Entry<String,Intent> entry : intents.entrySet()) {
            Intent intent = entry.getValue();
            intent.print();
        }
    }

    public void printIDs() {
        for(Map.Entry<String,Intent> entry : intents.entrySet()) {
            println(entry.getKey());
        }
    }

    /*
     * Translate into Dialogflow(use library?)
     */
    public void translateIntoDialogFlow() {
        for(Map.Entry<String,Intent> entry : intents.entrySet()) {
            Intent intent = entry.getValue();
            intent.translateIntoDialogFlow();
        }
    }



}
