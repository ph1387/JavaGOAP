package javaGOAP.graph;

/**
 * WeightedGraph.java --- A WeightedGraph using WeightedEdges as edges.
 * 
 * @author P H - 14.03.2017
 *
 */
public class DirectedWeightedGraph<VertexType, EdgeType extends WeightedEdge>
		extends DirectedGraph<VertexType, EdgeType> {

	// -------------------- Functions

	// ------------------------------ Getter / Setter

	/**
	 * Function for retrieving the weight of a specific edge inside the
	 * DirectedWeightedGraph.
	 * 
	 * @param edge
	 *            the edge whose weight is being searched for.
	 * @return the weight of the given edge.
	 */
	public double getEdgeWeight(WeightedEdge edge) {
		return edge.getWeight();
	}

	/**
	 * Function for setting an edges weight inside the DirectedWeightedGraph.
	 * 
	 * @param edge
	 *            the edge whose weight is being set.
	 * @param weight
	 *            the weight of the edge.
	 */
	public void setEdgeWeight(WeightedEdge edge, double weight) {
		edge.setWeight(weight);
	}
}
