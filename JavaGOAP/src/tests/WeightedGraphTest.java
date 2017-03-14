package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import graph.WeightedEdge;
import graph.WeightedGraph;

/**
 * WeightedGraphTest.java --- WeightedGraph TestFile. 
 * @author P H - 14.03.2017
 *
 */
public class WeightedGraphTest {
	
	@Test
	public void weights() {
		int vertexCount = 5;
		int edgeCount = 2;
		WeightedGraph<Integer, WeightedEdge> g = createBasicConnectedTestWeightedGraph(vertexCount, edgeCount);
		
		assertTrue(g.getEdgeWeight(g.getEdge(0, 1)) == 0);
	}
	
	public static WeightedGraph<Integer, WeightedEdge> createBasicTestWeightedGraph(int vertexCount) {
		WeightedGraph<Integer, WeightedEdge> g = new WeightedGraph<Integer, WeightedEdge>();

		for (int i = 0; i < vertexCount; i++) {
			g.addVertex(i);
		}
		
		return g;
	}
	
	public static WeightedGraph<Integer, WeightedEdge> createBasicConnectedTestWeightedGraph(int vertexCount, int edgeCount) {
		WeightedGraph<Integer, WeightedEdge> g = createBasicTestWeightedGraph(vertexCount);
		
		for(int i = 0; i < edgeCount; i++) {
			g.addEdge(i, i + 1, new WeightedEdge());
		}
		return g;
	}
}
