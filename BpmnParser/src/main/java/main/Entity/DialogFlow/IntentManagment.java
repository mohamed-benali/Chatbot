package main.Entity.DialogFlow;

// Imports the Google Cloud client library

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.cloud.dialogflow.v2.Intent.Message;
import com.google.cloud.dialogflow.v2.Intent.Message.Text;
import com.google.cloud.dialogflow.v2.Intent.TrainingPhrase;
import com.google.cloud.dialogflow.v2.Intent.TrainingPhrase.Part;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DialogFlow API Intent sample.
 */
public class IntentManagment {
    private IntentsClient intentsClient;


    public IntentManagment() throws IOException {
        String projectId = "greetingsbot-qtakwv";

        String jsonPath = "./greetingsbot-qtakwv-c046349de531.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
        //Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        IntentsSettings intentsSettings =
                IntentsSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
        this.intentsClient = IntentsClient.create(intentsSettings);
    }

    /**
     * List intents
     *
     * @param projectId Project/Agent Id.
     * @return Intents found.
     */
    public List<Intent> listIntents(String projectId) throws Exception {
        List<Intent> intents = Lists.newArrayList();
        // Instantiates a client
            // Set the project agent name using the projectID (my-project-id)
            ProjectAgentName parent = ProjectAgentName.of(projectId);

            // Performs the list intents request
            for (Intent intent : intentsClient.listIntents(parent).iterateAll()) {
                System.out.println("====================");
                System.out.format("Intent name: '%s'\n", intent.getName());
                System.out.format("Intent display name: '%s'\n", intent.getDisplayName());
                System.out.format("Action: '%s'\n", intent.getAction());
                System.out.format("Root followup intent: '%s'\n", intent.getRootFollowupIntentName());
                System.out.format("Parent followup intent: '%s'\n", intent.getParentFollowupIntentName());

                System.out.format("Input contexts:\n");
                for (String inputContextName : intent.getInputContextNamesList()) {
                    System.out.format("\tName: %s\n", inputContextName);
                }
                System.out.format("Output contexts:\n");
                for (Context outputContext : intent.getOutputContextsList()) {
                    System.out.format("\tName: %s\n", outputContext.getName());
                }

                intents.add(intent);
            }
        return intents;
    }
    // [END dialogflow_list_intents]

    // [START dialogflow_create_intent]

    /**
     * Create an intent of the given intent type
     *
     * @param displayName          The display name of the intent.
     * @param projectId            Project/Agent Id.
     * @param trainingPhrasesParts Training phrases.
     * @param messageTexts         Message texts for the agent's response when the intent is detected.
     * @param outputContextNames
     * @return The created Intent.
     */
    public Intent createIntent(
            String displayName,
            String projectId,
            List<String> trainingPhrasesParts,
            List<String> messageTexts,
            List<String> inputContextsNames, List<String> outputContextNames) throws Exception {
        // Instantiates a client
            // Set the project agent name using the projectID (my-project-id)
            ProjectAgentName parent = ProjectAgentName.of(projectId);

            // Build the trainingPhrases from the trainingPhrasesParts
            List<TrainingPhrase> trainingPhrases = new ArrayList<TrainingPhrase>();
            for (String trainingPhrase : trainingPhrasesParts) {
                trainingPhrases.add(
                        TrainingPhrase.newBuilder().addParts(
                                Part.newBuilder().setText(trainingPhrase).build())
                                .build());
            }

            // Build the message texts for the agent's response
            Message message = Message.newBuilder()
                    .setText(
                            Text.newBuilder()
                                    .addAllText(messageTexts).build()
                    ).build();


            // Input contexts
            List<ContextName> inputContextNamesList = new ArrayList<ContextName>();
            List<String> inputContextNamesStringList = new ArrayList<String>();

            for(String inputContextName : inputContextsNames) {
                ContextName contextName = ContextName.newBuilder()
                        .setProject(projectId)
                        .setSession("-")
                        .setContext(inputContextName)
                        .build();

                inputContextNamesList.add(contextName);
                inputContextNamesStringList.add(contextName.toString());
            }

            // Output context, lifespan 100
            List<Context> outputContexts = new ArrayList<Context>();
            for(String outputContext : outputContextNames) {
                ContextName contextName = ContextName.newBuilder()
                        .setProject(projectId)
                        .setSession("-")
                        .setContext(outputContext)
                        .build();


                // Create the context with the context name and lifespan count
                Context context = Context.newBuilder()
                        .setName(contextName.toString()) // The unique identifier of the context
                        .setLifespanCount(100) // Number of query requests before the context expires. // TODO: Hardcoded for now
                        .build();

                outputContexts.add(context);
            }


            // Output context to be deleted lifespan 0   (for now use inputContextsNames)
            for(String inputContextName : inputContextsNames) {
                ContextName contextName = ContextName.newBuilder()
                        .setProject(projectId)
                        .setSession("-")
                        .setContext(inputContextName)
                        .build();


                // Create the context with the context name and lifespan count
                Context context = Context.newBuilder()
                        .setName(contextName.toString()) // The unique identifier of the context
                        .setLifespanCount(0) // Number of query requests before the context expires. // TODO: Hardcoded for now
                        .build();

                outputContexts.add(context);
            }



        // Build the intent
            Intent intent = Intent.newBuilder()
                    .setDisplayName(displayName)
                    .addMessages(message)
                    .addAllTrainingPhrases(trainingPhrases)
                    .addAllInputContextNames(inputContextNamesStringList)
                    .addAllOutputContexts(outputContexts)
                    .build();


            // Performs the create intent request
            Intent response = intentsClient.createIntent(parent, intent);
            System.out.format("Intent created: %s\n", response);

            return response;
    }
    // [END dialogflow_create_intent]

    // [START dialogflow_delete_intent]

    /**
     * Delete intent with the given intent type and intent value
     *
     * @param intentId  The id of the intent.
     * @param projectId Project/Agent Id.
     */
    public void deleteIntent(String intentId, String projectId) throws Exception {
        // Instantiates a client
            IntentName name = IntentName.of(projectId, intentId);
            // Performs the delete intent request
            intentsClient.deleteIntent(name);
    }
    // [END dialogflow_delete_intent]

    /**
     * Helper method for testing to get intentIds from displayName.
     */
    public List<String> getIntentIds(String displayName, String projectId) throws Exception {
        List<String> intentIds = new ArrayList<String>();

        // Instantiates a client
            ProjectAgentName parent = ProjectAgentName.of(projectId);
            for (Intent intent : intentsClient.listIntents(parent).iterateAll()) {
                if (intent.getDisplayName().equals(displayName)) {
                    String[] splitName = intent.getName().split("/");
                    intentIds.add(splitName[splitName.length - 1]);
                }
            }
        return intentIds;
    }
}
