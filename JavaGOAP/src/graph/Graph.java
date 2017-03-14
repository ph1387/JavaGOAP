package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

/**
 * Graph.java --- Superclass for all Graphs.
 * 
 * @author P H - 14.03.2017
 *
 */
public class Graph<VertexType, EdgeType extends Edge> {
	protected HashMap<VertexType, HashMap<VertexType, EdgeType>> graphContent = new HashMap<VertexType, HashMap<VertexType, EdgeType>>();

	public Graph() {

	}

	public Graph(HashSet<VertexType> vertices) {
		for (VertexType vertex : vertices) {
			this.graphContent.put(vertex, new HashMap<VertexType, EdgeType>());
		}
	}

	// -------------------- Functions

	public void addVertex(VertexType vertex) {
		this.graphContent.put(vertex, new HashMap<VertexType, EdgeType>());
	}

	public void addEdge(VertexType firstVertex, VertexType secondVertex, EdgeType edge) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		connectionsToVertex.put(secondVertex, edge);
	}

	public boolean containsEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		if (connectionsToVertex.containsKey(secondVertex)) {
			return true;
		}
		return false;
	}

	public void removeEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		if (connectionsToVertex.containsKey(secondVertex)) {
			connectionsToVertex.remove(secondVertex);
		}
	}

	// ------------------------------ Getter / Setter

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

	public EdgeType getEdge(VertexType firstVertex, VertexType secondVertex) {
		HashMap<VertexType, EdgeType> connectionsToVertex = this.graphContent.get(firstVertex);

		return connectionsToVertex.getOrDefault(secondVertex, null);
	}
}
