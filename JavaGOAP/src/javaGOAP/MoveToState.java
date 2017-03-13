package javaGOAP;

/**
 * MoveToState.java --- State on the FSM Stack
 * 
 * @author P H - 28.01.2017
 */
class MoveToState implements IFSMState {

	GoapAction currentAction;

	/**
	 * @param currentAction
	 *            the action which requires the unit to be in a certain range to
	 *            its target.
	 */
	MoveToState(GoapAction currentAction) {
		this.currentAction = currentAction;
	}

	/**
	 * Move to the target of the currentAction until the unit is in range to
	 * perform the action itself.
	 * 
	 * @see unitControlModule.goapActionTaking.IFSMState#runGoapAction(unitControlModule.goapActionTaking.GoapUnit)
	 */
	@Override
	public boolean runGoapAction(IGoapUnit goapUnit) {
		boolean stillMoving = true;

		if ((this.currentAction.requiresInRange(goapUnit) && this.currentAction.isInRange(goapUnit))
				|| this.currentAction.target == null) {
			stillMoving = false;
		} else {
			goapUnit.moveTo(this.currentAction.target);
		}
		return stillMoving;
	}

}
