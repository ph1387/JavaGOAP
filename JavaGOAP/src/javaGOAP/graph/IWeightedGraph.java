package javaGOAP.graph;

/**
 * IWeightedGraph.java --- Interface for a basic WeightedGraph.
 * 
 * @author P H - 12.03.2017
 */
public interface IWeightedGraph<VertexType, EdgeType> extends IGraph<VertexType, EdgeType> {
	/**
	 * Function for retrieving the weight of a specific edge inside the
	 * IWeightedGraphs implementer.
	 * 
	 * @param edge
	 *            the edge whose weight is being searched for.
	 * @return the weight of the given edge.
	 */
	public double getEdgeWeight(WeightedEdge edge);

	/**
	 * Function for setting an edges weight inside the IWeightedGraphs
	 * implementer.
	 * 
	 * @param edge
	 *            the edge whose weight is being set.
	 * @param weight
	 *            the weight of the edge.
	 */
	public void setEdgeWeight(WeightedEdge edge, double weight);
}
