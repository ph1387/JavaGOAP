package javaGOAP;

import java.util.Queue;

/**
 * IGoapPlanner.java --- Interface which all GoapPlanners assigned to GoapAgents
 * have to implement.
 * 
 * @author P H - 12.03.2017
 */
public interface IGoapPlanner {
	public Queue<GoapAction> plan(IGoapUnit goapUnit);
}
