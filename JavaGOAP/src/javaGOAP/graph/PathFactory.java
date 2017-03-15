package javaGOAP.graph;

// TODO: UML
import java.util.List;

/**
 * PathFactory.java --- Factory for generating Path objects. Checks the provided
 * information against a provided Graph.
 * 
 * @author P H - 14.03.2017
 *
 */
public class PathFactory {

	// -------------------- Functions

	/**
	 * Function for generating a simple Path. The given information are being
	 * checked against the given Graph.
	 * 
	 * @param IGraph
	 *            the Graph the information are being checked against.
	 * @param start
	 *            the starting vertex of the Path.
	 * @param end
	 *            the end vertex of the Path.
	 * @param vertexList
	 *            the List of all vertices of the Path. These elements also get
	 *            checked to secure their conformity with the given Graph.
	 * @param edgeList
	 *            the List of all edges of the Path. These elements also get
	 *            checked to secure their conformity with the given Graph.
	 * @param <VertexType>
	 *            the type of vertex being used inside the Path.
	 * @param <EdgeType>
	 *            the type of Edge being used to connect all vertices inside the
	 *            Path.
	 * @return a Path leading from one point inside the Graph to another one.
	 */
	public static <VertexType, EdgeType extends Edge> Path<VertexType, EdgeType> generatePath(
			IGraph<VertexType, EdgeType> graph, VertexType start, VertexType end, List<VertexType> vertexList,
			List<EdgeType> edgeList) {
		if (validateStartAndEnd(start, end, vertexList) && validateConnections(graph, vertexList, edgeList)) {
			return new Path<VertexType, EdgeType>(vertexList, edgeList, start, end);
		} else {
			return null;
		}
	}

	/**
	 * Function for generating a WeightedPath. The given information are being
	 * checked against the given Graph.
	 * 
	 * @param IGraph
	 *            the Graph the information are being checked against.
	 * @param start
	 *            the starting vertex of the WeightedPath.
	 * @param end
	 *            the end vertex of the WeightedPath.
	 * @param vertexList
	 *            the List of all vertices of the WeightedPath. These elements
	 *            also get checked to secure their conformity with the given
	 *            Graph.
	 * @param edgeList
	 *            the List of all edges of the WeightedPath. These elements also
	 *            get checked to secure their conformity with the given Graph.
	 * @param <VertexType>
	 *            the type of vertex being used inside the DirectedWeightedPath.
	 * @param <EdgeType>
	 *            the type of Edge being used to connect all vertices inside the
	 *            DirectedWeightedPath.
	 * @return a WeightedPath leading from one point inside the Graph to another
	 *         one.
	 */
	public static <VertexType, EdgeType extends WeightedEdge> WeightedPath<VertexType, EdgeType> generateWeightedPath(
			IGraph<VertexType, EdgeType> graph, VertexType start, VertexType end,
			List<VertexType> vertexList, List<EdgeType> edgeList) {
		if (validateStartAndEnd(start, end, vertexList) && validateConnections(graph, vertexList, edgeList)) {
			return new WeightedPath<VertexType, EdgeType>(vertexList, edgeList, start, end);
		} else {
			return null;
		}
	}

	/**
	 * Function for validating the given start and end vertices.
	 * 
	 * @param start
	 *            the provided start vertex.
	 * @param end
	 *            the provided end vertex.
	 * @param vertexList
	 *            the List of all vertices.
	 * @param <VertexType>
	 *            the type of vertex being used inside the Graph / Path.
	 * @return true or false depending if the provided vertices are indeed the
	 *         start and the end vertices.
	 */
	protected static <VertexType> boolean validateStartAndEnd(VertexType start, VertexType end,
			List<VertexType> vertexList) {
		return vertexList.contains(start) && vertexList.contains(end) && vertexList.get(0).equals(start)
				&& vertexList.get(vertexList.size() - 1).equals(end);
	}

	/**
	 * Function for validating all vertices and edges of the given Lists.
	 * 
	 * @param IGraph
	 *            the graph the information is being checked against.
	 * @param vertexList
	 *            the List of all vertices of the Path being created.
	 * @param edgeList
	 *            the List of all edges of the Path being created.
	 * @param <VertexType>
	 *            the type of vertex being used inside the Graph / Path.
	 * @param <EdgeType>
	 *            the type of Edge being used to connect all vertices inside the
	 *            Graph / Path.
	 * @return true or false depending if the provided Lists match the given
	 *         Graph.
	 */
	protected static <VertexType, EdgeType extends Edge> boolean validateConnections(
			IGraph<VertexType, EdgeType> graph, List<VertexType> vertexList, List<EdgeType> edgeList) {
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
