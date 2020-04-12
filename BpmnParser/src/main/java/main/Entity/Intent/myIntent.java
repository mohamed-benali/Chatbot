package main.Entity.Intent;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Lists;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.grpc.Context;
import io.opencensus.metrics.export.Distribution;
import main.Entity.DialogFlow.IntentManagment;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class myIntent {
    protected IntentsClient intentsClient;

    public static void println(String s) {
        System.out.println(s);
    }


    protected String name; // Nom del Intent, IDENTIFICADOR
    protected String subject; // Who does it
    protected String task;    // What is done

    protected List<String> inputContexts;  // Contexts that go before this intent

    protected List<String> outputIntents; // Intents that go after this intent


    protected List<String> outputContexts; // Output context to be placed on DialogFlow

    protected List<String> trainingPhrases; // Training phrases

    protected IntentManagment intentManagment;

    public myIntent() throws IOException {
        inputContexts = new ArrayList<String>();
        outputIntents = new ArrayList<String>();
        outputContexts = new ArrayList<String>();
        trainingPhrases = new ArrayList<String>();

        intentManagment = new IntentManagment();
    }

    public myIntent(String name) throws IOException {
        this.name = name;

        inputContexts = new ArrayList<String>();
        outputIntents = new ArrayList<String>();
        trainingPhrases = new ArrayList<String>();
        outputContexts = new ArrayList<String>();

        intentManagment = new IntentManagment();

    }

    public myIntent(String name, String subject, String task) throws IOException {
        this.name = name;
        this.subject = subject;
        this.task = task;

        inputContexts = new ArrayList<String>();
        outputIntents = new ArrayList<String>();
        trainingPhrases = new ArrayList<String>();
        outputContexts = new ArrayList<String>();

        intentManagment = new IntentManagment();

    }


    public void addBasicInfo(String inputContextID, String outputContextID,
                             String outputIntentID, String trainingPhrase) {
        this.addInputContextID(inputContextID);
        this.addOutputContextID(outputContextID);
        this.addOutputIntentID(outputIntentID);
        this.addTrainingPhrase(trainingPhrase);
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof myIntent) {
            myIntent otherIntent = (myIntent) o;
            if (this.getId().equals(otherIntent.getId()) &&
                this.getName().equals(otherIntent.getName()) &&
                this.getSubject().equals(otherIntent.getSubject()) &&
                this.getTask().equals(otherIntent.getTask()) &&
                this.getInputContexts().equals(otherIntent.getInputContexts()) &&
                this.getOutputContexts().equals(otherIntent.getOutputContexts()) &&
                this.getOutputIntents().equals(otherIntent.getOutputIntents()) &&
                this.getTrainingPhrases().equals(otherIntent.getTrainingPhrases()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String result = "";

        result += "Name:    " + this.getName() + "\n";
        result += "Subject: " + this.getSubject() + "\n";
        result += "Task:    " + this.getTask() + "\n";
        result += "Input Contexts:" + "\n";
        result += inputContexts.toString() + "\n";
        result += "Output Contexts:" + "\n";
        result += outputContexts.toString() + "\n";
        result += "Output Intents:" + "\n";
        result += outputIntents.toString() + "\n";
        result += "Training Phrases:" + "\n";
        result += trainingPhrases.toString() + "\n";
        result += "\n";

        return result;
    }


    /*
     * GETTERS & SETTERS
     */
    public String getId() {
        return this.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    /*
     * INPUT
     */
    public List<String> getInputContexts() {
        return this.inputContexts;
    }

    public void setInputContexts(List<String> inputIntents) {
        this.inputContexts = inputIntents;
    }

    public void addInputContextID(String intent) {
        this.inputContexts.add(intent);
    }

    // TODO: Update the Outgoing intents of the targetInputIntents?? OR leave it to "void insertIntent(...)" from Intents.java
    // TODO: Requieres the modelInstance(singleton???)
    public void addInputContextIDs(List<String> intents) {
        this.inputContexts.addAll(intents);
    }

    public void removeInputContextID(String intentID) {
        this.inputContexts.remove(intentID);
    }

    public void clearInputContexts() {
        inputContexts.clear();
    }

    /*
     * OUTPUT
     */
    public List<String> getOutputIntents() { return this.outputIntents; }

    public void setOutputIntents(List<String> outputIntents) {
        this.outputIntents = outputIntents;
    }

    public void addOutputIntentID(String intent) {
        this.outputIntents.add(intent);
    }

    // TODO: Update the incoming intents of the sourceOutputIntents
    // TODO: Requieres the modelInstance(singleton???)
    public void addOutputIntentIDs(List<String> intents) {
        this.outputIntents.addAll(intents);
    }

    public void removeOutputIntentID(String intentID) {
        this.outputIntents.remove(intentID);
    }

    public void clearOutputIntents() {
        outputIntents.clear();
    }

    /*
     * OUTPUT CONTEXT
     */
    public List<String> getOutputContexts() { return outputContexts; }
    public void setOutputContexts(List<String> outputContexts) { this.outputContexts = outputContexts; }


    public void addOutputContextID(String intent) {
        //this.outputContexts.add(intent);
        outputContexts.add(intent);
    }

    public void addOutputContextIDs(List<String> intents) { this.outputContexts.addAll(intents); }

    public void removeOutputContextID(String intentID) {
        this.outputContexts.remove(intentID);
    }

    public void clearOutputContexts() {
        outputContexts.clear();
    }


    /*
     * TRAINING PHRASES
     */
    public List<String> getTrainingPhrases() {
        return this.trainingPhrases;
    }

    public void setTrainingPhrases(List<String> trainingPhrases) {
        this.trainingPhrases = trainingPhrases;
    }

    public void addTrainingPhrase(String trainingPhrase) {
        this.trainingPhrases.add(trainingPhrase);
    }

    public void addTrainingPhrases(List<String> trainingPhrases) {
        this.trainingPhrases.addAll(trainingPhrases);
    }

    public void clearTrainingPhrases() {
        trainingPhrases.clear();
    }






    /*
     * Methods
     */

    public void print() {
        println("INTENT");
        println("Name:    " + this.getName());
        println("Subject: " + this.getSubject());
        println("Task:    " + this.getTask());
        println("Input Intents:");
        printIDs(inputContexts);
        println("Output Intents:");
        printIDs(outputIntents);
        println("Training Phrases:");
        for (String trainingPhrase : this.trainingPhrases) {
            println(trainingPhrase);
        }

        println("");
        println("");
    }

    public void printIDs(List<String> list) {
        for (String id : list) {
            println(id);
        }
    }

    public void translateIntoDialogFlow() throws Exception {
        // DialogFlow info
        String title = this.getName();
        println("");

        // Contexts
        //this.getInputIntents();
        //this.getOutputIntents();

        List<String> trainingPhrases = this.buildTrainingPhrases(this.getTrainingPhrases());
        List<String> responses = this.makeResponse();

        List<String> inputContextNames = this.buildInputContext(this.getInputContexts());
        List<String> outputContextNames = this.buildOutputContext();


        String projectId = "greetingsbot-qtakwv";
        //this.intentManagment.deleteIntent(projectId, title);

        // TODO: Provide the correct contexts(input, output, etc) (fix makeResponse, buildTrainingPhrases, etc)
        this.intentManagment.createIntent(title, projectId, trainingPhrases,
                                            responses, inputContextNames, outputContextNames);

    }

    // Returns the output contexts that this intent generates when is matched
    private List<String> buildOutputContext() {
        // Not Itself
        //List<String> outputContexts = new ArrayList<String>(this.getOutputIntents());
        List<String> outputContexts = new ArrayList<String>(this.getOutputContexts());
        //outputContexts.add(this.getId());

        // Delete Input context
        // TODO: Per ara els eliminare directament al crearlos aprofitant que
        //  ja els passo en la variable "inputContextNames"[en intentManagment.createIntent(...)]

        return outputContexts;
    }

    private List<String> buildInputContext(List<String> inputIntents) {
        return inputIntents;
    }

    protected List<String> buildTrainingPhrases(List<String> trainingPhrases) {
        println("Name: " + this.getId());
        System.out.println("-Size: " + trainingPhrases.size());
        println("-Before Phrases: ");
        for (String phrase : trainingPhrases) println(phrase);

        if(trainingPhrases.size() == 0) {
            //
        }
        else {
            for(int i = 0; i < trainingPhrases.size(); ++i) {
                if(trainingPhrases.get(i) == null) trainingPhrases.set(i, "Next");
            }
        }

        println("-After Phrases: ");
        for (String phrase : trainingPhrases) println(phrase);
        return trainingPhrases;
    }


    protected List<String> makeResponse() {
        List<String> responses = new ArrayList<String>();
        String response = this.getSubject() + " " + this.getTask();
        responses.add(response);
        return responses;
    }



}