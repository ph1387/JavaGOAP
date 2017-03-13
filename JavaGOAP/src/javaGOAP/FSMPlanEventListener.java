package javaGOAP;

import java.util.Queue;

interface FSMPlanEventListener {
	/**
	 * Gets called when a RunActionState on the FSM throws an exception.
	 *
	 * @param actions
	 *            the rest of the action Queue which failed to execute.
	 */
	public void onPlanFailed(Queue<GoapAction> actions);

	/**
	 * Gets called when a RunActionState on the FSM returns true and therefore
	 * signals that it is finished.
	 *
	 */
	public void onPlanFinished();
}
