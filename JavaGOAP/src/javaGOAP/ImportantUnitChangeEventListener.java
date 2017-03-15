package javaGOAP;

interface ImportantUnitChangeEventListener {
	/**
	 * This event is needed to change a current goal to a new one, while keeping
	 * the old one on the FSM-Stack for its later execution. The importance is
	 * set to the highest possible value to ensure that the given goal is the
	 * main one of the unit. This causes the Idle Stack to create a Queue
	 * specifically for this GoapState, which is empty if no valid Queue is
	 * found (null not possible since that would result in the IdleState to try
	 * until one is found). The empty Queue causes the unit to proceed with its
	 * previous action.
	 * 
	 * @param newGoalState
	 *            the new goal that the GoapUnit is going to accomplish.
	 */
	public void onImportantUnitGoalChange(GoapState newGoalState);

	/**
	 * Event emitted by the GoapUnit if the Actions on the FSM-Stack must reset.
	 */
	public void onImportantUnitStackResetChange();
}
