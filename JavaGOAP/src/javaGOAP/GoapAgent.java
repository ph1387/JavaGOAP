package javaGOAP;

import java.util.Queue;

/**
 * GoapAgent.java --- The Agent which controls a units actions
 * 
 * @author P H - 28.01.2017
 */
public class GoapAgent implements ImportantUnitChangeEventListener, PlanCreatedEventListener, FSMPlanEventListener {

	private FSM fsm = new FSM();
	private IdleState idleState = new IdleState();
	private IGoapUnit assignedGoapUnit;

	/**
	 * @param assignedUnit
	 *            the GoapUnit the agent works with.
	 */
	public GoapAgent(IGoapUnit assignedUnit) {
		this.assignedGoapUnit = assignedUnit;

		// Only subclasses of the own GoapUnit are able to emit events
		if(this.assignedGoapUnit instanceof GoapUnit) {
			((GoapUnit) this.assignedGoapUnit).addImportantUnitGoalChangeListener(this);
		}
		this.idleState.addPlanCreatedListener(this);
		this.fsm.addPlanEventListener(this);
	}

	// -------------------- Functions

	public void update() {
		if (!this.fsm.hasStates()) {
			this.fsm.pushStack(this.idleState);
		}

		this.assignedGoapUnit.update();
		this.fsm.update(this.assignedGoapUnit);
	}

	// ------------------------------ Getter / Setter

	public IGoapUnit getAssignedGoapUnit() {
		return this.assignedGoapUnit;
	}

	// -------------------- Eventlisteners

	// ------------------------------ IdleState
	/**
	 * This event is needed to push real action Queues on the FSM-Stack. Has to
	 * pop the FSM-Stack, since the event fires before the return value of the
	 * state gets checked.
	 * 
	 * @see unitControlModule.goapActionTaking.PlanCreatedEventListener#onPlanCreated(java.util.Queue)
	 */
	@Override
	public void onPlanCreated(Queue<GoapAction> plan) {
		this.assignedGoapUnit.goapPlanFound(plan);
		
		this.fsm.popStack();
		this.fsm.pushStack(new RunActionState(this.fsm, plan));
	}

	// ------------------------------ GoapUnit
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
	 * @see unitControlModule.goapActionTaking.ImportantUnitChangeEventListener#onImportantUnitGoalChange(unitControlModule.goapActionTaking.GoapState)
	 */
	@Override
	public void onImportantUnitGoalChange(GoapState newGoalState) {
		newGoalState.importance = Integer.MAX_VALUE;

		this.fsm.pushStack(this.idleState);
	}

	@Override
	public void onImportantUnitStackResetChange() {
		this.fsm.clearStack();
		this.fsm.pushStack(this.idleState);
	}

	// ------------------------------ FSM
	@Override
	public void onPlanFailed(Queue<GoapAction> actions) {
		this.assignedGoapUnit.goapPlanFailed(actions);
	}

	@Override
	public void onPlanFinished() {
		this.assignedGoapUnit.goapPlanFinished();
	}
}
