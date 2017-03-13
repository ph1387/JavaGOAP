package javaGOAP;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * FSM.java --- FSM for all unit states
 * 
 * @author P H - 28.01.2017
 */
final class FSM {

	private Stack<IFSMState> states = new Stack<IFSMState>();
	private List<Object> planEventListeners = new ArrayList<Object>();

	public FSM() {

	}

	// -------------------- Functions

	/**
	 * Run through all action in the specific states. If an Exception occurs
	 * (mainly in RunActionState) the FSM assumes the plan failed. If an action
	 * state returns false the FSM assumes the plan finished.
	 * 
	 * @param goapUnit
	 *            unit whose actions are getting cycled.
	 */
	void update(IGoapUnit goapUnit) {
		try {
			if (!this.states.isEmpty() && !this.states.peek().runGoapAction(goapUnit)) {
				IFSMState state = this.states.pop();

				if (state instanceof RunActionState) {
					this.dispatchNewPlanFinishedEvent();
				}
			}
		} catch (Exception e) {
			IFSMState state = this.states.pop();

			if (state instanceof RunActionState) {
				this.dispatchNewPlanFailedEvent(((RunActionState) state).getCurrentActions());
			}

			// TODO: Possible Change: Add System.out
			e.printStackTrace();
		}
	}

	void pushStack(IFSMState state) {
		this.states.push(state);
	}

	void popStack() {
		this.states.pop();
	}

	void clearStack() {
		this.states.clear();
	}

	boolean hasStates() {
		return !(this.states.isEmpty());
	}

	// -------------------- Events

	synchronized void addPlanEventListener(Object listener) {
		this.planEventListeners.add(listener);
	}

	synchronized void removePlanEventListener(Object listener) {
		this.planEventListeners.remove(listener);
	}

	private synchronized void dispatchNewPlanFailedEvent(Queue<GoapAction> actions) {
		for (Object listener : this.planEventListeners) {
			((FSMPlanEventListener) listener).onPlanFailed(actions);
		}
	}

	private synchronized void dispatchNewPlanFinishedEvent() {
		for (Object listener : this.planEventListeners) {
			((FSMPlanEventListener) listener).onPlanFinished();
		}
	}
}
