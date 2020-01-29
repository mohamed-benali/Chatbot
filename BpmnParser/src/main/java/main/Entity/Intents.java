package main.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Intents {

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

    /*
     * Translate into Dialogflow(use library?)
     */
    public void translateIntoDialogFlow() {

    }


}
