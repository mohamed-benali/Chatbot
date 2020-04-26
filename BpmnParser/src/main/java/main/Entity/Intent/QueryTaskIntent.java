package main.Entity.Intent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryTaskIntent extends myIntent {
    public QueryTaskIntent(String name, String subject, String task) throws IOException {
        super(name+"_query", subject, task);

        // TODO: Use the analysis of Freeling and SimpleNLG to build a better training phrase(use the verb, for example)
        this.addTrainingPhrase("Who " + task);
    }


    protected List<String> makeResponse() {
        // TODO: Use the analysis og Freeling and SimpleNLG to build a better response
        List<String> responses = new ArrayList<String>();
        String response = this.getSubject();
        responses.add(response);
        return responses;
    }
}
