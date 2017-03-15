package javaGOAP.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Path.java --- A Class for displaying a path inside a Graph containing Lists
 * of vertices and edges.
 * 
 * @author P H - 14.03.2017
 *
 */
public class Path<VertexType, EdgeType extends Edge> {
	private List<VertexType> vertexList = new ArrayList<VertexType>();
	private List<EdgeType> edgeList = new ArrayList<EdgeType>();
	private VertexType startVertex = null;
	private VertexType endVertex = null;

	public Path(List<VertexType> vertexList, List<EdgeType> edgeList, VertexType startVertex, VertexType endVertex) {
		this.vertexList = vertexList;
		this.edgeList = edgeList;
		this.startVertex = startVertex;
		this.endVertex = endVertex;
	}

	// -------------------- Functions

	// ------------------------------ Getter / Setter

	/**
	 * @return a List containing all vertices of the Path.
	 */
	public List<VertexType> getVertexList() {
		return vertexList;
	}

	/**
	 * @return a List containing all edges of the Path.
	 */
	public List<EdgeType> getEdgeList() {
		return edgeList;
	}

	public VertexType getStartVertex() {
		return startVertex;
	}

	public VertexType getEndVertex() {
		return endVertex;
	}
}
