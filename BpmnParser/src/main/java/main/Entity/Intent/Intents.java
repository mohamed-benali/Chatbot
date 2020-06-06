package main.Entity.Intent;

import main.Entity.Intent.Intent.myIntent;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.Exceptions.SpinnerChief_SentenceParaphraserException;
import main.SentenceGeneration.SentenceEntities.Sentences.ParaphrasedSentences;
import main.SentenceGeneration.SentenceParaphraser.SentenceParaphraser;
import main.SentenceGeneration.SentenceParaphraser.SpinnerChief_SetenceParaphraserImpl.SpinnerChief_SentenceParaphraserImpl;

import com.google.api.gax.rpc.ResourceExhaustedException;

import java.io.IOException;
import java.util.*;

public class Intents {
    public static void println(String s) { System.out.println(s); }

    private Map<String, myIntent> intents; // String: name(identificador)
    private SentenceParaphraser sentenceParaphraser;


    public static Intents instance;

    public static Intents getInstance(){
        if(instance==null) instance = new Intents();
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    /*
     * CONSTRUCTORS
     */
    public Intents() {
        intents = new TreeMap<>();
        //TODO: use factory
        sentenceParaphraser = new SpinnerChief_SentenceParaphraserImpl();
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


    /**
     * Per fer més rapid la generacio dels tests
     * @return Retorna un string per on hi ha el codi de la creacio de tots intent amb la info basica.
     * Si hi ha elements amb més d'un element no els considera
     */
    public String createCode(){
        StringBuilder response = new StringBuilder();
        for(var entry : getIntents().entrySet()) {
            response.append(entry.getValue().createCode());
            response.append("\n");
            response.append("\n");

        }
        return response.toString();
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
    public void add(Intents intents) { if(intents!=null) this.add(intents.getIntents()); }

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



    //region REGION: Build Intents(ExtraIntents, TrainingPhrases, etc)
    /**
     * Builds the intents
     * <br><br>
     * Specifically builds(prepares) all the information, such as the TrainingPhrases, that will be uploaded on DialogFlow.
     *<br>
     * It also prepares the the extraIntents, such as QueryTaskIntent
     * <br><br>
     * The order is: build extra intents, build Training Phrases
     */
    public void build() throws InterruptedException, SpinnerChief_SentenceParaphraserException, IOException, SentenceAnalyzerException, NoFreelingKeyException {
        /* TODO:
             maybe create here(or in Parser.parse() ) the QueryTasks( createAdditionalTask or createQueryTasks)
             Responses
             TrainingPhrases
             Some other thing
        */

        /* TODO: En el parse: Genera els graf de intents
            Aqui actualitzar perque DialogFlow ho pugui entendre, per tant, el build es un "Build DialogFlow"
            Fer que sigui una interficie (chatbotBuilder ????) i segons la Impl es fa build de dialogflow.
        */
        this.buildExtraIntents();
        this.buildTrainingPhrases();
    }

    //region REGION: Build Extra Intents
    /**
     * Builds the extra intents such as QueryTaskIntent
     *
     */
    private void buildExtraIntents() throws IOException, InterruptedException, SentenceAnalyzerException, NoFreelingKeyException {
        Intents tmpIntents = new Intents();
        for(Map.Entry<String, myIntent> entry : getIntents().entrySet()) {
            myIntent intent = entry.getValue();
            Intents extraIntents = intent.buildExtraIntents();
            tmpIntents.add(extraIntents);
        }
        this.add(tmpIntents);
    }
    //endregion


    //region REGION: Build Training Phrases
    /**
     * For each intent, builds it's trainingPhrases, generates similar sentences, and adds them as training phrases.
     */
    private void buildTrainingPhrases() throws InterruptedException, SpinnerChief_SentenceParaphraserException, IOException, SentenceAnalyzerException, NoFreelingKeyException {
        List<String> trainingPhrasesToParaphrase = this.getTrainingPhrasesToParaphrase();

        ParaphrasedSentences paraphrasedSentences = sentenceParaphraser.paraphraseSentences(trainingPhrasesToParaphrase);
        this.updateIntentsTrainingPhrases(paraphrasedSentences);
    }

    /**
     * Gets the training phrases to paraphrase
     * @return Returns the training phrases to paraphrase
     */
    private List<String> getTrainingPhrasesToParaphrase() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        Set<String> resultSet = new TreeSet<>(); // Uses set to reduce the amount of words
        for(Map.Entry<String, myIntent> entry : intents.entrySet()) {
            myIntent intent = entry.getValue();
            resultSet.addAll(intent.getTrainingPhrasesToParaphrase() );
        }
        return new ArrayList<>(resultSet);
    }

    /**
     * Updates the intent's trainingPhrases with the parameter {@code paraphrasedSentences}
     * <br>
     * Concretely, adds the similar sentences to {@code intent.trainingPhrases} for each existing sentence in attribute {@code intent.trainingPhrases} and parameter {@code paraphrasedSentences} at the same time
     * @param paraphrasedSentences Paraphrased Sentences. Each sentence has associated similar sentences.
     */
    private void updateIntentsTrainingPhrases(ParaphrasedSentences paraphrasedSentences) {
        for(Map.Entry<String, myIntent> entry : intents.entrySet()) {
            myIntent intent = entry.getValue();
            intent.updateTrainingPhrases(paraphrasedSentences);
        }
    }
    //endregion

    //endregion



    /*
     * Translate into Dialogflow
     */
    public void translateIntoDialogFlow() throws Exception {
        int size = intents.size();
        int i = 0;
        for(Map.Entry<String, myIntent> entry : intents.entrySet()) {
            myIntent intent = entry.getValue();
            if((i+1)%51==0)  {
                System.out.println("\n\nWaiting(61 seconds) due to exceding the dialogflow minute limit(60 intents/min): "+
                        size+" intents(total)  "+ i+" intents now\n\n");
                Thread.sleep(1000*15); // 15 seconds
                System.out.println("15 s");
                Thread.sleep(1000*15); // 15 seconds
                System.out.println("30 s");
                Thread.sleep(1000*15); // 15 seconds
                System.out.println("45 s");
                Thread.sleep(1000*16); // 16 seconds

            }
            try {
                intent.translateIntoDialogFlow();
            } catch (ResourceExhaustedException e) {
                println(e.getMessage());
                System.out.println("\n\nWaiting(61 seconds) due to exceding the dialogflow minute limit(60 intents/min): "+
                        size+" intents(total)  "+ i+" intents now\n\n");
                Thread.sleep(1000*15); // 15 seconds
                System.out.println("15 s");
                Thread.sleep(1000*15); // 15 seconds
                System.out.println("30 s");
                Thread.sleep(1000*15); // 15 seconds
                System.out.println("45 s");
                Thread.sleep(1000*16); // 16 seconds
                intent.translateIntoDialogFlow();
            } /*catch (Exception e) {
                println(e.getMessage());
            }*/

            //intent.translateIntoDialogFlow(); // For debugging
            ++i;
        }
    }






}
