package javaGOAP;

import java.util.Queue;

public interface IGoapPlanner {
	public Queue<GoapAction> plan(IGoapUnit goapUnit);
}
