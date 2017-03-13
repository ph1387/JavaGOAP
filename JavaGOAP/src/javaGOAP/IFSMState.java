package javaGOAP;

interface IFSMState {
	/**
	 * Returning false results in the removing of the implementers instance on
	 * the stack of the FSM. True signalizes that the running actions are valid
	 * and not finished / obsolete. Catches Exceptions for the FSM State to
	 * check if a plan has failed.
	 * 
	 * @param goapUnit
	 *            the unit which action is being run.
	 * @return true or false depending if the action is still running (true) or
	 *         is completed (false).
	 */
	public boolean runGoapAction(GoapUnit goapUnit) throws Exception;
}
