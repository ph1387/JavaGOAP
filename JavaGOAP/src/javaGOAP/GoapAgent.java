package javaGOAP;

import java.util.HashSet;
import java.util.Queue;

/**
 * GoapAgent.java --- The Agent which controls a units actions
 * 
 * @author P H - 28.01.2017
 */
public abstract class GoapAgent
		implements ImportantUnitChangeEventListener, PlanCreatedEventListener, FSMPlanEventListener {

	private FSM fsm = new FSM();
	private IdleState idleState;
	private IGoapUnit assignedGoapUnit;

	/**
	 * @param assignedUnit
	 *            the GoapUnit the agent works with.
	 */
	public GoapAgent(IGoapUnit assignedUnit) {
		this.assignedGoapUnit = assignedUnit;
		this.idleState = new IdleState(this.generatePlannerObject());

		// Only subclasses of the own GoapUnit are able to emit events
		if (this.assignedGoapUnit instanceof GoapUnit) {
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

	/**
	 * Function for subclasses to provide an instance of an planner which is
	 * going to be used to create the action Queue.
	 * 
	 * @return the used planner instance implementing the IGoapPlanner
	 *         interface.
	 */
	protected abstract IGoapPlanner generatePlannerObject();

	// ------------------------------ Getter / Setter

	public IGoapUnit getAssignedGoapUnit() {
		return this.assignedGoapUnit;
	}

	// -------------------- Eventlisteners

	// ------------------------------ IdleState
	@Override
	public void onPlanCreated(Queue<GoapAction> plan) {
		this.assignedGoapUnit.goapPlanFound(plan);

		this.fsm.popStack();
		this.fsm.pushStack(new RunActionState(this.fsm, plan));
	}

	// ------------------------------ GoapUnit
	@Override
	public void onImportantUnitGoalChange(GoapState newGoalState) {
		newGoalState.importance = Integer.MAX_VALUE;

		this.fsm.pushStack(this.idleState);
	}

	@Override
	public void onImportantUnitStackResetChange() {
		HashSet<GoapAction> actions = this.assignedGoapUnit.getAvailableActions();
		
		// Reset all actions of the IGoapUnit.
		for (GoapAction goapAction : actions) {
			goapAction.reset();
		}
		
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
