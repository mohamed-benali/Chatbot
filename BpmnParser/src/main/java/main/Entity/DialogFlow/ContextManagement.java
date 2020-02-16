package main.Entity.DialogFlow;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.common.collect.Lists;
import com.google.protobuf.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

/**
 * DialogFlow API Context sample.
 */
public class ContextManagement {
    private ContextsClient contextsClient;


    public ContextManagement() throws IOException {
        String projectId = "greetingsbot-qtakwv";

        String jsonPath = "./greetingsbot-qtakwv-c046349de531.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
        //Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        ContextsSettings intentsSettings =
                ContextsSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
        this.contextsClient = ContextsClient.create(intentsSettings);
    }


    /**
     * Lists contexts
     *
     * @param sessionId Identifier of the DetectIntent session.
     * @param projectId Project/Agent Id.
     * @return List of Contexts found.
     */
    public List<Context> listContexts(String sessionId, String projectId) throws Exception {
        List<Context> contexts = Lists.newArrayList();
        // Instantiates a client
            // Set the session name using the sessionId (UUID) and projectId (my-project-id)
            SessionName session = SessionName.of(projectId, sessionId);

            // Performs the list contexts request
            for (Context context : contextsClient.listContexts(session).iterateAll()) {
                System.out.format("Context name: %s\n", context.getName());
                System.out.format("Lifespan Count: %d\n", context.getLifespanCount());
                System.out.format("Fields:\n");
                for (Entry<String, Value> entry : context.getParameters().getFieldsMap().entrySet()) {
                    if (entry.getValue().hasStructValue()) {
                        System.out.format("\t%s: %s\n", entry.getKey(), entry.getValue());
                    }
                }

                contexts.add(context);
            }

        return contexts;
    }
    // [END dialogflow_list_contexts]

    // [START dialogflow_create_context]

    /**
     * Create an entity type with the given display name
     *
     * @param contextId     The Id of the context.
     * @param sessionId     Identifier of the DetectIntent session.
     * @param lifespanCount The lifespan count of the context.
     * @param projectId     Project/Agent Id.
     * @return The new Context.
     */
    public Context createContext(
            String contextId,
            String sessionId,
            String projectId,
            int lifespanCount) throws Exception {
        // Instantiates a client

            // Set the session name using the sessionId (UUID) and projectID (my-project-id)
            SessionName session = SessionName.of(projectId, sessionId);

            // Create the context name with the projectId, sessionId, and contextId
            ContextName contextName = ContextName.newBuilder()
                    .setProject(projectId)
                    .setSession(sessionId)
                    .setContext(contextId)
                    .build();

            // Create the context with the context name and lifespan count
            Context context = Context.newBuilder()
                    .setName(contextName.toString()) // The unique identifier of the context
                    .setLifespanCount(lifespanCount) // Number of query requests before the context expires.
                    .build();

            // Performs the create context request
            Context response = contextsClient.createContext(session, context);
            System.out.format("Context created: %s\n", response);

            return response;

    }
    // [END dialogflow_create_context]

    // [START dialogflow_delete_context]

    /**
     * Delete entity type with the given entity type name
     *
     * @param contextId The Id of the context.
     * @param sessionId Identifier of the DetectIntent session.
     * @param projectId Project/Agent Id.
     */
    public void deleteContext(String contextId, String sessionId, String projectId)
            throws Exception {
        // Instantiates a client
            // Create the context name with the projectId, sessionId, and contextId
            ContextName contextName = ContextName.of(projectId, sessionId, contextId);
            // Performs the delete context request
            contextsClient.deleteContext(contextName);

    }
    // [END dialogflow_delete_context]
}