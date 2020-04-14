package main.Entity.Parser.ParserFlowNodes;

import com.google.cloud.dialogflow.v2.Intent;
import main.Entity.Intent.*;
import main.Entity.Parser.BpmnAlgorithm;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Flow;

public class ParserFlowNodes {
    public static void println(String s) { System.out.println(s); }

    public BpmnModelInstance modelInstance;

    // Helpers
    public ParseFlowNodesHelper parseFlowNodesHelper;
    protected CamundaHelper camundaHelper;

    public ParserFlowNodes(BpmnModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        parseFlowNodesHelper = new ParseFlowNodesHelper(modelInstance);
        camundaHelper = new CamundaHelper(modelInstance);
    }

    public CamundaHelper getCamundaHelper() { return camundaHelper; }
    public ParseFlowNodesHelper getParseFlowNodesHelper() { return parseFlowNodesHelper; }


    /*
     * PARSE FLOW NODES
     */

    private void addTrainingPhrases(myIntent intent, FlowNode node) {
        Collection<SequenceFlow> incomingSequenceFlow = node.getIncoming();
        for(SequenceFlow sequenceFlow : incomingSequenceFlow) intent.addTrainingPhrase(sequenceFlow.getName());
    }

    private void addInputContextIDs(myIntent intent, FlowNode node, Participant participant, Process process) {
        Collection<FlowNode> incomingFlowNodes = getCamundaHelper().getRelevantIncomingFlowNodes(node);
        for(FlowNode flowNode : incomingFlowNodes) intent.addInputContextID(this.getParseFlowNodesHelper().createName(participant, process, flowNode));
    }

    private void addOutputIntentIDs(myIntent intent, FlowNode node, Participant participant, Process process) {
        Collection<FlowNode> outgoingFlowNodes = getCamundaHelper().getRelevantFlowingFlowNodes(node);
        for(FlowNode flowNode : outgoingFlowNodes) intent.addOutputIntentID(this.getParseFlowNodesHelper().createName(participant, process, flowNode));
    }

    private void addOutputContextIDs(myIntent intent, FlowNode node, Participant participant, Process process) {
        Collection<FlowNode> outgoingFlowNodes = getCamundaHelper().getRelevantFlowingFlowNodes(node);
        for(FlowNode flowNode : outgoingFlowNodes) intent.addOutputContextID(this.getParseFlowNodesHelper().createName(participant, process, flowNode));
    }

    //TODO: ****************************************************************************************/
    // TODO: Create some type of interfaceImpl, and here redirect to the correct Impl
    // TODO: Or convert the FlowNodes to some classes of mine, so i can use polyformism
    //TODO: ****************************************************************************************/
    public Intents parseFlowNode(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        String name = this.getParseFlowNodesHelper().createName(participant, process, node); // The name is the identificator
        String subject = participant.getName();
        String tasca = node.getName();
        myIntent intent = new myIntent(name, subject, tasca);


        addTrainingPhrases(intent, node);

        intent.addInputContextIDs(this.getInputContextIDs(node));
        intent.addOutputContextIDs(this.getOutputContextIDs(node));

        intent.addOutputIntentIDs(this.getOutputIntentIDs(node));

        //addInputContextIDs (intent, node, participant, process);
        //addOutputIntentIDs(intent, node, participant, process);
        //addOutputContextIDs(intent, node, participant, process); // Add itself
        //intent.addOutputContextID(name); // Add itself


        intents.add(intent);

        return intents;
    }

    public Intents parseStartEvent(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        StartEvent startEvent = modelInstance.getModelElementById(node.getId());

        String name = this.getParseFlowNodesHelper().createName(participant, process, startEvent); // The name is the identificator
        String subject = participant.getName();
        String tasca = startEvent.getName();
        myIntent intent = new StartIntent(name, subject, tasca);

        addTrainingPhrases(intent, startEvent);
        addInputContextIDs (intent, startEvent, participant, process);
        addOutputIntentIDs(intent, startEvent, participant, process);
        intent.addOutputContextID(name); // Add itself

        //intent.addInputContextIDs(this.getInputContextIDs(node));
        //intent.addOutputContextIDs(this.getOutputContextIDs(node));

        //intent.addOutputIntentIDs(this.getOutputIntentIDs(node));

        intents.add(intent);

        return intents;
    }

