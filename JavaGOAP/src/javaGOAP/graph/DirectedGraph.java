package javaGOAP.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

/**
 * DirectedGraph.java --- Superclass for all DirectedGraphs.
 * 
 * @author P H - 14.03.2017
 *
 */
public class DirectedGraph<VertexType, EdgeType extends Edge> implements IGraph<VertexType, EdgeType> {
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

	@Override
	public void addVertex(VertexType vertex) {
		this.graphContent.put(vertex, new HashMap<VertexType, EdgeType>());
	}

	@Override
	public void addEdge(VertexType firstVertex, VertexType secondVertex, EdgeType edge) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		connectionsToVertex.put(secondVertex, edge);
	}

	@Override
	public boolean containsEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		if (connectionsToVertex.containsKey(secondVertex)) {
			return true;
		}
		return false;
	}

	@Override
	public void removeEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		if (connectionsToVertex.containsKey(secondVertex)) {
			connectionsToVertex.remove(secondVertex);
		}
	}

	// ------------------------------ Getter / Setter

	@Override
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

	@Override
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

	@Override
	public EdgeType getEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		return connectionsToVertex.getOrDefault(secondVertex, null);
	}
}
