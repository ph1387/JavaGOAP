package graph;

import java.util.List;

/**
 * PathFactory.java --- Factory for generating Path objects.
 * 
 * @author P H - 14.03.2017
 *
 */
public class PathFactory {

	// -------------------- Functions

	public static <VertexType, EdgeType extends Edge> Path<VertexType, EdgeType> generatePath(
			Graph<VertexType, EdgeType> graph, VertexType start, VertexType end, List<VertexType> vertexList,
			List<EdgeType> edgeList) {
		if (validateStartAndEnd(start, end, vertexList) && validateConnections(graph, vertexList, edgeList)) {
			return new Path<VertexType, EdgeType>(vertexList, edgeList, start, end);
		} else {
			return null;
		}
	}

	public static <VertexType, EdgeType extends WeightedEdge> Path<VertexType, EdgeType> generateWeightedPath(
			Graph<VertexType, EdgeType> graph, VertexType start, VertexType end, List<VertexType> vertexList,
			List<EdgeType> edgeList) {
		if (validateStartAndEnd(start, end, vertexList) && validateConnections(graph, vertexList, edgeList)) {
			return new WeightedPath<VertexType, EdgeType>(vertexList, edgeList, start, end);
		} else {
			return null;
		}
	}

	protected static <VertexType> boolean validateStartAndEnd(VertexType start, VertexType end,
			List<VertexType> vertexList) {
		return vertexList.contains(start) && vertexList.contains(end) && vertexList.get(0).equals(start)
				&& vertexList.get(vertexList.size() - 1).equals(end);
	}

	protected static <VertexType, EdgeType extends Edge> boolean validateConnections(Graph<VertexType, EdgeType> graph,
			List<VertexType> vertexList, List<EdgeType> edgeList) {
		boolean success = true;
		VertexType previousVertex = null;

		for (VertexType vertex : vertexList) {
			if (previousVertex != null && !graph.containsEdge(previousVertex, vertex)) {
				success = false;
				break;
			}

			previousVertex = vertex;
		}
		return success;
	}
}
