package javaGOAP;

import java.util.Queue;

/**
 * RunActionState.java --- State on the FSM Stack
 * 
 * @author P H - 28.01.2017
 */
class RunActionState implements IFSMState {

	private Queue<GoapAction> currentActions;
	private FSM fsm;

	/**
	 * @param fsm
	 *            the FSM on which all states are being stacked.
	 * @param currentActions
	 *            the Queue of actions to be taken in order to archive a goal.
	 */
	RunActionState(FSM fsm, Queue<GoapAction> currentActions) {
		this.fsm = fsm;
		this.currentActions = currentActions;
	}

	// -------------------- Functions

	/**
	 * Cycle trough all actions until an invalid one or the end of the Queue is
	 * reached. A false return type here causes the FSM to pop the state from
	 * its stack.
	 * 
	 * @see unitControlModule.goapActionTaking.IFSMState#runGoapAction(unitControlModule.goapActionTaking.GoapUnit)
	 */
	@Override
	public boolean runGoapAction(IGoapUnit goapUnit) throws Exception {
		boolean workingOnQueue = false;

		try {
			boolean missingAction = true;

			while (missingAction) {
				if (!this.currentActions.isEmpty() && this.currentActions.peek().isDone(goapUnit)) {
					this.currentActions.poll().reset();
				} else {
					missingAction = false;
				}
			}

			if (!this.currentActions.isEmpty()) {
				GoapAction currentAction = this.currentActions.peek();

				// No Exception since handling this is user specific
				if (currentAction.target == null) {
					System.out.println("Target is null! " + currentAction.getClass().getSimpleName());
				}

				if (currentAction.requiresInRange(goapUnit) && !currentAction.isInRange(goapUnit)) {
					this.fsm.pushStack(new MoveToState(currentAction));
				} else if (currentAction.checkProceduralPrecondition(goapUnit)
						&& !currentAction.performAction(goapUnit)) {
					throw new Exception("Action (" + currentAction.getClass().getSimpleName()
							+ ") could not be performed! (proceduralPrecondition=True, performAction=False)");
				}

				workingOnQueue = true;
			}
		} catch (Exception e) {
			e.printStackTrace();

			// throw new Exception();
		}
		return workingOnQueue;
	}

	// ------------------------------ Getter / Setter

	Queue<GoapAction> getCurrentActions() {
		return this.currentActions;
	}
}
