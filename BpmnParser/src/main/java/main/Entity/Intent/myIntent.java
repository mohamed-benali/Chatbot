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
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.IntentsClient;
import com.google.cloud.dialogflow.v2.IntentsSettings;
import com.google.cloud.dialogflow.v2.ProjectAgentName;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.grpc.Context;
import io.opencensus.metrics.export.Distribution;

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

    protected List<String> inputIntents;  // Intents before

    protected List<String> outputIntents; // Intents after

    protected List<String> trainingPhrases; // Training phrases

    public myIntent() {
        inputIntents = new ArrayList<String>();
        outputIntents = new ArrayList<String>();
        trainingPhrases = new ArrayList<String>();
    }

    public myIntent(String name) {
        this.name = name;

        inputIntents = new ArrayList<String>();
        outputIntents = new ArrayList<String>();
        trainingPhrases = new ArrayList<String>();
    }

    public myIntent(String name, String subject, String task) {
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
    public List<String> getInputIntents() {
        return this.inputIntents;
    }

    public void setInputIntents(List<String> inputIntents) {
        this.inputIntents = inputIntents;
    }

    public void addInputIntentID(String intent) {
        this.inputIntents.add(intent);
    }

    // TODO: Update the Outgoing intents of the targetInputIntents?? OR leave it to "void insertIntent(...)" from Intents.java
    // TODO: Requieres the modelInstance(singleton???)
    public void addInputIntentIDs(List<String> intents) {
        this.inputIntents.addAll(intents);
    }

    public void removeInputIntentID(String intentID) {
        this.inputIntents.remove(intentID);
    }

    public void clearInputIntents() {
        inputIntents.clear();
    }

    /*
     * OUTPUT
     */
    public List<String> getOutputIntents() {
        return this.outputIntents;
    }

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
        printIDs(inputIntents);
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


        String projectId = "greetingsbot-qtakwv";

        String jsonPath = "./greetingsbot-qtakwv-c046349de531.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
        //Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        println(credentials.getAuthenticationType());

        IntentsSettings intentsSettings =
                IntentsSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
        this.intentsClient = IntentsClient.create(intentsSettings);

        this.createIntent(title, projectId, trainingPhrases, responses);




    }

    protected List<String> buildTrainingPhrases(List<String> trainingPhrases) {
        System.out.println("-Size: " + trainingPhrases.size());
        println("-Phrases: ");
        for (String phrase: trainingPhrases) println(phrase);
        return trainingPhrases;
    }


    protected List<String> makeResponse() {
        List<String> responses = new ArrayList<String>();
        String response = this.getSubject() + " " + this.getTask();
        responses.add(response);
        return responses;
    }


    /**
     * Create an intent of the given intent type
     *
     * @param displayName          The display name of the intent.
     * @param projectId            Project/Agent Id.
     * @param trainingPhrasesParts Training phrases.
     * @param messageTexts         Message texts for the agent's response when the intent is detected.
     * @return The created Intent.
     */
    public Intent createIntent(
            String displayName,
            String projectId,
            List<String> trainingPhrasesParts,
            List<String> messageTexts) throws Exception {

        // Set the project agent name using the projectID (my-project-id)
        ProjectAgentName parent = ProjectAgentName.of(projectId);

        // Build the trainingPhrases from the trainingPhrasesParts
        List<Intent.TrainingPhrase> trainingPhrases = new ArrayList<Intent.TrainingPhrase>();
        for (String trainingPhrase : trainingPhrasesParts) {
            trainingPhrases.add(
                    Intent.TrainingPhrase.newBuilder().addParts(
                            Intent.TrainingPhrase.Part.newBuilder().setText(trainingPhrase).build())
                            .build());
        }

        // Build the message texts for the agent's response
        Intent.Message message = Intent.Message.newBuilder()
                .setText(
                        Intent.Message.Text.newBuilder()
                                .addAllText(messageTexts).build()
                ).build();

        // Build the intent
        Intent intent = Intent.newBuilder()
                .setDisplayName(displayName)
                .addMessages(message)
                .addAllTrainingPhrases(trainingPhrases)
                .build();

        // Performs the create intent request
        Intent response = intentsClient.createIntent(parent, intent);
        System.out.format("Intent created: %s\n", response);

        return response;
    }

}