    public Intents parseTask(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();

        Task task = modelInstance.getModelElementById(node.getId());
        String name = this.getParseFlowNodesHelper().createName(participant, process, task); // The name is the identificator
        String subject = participant.getName();
        String tasca = task.getName();
        TaskIntent intent = new TaskIntent(name, subject, tasca);

        addTrainingPhrases(intent, task);


        // TODO: Document this(the idea for doing this[Dialoglow dont has OR in input context, so in an if structure, only
        //  one context can be required. So some nodes have to give/require the same context])

        intent.addInputContextIDs(this.getInputContextIDs(node));
        intent.addOutputContextIDs(this.getOutputContextIDs(node));

        intent.addOutputIntentIDs(this.getOutputIntentIDs(node));

        intents.add(intent);

        // Query intent
        QueryTaskIntent queryTaskIntent = new QueryTaskIntent(name, subject, tasca);

        intents.add(queryTaskIntent);

        return intents;
    }


    /*
     * Parses an opening exclusive gateway
     * PRE: node is a opening exclusive gateway
     * POST: Returns the intents generated
     */
    public Intents parseExclusiveGateway(Participant participant, Process process, FlowNode node) throws IOException {
        Intents intents = new Intents();
        String openExclusiveGatewayID = node.getId();

        ExclusiveGateway exclusiveGateway = modelInstance.getModelElementById(openExclusiveGatewayID);
        intents.add(this.parseFlowNode(participant, process, exclusiveGateway));

        List<FlowNode> flowingFlowNodes = this.getCamundaHelper().getAllFlowingFlowNodesAsList(exclusiveGateway);
        for(int i =0; i < flowingFlowNodes.size(); ++i) {
            FlowNode flowNode = flowingFlowNodes.get(i);
            if(this.getParseFlowNodesHelper().isClosingExclusiveGateway(flowNode)) {
                // Si No te tasques entremig, S'ha de crear el intent extra
                List<String> nextIntentIDs = this.getOutputIntentIDs(flowNode); // Should only be one
                for(String nextIntentID : nextIntentIDs) {
                    FlowNode nextNode = modelInstance.getModelElementById(nextIntentID);

                    String name = this.getParseFlowNodesHelper().createName(participant, process, nextNode,
                                                                "","_" + openExclusiveGatewayID); // The name is the identificator
                    String subject = participant.getName();
                    String tasca = nextNode.getName();
                    myIntent nextIntent = new myIntent(name, subject, tasca); // TODO: May change the type of intent

                    nextIntent.addInputContextID(openExclusiveGatewayID);

                    nextIntent.addOutputContextIDs(this.getOutputContextIDs(nextNode));
                    nextIntent.addOutputIntentIDs(this.getOutputIntentIDs(nextNode));

                    Collection<SequenceFlow> sequenceFlowsCollection = node.getOutgoing();
                    List<SequenceFlow> sequenceFlows = this.parseFlowNodesHelper.convertToSequenceFlowList(sequenceFlowsCollection);
                    SequenceFlow sequenceFlow = sequenceFlows.get(i); // TODO: Test that it works(same i)
                    String trainingPhrase = sequenceFlow.getName();
                    nextIntent.addTrainingPhrase(trainingPhrase);

                    intents.add(nextIntent);
                }

            }
        }

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
        String sourceSubject = this.getCamundaHelper().getSourceSubject(messageFlow);
        String targetSubject = this.getCamundaHelper().getTargetSubject(messageFlow);

        String sourceIntentName = this.getParseFlowNodesHelper().createName(messageFlow, sourceSubject);
        myIntent sourceIntent = new CollaborationIntent(sourceIntentName, sourceSubject, targetSubject, task);

        return sourceIntent;
    }

    /*
     * Generates an intent for the target node
     */
    public myIntent parseTargetMessageFlow(MessageFlow messageFlow) throws IOException {
        String task = messageFlow.getName();
        String sourceSubject = this.getCamundaHelper().getSourceSubject(messageFlow);
        String targetSubject = this.getCamundaHelper().getTargetSubject(messageFlow);

        String targetIntentName = this.getParseFlowNodesHelper().createName(messageFlow, targetSubject);
        myIntent targetIntent = new CollaborationIntent(targetIntentName, sourceSubject, targetSubject, task);

        return targetIntent;
    }


    /*
     *PRE: Les branques no s'uneixen i de les branques no es surt a fora
     */

