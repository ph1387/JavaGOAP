package javaGOAP;

import java.util.HashSet;

/**
 * GoapAction.java --- Superclass for all actions a unit can perform
 * 
 * @author P H - 28.01.2017
 */
public abstract class GoapAction {

	protected Object target = null;

	private HashSet<GoapState> preconditions = new HashSet<GoapState>();
	private HashSet<GoapState> effects = new HashSet<GoapState>();

	/**
	 * @param target
	 *            the target of the action. Since "Object" is being used this is
	 *            NOT type safe!
	 */
	public GoapAction(Object target) {
		this.target = target;
	}

	// -------------------- Functions

	/**
	 * Checks if the current action of the GoapAction Queue is finished. Gets
	 * called until it returns true.
	 *
	 * @param goapUnit
	 *            the unit the action is checked for.
	 * @return true or false depending on the success of the action. Returning
	 *         true causes the swap to the next action in the Queue.
	 */
	protected abstract boolean isDone(GoapUnit goapUnit);

	/**
	 * Gets called when the action is going to be executed by the Unit.
	 * 
	 * @param goapUnit
	 *            the GoapUnit that is trying to execute the action.
	 * @return true or false depending if the action was successful.
	 */
	protected abstract boolean performAction(GoapUnit goapUnit);

	/**
	 * This function will be called for each GoapAction in the generation of
	 * each Graph to determine the cost for each node in the graph. The two
	 * functions called in this function have to be implemented by the Subclass
	 * to get the sum of both costs. Differentiating between the base cost and
	 * the cost relative to the target gives a proper representation of the work
	 * the unit has to do i.e. if it has to travel a large distance to reach its
	 * target.
	 *
	 * @param goapUnit
	 *            the unit whose action cost is being calculated.
	 * @return the calculated action cost.
	 */
	float generateCost(GoapUnit goapUnit) {
		return generateBaseCost(goapUnit) + generateCostRelativeToTarget(goapUnit);
	}

	/**
	 * Defines the base cost of the action.
	 * 
	 * @param goapUnit
	 *            the unit the action is being executed from.
	 * @return the base cost of the action which is added to the cost relative
	 *         to the target.
	 */
	protected abstract float generateBaseCost(GoapUnit goapUnit);

	/**
	 * Defines the relative cost of the action.
	 * 
	 * @param goapUnit
	 *            the unit the action is being executed from.
	 * @return the relative cost of the action in relation to the current
	 *         target, which is added to the base cost.
	 */
	protected abstract float generateCostRelativeToTarget(GoapUnit goapUnit);

	/**
	 * Gets called to determine if the preconditions of an action are met. If
	 * they are not, the action will not be taken in consideration for the
	 * generation of the action graph.
	 * 
	 * @param goapUnit
	 *            the unit the action is being executed from.
	 * @return true or false depending if the action can be taken in the first
	 *         place.
	 */
	protected abstract boolean checkProceduralPrecondition(GoapUnit goapUnit);

	/**
	 * Defines if the unit needs to be in a certain range in relation to the
	 * target to execute the action.
	 * 
	 * @param goapUnit
	 *            the unit the action is being executed from.
	 * @return true or false depending if the action requires the unit to be in
	 *         a certain range near the target.
	 */
	protected abstract boolean requiresInRange(GoapUnit goapUnit);

	/**
	 * Function to determine if the unit is in a certain range. Only gets called
	 * if the action requires to be in range relative to the target.
	 * 
	 * @see #requiresInRange(GoapUnit goapUnit)
	 * 
	 * @param goapUnit
	 *            the unit the action is being executed from.
	 * @return true or false depending if the unit is in range to execute the
	 *         action.
	 */
	protected abstract boolean isInRange(GoapUnit goapUnit);

	// ------------------------------ Getter / Setter

	protected HashSet<GoapState> getPreconditions() {
		return this.preconditions;
	}

	protected HashSet<GoapState> getEffects() {
		return this.effects;
	}

	// ------------------------------ Others

	// ------------------------------ Preconditions
	/**
	 * Overloaded function for convenience.
	 * 
	 * @see #addPrecondition(GoapState precondition)
	 */
	protected void addPrecondition(int importance, String effect, Object value) {
		this.addPrecondition(new GoapState(importance, effect, value));
	}

	/**
	 * Add a precondition, which is not already in the HashSet.
	 * 
	 * @param precondition
	 *            which is going to be added to the action.
	 */
	protected void addPrecondition(GoapState precondition) {
		boolean alreadyInList = false;

		for (GoapState goapState : this.preconditions) {
			if (goapState.equals(precondition)) {
				alreadyInList = true;
			}
		}

		if (!alreadyInList) {
			this.preconditions.add(precondition);
		}
	}

	/**
	 * Overloaded function for convenience.
	 * 
	 * @see #removePrecondition(String preconditionEffect)
	 */
	protected boolean removePrecondition(GoapState precondition) {
		return this.removePrecondition(precondition.effect);
	}

	/**
	 * Remove a precondition from the HashSet.
	 * 
	 * @param preconditionEffect
	 *            the effect which is going to be removed.
	 * @return true or false depending if the precondition was removed.
	 */
	protected boolean removePrecondition(String preconditionEffect) {
		GoapState stateToBeRemoved = null;

		for (GoapState goapState : this.effects) {
			if (goapState.effect.equals(preconditionEffect)) {
				stateToBeRemoved = goapState;
			}
		}

		if (stateToBeRemoved != null) {
			this.preconditions.remove(stateToBeRemoved);
			return true;
		} else {
			return false;
		}
	}

	// ------------------------------ Effects

	/**
	 * Overloaded function for convenience.
	 * 
	 * @see #addEffect(GoapState effect)
	 */
	protected void addEffect(int importance, String effect, Object value) {
		this.addEffect(new GoapState(importance, effect, value));
	}

	/**
	 * Add a effect, which is not already in the HashSet
	 * 
	 * @param effect
	 *            the effect which is going to be added to the action.
	 */
	protected void addEffect(GoapState effect) {
		boolean alreadyInList = false;

		for (GoapState goapState : this.effects) {
			if (goapState.equals(effect)) {
				alreadyInList = true;
			}
		}

		if (!alreadyInList) {
			this.effects.add(effect);
		}
	}

	/**
	 * Overloaded function for convenience.
	 * 
	 * @see #removeEffect(String effectEffect)
	 */
	protected boolean removeEffect(GoapState effect) {
		return this.removeEffect(effect.effect);
	}

	/**
	 * Remove a effect from the HashSet.
	 * 
	 * @param effectEffect
	 *            the effect which is going to be removed.
	 * @return true or false depending if the effect was removed.
	 */
	protected boolean removeEffect(String effectEffect) {
		GoapState stateToBeRemoved = null;

		for (GoapState goapState : this.effects) {
			if (goapState.effect.equals(effectEffect)) {
				stateToBeRemoved = goapState;
			}
		}

		if (stateToBeRemoved != null) {
			this.effects.remove(stateToBeRemoved);
			return true;
		} else {
			return false;
		}
	}
}
