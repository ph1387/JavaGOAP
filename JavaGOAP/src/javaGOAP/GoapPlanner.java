package javaGOAP;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * GoapPlanner.java --- Class for generating a Queue of GoapActions using the
 * JGraphT library.
 * 
 * @author P H - 28.01.2017
 */
public class GoapPlanner implements IGoapPlanner {
	private IGoapUnit goapUnit;
	private GraphNode startNode;
	private List<GraphNode> endNodes;

	// -------------------- Functions

	/**
	 * Generate a plan (Queue of GoapActions) which is then performed by the
	 * assigned GoapUnit. The planner uses the JGraphT library to create a
	 * directed-weighted graph. A search algorithm is not needed as each node
	 * contains each path to itself. Therefore each goal contains a list of
	 * paths leading starting from the worldState through multiple node directly
	 * to itself. The goals and their paths can be sorted according to these
	 * paths and the importance of each goal with the weight provided by each
	 * node inside the graph.
	 * 
	 * @param goapUnit
	 *            the GoapUnit the plan gets generated for.
	 * @return a generated plan (Queue) of GoapActions, that the GoapUnit has to
	 *         perform to archive the desired goalState OR null, if no plan was
	 *         generated.
	 */
	public Queue<GoapAction> plan(IGoapUnit goapUnit) {
		Queue<GoapAction> createdPlan = null;
		this.goapUnit = goapUnit;
		this.startNode = new GraphNode(null);
		this.endNodes = new ArrayList<GraphNode>();

		try {
			sortGoalStates();

			// The Integer.MaxValue indicates that the goal was passed by the
			// changeGoalImmediatly function. An empty Queue is returned instead
			// of null because null would result in the IdleState to call this
			// function again. An empty Queue is finished in one cycle with no
			// effect at all.
			if (this.goapUnit.getGoalState().get(0).importance == Integer.MAX_VALUE) {
				List<GoapState> goalState = new ArrayList<GoapState>();

				goalState.add(this.goapUnit.getGoalState().get(0));

				createdPlan = searchGraphForActionQueue(createGraph(goalState));

				if (createdPlan == null) {
					createdPlan = new LinkedList<GoapAction>();
				}

				this.goapUnit.getGoalState().remove(0);
			} else {
				createdPlan = searchGraphForActionQueue(createGraph());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createdPlan;
	}

	// ------------------------------ Sort the goals

	/**
	 * Function for sorting a goapUnits goalStates (descending). The most
	 * important goal has the highest importance value.
	 * 
	 * @return the sorted goal list of the goapUnit.
	 */
	private List<GoapState> sortGoalStates() {
		if (this.goapUnit.getGoalState().size() > 1) {
			this.goapUnit.getGoalState().sort(new Comparator<GoapState>() {

				@Override
				public int compare(GoapState o1, GoapState o2) {
					return o2.importance.compareTo(o1.importance);
				}
			});
		}
		return this.goapUnit.getGoalState();
	}

	// ------------------------------ Create a graph

	/**
	 * Convenience function for all current goalStates.
	 * 
	 * @see #createGraph(List goalState)
	 */
	private SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> createGraph() {
		return createGraph(goapUnit.getGoalState());
	}

	/**
	 * Function to create a graph based on all possible unit actions of the
	 * GoapUnit for (a) specific goal/-s.
	 * 
	 * @param goalState
	 *            list of states the action queue has to fulfill.
	 * @return a directedWeightedGraph generated from all possible unit actions
	 *         for a goal.
	 */
	private SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> createGraph(List<GoapState> goalState) {
		SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> generatedGraph = new SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		addVertices(generatedGraph, goalState);
		addEdges(generatedGraph);

		return generatedGraph;
	}

	// ---------------------------------------- Vertices

	/**
	 * Function for adding vertices to a graph.
	 * 
	 * @param graph
	 *            the graph the vertices are being added to.
	 * @param goalState
	 *            List of States the unit has listed as their goals.
	 */
	private void addVertices(SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> graph,
			List<GoapState> goalState) {
		// The effects from the world state as well as the precondition of each
		// goal have to be set at the beginning, since these are the effects the
		// unit tries to archive with its actions. Also the startNode has to
		// overwrite the existing GraphNode as an initialization of a new Object
		// would not be reflected to the function caller.
		GraphNode start = new GraphNode(null, this.goapUnit.getWorldState());
		this.startNode.overwriteOwnProperties(start);
		graph.addVertex(this.startNode);

		for (GoapState state : goalState) {
			HashSet<GoapState> goalStateHash = new HashSet<GoapState>();
			goalStateHash.add(state);

			GraphNode end = new GraphNode(goalStateHash, null);
			graph.addVertex(end);
			this.endNodes.add(end);
		}

		HashSet<GoapAction> possibleActions = extractPossibleActions();

		// Afterward all other possible actions have to be added as well.
		if (possibleActions != null) {
			for (GoapAction goapAction : possibleActions) {
				graph.addVertex(new GraphNode(goapAction));
			}
		}
	}

	/**
	 * Needed to check if the available actions can actually be performed
	 * 
	 * @return all possible actions which are actually available for the unit.
	 */
	private HashSet<GoapAction> extractPossibleActions() {
		HashSet<GoapAction> possibleActions = new HashSet<GoapAction>();

		try {
			for (GoapAction goapAction : this.goapUnit.getAvailableActions()) {
				if (goapAction.checkProceduralPrecondition(this.goapUnit)) {
					possibleActions.add(goapAction);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return possibleActions;
	}

	// ---------------------------------------- Edges

	/**
	 * Function for adding (all) edges in the provided graph based on the
	 * GoapUnits actions and their combined effects along the way. The way this
	 * is archived is by first adding all default nodes, whose preconditions are
	 * met by the effect of the beginning state (worldState). All further
	 * connections base on these first connected nodes in the Queue. Elements
	 * connected are getting added to a Queue which is then being worked on.
	 * 
	 * @param graph
	 *            the graph the edges are being added to.
	 * @param endNodes
	 *            the end nodes, to which paths inside the graph lead to.
	 */
	private void addEdges(SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> graph) {
		Queue<GraphNode> nodesToWorkOn = new LinkedList<GraphNode>();

		addDefaultEdges(graph, nodesToWorkOn);

		// TODO: Possible Change: Add a HashSet to keep track of all nodes
		// already
		// connected once so that those nodes do not get added to the queue
		// again. -> performance!

		// Check each already connected once node against all other nodes to
		// find a possible match between the combined effects of the path + the
		// worldState and the preconditions of the current node.
		while (!nodesToWorkOn.isEmpty()) {
			GraphNode node = nodesToWorkOn.poll();

			// Select only node to which a path can be created (-> targets!)
			if (!node.equals(this.startNode) && !this.endNodes.contains(node)) {
				tryToConnectNode(graph, node, nodesToWorkOn);
			}
		}
	}

	/**
	 * Function for adding the edges to the graph which are the connection from
	 * the starting node to all default accessible nodes (= actions). These
	 * nodes either have no precondition or their preconditions are all in the
	 * effect HashSet of the starting node. These default edges are needed since
	 * all further connections rely on them as nodes in the further steps are
	 * not allowed to connect to the starting node anymore.
	 * 
	 * @param graph
	 *            the graph the edges are getting added to.
	 * @param nodesToWorkOn
	 *            the Queue in which nodes which got connected are getting added
	 *            to.
	 */
	private void addDefaultEdges(SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> graph,
			Queue<GraphNode> nodesToWorkOn) {

		// TODO: Possible Change: Remove graphNode.action != null
		for (GraphNode graphNode : graph.vertexSet()) {
			if (!this.startNode.equals(graphNode) && graphNode.action != null && (graphNode.preconditions.isEmpty()
					|| areAllPreconditionsMet(graphNode.preconditions, this.startNode.effects))) {
				addEgdeWithWeigth(graph, this.startNode, graphNode, new DefaultWeightedEdge(), 0);
				if (!nodesToWorkOn.contains(graphNode)) {
					nodesToWorkOn.add(graphNode);
				}

				// Add the path to the node to the GraphPath list in the node
				// since this is the first step inside the graph.
				List<GraphNode> vertices = new ArrayList<GraphNode>();
				List<DefaultWeightedEdge> edges = new ArrayList<DefaultWeightedEdge>();

				vertices.add(this.startNode);
				vertices.add(graphNode);

				edges.add(graph.getEdge(this.startNode, graphNode));

				GraphPath<GraphNode, DefaultWeightedEdge> graphPathToDefaultNode = new GraphWalk<GraphNode, DefaultWeightedEdge>(
						graph, this.startNode, graphNode, vertices, edges,
						graph.getEdgeWeight(graph.getEdge(this.startNode, graphNode)));
				graphNode.addGraphPath(null, graphPathToDefaultNode);
			}
		}
	}

	/**
	 * Function for testing if all preconditions in a given HashSet are also in
	 * another HashSet (effects) with the same values.
	 * 
	 * @param preconditions
	 *            HashSet of states which are present.
	 * @param effects
	 *            HashSet of states which are required.
	 * @return true or false depending if all preconditions are met with the
	 *         given effects.
	 */
	private static boolean areAllPreconditionsMet(HashSet<GoapState> preconditions, HashSet<GoapState> effects) {
		boolean preconditionsMet = true;

		for (GoapState precondition : preconditions) {
			boolean currentPreconditionMet = false;

			for (GoapState effect : effects) {
				if (precondition.effect.equals(effect.effect)) {
					if (precondition.value.equals(effect.value)) {
						currentPreconditionMet = true;
					} else {
						preconditionsMet = false;
					}
				}
			}

			if (!preconditionsMet || !currentPreconditionMet) {
				preconditionsMet = false;

				break;
			}
		}
		return preconditionsMet;
	}

	/**
	 * Convenience function for adding a weighted edge to an existing graph.
	 * 
	 * @param graph
	 *            the graph the edge is added to.
	 * @param firstVertex
	 *            start vertex.
	 * @param secondVertex
	 *            target vertex.
	 * @param edge
	 *            edge reference.
	 * @param weight
	 *            the weight of the edge being created.
	 * @return true or false depending if the creation of the edge was
	 *         successful or not.
	 */
	private static <V, E> boolean addEgdeWithWeigth(SimpleDirectedWeightedGraph<V, E> graph, V firstVertex,
			V secondVertex, E edge, double weight) {
		try {
			graph.addEdge(firstVertex, secondVertex, edge);
			graph.setEdgeWeight(graph.getEdge(firstVertex, secondVertex), weight);

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	/**
	 * Function for trying to connect a given node to all other nodes in the
	 * graph besides the starting node.
	 * 
	 * @param graph
	 *            the graph in which the provided nodes are located.
	 * @param node
	 *            the node which is being connected to another node.
	 * @param nodesToWorkOn
	 *            the Queue to which any connected nodes are being added to work
	 *            on these connections in further iterations.
	 * @return true or false depending on if the node was connected to another
	 *         node.
	 */
	private boolean tryToConnectNode(SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> graph, GraphNode node,
			Queue<GraphNode> nodesToWorkOn) {
		boolean connected = false;

		for (GraphNode otherNodeInGraph : graph.vertexSet()) {
			// End nodes can not have a edge towards another node and the target
			// node must not be itself. Also there must not already be an edge
			// in the graph.
			// && !graph.containsEdge(node, nodeInGraph) has to be added
			// or loops occur which lead to a crash. This leads to the case
			// where no
			// alternative routes are being stored inside the pathsToThisNode
			// list. This is because of the use of a Queue, which loses the
			// memory of which nodes were already connected.
			if (!node.equals(otherNodeInGraph) && !this.startNode.equals(otherNodeInGraph)
					&& !graph.containsEdge(node, otherNodeInGraph)) {

				// Every saved path to this node is checked if any of these
				// produce a suitable effect set regarding the preconditions of
				// the current node.
				for (GraphPath<GraphNode, DefaultWeightedEdge> pathToListNode : node.pathsToThisNode) {
					if (areAllPreconditionsMet(otherNodeInGraph.preconditions, node.getEffectState(pathToListNode))) {
						connected = true;

						addEgdeWithWeigth(graph, node, otherNodeInGraph, new DefaultWeightedEdge(),
								node.action.generateCost(this.goapUnit));

						otherNodeInGraph.addGraphPath(pathToListNode,
								addNodeToGraphPath(graph, pathToListNode, otherNodeInGraph));

						nodesToWorkOn.add(otherNodeInGraph);

						// break; // TODO: Possible Change: If set then only one
						// connection at a time can be made from each path to
						// each node.
					}
				}
			}
		}
		return connected;
	}

	/**
	 * Function for adding a new node to a given GraphPath. The new node is
	 * added to a sublist of the provided path vertexSet.
	 * 
	 * @param graph
	 *            the graph the path is located in.
	 * @param baseGraphPath
	 *            the path to which a node is being added.
	 * @param nodeToAdd
	 *            the node which shall be added.
	 * @return a graphPath with a given node as the end element, updated
	 *         vertexSet, edgeSet and weight.
	 */
	private static GraphPath<GraphNode, DefaultWeightedEdge> addNodeToGraphPath(
			SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> graph,
			GraphPath<GraphNode, DefaultWeightedEdge> baseGraphPath, GraphNode nodeToAdd) {
		double weight = baseGraphPath.getWeight();
		List<GraphNode> vertices = new ArrayList<GraphNode>(baseGraphPath.getVertexList());
		List<DefaultWeightedEdge> edges = new ArrayList<DefaultWeightedEdge>(baseGraphPath.getEdgeList());

		if (nodeToAdd.action != null) {
			weight += graph.getEdgeWeight(graph.getEdge(baseGraphPath.getEndVertex(), nodeToAdd));
		}

		vertices.add(nodeToAdd);
		edges.add(graph.getEdge(baseGraphPath.getEndVertex(), nodeToAdd));

		return new GraphWalk<GraphNode, DefaultWeightedEdge>(graph, baseGraphPath.getStartVertex(), nodeToAdd, vertices,
				edges, weight);
	}

	// ------------------------------ Search the graph for a Queue of
	// GoapActions

	/**
	 * Function for searching a graph for the lowest cost of a series of actions
	 * which have to be taken to archive a certain goal which has most certainly
	 * the highest importance.
	 * 
	 * @param graph
	 *            the graph of GoapActions the unit has to take in order to
	 *            archive a goal.
	 * @return the Queue of GoapActions which has the lowest cost to archive a
	 *         goal.
	 */
	private Queue<GoapAction> searchGraphForActionQueue(
			SimpleDirectedWeightedGraph<GraphNode, DefaultWeightedEdge> graph) {
		Queue<GoapAction> actionQueue = null;

		for (int i = 0; i < this.endNodes.size() && actionQueue == null; i++) {
			sortPathsLeadingToNode(this.endNodes.get(i));

			for (int j = 0; j < this.endNodes.get(i).pathsToThisNode.size() && actionQueue == null; j++) {
				actionQueue = extractActionsFromGraphPath(this.endNodes.get(i).pathsToThisNode.get(j), this.startNode,
						this.endNodes.get(i));
			}
		}
		return actionQueue;
	}

	/**
	 * Sorting function for the paths leading to a node based on their combined
	 * edge weights (ascending).
	 * 
	 * @param node
	 *            the node whose paths leading to it are being sorted.
	 */
	private static void sortPathsLeadingToNode(GraphNode node) {
		node.pathsToThisNode.sort(new Comparator<GraphPath<GraphNode, DefaultWeightedEdge>>() {

			@Override
			public int compare(GraphPath<GraphNode, DefaultWeightedEdge> o1,
					GraphPath<GraphNode, DefaultWeightedEdge> o2) {
				return ((Double) o1.getWeight()).compareTo(o2.getWeight());
			}
		});
	}

	/**
	 * Function for extracting all Actions from a GraphPath.
	 * 
	 * @param path
	 *            the path from which the actions are being extracted.
	 * @param startNode
	 *            the starting node needs to be known as it contains no action.
	 * @param endNode
	 *            the end node needs to be known since it contains no action.
	 * @return a Queue in which all actions from the given path are listed.
	 */
	private static Queue<GoapAction> extractActionsFromGraphPath(GraphPath<GraphNode, DefaultWeightedEdge> path,
			GraphNode startNode, GraphNode endNode) {
		Queue<GoapAction> actionQueue = new LinkedList<GoapAction>();

		for (GraphNode node : path.getVertexList()) {
			if (!node.equals(startNode) && !node.equals(endNode)) {
				actionQueue.add(node.action);
			}
		}
		return actionQueue;
	}
}
