package javaGOAP;

import java.util.Queue;

/**
 * IGoapPlanner.java --- Interface which all GoapPlanners assigned to GoapAgents
 * have to implement.
 * 
 * @author P H - 12.03.2017
 */
public interface IGoapPlanner {
	/**
	 * Function for planning an GoapAction Queue.
	 * 
	 * @param goapUnit
	 *            the GoapUnit for which an Action Queue is being created.
	 * @return a created GoapAction Queue or null, if no Actions and goals
	 *         match.
	 */
	public Queue<GoapAction> plan(IGoapUnit goapUnit);
}
