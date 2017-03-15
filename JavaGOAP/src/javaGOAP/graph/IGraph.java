package javaGOAP.graph;

import java.util.HashSet;

/**
 * IGraph.java --- Interface for a basic Graph.
 * 
 * @author P H - 15.03.2017
 */
public interface IGraph<VertexType, EdgeType> {
	/**
	 * Function for adding a vertex to the Graph.
	 * 
	 * @param vertex
	 *            the vertex being added.
	 */
	public void addVertex(VertexType vertex);

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
	public void addEdge(VertexType firstVertex, VertexType secondVertex, EdgeType edge);

	/**
	 * Function to testing if an edge exists inside a Graph.
	 * 
	 * @param firstVertex
	 *            the vertex from which the edge is coming from.
	 * @param secondVertex
	 *            the vertex the edge is going to.
	 * @return true or false depending if the edge exists.
	 */
	public boolean containsEdge(VertexType firstVertex, VertexType secondVertex);

	/**
	 * Function for removing an edge from a Graph.
	 * 
	 * @param firstVertex
	 *            the vertex from which the edge is coming from.
	 * @param secondVertex
	 *            the vertex the edge is going to.
	 */
	public void removeEdge(VertexType firstVertex, VertexType secondVertex);

	// ------------------------------ Getter / Setter

	/**
	 * Function for retrieving all vertices inside the Graph.
	 * 
	 * @return all vertices inside the Graph.
	 */
	public HashSet<VertexType> getVertices();

	/**
	 * Function for retrieving all edges inside the Graph.
	 * 
	 * @return all edges inside the Graph.
	 */
	public HashSet<EdgeType> getEdges();

	/**
	 * Function for retrieving a specific edge in the Graph.
	 * 
	 * @param firstVertex
	 *            the vertex from which the edge is coming from.
	 * @param secondVertex
	 *            the vertex the edge is going to.
	 * @return the desired edge or null, if none is found.
	 */
	public EdgeType getEdge(VertexType firstVertex, VertexType secondVertex);
}
