package main;

import main.Entity.Intent.Intents;
import main.Entity.Parser.Parser;


public class main {
    public static void println(String s) {
        System.out.println(s);
    }

    public static String diagramName = "diagram.bpmn";
    public static String diagramParallelGatewayName = "diagramParallelGateway.bpmn";

    public static void main(String[] args) {
        String packagePath = "./src/main/java/main";
        String dataPath = "/../Data/";
        String bpmnPath = packagePath + dataPath + diagramParallelGatewayName;

        Parser parser = new Parser(bpmnPath);
        parser.parse();
        Intents intents = parser.getIntents();

        intents.print();

        intents.translateIntoDialogFlow();

    }
}
