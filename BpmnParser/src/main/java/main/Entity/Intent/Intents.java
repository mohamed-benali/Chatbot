package main.Entity.Intent;

import com.google.cloud.dialogflow.v2.Intent;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Intents {
    public static void println(String s) { System.out.println(s); }

    private Map<String, myIntent> intents; // String: name(identificador)

    /*
     * CONSTRUCTORS
     */
    public Intents() {
        intents = new TreeMap<String, myIntent>();
    }


    //region REGION: Override(equals,toString)
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Intents) {
            Intents otherIntents = (Intents) obj;
            if (this.getIntents().equals(otherIntents.getIntents())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return intents.toString();
    }
    //endregion

    /*
     * GETTERS & SETTERS
     */
    public Map<String, myIntent> getIntents() { return this.intents; }
    public void setIntents(Map<String, myIntent> intents) { this.intents = intents; }

    public myIntent getIntentByID(String firstNodeId) {
        return intents.get(firstNodeId);
    }

    //region REGION: Add Intent
    /*
     * Add intents
     */
    public void add(myIntent intent) { this.intents.put(intent.getId(), intent); }

    public void add(Map<String, myIntent> intents) {
        for(Map.Entry<String, myIntent> entry : intents.entrySet()) {
            this.add(entry.getValue());
        }
    }
    public void add(Intents intents) {
        this.add(intents.getIntents());
    }

    public void add_null_intent(String intentID) { this.intents.put(intentID, null); }
    //endregion

    //region REGION: Insert Intent
    /*
     * Inserts an intent after the intent identified by *intentId*
     */ // todo: should take into account if this is near a gateway(so can put the proper output context)
    public void insertAfterIntent(myIntent intent, String intentId) {
        myIntent sourceIntent = this.intents.get(intentId); // Outgoing

        List<String> sourceOutputIntents = sourceIntent.getOutputIntents();
        intent.addOutputIntentIDs(sourceOutputIntents); // Intent
        intent.addOutputContextID(intent.getId()); // TODO: Put the correct output Context(ex: if there is a gateway)
        //TODO: Consider the gateways when creating the output context
        for (String outputIntentID : intent.getOutputIntents() ) {
            myIntent outputIntent = this.intents.get(outputIntentID);
            outputIntent.addInputContextID(intent.getId());
            outputIntent.removeInputContextID(intentId); // TODO: Consider the gateways when creating the context
        }

        sourceIntent.clearOutputIntents();
        sourceIntent.addOutputIntentID(intent.getId());
        //sourceIntent.addOutputContextID(intent.getId());
        intent.addInputContextID(sourceIntent.getId());

        this.add(intent);
    }

    /*
     * Inserts an intent before the intent identified by *intentId*
     */ // TODO: Inserir per tots aquells que tenen al intent com a outputIntent(aprofitar que si jo soc el outputIntent,
    // TODO: tu ets el meu inputIntent
    public void insertBeforeIntent(myIntent intent, String intentId) {
        myIntent targetIntent = this.intents.get(intentId); // Incoming
        List<String> targetInputIntents = targetIntent.getInputContexts();
        intent.addInputContextIDs(targetInputIntents);
        for (String inputIntentID : intent.getInputContexts() ) {
            myIntent inputIntent = this.intents.get(inputIntentID);
            inputIntent.addOutputIntentID(intent.getId());
            inputIntent.removeOutputIntentID(intentId);
        }

        targetIntent.clearInputContexts();
        targetIntent.addInputContextID(intent.getId());
        intent.addOutputIntentID(targetIntent.getId());
        intent.addOutputContextID(intent.getId()); // Context

        this.add(intent);
    }
    //endregion


    //region REGION: Print Intent
    /*
     * PRINT Intents
     */
    public void print() {
        for(Map.Entry<String, myIntent> entry : intents.entrySet()) {
            myIntent intent = entry.getValue();
            intent.print();
        }
    }

    public void printIDs() {
        for(Map.Entry<String, myIntent> entry : intents.entrySet()) {
            println(entry.getKey());
        }
    }
    //endregion



    /**
     * Builds the intents
     * <br><br>
     * Specifically builds(prepares) all the information, such as the TrainingPhrases, that will be uploaded on DialogFlow.
     *
     * dsdsd
     */
    public void build() {
        /* TODO:
             maybe create here(or in Parser.parse() ) the QueryTasks( createAdditionalTask or createQueryTasks)
             Responses
             TrainingPhrases
             Some other thing
        */

        for(Map.Entry<String, myIntent> entry : intents.entrySet()) {
            this.add(entry.getValue());
        }
        //
    }

    /*
     * Translate into Dialogflow
     */
    public void translateIntoDialogFlow() throws Exception {
        for(Map.Entry<String, myIntent> entry : intents.entrySet()) {
            myIntent intent = entry.getValue();
            /*
            try {
                intent.translateIntoDialogFlow();
            } catch (Exception e) {
                println(e.getMessage());
            }*/
            intent.translateIntoDialogFlow(); // For debugging

        }
    }






}
