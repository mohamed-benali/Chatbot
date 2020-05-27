package main.Entity.Intent.Intent;

import main.Entity.Intent.Intent.CollaborationManager.CollaborationManager;
import main.Entity.Intent.Intent.CollaborationManager.CollaborationManagerImpl;
import main.Entity.Intent.Intents;
import main.Entity.Intent.Intent.TrainingPhrases.myTrainingPhrase;
import main.Entity.Intent.Intent.TrainingPhrases.myTrainingPhrases;
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
import java.util.Objects;

public class myIntent {
    protected IntentsClient intentsClient;

    public static void println(String s) {
        System.out.println(s);
    }


    protected String name; // Nom del Intent, IDENTIFICADOR
    protected String subject; // Who does it
    protected String task;    // What is done


    protected List<String> inputIntents; // Intents that go before this intent

    protected List<String> inputContexts;  // Contexts that go before this intent

    protected List<String> outputIntents; // Intents that go after this intent

    protected List<String> outputContexts; // Output context to be placed on DialogFlow

    protected myTrainingPhrases trainingPhrases;

    protected IntentManagment intentManagment;

    private SentenceBuilder sentenceBuilder;

    /**
     * Tells if the intent is a builded intent(e.g. builded by other intent in method buildExtraIntents)
     */
    protected boolean isABuildedIntent;

    /**
     * Manages the collaboration & messages between participants
     * Specifically, manages if the intent is marked as a collaboration source/target
     */
    protected CollaborationManager collaborationManager;


    //region REGION: Constructors
    private void basic_initialization() throws IOException {
        inputIntents = new ArrayList<>();
        inputContexts = new ArrayList<>();
        outputIntents = new ArrayList<>();
        outputContexts = new ArrayList<>();
        trainingPhrases = new myTrainingPhrases();
        intentManagment = new IntentManagment();
        setCollaborationManager(new CollaborationManagerImpl());

        //TODO: use factory
        setSentenceBuilder(new SentenceBuilderImpl());

        setAsBuildedIntent(false);
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


    public void addBasicInfo(String inputContextID, String outputContextID,
                             String outputIntentID, String trainingPhrase) {
        this.addInputContextID(inputContextID);
        this.addOutputContextID(outputContextID);
        this.addOutputIntentID(outputIntentID);
        this.addTrainingPhrase(trainingPhrase);
    }

    public void addBasicInfo(String inputIntentID, String inputContextID,
                             String outputContextID, String outputIntentID, String trainingPhrase) {
        this.addInputIntentID(inputIntentID);
        this.addInputContextID(inputContextID);
        this.addOutputContextID(outputContextID);
        this.addOutputIntentID(outputIntentID);
        this.addTrainingPhrase(trainingPhrase);
    }
    //endregion


    //region REGION: Override(equals, toString)  + copy
    @Override // TODO: ADD InputIntent
    public boolean equals(Object o) {
        if (o instanceof myIntent) {
            myIntent otherIntent = (myIntent) o;
            if (this.getId().equals(otherIntent.getId()) &&
                this.getName().equals(otherIntent.getName()) &&
                this.getSubject().equals(otherIntent.getSubject()) &&
                this.getTask().equals(otherIntent.getTask()) &&
                this.getInputIntents().equals(otherIntent.getInputIntents()) &&
                this.getInputContexts().equals(otherIntent.getInputContexts()) &&
                this.getOutputContexts().equals(otherIntent.getOutputContexts()) &&
                this.getOutputIntents().equals(otherIntent.getOutputIntents()) &&
                this.getTrainingPhrases().equals(otherIntent.getTrainingPhrases()) &&
                Objects.equals(this.getCollaborationManager(), otherIntent.getCollaborationManager()) &&
                Objects.equals(this.isABuildedIntent(), otherIntent.isABuildedIntent())
            )
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
        result += "Input Intents:" + "\n";
        result += inputIntents.toString() + "\n";
        result += "Input Contexts:" + "\n";
        result += inputContexts.toString() + "\n";
        result += "Output Contexts:" + "\n";
        result += outputContexts.toString() + "\n";
        result += "Output Intents:" + "\n";
        result += outputIntents.toString() + "\n";
        result += "Training Phrases:" + "\n";
        result += trainingPhrases.toString() + "\n";
        result += "Collaboration Manager:" + "\n";
        result += getCollaborationManager().toString() + "\n";
        result += "Is a Builded Intent:    " + this.isABuildedIntent() + "\n";
        result += "\n";

        return result;
    }

    public myIntent copy() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        // TODO: Patro Plantilla--> Cada subclase deicideix la instancia a crea
        myIntent clone = createBasicIntentInstance(this.getName(), this.getSubject(),this.getTask());

        clone.setName(this.getName());
        clone.setSubject(this.getSubject());
        clone.setTask(this.getTask());

        // TODO: Correct?
        clone.setInputIntents(new ArrayList<>(this.getInputIntents()));
        clone.setInputContexts(new ArrayList<>(this.getInputContexts()));
        clone.setOutputContexts(new ArrayList<>(this.getOutputContexts()));
        clone.setOutputIntents(new ArrayList<>(this.getOutputIntents()));

        clone.addTrainingPhrases(this.getTrainingPhrases().copy() );

        clone.setCollaborationManager(this.getCollaborationManager().copy());

        clone.setAsBuildedIntent(this.isABuildedIntent());

        return clone;
    }

    protected myIntent createBasicIntentInstance(String name, String subject, String task) throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        return new myIntent(name, subject, task);
    }

