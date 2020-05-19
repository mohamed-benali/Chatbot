package main.Entity.Parser.BpmnAlgorithm;

import main.Entity.Intent.BeginIntent_Special;
import main.Entity.Intent.myIntent;
import main.Entity.Intent.Intents;
import main.Entity.Parser.ParserFlowNodes.Helpers.ParseFlowNodesHelper.ParseFlowNodesHelper;
import main.Entity.Parser.ParserFlowNodes.Helpers.ParseFlowNodesHelper.ParseFlowNodesHelperImpl;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodesDTO.ParserFlowNodesDTO;
import main.Entity.Parser.ParserFlowNodes.Helpers.CamundaHelper.CamundaHelper;
import main.Entity.Parser.ParserFlowNodes.Helpers.CamundaHelper.CamundaHelperImpl;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodes;
import main.Entity.Parser.ParserFlowNodes.ParserFlowNodesImpl;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.io.IOException;
import java.util.*;

public class BpmnAlgorithmImpl implements BpmnAlgorithm{
    public static void println(String s) { System.out.println(s); }

    public Intents intents;
    public BpmnModelInstance modelInstance; //TODO: Create a Wrapper class of modelInstance, so can create custom methods

    public ParserFlowNodes parserFlowNodes;
    public ParseFlowNodesHelper parseFlowNodesHelper;
    public CamundaHelper camundaHelper;

    private static Set<String> visitedFlowNodesInstance;

    public static Set<String> getVisitedFlowNodesInstance() {
        if(visitedFlowNodesInstance == null) visitedFlowNodesInstance = new TreeSet<>();
        return visitedFlowNodesInstance;
    }

    public static void resetVisitedFlowNodesInstance() {
        visitedFlowNodesInstance = null;
    }

    /*
     * CONSTRUCTORS
     */
    public BpmnAlgorithmImpl(BpmnModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        this.intents = new Intents();
        this.parserFlowNodes = new ParserFlowNodesImpl(modelInstance);
        this.parseFlowNodesHelper = new ParseFlowNodesHelperImpl(modelInstance);
        this.camundaHelper = parseFlowNodesHelper.getCamundaHelper();

        BpmnAlgorithmImpl.resetVisitedFlowNodesInstance(); // Reset it
    }




    /*
     * Parser
     */

    public Intents parse() throws IOException {
        // Participants
        List<String> participantNames = new ArrayList<String>();
        Collection<Participant> ParticipantInstances = modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : ParticipantInstances) {
            this.intents.add(this.parseParticipant(participant));

            participantNames.add(participant.getName());
        }
        //TODO: Add contexts    beginIntent.addDeleteContexts(???)

        // Begin intent
        BeginIntent_Special beginIntentSpecial = new BeginIntent_Special();
        beginIntentSpecial.setParticipantNames(participantNames);
        this.intents.add(beginIntentSpecial);

        // TODO: Crea i Actualitzar els input Intent abans de continuar

        // TODO: Inserir per tots aquells que tenen al intent com a outputIntent(aprofitar que si jo soc el outputIntent,
        // TODO: tu ets el meu inputIntent

        // Collaboration
        Collection<MessageFlow> messageFlowIntsances = modelInstance.getModelElementsByType(MessageFlow.class);
        for(MessageFlow messageFlow : messageFlowIntsances) {
            myIntent sourceIntent = this.parserFlowNodes.parseSourceMessageFlow(messageFlow);
            myIntent targetIntent = this.parserFlowNodes.parseTargetMessageFlow(messageFlow);

            this.intents.insertAfterIntent (sourceIntent, messageFlow.getSource().getId());
            this.intents.insertBeforeIntent(targetIntent, messageFlow.getTarget().getId());
        }

        return this.intents;
    }



    public Intents parseParticipant(Participant participant) throws IOException {
        Process process = participant.getProcess();
        Intents intents = this.parseProcess(participant, process);
        return intents;
    }

    public Intents parseProcess(Participant participant, Process process) throws IOException {
        Intents intents = new Intents();

        Collection<StartEvent> startEvents = process.getChildElementsByType(StartEvent.class);
        for(StartEvent startEvent : startEvents) { //  Just one?
            intents.add(this.parseFlowNode(participant, process, startEvent) ); // Each node parses itself and parses outgoing nodes
        }

        return intents;
    }



    /*
     * Each node parses itself, then, calls recursively the function to parse outgoing nodes
     */
    public Intents parseFlowNode(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        Set<String> visitedFlowNodes = BpmnAlgorithmImpl.getVisitedFlowNodesInstance();
        String flowNodeId = node.getId();
        if (visitedFlowNodes.contains(flowNodeId)) return intents; // Stop parsing, return empty intents
        else {
            visitedFlowNodes.add(flowNodeId);

            ParserFlowNodesDTO parserFlowNodesDTO = new ParserFlowNodesDTO(participant, process, node);
            this.parserFlowNodes.parseFlowNode(parserFlowNodesDTO); // May actualize the node reference(e.g. in parallel gateway)

            node = parserFlowNodesDTO.getFlowNode();
            intents.add(parserFlowNodesDTO.getIntents());

            // Recursively parse the outgoing flowNodes.
            Collection<FlowNode> flowNodes = this.camundaHelper.getRelevantFlowingFlowNodes(node); // Get following flow nodes(task, gateway, etc) of a FlowNode.
            for (FlowNode flowNode : flowNodes) {
                intents.add(this.parseFlowNode(participant, process, flowNode));
            }

            return intents;
        }
    }


}