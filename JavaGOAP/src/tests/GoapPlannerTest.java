package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Queue;

import org.junit.Test;

import javaGOAP.DefaultGoapPlanner;
import javaGOAP.GoapAction;
import javaGOAP.IGoapPlanner;

/**
 * GoapPlannerTest.java --- GoapPlanner test file.
 * 
 * @author P H - 13.03.2017
 *
 */
public class GoapPlannerTest {

	// No Connections equals a null pointer.
	@Test
	public void noConnectionsNullPointer() {
		assertEquals(null, getTestingGoapPlanner().plan(new TestUnit()));
	}

	// Connection between start and nodeOne leads to no goal
	@Test
	public void connectionAndNoGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tOne);

		assertEquals(null, getTestingGoapPlanner().plan(tUnit));
	}

	// No connection between node two and the start
	@Test
	public void noConnectionAndNoGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tTwo);

		assertEquals(null, getTestingGoapPlanner().plan(tUnit));
	}

	// Single node connected from start to goal
	@Test
	public void singleConnectionStartAndGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tThree);

		assertNotEquals(null, getTestingGoapPlanner().plan(tUnit));
		assertEquals(1, getTestingGoapPlanner().plan(tUnit).size());
	}

	// Connection between two nodes from start to goal
	@Test
	public void doubleConnectionStartAndGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tOne);
		tUnit.addAA(tUnit.tTwo);

		assertNotEquals(null, getTestingGoapPlanner().plan(tUnit));
		assertEquals(2, getTestingGoapPlanner().plan(tUnit).size());
	}

	// Shortest path with all three nodes
	@Test
	public void tripleVertexSingleConnectionStartAndGoal() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tOne);
		tUnit.addAA(tUnit.tTwo);
		tUnit.addAA(tUnit.tThree);

		assertNotEquals(null, getTestingGoapPlanner().plan(tUnit));
		assertEquals(1, getTestingGoapPlanner().plan(tUnit).size());
	}

	// Connection Test
	@Test
	public void correctActionsUsedDoubleConnection() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tOne);
		tUnit.addAA(tUnit.tTwo);
		Queue<GoapAction> q = getTestingGoapPlanner().plan(tUnit);

		// tOne -> tTwo -> end
		assertEquals(tUnit.tOne, q.poll());
		assertEquals(tUnit.tTwo, q.poll());
		assertEquals(0, q.size());
	}

	// Connection Test
	@Test
	public void correctActionsUsedSingleConnection() {
		TestUnit tUnit = generateBaseTestUnit();
		tUnit.addAA(tUnit.tOne);
		tUnit.addAA(tUnit.tTwo);
		tUnit.addAA(tUnit.tThree);
		Queue<GoapAction> q = getTestingGoapPlanner().plan(tUnit);

		// tOne -> tTwo -> end
		// tThree -> end
		assertEquals(tUnit.tThree, q.poll());
		assertEquals(0, q.size());
	}

	/**
	 * @return the IGoapPlanner that is currently being tested.
	 */
	public static IGoapPlanner getTestingGoapPlanner() {
		return new DefaultGoapPlanner();
	}

	public static TestUnit generateBaseTestUnit() {
		TestUnit tUnit = new TestUnit();
		tUnit.addWS(tUnit.worldS);
		tUnit.addGS(tUnit.goalS);

		return tUnit;
	}
}
