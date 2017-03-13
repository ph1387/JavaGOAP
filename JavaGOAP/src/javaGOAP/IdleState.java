package javaGOAP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

/**
 * IdleState.java --- State on the FSM Stack
 * 
 * @author P H - 28.01.2017
 */
class IdleState implements IFSMState {

	private List<Object> planCreatedListeners = new ArrayList<Object>();

	public IdleState() {

	}

	// -------------------- Functions

	@Override
	public boolean runGoapAction(GoapUnit goapUnit) {
		Queue<GoapAction> plannedQueue = GoapPlanner.plan(goapUnit);

		if (plannedQueue != null) {
			this.dispatchNewPlanCreatedEvent(plannedQueue);
		}

		// Returning false would result in the RunActionState, which gets added
		// to the Stack by the Agent, to be removed.
		return true;
	}

	// -------------------- Events

	// ------------------------------ Plan created
	synchronized void addPlanCreatedListener(Object listener) {
		this.planCreatedListeners.add(listener);
	}

	synchronized void removePlanCreatedListener(Object listener) {
		this.planCreatedListeners.remove(listener);
	}

	private synchronized void dispatchNewPlanCreatedEvent(Queue<GoapAction> plan) {
		for (Object listener : this.planCreatedListeners) {
			((PlanCreatedEventListener) listener).onPlanCreated(plan);
		}
	}
}
