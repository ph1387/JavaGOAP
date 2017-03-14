package graph;


/**
 * WeightedGraph.java --- A WeightedGraph using WeightedEdgdes as edges.
 * 
 * @author P H - 14.03.2017
 *
 */
public class WeightedGraph<VertexType, EdgeType extends WeightedEdge> extends Graph<VertexType, EdgeType> {

	// -------------------- Functions

	// ------------------------------ Getter / Setter

	public double getEdgeWeight(WeightedEdge edge) {
		return edge.getWeight();
	}

	public void setEdgeWeight(WeightedEdge edge, double weight) {
		edge.setWeight(weight);
	}
}
