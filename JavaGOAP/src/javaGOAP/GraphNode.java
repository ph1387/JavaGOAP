package javaGOAP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import graph.WeightedEdge;
import graph.WeightedPath;


/**
 * GraphNode.java --- Node on the JGraph
 * 
 * @author P H - 28.01.2017
 */
class GraphNode {

	GoapAction action = null;

	HashSet<GoapState> preconditions;
	HashSet<GoapState> effects;
	List<WeightedPath<GraphNode, WeightedEdge>> pathsToThisNode = new ArrayList<WeightedPath<GraphNode, WeightedEdge>>();
	private Hashtable<WeightedPath<GraphNode, WeightedEdge>, HashSet<GoapState>> storedEffectStates = new Hashtable<>();

	/**
	 * @param preconditions
	 *            the HashSet of preconditions the node has. Each preconditions
	 *            has to be met before another node can be connected to this
	 *            node.
	 * @param effects
	 *            the HashSet of effects the node has on the graph. Effects get
	 *            added together along the graph to hopefully meet a goalState.
	 */
	GraphNode(HashSet<GoapState> preconditions, HashSet<GoapState> effects) {
		this.preconditions = preconditions;
		this.effects = effects;
	}

	/**
	 * @param goapAction
	 *            the action whose effects and preconditions are being used to
	 *            define the node.
	 */
	GraphNode(GoapAction goapAction) {
		if (goapAction != null) {
			this.preconditions = goapAction.getPreconditions();
			this.effects = goapAction.getEffects();
			this.action = goapAction;
		}
	}

	// -------------------- Functions

	/**
	 * Function for inserting existing GraphNodes values into the current one.
	 * 
	 * @param newGraphNode
	 *            the node whose properties are going to be copied.
	 */
	void overwriteOwnProperties(GraphNode newGraphNode) {
		if (newGraphNode != null) {
			this.action = newGraphNode.action;
			this.preconditions = newGraphNode.preconditions;
			this.effects = newGraphNode.effects;
		}
	}

	/**
	 * Function for adding paths to a node so that the order in which the node
	 * is accessed is saved (Important!). If these would not be stored invalid
	 * orders of actions could be added to the graph as a node can return
	 * multiple access paths!
	 * 
	 * @param pathToPreviousNode
	 *            the path with which the previous node is accessed.
	 * @param newPath
	 *            the path with which the node is accessed.
	 */
	void addGraphPath(WeightedPath<GraphNode, WeightedEdge> pathToPreviousNode,
			WeightedPath<GraphNode, WeightedEdge> newPath) {
		List<GraphNode> newPathNodeList = newPath.getVertexList();
		boolean notInSet = true;

		if (this.pathsToThisNode.isEmpty()) {
			notInSet = true;
		} else {
			for (WeightedPath<GraphNode, WeightedEdge> storedPath : this.pathsToThisNode) {
				List<GraphNode> nodeList = storedPath.getVertexList();
				boolean isSamePath = true;

				for (int i = 0; i < nodeList.size() && isSamePath; i++) {
					if (!nodeList.get(i).equals(newPathNodeList.get(i))) {
						isSamePath = false;
					}
				}

				if (isSamePath) {
					notInSet = false;

					break;
				}
			}
		}

		if (notInSet) {
			this.pathsToThisNode.add(newPath);
			if (newPath.getEndVertex().action != null) {
				this.storedEffectStates.put(newPath, addPathEffectsTogether(pathToPreviousNode, newPath));
			}
		}
	}

	/**
	 * Function for adding all effects in a path together to get the effect at
	 * the last node in the path.
	 * 
	 * @param pathToPreviousNode
	 *            to the previous node so that not all effects need to be added
	 *            together again and again. The reference to this is the key in
	 *            the last elements storedPathEffects HashTable.
	 * @param path
	 *            the path on which all effects are getting added together.
	 * @return the HashSet of effects at the last node in the path.
	 */
	private HashSet<GoapState> addPathEffectsTogether(WeightedPath<GraphNode, WeightedEdge> pathToPreviousNode,
			WeightedPath<GraphNode, WeightedEdge> path) {
		HashSet<GoapState> combinedNodeEffects;
		List<GoapState> statesToBeRemoved = new ArrayList<GoapState>();

		// No path leading to the previous node = node is starting point =>
		// sublist of all effects
		if (pathToPreviousNode == null) {
			combinedNodeEffects = new HashSet<GoapState>(path.getStartVertex().effects);
		} else {
			combinedNodeEffects = new HashSet<GoapState>(
					pathToPreviousNode.getEndVertex().getEffectState(pathToPreviousNode));
		}

		// Mark effects to be removed
		for (GoapState nodeWorldState : combinedNodeEffects) {
			for (GoapState pathNodeEffect : this.effects) {
				if (nodeWorldState.effect.equals(pathNodeEffect.effect)) {
					statesToBeRemoved.add(nodeWorldState);
				}
			}
		}

		// Remove marked effects from the state
		for (GoapState stateToRemove : statesToBeRemoved) {
			combinedNodeEffects.remove(stateToRemove);
		}

		// Add all effects from the current node to the HashSet
		for (GoapState effect : this.effects) {
			combinedNodeEffects.add(effect);
		}
		return combinedNodeEffects;
	}

	// ------------------------------ Getter / Setter

	HashSet<GoapState> getEffectState(WeightedPath<GraphNode, WeightedEdge> pathKey) {
		return this.storedEffectStates.get(pathKey);
	}
}
