package main.Entity.Intent;

import java.io.IOException;

public class StartIntent extends myIntent {
    public static String BEGIN_CONTEXT = "BEGIN_CONTEXT"; // TODO: Replicated on "BeginIntent" class
                                                          // Should make some type of global access(Singleton)

    public StartIntent(String name, String subject, String tasca) throws IOException {
        super(name, subject, tasca);
        this.addInputIntentID(BEGIN_CONTEXT);
        this.addTrainingPhrase(subject);
    }
}