    public Intents parseParallelGateway(Participant participant, Process process, List<FlowNode> closingParallelGatewayWrapperNode) throws IOException {
        Intents intents = new Intents();
        FlowNode node = closingParallelGatewayWrapperNode.get(0);
        ParallelGateway parallelGateway = modelInstance.getModelElementById(node.getId());

        Collection<FlowNode> followingNodes = this.getCamundaHelper().getRelevantFlowingFlowNodes(parallelGateway);
        List<FlowNode> firstNodes = this.getParseFlowNodesHelper().convertToFlowNodeList(followingNodes);
        int nBranques = firstNodes.size();

        Map<String, FlowNode> firstNodesMap = new TreeMap<String, FlowNode>();
        for(FlowNode flowNode : firstNodes) { firstNodesMap.put(flowNode.getId(), flowNode); } // Convertir en Map

        ParallelGateway closingParallelGateway = getClosingParallelGateway(parallelGateway); // Gateway on convergeixen

        List<Intents> branques = new ArrayList<Intents>(Arrays.asList(new Intents[nBranques]));
        List<FlowNode> lastNodes = new ArrayList<FlowNode>(Arrays.asList(new FlowNode[nBranques])); // Ultims nodes de les branques

        for(int i = 0; i < nBranques; ++i) {
            FlowNode lastNode = null;
            Intents branchIntents = parseTillClosingParallelGateway(participant, process, firstNodes.get(i),
                                                                    closingParallelGateway, lastNodes, i);
            branques.set(i, branchIntents);
        }

        Map<String, FlowNode> lastNodesMap = new TreeMap<String, FlowNode>();
        for(FlowNode flowNode : lastNodes) { lastNodesMap.put(flowNode.getId(), flowNode); } // Convertir en Map

        for(int i = 0; i < nBranques; ++i) {
            if(i==0) { // Primera branca
                FlowNode firstNode = firstNodes.get(i);
                String firstNodeId = firstNode.getId();
                myIntent firstNodeIntent = branques.get(i).getIntentByID(firstNodeId);
                firstNodeIntent.clearInputContexts();
                firstNodeIntent.addInputContextIDs(this.getInputContextIDs(firstNode));
            }
            else {


                String firstNodeId = firstNodes.get(i).getId();
                String lastNodeId = lastNodes.get(i - 1).getId();

                myIntent firstNodeIntent = branques.get(i).getIntentByID(firstNodeId);
                myIntent lastNodeIntent = branques.get(i - 1).getIntentByID(lastNodeId);

                // TODO: Arregla bug(del context que es requerix) quan l'ultim node d'una branca te una colaboracio de sortida
                // TODO: Tenir en compte que el parsing del messageflow, ara mateix es fa a posteriori)
                firstNodeIntent.clearInputContexts();
                firstNodeIntent.addInputContextID(lastNodeId);

                // TODO: Rename OutputIntent to an appropiate name(following)
                lastNodeIntent.clearOutputContexts();
                lastNodeIntent.clearOutputIntents();
                lastNodeIntent.addOutputContextID(lastNodeIntent.getId());
                lastNodeIntent.addOutputIntentID(firstNodeIntent.getId());
            }
        }

        FlowNode lastNode = lastNodes.get(nBranques-1); // Ultima branca
        myIntent lastNodeIntent = branques.get(nBranques-1).getIntentByID(lastNode.getId());
        lastNodeIntent.clearOutputIntents();
        lastNodeIntent.clearOutputContexts();

        // TODO: Call
        lastNodeIntent.addOutputContextIDs(this.getOutputContextIDs(closingParallelGateway));
        //this.addOutputIntentIDs(lastNodeIntent, closingParallelGateway, participant, process); // TODO: Improve or create function
        lastNodeIntent.addOutputIntentIDs(this.getOutputIntentIDs(closingParallelGateway));

        //lastNodeIntent.addOutputContextID(closingParallelGateway.getId());
        //lastNodeIntent.addOutputIntentID(closingParallelGateway.getId());

        // TODO: Tenir en compte que si l'ultim node es(per exemple) un *exclusiveGateway* que tanca, llavors el
        // TODO: output Context que s'haura de crea es el del *closingParallelGateway*. Les tasques previes al
        // TODO: *exclusiveGateway* tambe hauran de crear el context del *closingParallelGateway*

        closingParallelGatewayWrapperNode.set(0, closingParallelGateway); // Cambia el node per continua parsejant desde el closing

        for(Intents branchIntents: branques) {
            intents.add(branchIntents);
        }

        return intents;
    }

