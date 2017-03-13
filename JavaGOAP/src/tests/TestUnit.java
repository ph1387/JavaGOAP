package tests;

import java.util.Queue;

import javaGOAP.GoapAction;
import javaGOAP.GoapState;
import javaGOAP.GoapUnit;

/**
 * TestUnit.java --- Unit for testing purposes. 
 * @author P H - 13.03.2017
 *
 */
public class TestUnit extends GoapUnit {
	public TestActionOne tOne = new TestActionOne(null);
	public TestActionTwo tTwo = new TestActionTwo(null);
	public TestActionThree tThree = new TestActionThree(null);
	public GoapState goalS = new GoapState(1, "goal", true);
	public GoapState worldS = new GoapState(0, "goal", false);
	
	public TestUnit() {
		
	}

	@Override
	public void goapPlanFound(Queue<GoapAction> actions) {
		
	}

	@Override
	public void goapPlanFailed(Queue<GoapAction> actions) {
		
	}

	@Override
	public void goapPlanFinished() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean moveTo(Object target) {
		return false;
	}
	
	// ------------------------------ Custom test functions
	
	public void addGS(GoapState goal) {
		this.addGoalState(goal);
	}
	
	public void addWS(GoapState worldState) {
		this.addWorldState(worldState);
	}
	
	public void addAA(GoapAction action) {
		this.addAvailableAction(action);
	}
}