    // FIXME: Repeated code in IntentsTest.java
    protected String quotes(String s) {
        char quote = '"';
        if(s==null || s.equals("")) return null;
        else return quote + s + quote;
    }

    /**
     * Per fer més rapid la generacio dels tests
     * @return Retorna un string on hi ha el codi de la creacio de un intent amb la info basica.
     * Si hi ha elements amb més d'un element no els considera
     */ //FIXME: EndEvents may go wrong(output intent)
    public String createCode(){
        char quote = '"';

        String varName = "intent" + this.getName();
        String response = "";
        response += "myIntent " + varName + " = new myIntent(" + quotes(getName() ) + "," + quotes(getSubject()) +
                "," + quotes(getTask()) + ");" + "\n";

        String inputIntentID = "";
        for(int i = 0; i < getInputIntents().size(); ++i) {
            if(i > 0) break;
            else inputIntentID = getInputIntents().get(i); // i == 0
        }

        String inputContextID = "";
        for(int i = 0; i < getInputContexts().size(); ++i) {
            if(i > 0) break;
            else inputContextID = getInputContexts().get(i); // i == 0
        }

        String outputContextID = "";
        for(int i = 0; i < getOutputContexts().size(); ++i) {
            if(i > 0) break;
            else outputContextID = getOutputContexts().get(i); // i == 0
        }

        String outputIntentID = "";
        for(int i = 0; i < getOutputIntents().size(); ++i) {
            if(i > 0) break;
            else outputIntentID = getOutputIntents().get(i); // i == 0
        }

        String trainingPhrase = "";
        for(int i = 0; i < getTrainingPhrases().getTrainingPhrasesList().size(); ++i) {
            if(i > 0) {
                if(getTrainingPhrases().getTrainingPhrasesList().get(i) == null) trainingPhrase = null;
            }
            else trainingPhrase = getTrainingPhrases().getTrainingPhrasesList().get(i); // i == 0
        }

        response += varName + ".addBasicInfo(" + quotes(inputIntentID) +"," + quotes(inputContextID) + "," +"\n" +
                quotes(outputContextID) + "," +quotes(outputIntentID) + "," +
                quotes(trainingPhrase) + ");";


        return response;
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

    public CollaborationManager getCollaborationManager() { return collaborationManager; }

    public void setCollaborationManager(CollaborationManager collaborationManager) { this.collaborationManager = collaborationManager; }


    public boolean isABuildedIntent() { return isABuildedIntent; }
    public void setAsBuildedIntent(boolean ABuildedIntent) { isABuildedIntent = ABuildedIntent; }

    //endregion

    public boolean isAsCollaborationSource() { return getCollaborationManager().isAsCollaborationSource(); }
    public boolean isAsCollaborationTarget() { return getCollaborationManager().isAsCollaborationTarget(); }

    public void markAsCollaborationSource(String targetNodeID, String targetSubject) {
        getCollaborationManager().markAsCollaborationSource(targetNodeID, targetSubject);
    }

    public void markAsCollaborationTarget(String sourceNodeID,  String sourceSubject) {
        getCollaborationManager().markAsCollaborationTarget(sourceNodeID, sourceSubject);
    }


    //region REGION: INPUT

    //region REGION: INPUT INTENTS
    public List<String> getInputIntents() { return inputIntents; }

    public void setInputIntents(List<String> inputIntents) { this.inputIntents = inputIntents; }

    public void addInputIntentID(String intent) {
        this.inputIntents.add(intent);
    }

    public void addInputIntentIDs(List<String> intents) {
        this.inputIntents.addAll(intents);
    }

    public void removeInputIntentID(String intentID) {
        this.inputIntents.remove(intentID);
    }

    public void clearInputIntents() {
        inputIntents.clear();
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


    //region REGION: Translate Into DialogFlow
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

        String sentence = this.getTask();
        String subject = this.getSubject();
        String response = sentenceBuilder.buildSentence(sentence, subject);

        if(this.isAsCollaborationSource()) { //fixme
            CollaborationManager collaborationManager = this.getCollaborationManager();
            String targetNodeID = collaborationManager.getTargetNodeID();
            String targetSubject= collaborationManager.getTargetSubject();

            response += "\n\n";
            response += "Do you want to follow the " + targetSubject + " process? YES or NO";
        }

        //String response = this.getSubject() + " " + this.getTask();
        responses.add(response);
        return responses;
    }


    /**
     * Builds the extra intents such as QueryTaskIntent
     * @return Returns the extra intents that can be create from this intent
     */
    public Intents buildExtraIntents() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        Intents intents = new Intents();

        if(this.isAsCollaborationTarget()) {
            CollaborationManager collaborationManager = this.getCollaborationManager();
            String sourceNodeID = collaborationManager.getSourceNodeID();

            myIntent newIntent = this.copy();

            String newIntentID = newIntent.getName() + "_" + sourceNodeID;
            newIntent.setName(newIntentID);

            newIntent.clearInputContexts();
            newIntent.addInputContextID(sourceNodeID);

            newIntent.clearTrainingPhrases();
            newIntent.addTrainingPhrase("yes");

            newIntent.clearInputIntents();
            newIntent.addInputIntentID(sourceNodeID); // This may change o not. Not sure?

            myIntent sourceIntent = Intents.getInstance().getIntentByID(sourceNodeID); //FIXME: This may leave some buildedIntent without the correct option
                                                                                        //FIXME: Have a attribute to indicate the original intent id(if its a builded one)
            sourceIntent.addOutputIntentID(newIntentID);

            newIntent.setAsBuildedIntent(true);
            intents.add(newIntent);
        }

        if(this.isAsCollaborationSource()) { // Update following nodes to accept to match a "no" training phrase
            CollaborationManager collaborationManager = this.getCollaborationManager();
            String targetNodeID = collaborationManager.getTargetNodeID();
            String targetSubject= collaborationManager.getTargetSubject();

            Intents singletonIntents = Intents.getInstance();
            for(String outputIntentID : this.getOutputIntents()) { // Should only be one?
                myIntent outputIntent = singletonIntents.getIntentByID(outputIntentID);
                if(outputIntent == null) continue;
                else if(outputIntent.isABuildedIntent()) continue;

                myIntent newIntent = outputIntent.copy();

                String newIntentID = newIntent.getName() + "_" + this.getName();
                newIntent.setName(newIntentID);

                newIntent.clearTrainingPhrases();
                newIntent.addTrainingPhrase("no");

                newIntent.setAsBuildedIntent(true);
                intents.add(newIntent);
            }

        }

        return intents;
    }
    //endregion


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
                        - If there is a null trainingPhrase, How i will tell which type of trainingPhrase is? Next by default
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






