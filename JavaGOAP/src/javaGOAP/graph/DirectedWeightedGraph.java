package javaGOAP.graph;

/**
 * DirectedWeightedGraph.java --- A DirectedWeightedGraph using WeightedEdges as
 * edges.
 * 
 * @author P H - 14.03.2017
 *
 */
public class DirectedWeightedGraph<VertexType, EdgeType extends WeightedEdge>
		extends DirectedGraph<VertexType, EdgeType> implements IWeightedGraph<VertexType, EdgeType> {

	// -------------------- Functions

	// ------------------------------ Getter / Setter

	@Override
	public double getEdgeWeight(WeightedEdge edge) {
		return edge.getWeight();
	}

	@Override
	public void setEdgeWeight(WeightedEdge edge, double weight) {
		edge.setWeight(weight);
	}
}
