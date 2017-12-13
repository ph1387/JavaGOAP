package javaGOAP;

/**
 * UnperformableActionException.java --- Exception raised when an Action could
 * not be performed / returned false while calling the "performAction" function
 * even though the "checkProceduralPrecondition" function returned true.
 * 
 * @author P H - 13.12.2017
 *
 */
public class UnperformableActionException extends Exception {

	public UnperformableActionException(String className) {
		super("Action (" + className + ") could not be performed! (proceduralPrecondition=True, performAction=False)");
	}

}