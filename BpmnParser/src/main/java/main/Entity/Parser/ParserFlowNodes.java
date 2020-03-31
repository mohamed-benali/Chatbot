package main.Entity.Parser;

import main.Entity.Intent.*;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.io.IOException;
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
        String name = messageFlow.getId() + "_" + subject;
        return name;
    }




    /*
     * PARSE FLOW NODES
     */

    private void addTrainingPhrases(myIntent intent, FlowNode node) {
        Collection<SequenceFlow> incomingSequenceFlow = node.getIncoming();
        for(SequenceFlow sequenceFlow : incomingSequenceFlow) intent.addTrainingPhrase(sequenceFlow.getName());
    }

    private void addInputIntentIDs(myIntent intent, FlowNode node, Participant participant, Process process) {
        Collection<FlowNode> incomingFlowNodes = getRelevantIncomingFlowNodes(node);
        for(FlowNode flowNode : incomingFlowNodes) intent.addInputIntentID (createName(participant, process, flowNode));
    }

    private void addOutputIntentIDs(myIntent intent, FlowNode node, Participant participant, Process process) {
        Collection<FlowNode> outgoingFlowNodes = getRelevantFlowingFlowNodes(node);
        for(FlowNode flowNode : outgoingFlowNodes) intent.addOutputIntentID(createName(participant, process, flowNode));
    }

    private void addOutputContextIDs(myIntent intent, FlowNode node, Participant participant, Process process) {
        Collection<FlowNode> outgoingFlowNodes = getRelevantFlowingFlowNodes(node);
        for(FlowNode flowNode : outgoingFlowNodes) intent.addOutputContextID(createName(participant, process, flowNode));
    }


    public Intents parseFlowNode(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        String name = createName(participant, process, node); // The name is the identificator
        String subject = participant.getName();
        String tasca = node.getName();
        myIntent intent = new myIntent(name, subject, tasca);

        addTrainingPhrases(intent, node);
        addInputIntentIDs (intent, node, participant, process);
        addOutputIntentIDs(intent, node, participant, process);
        //addOutputContextIDs(intent, node, participant, process); // Add itself
        intent.addOutputContextID(name); // Add itself


        intents.add(intent);

        return intents;
    }

    public Intents parseStartEvent(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        StartEvent startEvent = modelInstance.getModelElementById(node.getId());

        String name = createName(participant, process, startEvent); // The name is the identificator
        String subject = participant.getName();
        String tasca = startEvent.getName();
        myIntent intent = new StartIntent(name, subject, tasca);

        addTrainingPhrases(intent, startEvent);
        addInputIntentIDs (intent, startEvent, participant, process);
        addOutputIntentIDs(intent, startEvent, participant, process);
        intent.addOutputContextID(name); // Add itself

        intents.add(intent);

        return intents;
    }

    public Intents parseTask(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        Task task = modelInstance.getModelElementById(node.getId());
        String name = createName(participant, process, task); // The name is the identificator
        String subject = participant.getName();
        String tasca = task.getName();
        TaskIntent intent = new TaskIntent(name, subject, tasca);

        addTrainingPhrases(intent, task);

        // TODO: Document this(the idea for doing this[Dialoglow dont has OR in inpt context, so in an if structure, only+
        //  one context can be requiered. So some nodes have to give/require the same context])
        if(this.after_exclusive_gateway(task) ) {
            Collection<FlowNode> flowNodes = getAllIncomingFlowNodes(task); // Should only be the gateway TODO: Test it
            FlowNode first = flowNodes.iterator().next();
            intent.addInputIntentID(first.getId());
        }
        else addInputIntentIDs (intent, task, participant, process);

        if(this.before_exlusive_gateway(task) ) {
            Collection<FlowNode> flowNodes = getAllFlowingFlowNodes(task); // Should only be the gateway TODO: Test it
            FlowNode first = flowNodes.iterator().next();
            intent.addOutputIntentID(first.getId()); // TODO: Think if change the outputIntent for "addOutputIntentIDs(intent, task, participant, process);
            intent.addOutputContextID(first.getId());
        }
        else {
            addOutputIntentIDs(intent, task, participant, process);
            intent.addOutputContextID(name); // Add itself
        }

        intents.add(intent);

        // Query intent
        QueryTaskIntent queryTaskIntent = new QueryTaskIntent(name, subject, tasca);

        intents.add(queryTaskIntent);

        return intents;
    }

    // Tells if the node is before ONLY one NON EMPTY exlusive gateway
    public boolean before_exlusive_gateway(FlowNode node) {
        // TODO: This function can be refactored because shares code with
        //  "private boolean after_exclusive_gateway(FlowNode node) {
        //  Only would be necesary to give the type ModelElementType to be compared and the flowNodes"
        Collection<FlowNode> flowNodes = getAllFlowingFlowNodes(node); // Should only be the gateway TODO: Test it
        if(flowNodes.size() == 1) {
            FlowNode only_node = flowNodes.iterator().next();

            ModelElementType exclusiveGatewayType   = this.modelInstance.getModel().getType(ExclusiveGateway.class);
            if(only_node.getElementType() == exclusiveGatewayType && only_node.getName() == null) return true;
            else return false;
        }
        else return false;
    }

    // Tells if the node is after ONLY one NON EMPTY exlusive gateway
    public boolean after_exclusive_gateway(FlowNode node) {
        // TODO: Possible Refactoring, look at "private boolean before_exlusive_gateway(FlowNode node) {"
        Collection<FlowNode> flowNodes = getAllIncomingFlowNodes(node); // Should only be the gateway TODO: Test it
        if(flowNodes.size() == 1) {
            FlowNode only_node = flowNodes.iterator().next();

            ModelElementType exclusiveGatewayType   = this.modelInstance.getModel().getType(ExclusiveGateway.class);
            if(only_node.getElementType() == exclusiveGatewayType && only_node.getName() == null) return true;
            else return false;
        }
        else return false;
    }


    public Intents parseExclusiveGateway(Participant participant, Process process, FlowNode node) throws IOException {
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

    public Intents parseEndEvent(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        EndEvent endEvent = modelInstance.getModelElementById(node.getId());
        intents.add(this.parseFlowNode(participant, process, endEvent));

        return intents;
    }


    /*
     * Generates an intent for the source node
     */
    public myIntent parseSourceMessageFlow(MessageFlow messageFlow) throws IOException {
        String task = messageFlow.getName();
        String sourceSubject = this.getSourceSubject(messageFlow);
        String targetSubject = this.getTargetSubject(messageFlow);

        String sourceIntentName = createName(messageFlow, sourceSubject);
        myIntent sourceIntent = new CollaborationIntent(sourceIntentName, sourceSubject, targetSubject, task);

        return sourceIntent;
    }

    /*
     * Generates an intent for the target node
     */
    public myIntent parseTargetMessageFlow(MessageFlow messageFlow) throws IOException {
        String task = messageFlow.getName();
        String sourceSubject = this.getSourceSubject(messageFlow);
        String targetSubject = this.getTargetSubject(messageFlow);

        String targetIntentName = createName(messageFlow, targetSubject);
        myIntent targetIntent = new CollaborationIntent(targetIntentName, sourceSubject, targetSubject, task);

        return targetIntent;
    }

}
