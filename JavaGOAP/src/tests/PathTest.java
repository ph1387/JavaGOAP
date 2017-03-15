package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javaGOAP.graph.DirectedGraph;
import javaGOAP.graph.Edge;
import javaGOAP.graph.Path;
import javaGOAP.graph.PathFactory;

/**
 * PathTest.java --- Path TestFile.
 * 
 * @author P H - 14.03.2017
 *
 */
public class PathTest {

	@Test
	public void creation() {
		int vertexCount = 5;
		int edgeCount = 3;
		Path<Integer, Edge> p = createBasicTestPath(vertexCount, edgeCount);

		assertNotEquals(null, p);
		assertTrue(p.getVertexList().size() > 0);
		assertTrue(p.getVertexList().size() == (edgeCount + 1));
		assertTrue(p.getEdgeList().size() > 0);
		assertTrue(p.getEdgeList().size() == edgeCount);
		assertEquals(new Integer(0), p.getStartVertex());
		assertEquals(new Integer(edgeCount), p.getEndVertex());
	}

	public static Path<Integer, Edge> createBasicTestPath(int vertexCount, int edgeCount) {
		DirectedGraph<Integer, Edge> g = DirectedGraphTest.createBasicConnectedTestGraph(vertexCount, edgeCount);

		// Vertices and edges retrieved with a breadthSearch or DepthSearch
		// would be better / ideal.
		List<Integer> vertices = new ArrayList<Integer>();
		List<Edge> edges = new ArrayList<Edge>();

		for (int i = 0; i <= edgeCount; i++) {
			if (i < edgeCount) {
				edges.add(g.getEdge(i, i + 1));
			}
			vertices.add(i);
		}

		return PathFactory.generatePath(g, vertices.get(0), vertices.get(vertices.size() - 1), vertices, edges);
	}
}
