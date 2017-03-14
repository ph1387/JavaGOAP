package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import graph.Edge;
import graph.Graph;
import graph.Path;
import graph.PathFactory;

/**
 * GraphTest.java --- Graph TestFile.
 * @author P H - 14.03.2017
 *
 */
public class GraphTest {

	@Test
	public void vertices() {
		int vertexCount = 5;
		Graph<Integer, Edge> g = createBasicTestGraph(vertexCount);
		
		assertNotEquals(null, g);
		assertNotEquals(null, g.getVertices());
		assertFalse(g.getVertices().isEmpty());
		assertEquals(vertexCount, g.getVertices().size());
	}
	
	@Test
	public void edges() {
		int vertexCount = 5;
		int edgeCount = 2;
		Graph<Integer, Edge> g = createBasicConnectedTestGraph(vertexCount, edgeCount);
		
		assertTrue(g.containsEdge(0, 1));
		assertFalse(g.containsEdge(3, 4));
		assertTrue(g.getEdges().size() == edgeCount);
		
		g.removeEdge(0, 1);
		
		assertFalse(g.containsEdge(0, 1));
		assertFalse(g.getEdges().isEmpty());
		assertTrue(g.getEdges().size() == (edgeCount - 1));
	}
	
	public static Graph<Integer, Edge> createBasicTestGraph(int vertexCount) {
		Graph<Integer, Edge> g = new Graph<Integer, Edge>();

		for (int i = 0; i < vertexCount; i++) {
			g.addVertex(i);
		}
		
		return g;
	}
	
	public static Graph<Integer, Edge> createBasicConnectedTestGraph(int vertexCount, int edgeCount) {
		Graph<Integer, Edge> g = createBasicTestGraph(vertexCount);
		
		for(int i = 0; i < edgeCount; i++) {
			g.addEdge(i, i + 1, new Edge());
		}
		return g;
	}
}
