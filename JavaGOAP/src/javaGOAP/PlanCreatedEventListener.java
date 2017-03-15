package javaGOAP;

import java.util.Queue;

interface PlanCreatedEventListener {
	/**
	 * This event is needed to push real action Queues on the FSM-Stack. Has to
	 * pop the FSM-Stack, since the event fires before the return value of the
	 * state gets checked.
	 * 
	 * @param plan
	 *            the plan that the Planner has created and is ready to be
	 *            executed.
	 */
	public void onPlanCreated(Queue<GoapAction> plan);
}
