package javaGOAP.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

/**
 * Graph.java --- Superclass for all Graphs.
 * 
 * @author P H - 14.03.2017
 *
 */
public class DirectedGraph<VertexType, EdgeType extends Edge> {
	protected HashMap<VertexType, HashMap<VertexType, EdgeType>> graphContent = new HashMap<VertexType, HashMap<VertexType, EdgeType>>();

	public DirectedGraph() {

	}

	/**
	 * @param vertices
	 *            a HashSet of all initial vertices of the Graph.
	 */
	public DirectedGraph(HashSet<VertexType> vertices) {
		for (VertexType vertex : vertices) {
			this.graphContent.put(vertex, new HashMap<VertexType, EdgeType>());
		}
	}

	// -------------------- Functions

	/**
	 * Function for adding a vertex to the Graph.
	 * 
	 * @param vertex
	 *            the vertex being added.
	 */
	public void addVertex(VertexType vertex) {
		this.graphContent.put(vertex, new HashMap<VertexType, EdgeType>());
	}

	/**
	 * Function for adding a edge to the Graph.
	 * 
	 * @param firstVertex
	 *            the vertex from which the edge is coming from.
	 * @param secondVertex
	 *            the vertex the edge is going to.
	 * @param edge
	 *            the edge itself that is going to be added.
	 */
	public void addEdge(VertexType firstVertex, VertexType secondVertex, EdgeType edge) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		connectionsToVertex.put(secondVertex, edge);
	}

	/**
	 * Function to testing if an edge exists inside a Graph.
	 * 
	 * @param firstVertex
	 *            the vertex from which the edge is coming from.
	 * @param secondVertex
	 *            the vertex the edge is going to.
	 * @return true or false depending if the edge exists.
	 */
	public boolean containsEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		if (connectionsToVertex.containsKey(secondVertex)) {
			return true;
		}
		return false;
	}

	/**
	 * Function for removing an edge from a Graph.
	 * 
	 * @param firstVertex
	 *            the vertex from which the edge is coming from.
	 * @param secondVertex
	 *            the vertex the edge is going to.
	 */
	public void removeEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		if (connectionsToVertex.containsKey(secondVertex)) {
			connectionsToVertex.remove(secondVertex);
		}
	}

	// ------------------------------ Getter / Setter

	/**
	 * Function for retrieving all vertices inside the Graph.
	 * 
	 * @return all vertices inside the Graph.
	 */
	public HashSet<VertexType> getVertices() {
		final HashSet<VertexType> vertices = new HashSet<>();

		this.graphContent.forEach(new BiConsumer<VertexType, HashMap<VertexType, EdgeType>>() {

			@Override
			public void accept(VertexType t, HashMap<VertexType, EdgeType> u) {
				vertices.add(t);
			}
		});
		return vertices;
	}

	/**
	 * Function for retrieving all edges inside the Graph.
	 * 
	 * @return all edges inside the Graph.
	 */
	public HashSet<EdgeType> getEdges() {
		final HashSet<VertexType> vertices = this.getVertices();
		final HashSet<EdgeType> edges = new HashSet<>();

		for (VertexType vertex : vertices) {
			HashMap<VertexType, EdgeType> connections = this.graphContent.get(vertex);

			connections.forEach(new BiConsumer<VertexType, EdgeType>() {

				@Override
				public void accept(VertexType t, EdgeType u) {
					edges.add(u);
				}
			});
		}
		return edges;
	}

	/**
	 * Function for retrieving a specific edge in the Graph.
	 * 
	 * @param firstVertex
	 *            the vertex from which the edge is coming from.
	 * @param secondVertex
	 *            the vertex the edge is going to.
	 * @return the desired edge or null, if none is found.
	 */
	public EdgeType getEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		return connectionsToVertex.getOrDefault(secondVertex, null);
	}
}
