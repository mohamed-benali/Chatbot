package main.Entity.Parser.BpmnAlgorithm;

import main.Entity.Intent.Intents;

import java.io.IOException;

public interface BpmnAlgorithm {
    Intents parse() throws IOException;
}
