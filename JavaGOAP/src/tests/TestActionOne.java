package tests;


import javaGOAP.GoapAction;
import javaGOAP.GoapState;
import javaGOAP.IGoapUnit;

/**
 * TestActionOne.java --- First TestAction. 
 * @author P H - 13.03.2017
 *
 */
public class TestActionOne extends GoapAction {

	public TestActionOne(Object target) {
		super(target);
		
		this.addPrecondition(new GoapState(0, "goal", false));
		this.addEffect(new GoapState(0, "step", true));
	}

	@Override
	protected boolean isDone(IGoapUnit goapUnit) {
		return false;
	}

	@Override
	protected boolean performAction(IGoapUnit goapUnit) {
		return false;
	}

	@Override
	protected float generateBaseCost(IGoapUnit goapUnit) {
		return 0;
	}

	@Override
	protected float generateCostRelativeToTarget(IGoapUnit goapUnit) {
		return 0;
	}

	@Override
	protected boolean checkProceduralPrecondition(IGoapUnit goapUnit) {
		return true;
	}

	@Override
	protected boolean requiresInRange(IGoapUnit goapUnit) {
		return false;
	}

	@Override
	protected boolean isInRange(IGoapUnit goapUnit) {
		return false;
	}
	
	// ------------------------------ Custom test functions
	
}