    /*
     * Parses recursively *node* till *closingParallelGateway*
     * *lastNode* is the node before the *closingParallelGateway*
     * i es el numero de la branca
     * lastNodes[i] tindra el ultim node de la branca i
     */ // TODO: Refactor code with BpmnAlgorithm.parseFlowNode(...)  [tenen codi compartit]
    private Intents parseTillClosingParallelGateway(Participant participant, Process process, FlowNode node,
                                                    ParallelGateway closingParallelGateway, List<FlowNode> lastNodes, int i) throws IOException {

        Intents intents = new Intents();

        Set<String> visitedFlowNodes = BpmnAlgorithm.getVisitedFlowNodesInstance();
        String flowNodeId = node.getId();
        if (visitedFlowNodes.contains(flowNodeId)) return intents; // Stop parsing, return empty intents
        else {
            visitedFlowNodes.add(flowNodeId);

            ModelElementType startEventType = this.modelInstance.getModel().getType(StartEvent.class);
            ModelElementType taskType = this.modelInstance.getModel().getType(Task.class);
            ModelElementType exclusiveGatewayType = this.modelInstance.getModel().getType(ExclusiveGateway.class);
            ModelElementType ParallelGatewayType = this.modelInstance.getModel().getType(ParallelGateway.class);
            ModelElementType messageFlowType = this.modelInstance.getModel().getType(MessageFlow.class);
            ModelElementType sequenceFlowType = this.modelInstance.getModel().getType(SequenceFlow.class);
            ModelElementType endEventType = this.modelInstance.getModel().getType(EndEvent.class);

            if (node.getElementType() == startEventType) intents.add(this.parseStartEvent(participant, process, node));
            else if (node.getElementType() == taskType) intents.add(this.parseTask(participant, process, node));
            else if (node.getElementType() == exclusiveGatewayType)
                intents.add(this.parseExclusiveGateway(participant, process, node));
            else if (node.getElementType() == ParallelGatewayType) {
                // After the execution of "parseParallelGateway", *node* changes to the closing parallelGateway
                List<FlowNode> workAroundChangeNodeInsideMethod = new ArrayList<FlowNode>();
                workAroundChangeNodeInsideMethod.add(node);
                Intents parsedIntents = this.parseParallelGateway(participant, process, workAroundChangeNodeInsideMethod);
                node = workAroundChangeNodeInsideMethod.get(0);
                intents.add(parsedIntents);
            } else if (node.getElementType() == messageFlowType)
                intents.add(this.parseMessageFlow(participant, process, node));
            else if (node.getElementType() == endEventType) intents.add(this.parseEndEvent(participant, process, node));
            else intents.add(this.parseFlowNode(participant, process, node));

            // Recursively parse the outgoing flownodes.
            Collection<FlowNode> flowNodes = this.getCamundaHelper().getRelevantFlowingFlowNodesPlusClosingParallelGateway(node); // Get following flow nodes(task, gateway, etc) of a FlowNode.
            for (FlowNode flowNode : flowNodes) {
                if (flowNode.getId().equals(closingParallelGateway.getId())) lastNodes.set(i, node);
                else
                    intents.add(this.parseTillClosingParallelGateway(participant, process, flowNode, closingParallelGateway, lastNodes, i));
            }

            return intents;
        }
    }

    /*
     * Recorre la primera branca i es guarda els nodes.
     * Recorre una segona branca fin que troba un parallelGateway PG amb mateix ID que un trobat en la primera branca
     * Retorna PG
     * PRE: Les branques no s'uneixen i de les branques no es surt a fora
     */
    public ParallelGateway getClosingParallelGateway(ParallelGateway parallelGateway) {
        String parallelGatewayID = parallelGateway.getId();

        Collection<FlowNode> followingNodesCollection = this.getCamundaHelper().getRelevantFlowingFlowNodes(parallelGateway);
        List<FlowNode> followingNodes = this.getParseFlowNodesHelper().convertToFlowNodeList(followingNodesCollection);
        String firstElementId = followingNodes.get(0).getId(); // Primer element de la Branca numero 0

        // BFS per obtenir els nodes visitats
        Set<String> visitedNodes = bfsWithForbbidenElement(firstElementId, parallelGatewayID);

        String secondBranchFirstElementId = followingNodes.get(1).getId(); // Primer element de la Branca numero 1
        String closingParallelGatewayID = bfsStopWhenVisitSameNode(secondBranchFirstElementId, parallelGatewayID,visitedNodes);

        ParallelGateway closingParallelGateway = modelInstance.getModelElementById(closingParallelGatewayID);
        return closingParallelGateway;
    }

