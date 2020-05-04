package main;

import main.Entity.Intent.Intents;
import main.Entity.Parser.Parser;


public class main {
    public static void println(String s) {
        System.out.println(s);
    }

    public static String basicDiagramName = "diagram.bpmn";
    public static String diagramParallelGatewayName = "diagramParallelGateway.bpmn";
    public static String diagramExclusiveGateway_If_without_elseName = "exclusiveGateway_If_without_else.bpmn";
    public static String diagramLoopName = "diagramLoop.bpmn";
    public static String diagramLoopWithoutCollaborationsName = "diagramLoopWithoutCollaborations.bpmn";

    public static String diagram = diagramParallelGatewayName;

    public static void main(String[] args) throws Exception {
        String packagePath = "./src/main/java/main";
        String dataPath = "/../Data/";
        String bpmnPath = packagePath + dataPath + diagram;

        Parser parser = new Parser(bpmnPath);
        parser.parse();
        Intents intents = parser.getIntents();

        //intents.print();

        //intents.translateIntoDialogFlow();

    }
}
