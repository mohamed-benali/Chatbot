package main.Entity.Intent;

import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.SentenceGeneration.SentenceBuilder.SentenceBuilder;
import main.SentenceGeneration.SentenceBuilder.SentenceBuilderImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExclusiveGatewayIntent extends myIntent {
    public ExclusiveGatewayIntent() throws IOException {
    }

    public ExclusiveGatewayIntent(String name) throws IOException {
        super(name);
    }

    public ExclusiveGatewayIntent(String name, String subject, String task) throws IOException {
        super(name, subject, task);
    }

    protected List<String> makeResponse() throws InterruptedException, SentenceAnalyzerException, NoFreelingKeyException, IOException {
        List<String> responses = new ArrayList<String>();

        String response = this.getTask();

        responses.add(response);
        return responses;
    }
}
