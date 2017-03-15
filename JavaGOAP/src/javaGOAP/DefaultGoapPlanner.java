package javaGOAP;

import javaGOAP.graph.DirectedWeightedGraph;
import javaGOAP.graph.IWeightedGraph;
import javaGOAP.graph.WeightedEdge;

/**
 * DefaultGoapPlanner.java --- The default implementation of the GoapPlanner.
 * 
 * @author P H - 15.03.2017
 *
 */
public class DefaultGoapPlanner extends GoapPlanner {

	@Override
	protected <EdgeType extends WeightedEdge> IWeightedGraph<GraphNode, EdgeType> generateGraphObject() {
		return new DirectedWeightedGraph<GraphNode, EdgeType>();
	}
}
