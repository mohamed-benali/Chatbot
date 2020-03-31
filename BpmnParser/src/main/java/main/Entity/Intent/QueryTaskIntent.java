package main.Entity.Intent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryTaskIntent extends myIntent {
    public QueryTaskIntent(String name, String subject, String task) throws IOException {
        super(name+"_query", subject, task);

        this.addTrainingPhrase("Who " + task);
    }


    protected List<String> makeResponse() {
        List<String> responses = new ArrayList<String>();
        String response = this.getSubject();
        responses.add(response);
        return responses;
    }
}
