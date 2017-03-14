package graph;

import java.util.List;

/**
 * WeightedPath.java --- A Class for displaying a weighted path inside a Graph
 * containing Lists of vertices and edges.
 * 
 * @author P H - 14.03.2017
 *
 */
public class WeightedPath<VertexType, EdgeType extends WeightedEdge> extends Path<VertexType, EdgeType> {
	private double totalWeight = 0.;

	public WeightedPath(List<VertexType> vertexList, List<EdgeType> edgeList, VertexType startVertex, VertexType endVertex) {
		super(vertexList, edgeList, startVertex, endVertex);

		for (EdgeType edge : edgeList) {
			this.totalWeight += edge.getWeight();
		}
	}
	// -------------------- Functions

	public double getTotalWeight() {
		return totalWeight;
	}
}
