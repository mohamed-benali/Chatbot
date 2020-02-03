package main;

import main.Entity.Intent.Intents;
import main.Entity.Parser.Parser;


public class main {
    public static void println(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        String packagePath = "./src/main/java/main";
        String bpmnPath = packagePath + "/../Data/diagram.bpmn";

        Parser parser = new Parser(bpmnPath);
        parser.parse();
        Intents intents = parser.getIntents();
        intents.translateIntoDialogFlow();

        intents.print();
    }
}
