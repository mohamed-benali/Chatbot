package main.Entity;

import java.util.ArrayList;
import java.util.List;

public class Intents {

    private List<Intent> intents;

    /*
     * CONSTRUCTORS
     */
    public Intents() {
        intents = new ArrayList<Intent>();
    }

    /*
     * GETTERS & SETTERS
     */
    public List<Intent> getIntents() { return this.intents; }
    public void setIntents(List<Intent> intents) { this.intents = intents; }

    /*
     * Add intents
     */
    public void add(Intent intent) { this.intents.add(intent); }
    public void add(List<Intent> intents) {
        for(Intent intent : intents) this.add(intent);
    }
    public void add(Intents intents) {
        this.add(intents.getIntents());
    }

    /*
     * Translate into Dialogflow(use library?)
     */
    public void translateIntoDialogFlow() {

    }


}
