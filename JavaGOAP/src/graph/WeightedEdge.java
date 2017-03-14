package graph;

/**
 * WeightedEdge.java --- A WeightedEdge for a Graph.
 * @author P H - 14.03.2017
 *
 */
public class WeightedEdge extends Edge {
	private double weight = 0.;
	
	public WeightedEdge() {
		
	}
	
	public WeightedEdge(double weight) {
		this.weight = weight;
	}

	// -------------------- Functions

	// ------------------------------ Getter / Setter

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
