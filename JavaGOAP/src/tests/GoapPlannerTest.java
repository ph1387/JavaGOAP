package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import javaGOAP.GoapPlanner;
import javaGOAP.GoapState;
import javaGOAP.IGoapUnit;

/**
 * GoapPlannerTest.java --- GoapPlanner test file.
 * @author P H - 13.03.2017
 *
 */
public class GoapPlannerTest {

	// No Connections equals a null pointer.
	@Test
	public void noConnectionsNullPointer() {
		assertEquals(null, new GoapPlanner().plan(new TestUnit()));
	}
	
	// Connection between start and nodeOne leads to no goal
	@Test
	public void connectionAndNoGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tOne);
		
		assertEquals(null, new GoapPlanner().plan(tUnit));
	}
	
	// No connection between node two and the start
	@Test
	public void noConnectionAndNoGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tTwo);
		
		assertEquals(null, new GoapPlanner().plan(tUnit));
	}
	
	// Single node connected from start to goal
	@Test
	public void singleConnectionStartAndGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tThree);
		
		assertNotEquals(null, new GoapPlanner().plan(tUnit));
		assertEquals(1, new GoapPlanner().plan(tUnit).size());
	}
	
	// Connection between two nodes from start to goal
	@Test
	public void doubleConnectionStartAndGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tOne);
		tUnit.addAA(tUnit.tTwo);
		
		assertNotEquals(null, new GoapPlanner().plan(tUnit));
		assertEquals(2, new GoapPlanner().plan(tUnit).size());
	}
	
	// Shortest path with all three nodes
	@Test
	public void tripleConnectionStartAndGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tOne);
		tUnit.addAA(tUnit.tTwo);
		tUnit.addAA(tUnit.tThree);
		
		assertNotEquals(null, new GoapPlanner().plan(tUnit));
		assertEquals(1, new GoapPlanner().plan(tUnit).size());
	}
	
	public static TestUnit generateBaseTestUnit() {
		TestUnit tUnit = new TestUnit();
		tUnit.addWS(tUnit.worldS);
		tUnit.addGS(tUnit.goalS);
		
		return tUnit;
	}
}
