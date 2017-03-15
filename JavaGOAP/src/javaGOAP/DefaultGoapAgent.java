package javaGOAP;

import javaGOAP.GoapAgent;
import javaGOAP.GoapPlanner;
import javaGOAP.IGoapPlanner;
import javaGOAP.IGoapUnit;

//TODO UML
/**
 * DefaultGoapAgent.java --- The Default implementation of the GoapAgent. Only
 * the IGoapUnit has to be assigned.
 * 
 * @author P H - 15.03.2017
 *
 */
public class DefaultGoapAgent extends GoapAgent {

	public DefaultGoapAgent(IGoapUnit assignedUnit) {
		super(assignedUnit);
	}

	@Override
	protected IGoapPlanner createPlanner() {
		return new GoapPlanner();
	}

}
