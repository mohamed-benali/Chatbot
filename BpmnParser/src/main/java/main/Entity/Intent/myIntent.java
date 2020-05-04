package main.Entity.Intent;

import main.Entity.Intent.TrainingPhrases.myTrainingPhrase;
import main.Entity.Intent.TrainingPhrases.myTrainingPhrases;
import main.Enums.DefaultTrainingPhraseType;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceBuilder.SentenceBuilder;
import main.SentenceGeneration.SentenceBuilder.SentenceBuilderImpl;
import com.google.cloud.dialogflow.v2.*;
import main.Entity.DialogFlow.IntentManagment;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceEntities.Sentences.Sentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    protected myTrainingPhrases trainingPhrases;

    protected IntentManagment intentManagment;

    private SentenceBuilder sentenceBuilder;

    //region REGION: Constructors
    private void basic_initialization() throws IOException {
        inputContexts = new ArrayList<>();
        outputIntents = new ArrayList<>();
        outputContexts = new ArrayList<>();
        trainingPhrases = new myTrainingPhrases();
        intentManagment = new IntentManagment();
        setSentenceBuilder(new SentenceBuilderImpl());
    }

    public myIntent() throws IOException {
        basic_initialization();
    }

    public myIntent(String name) throws IOException {
        this.name = name;
        basic_initialization();
    }

    public myIntent(String name, String subject, String task) throws IOException {
        this.name = name;
        this.subject = subject;
        this.task = task;
        basic_initialization();
    }
    //endregion


    public void addBasicInfo(String inputContextID, String outputContextID,
                             String outputIntentID, String trainingPhrase) {
        this.addInputContextID(inputContextID);
        this.addOutputContextID(outputContextID);
        this.addOutputIntentID(outputIntentID);
        this.addTrainingPhrase(trainingPhrase);
    }


    //region REGION: Override(equals, toString)
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
    //endregion


    //region REGION: Getters and setters of (Name, Task, Subject)
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
    //endregion

    //region REGION: INPUT CONTEXTS
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
    //endregion

    //region REGION: OUTPUT
    /*
     * OUTPUT
     */

    //region REGION: OUTPUT INTENTS
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
    //endregion

    //region REGION: OUTPUT CONTEXT
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
    //endregion
    //endregion


    //region REGION: TRAINING PHRASES
    /*
     * TRAINING PHRASES
     */
    public myTrainingPhrases getTrainingPhrases() {
        return this.trainingPhrases;
    }

    public void setTrainingPhrases(myTrainingPhrases trainingPhrases) {
        this.trainingPhrases = trainingPhrases;
    }

    public void addTrainingPhrase(String trainingPhrase) {
        this.trainingPhrases.addTrainingPhrase(trainingPhrase);
    }
    public void addTrainingPhrases(myTrainingPhrase trainingPhrase) { this.trainingPhrases.addTrainingPhrase(trainingPhrase); }

    public void addTrainingPhrases(List<String> trainingPhrases) { this.trainingPhrases.addAllTrainingPhrases(trainingPhrases); }
    public void addTrainingPhrases(myTrainingPhrases trainingPhrases) { this.trainingPhrases.addAllTrainingPhrases(trainingPhrases); }


    public void clearTrainingPhrases() { trainingPhrases.clear(); }
    //endregion

    //region REGION: Getters & Setters of SentenceBuilder
    public SentenceBuilder getSentenceBuilder() { return sentenceBuilder; }
    public void setSentenceBuilder(SentenceBuilder sentenceBuilder) { this.sentenceBuilder = sentenceBuilder; }
    //endregion

    //region REGION: Prints
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
        println(trainingPhrases.toString());

        println("");
        println("");
    }

    public void printIDs(List<String> list) {
        for (String id : list) {
            println(id);
        }
    }
    //endregion

    public void translateIntoDialogFlow() throws Exception {
        // DialogFlow info
        String title = this.getName();
        println("");

        // Contexts
        //this.getInputIntents();
        //this.getOutputIntents();

        List<String> trainingPhrases = this.getBuildedTrainingPhrases();
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


    protected List<String> makeResponse() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        List<String> responses = new ArrayList<String>();
        SentenceBuilder sentenceBuilder = new SentenceBuilderImpl();

        String sentence = this.getTask();
        String subject = this.getSubject();
        String response = sentenceBuilder.buildSentence(sentence, subject);


        //String response = this.getSubject() + " " + this.getTask();
        responses.add(response);
        return responses;
    }


    /**
     * Builds the extra intents such as QueryTaskIntent
     * @return Returns the extra intents that can be create from this intent
     */
    public Intents buildExtraIntents() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        return new Intents();
    }


    //region REGION: Training Phrases
    /**
     * Function that is called to build the training Phrases of the intent that will be uploaded into DialogFlow
     * @return Returns the training Phrases of the intent that will be uploaded into DialogFlow
     */
    protected List<String> getBuildedTrainingPhrases() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        this.setDefaultNullTrainingPhrase(); // TODO: Patro plantilla pels que puguin? Y els que no pues override de tot
        return this.getTrainingPhrases().getBuildedTrainingPhrases();
    }

    /**
     * Sets the type of the default training phrase. Each intent subclass will use it to decide how the null trainingPhrases
     * should be approached
     */
    protected void setDefaultNullTrainingPhrase() {
        //this.getTrainingPhrases().setHasNullTrainingPhrase(true);This should come from the parse or from some specific subclassIntent
        this.getTrainingPhrases().setDefaultTrainingPhraseType(DefaultTrainingPhraseType.NEXT_TYPE); // Default in case null is true
    }

    /**
     * Gets the training phrases to paraphrase
     * @return Returns the training phrases to paraphrase
     */
    public Collection<? extends String> getTrainingPhrasesToParaphrase() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        Sentences trainingPhrases = this.buildTrainingPhrasesToParaphrase();
        return trainingPhrases.getSentencesList();
    }


    /**
     * Builds the training phrases of this Intent
     * <br>
     * By builded we mean correct sentences. NOT the paraphrased sentences
     * <br>
     * The null training Phrases are not included because they are create manually.
     * @return Returns the builded sentences. By default returns the sentences as if they are a TaskIntent
     */
    protected Sentences buildTrainingPhrasesToParaphrase() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
    // TODO: Should this.getTrainingPhrases().getTrainingPhrasesList() include the sentences corresponding to the null type?
    // TODO: Should the null sentences be added outside?

    /* TODO******:
        Should they be paraphrased? Probably NOT, because they are generated manually, so in update, null sentences are not checked
             How i will tell which type of trainingPhrase is? Next by default?
             - Create function getAllTrainingPhrasesList or getTrainingPhrasesListWithNullIncluded ?
             - ****** When i get a getBuildedTrainingPhrases(), i should add the manual sentences to the paraphrases ones


             TODO: Dont include null in this function
                    Dont consider them when updating
                    When getBuildedTrainingPhrases(), add manually the *null sentences* to the paraphrased ones
                        - If there is a null trainigPhrase, How i will tell which type of trainingPhrase is? Next by default
                            - I could always set the default null type externally from the intent

     */
        Sentences sentences = new Sentences();
        sentences.addSentences(this.getTrainingPhrases().getTrainingPhrasesList());
        return sentences;
    }


    /**
     * Updates the intent's trainingPhrases with the parameter {@code paraphrasedSentences}
     * <br>
     * Concretely, adds the similar sentences to {@code this.trainingPhrases} for each existing sentence in attribute {@code this.trainingPhrases} and parameter {@code paraphrasedSentences} at the same time
     * <br>
     * The null training Phrases are not considered because they are created manually and therefore they are not paraphrased
     * @param paraphrasedSentences Paraphrased Sentences. Each sentence has associated similar sentences.
     */
    public void updateTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
        this.getTrainingPhrases().updateTrainingPhrases(paraphrasedSentences);
    }


    //endregion
}