    /*
     * Returns the id of the accesible nodes from *firstElementId*.
     * *forbiddenNodeIDs* are not considered
     * The algorithm ends and returns one *nodeID* if it is contained in *stopNodesIDs*
     */
    // Set<String> bfsWithForbbidenElement(String firstElementId, Set<String> forbiddenNodeIDs, Set<String> stopNodesIDs) {

    /*
     * BFS per obtenir els nodes que poden ser visitats desde *firstElementId*
     * *forbiddenNodeID* is not considered
     */
    public Set<String> bfsWithForbbidenElement(String firstElementId, String forbiddenNodeID) {
        Set<String> visitedNodes = new TreeSet<String>();
        Queue<String> toVisitNodes = new LinkedList<String>();
        toVisitNodes.add(firstElementId);

        // BFS per obtenir els nodes visitats
        while(! toVisitNodes.isEmpty()) {
            String nodeID = toVisitNodes.element();
            toVisitNodes.remove();
            if(!forbiddenNodeID.equals(nodeID) && !visitedNodes.contains(nodeID)) {//Si no es el Gateway i no l'ha visitat
                visitedNodes.add(nodeID);

                FlowNode node = modelInstance.getModelElementById(nodeID);
                Collection<FlowNode> flowNodes = this.getCamundaHelper().getRelevantFlowingFlowNodesPlusClosingParallelGateway(node);
                for(FlowNode flowNode: flowNodes) {
                    String flowNodeID = flowNode.getId();
                    toVisitNodes.add(flowNodeID);
                }
            }
        }
        return visitedNodes;
    }

    /*
     * Stops when finds a parallelGateway *PG* inside *stoppingVisitedNodes*
     * *forbiddenNodeID* is not considered
     * Returns *PG*
     *
     * PRE: Les branques no s'uneixen i de les branques no es surt a fora
     */
    public String bfsStopWhenVisitSameNode(String firstElementId, String forbiddenNodeID , Set<String> stoppingVisitedNodes) {
        Set<String> visitedNodes = new TreeSet<String>();
        Queue<String> toVisitNodes = new LinkedList<String>();
        toVisitNodes.add(firstElementId);

        // BFS per obtenir els nodes visitats
        while(! toVisitNodes.isEmpty()) {
            String nodeID = toVisitNodes.element();
            toVisitNodes.remove();
            if(stoppingVisitedNodes.contains(nodeID)){
                FlowNode node = modelInstance.getModelElementById(nodeID);
                ModelElementType ParallelGatewayType    = this.modelInstance.getModel().getType(ParallelGateway.class);
                if(node.getElementType() == ParallelGatewayType) return nodeID;
            }
            if(!forbiddenNodeID.equals(nodeID) && !visitedNodes.contains(nodeID)) {//Si no es el Gateway i no l'ha visitat
                visitedNodes.add(nodeID);

                FlowNode node = modelInstance.getModelElementById(nodeID);
                Collection<FlowNode> flowNodes = this.getCamundaHelper().getRelevantFlowingFlowNodesPlusClosingParallelGateway(node);
                for(FlowNode flowNode: flowNodes) {
                    String flowNodeID = flowNode.getId();
                    toVisitNodes.add(flowNodeID);
                }
            }
        }
        return null;
    }



    // TODO: DO MORE TESTING: TEST MORE COMPLEX BPMNs

    /*
     * Gets the input context IDs of a node
     */ // TODO: Ignore if is StartEvent
    public List<String> getInputContextIDs(FlowNode node) {
        List<String> inputIntentIDs = new ArrayList<String>();

        Collection<FlowNode> flowNodes = getCamundaHelper().getAllIncomingFlowNodes(node);
        if( this.getParseFlowNodesHelper().after_closing_exclusive_gateway(node) ||
            this.getParseFlowNodesHelper().after_opening_exclusive_gateway(node) ||
            this.getParseFlowNodesHelper().after_closing_parallel_gateway(node))
        {
            FlowNode inputflowNode = flowNodes.iterator().next(); // Nomes hauria de ser 1
            inputIntentIDs.addAll(getOutputContextIDs(inputflowNode));
        }
        else if(this.getParseFlowNodesHelper().after_opening_parallel_gateway(node)) {
            FlowNode inputflowNode = flowNodes.iterator().next(); // Nomes hauria de ser 1
            inputIntentIDs.addAll(getInputContextIDs(inputflowNode));
            // TODO: Fer qualsevol cosa, i en el post processat de parseParallelGateway arreglar-ho a partir de la info
            // TODO: que hi ha alla
        }
        else { // After other components
            FlowNode inputflowNode = flowNodes.iterator().next(); // Nomes hauria de ser 1
            inputIntentIDs.add(inputflowNode.getId());
        }

        return inputIntentIDs;
    }

