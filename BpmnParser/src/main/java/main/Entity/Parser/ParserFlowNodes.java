package main.Entity.Parser;

import main.Entity.Intent.CollaborationIntent;
import main.Entity.Intent.Intent;
import main.Entity.Intent.Intents;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.ArrayList;
import java.util.Collection;

public class ParserFlowNodes {
    public static void println(String s) { System.out.println(s); }

    public BpmnModelInstance modelInstance;

    public ParserFlowNodes(BpmnModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }


    /*
     * HELPERS(Camunda BPMN)
     */

    // Get following flow nodes(task, gateway, etc) of a FlowNode.
    public Collection<FlowNode> getAllFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            followingFlowNodes.add(sequenceFlow.getTarget());
            // TODO: Consider if the next flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the following nodes are the following of the gateway,
        }
        return followingFlowNodes;
    }

    // Get previous flow nodes(task, gateway, etc) of a FlowNode.
    public Collection<FlowNode> getAllIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            followingFlowNodes.add(sequenceFlow.getSource());
            // TODO: Consider if the incoming flowNode is a closing exlusiveGateway(with no text) or similar.
            // TODO: If its the case, then the incoming nodes are the incoming of that gateway,
        }
        return followingFlowNodes;
    }

    // Get following flow nodes(task, gateway, etc) of a FlowNode.
    // Ignores a flownode if it hasnt a Name.
    public Collection<FlowNode> getRelevantFlowingFlowNodes(FlowNode node) {
        Collection<FlowNode> followingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            FlowNode followingNode = sequenceFlow.getTarget();
            if(followingNode.getName() != null) followingFlowNodes.add(followingNode);
            else followingFlowNodes.addAll(this.getRelevantFlowingFlowNodes(followingNode));
        }
        return followingFlowNodes;
    }

    // Get previous flow nodes(task, gateway, etc) of a FlowNode.
    // Ignores a flownode if it hasnt a Name.
    public Collection<FlowNode> getRelevantIncomingFlowNodes(FlowNode node) {
        Collection<FlowNode> incomingFlowNodes = new ArrayList<FlowNode>();
        for (SequenceFlow sequenceFlow : node.getIncoming()) {
            FlowNode incomingNode = sequenceFlow.getSource();
            if(incomingNode.getName() != null) incomingFlowNodes.add(incomingNode);
            else incomingFlowNodes.addAll(this.getRelevantIncomingFlowNodes(incomingNode));
        }
        return incomingFlowNodes;
    }

    // Gives the source subject (ex:"Departament", "Empleat") of the message flow
    public String getSourceSubject(MessageFlow messageFlow) {
        String sourceSubject = null;

        InteractionNode interactionNode = messageFlow.getSource();
        String nodeID = interactionNode.getId();

        FlowNode flowNode = this.modelInstance.getModelElementById(nodeID);
        Process process = (Process) flowNode.getParentElement();
        String processID = process.getId();

        Collection<Participant> participants = this.modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : participants) {
            if(processID.equals(participant.getProcess().getId() ) ) sourceSubject = participant.getName();
        }

        return sourceSubject;
    }

    // Gives the target subject (ex:"Departament", "Empleat") of the message flow
    public String getTargetSubject(MessageFlow messageFlow) {
        String targetSubject = null;

        InteractionNode interactionNode = messageFlow.getTarget();
        String nodeID = interactionNode.getId();

        FlowNode flowNode = this.modelInstance.getModelElementById(nodeID);
        Process process = (Process) flowNode.getParentElement();
        String processID = process.getId();

        Collection<Participant> participants = this.modelInstance.getModelElementsByType(Participant.class);
        for(Participant participant : participants) {
            if(processID.equals(participant.getProcess().getId() ) ) targetSubject = participant.getName();
        }

        return targetSubject;
    }


    /*
     * AUXILIAR METHODS
     */
    // Creates the name(id) for the Intent
    public String createName(Participant participant, Process process, FlowNode node) {
        //String name = participant.getName() + ;
        String name = node.getId();
        return name;
    }
    public String createName(MessageFlow messageFlow, String subject) {
        String name = messageFlow.getId() + " " + subject;
        return name;
    }




    /*
     * PARSE FLOW NODES
     */

    private void addTrainingPhrases(Intent intent, FlowNode node) {
        Collection<SequenceFlow> incomingSequenceFlow = node.getIncoming();
        for(SequenceFlow sequenceFlow : incomingSequenceFlow) intent.addTrainingPhrase(sequenceFlow.getName());
    }

    private void addInputIntentIDs(Intent intent, FlowNode node, Participant participant, Process process) {
        Collection<FlowNode> incomingFlowNodes = getRelevantIncomingFlowNodes(node);
        for(FlowNode flowNode : incomingFlowNodes) intent.addInputIntentID (createName(participant, process, flowNode));
    }

    private void addOutputIntentIDs(Intent intent, FlowNode node, Participant participant, Process process) {
        Collection<FlowNode> outgoingFlowNodes = getRelevantFlowingFlowNodes(node);
        for(FlowNode flowNode : outgoingFlowNodes) intent.addOutputIntentID(createName(participant, process, flowNode));
    }

    public Intents parseFlowNode(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        String name = createName(participant, process, node); // The name is the identificator
        String subject = participant.getName();
        String tasca = node.getName();
        Intent intent = new Intent(name, subject, tasca);

        addTrainingPhrases(intent, node);
        addInputIntentIDs (intent, node, participant, process);
        addOutputIntentIDs(intent, node, participant, process);

        intents.add(intent);

        return intents;
    }

    public Intents parseStartEvent(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        StartEvent startEvent = modelInstance.getModelElementById(node.getId());
        intents.add(this.parseFlowNode(participant, process, startEvent));
        return intents;
    }

    public Intents parseTask(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        Task task = modelInstance.getModelElementById(node.getId());

        intents.add(this.parseFlowNode(participant, process, node));

        // TODO: LOOK AT BpmnAlgorithm.java on function: public Intents parse()
        // TODO: CONSIDER THAT IDEA

        return intents;
    }



    public Intents parseExclusiveGateway(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        ExclusiveGateway exclusiveGateway = modelInstance.getModelElementById(node.getId());
        intents.add(this.parseFlowNode(participant, process, exclusiveGateway));

        return intents;
    }

    public Intents parseMessageFlow(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        MessageFlow messageFlow = modelInstance.getModelElementById(node.getId());

        return intents;
    }

    public Intents parseEndEvent(Participant participant, Process process, FlowNode node) {
        Intents intents = new Intents();

        EndEvent endEvent = modelInstance.getModelElementById(node.getId());
        intents.add(this.parseFlowNode(participant, process, endEvent));

        return intents;
    }


    /*
     * Generates an intent for the source node
     */
    public Intent parseSourceMessageFlow(MessageFlow messageFlow) {
        String task = messageFlow.getName();
        String sourceSubject = this.getSourceSubject(messageFlow);
        String targetSubject = this.getTargetSubject(messageFlow);

        String sourceIntentName = createName(messageFlow, sourceSubject);
        Intent sourceIntent = new CollaborationIntent(sourceIntentName, sourceSubject, targetSubject, task);

        return sourceIntent;
    }

    /*
     * Generates an intent for the target node
     */
    public Intent parseTargetMessageFlow(MessageFlow messageFlow) {
        String task = messageFlow.getName();
        String sourceSubject = this.getSourceSubject(messageFlow);
        String targetSubject = this.getTargetSubject(messageFlow);

        String targetIntentName = createName(messageFlow, targetSubject);
        Intent targetIntent = new CollaborationIntent(targetIntentName, sourceSubject, targetSubject, task);

        return targetIntent;
    }

}
