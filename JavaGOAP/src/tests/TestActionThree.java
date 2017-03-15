package tests;

import javaGOAP.GoapState;
import javaGOAP.IGoapUnit;

/**
 * TestActionThree.java --- Third TestAction. 
 * @author P H - 13.03.2017
 *
 */
public class TestActionThree extends TestActionOne {
	public TestActionThree(Object target) {
		super(target);
		
		this.addPrecondition(new GoapState(0, "goal", false));
		this.addEffect(new GoapState(0, "goal", true));
	}
	
	@Override
	protected float generateBaseCost(IGoapUnit goapUnit) {
		return 0;
	}
}