    // TODO: Refactor this to make it more reusable or mantenible
    public List<String> getOutputContextIDs(FlowNode node) {
        List<String> outputContextIDs = new ArrayList<String>();

        Collection<FlowNode> flowNodes = getCamundaHelper().getAllFlowingFlowNodes(node);
        Collection<FlowNode> incomingFlowNodes = getCamundaHelper().getAllIncomingFlowNodes(node);

        if( this.getParseFlowNodesHelper().before_opening_exclusive_gateway(node) ||
            this.getParseFlowNodesHelper().before_opening_parallel_gateway(node))
        {
            outputContextIDs.add(node.getId());
        }
        else if(this.getParseFlowNodesHelper().before_closing_parallel_gateway(node)) {
            outputContextIDs.add(node.getId());// TODO: Fer qualsevol cosa, i en el post processat de parseParallelGateway arreglar-ho a partir de la info
            // TODO: que hi ha alla
        }
        else if(    ! this.getParseFlowNodesHelper().isOpeningExclusiveGateway(node) &&
                    this.getParseFlowNodesHelper().before_closing_exclusive_gateway(node))
        {
            for(FlowNode outputFlowNode : flowNodes) { // Nomes hauria de ser 1
                outputContextIDs.addAll(getOutputContextIDs(outputFlowNode));
            }

        }
        else if(this.parseFlowNodesHelper.isOpeningParallelGateway(node)) {
                FlowNode inputflowNode = flowNodes.iterator().next(); // Nomes hauria de ser 1
                outputContextIDs.addAll(getOutputContextIDs(inputflowNode));
        }
        else {
            outputContextIDs.add(node.getId());
        }

        return outputContextIDs;
    }



    /*
     * Gets the output intent IDs of an opening parallel gateway
     * PRE: "parallelGateway" is an opening parallel gateway
     * POST: Returns the id of the first intent of the "parallelGateway"
     */
    public List<String> firstIntentOfFirstBranch(FlowNode parallelGateway) {
        List<String> outputIntentIDs = new ArrayList<String>();

        Collection<FlowNode> flowNodes = getCamundaHelper().getAllFlowingFlowNodes(parallelGateway);
        List<FlowNode> firstNodes = this.getParseFlowNodesHelper().convertToFlowNodeList(flowNodes);
        FlowNode firstNode = firstNodes.get(0); // First node of the first branch
        if(this.parseFlowNodesHelper.isOpeningParallelGateway(firstNode)) {
            outputIntentIDs.addAll(firstIntentOfFirstBranch(firstNode));
        }
        else outputIntentIDs.add(firstNode.getId());

        return outputIntentIDs;
    }


    /*
     * Gets the output intent IDs of a node
     */ // TODO: Create different function for this function depending on the subtype of the FlowNode(parameter)
    // TODO: Incomplete function. For now, i use: addOutputIntentIDs(...) from ParserFlowNodes.java
    public List<String> getOutputIntentIDs(FlowNode node) {
        List<String> outputIntentIDs = new ArrayList<String>();

        if(this.getParseFlowNodesHelper().isOpeningParallelGateway(node)) {
            outputIntentIDs.addAll(this.firstIntentOfFirstBranch(node));
        }
        else {
            Collection<FlowNode> flowNodes = getCamundaHelper().getAllFlowingFlowNodes(node);
            for(FlowNode flowNode : flowNodes) {
                if(this.getParseFlowNodesHelper().isOpeningParallelGateway(flowNode)) {
                    outputIntentIDs.addAll(this.getOutputIntentIDs(flowNode));
                }
                else if(this.getParseFlowNodesHelper().isClosingParallelGateway(flowNode)) {
                    outputIntentIDs.addAll(this.getOutputIntentIDs(flowNode));
                }
                else if(this.getParseFlowNodesHelper().isClosingExclusiveGateway(flowNode)) {
                    outputIntentIDs.addAll(this.getOutputIntentIDs(flowNode));
                }
                else {
                    outputIntentIDs.add(flowNode.getId());
                }
            }
        }

        return outputIntentIDs;
    }

}
