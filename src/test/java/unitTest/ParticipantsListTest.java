package test.java.unitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.java.sorter.Participant;
import main.java.sorter.ParticipantsList;
import main.java.util.Constants;

public class ParticipantsListTest {
	private ParticipantsList p;

	@Before
	public void setUp() throws Exception {
		p = new ParticipantsList();
	}

	@Test
	public void testAddStartTime() {
		p.addStart(1, "12.00.00");
		assertEquals("12.00.00", p.getStart(1));
	}

	@Test
	public void testAddFinishTime() {
		p.addFinishTime(2, "13.13.13");
		assertEquals("13.13.13", p.getFinish(2));
	}

	@Test
	public void testGetExistingStartTime() {
		p.addStart(1, "123");
		String s = p.getStart(1);
		assertEquals("123", s);
	}

	@Test
	public void testGetEmptyStartTime() {
		String s = p.getStart(1);
		assertEquals("--.--.--", s);
	}

	@Test
	public void testGetExistingFinishTime() {
		p.addFinishTime(2, "456");
		String s = p.getFinish(2);
		assertEquals("456", s);
	}

	@Test
	public void testGetEmptyFinishTime() {
		String s = p.getFinish(1);
		assertEquals("--.--.--", s);
	}

	@Test
	public void testParticipantName() {
		p.addParticipantName(3, "Bertil Bertilsson");
		assertEquals("Bertil Bertilsson", p.getParticipantName(3));
	}

	@Test
	public void testGetEmptyParticipant() {
		assertEquals(Constants.PARTICIPANT_NOT_FOUND, p.getParticipantName(1));
	}

	@Test
	public void testCollisionSameStartNbr() {
		p.addStart(1, "12.00.00");
		p.addStart(1, "13.13.13");
		assertEquals("12.00.00", p.getStart(1));
	}

	@Test
	public void testAddMultipleParticipants() {
		for (int i = 0; i < 10; i++) {
			p.addStart(i, "" + i);
		}
		for (int i = 0; i < 10; i++) {
			assertEquals(Integer.toString(i), p.getStart(i));
		}
	}

	@Test
	public void testGetUnregisteredParticipants() {
		p.addStart(1, "12.00.00");
		p.addParticipantName(1, "Has a name");
		p.addStart(2, "12.00.01"); // Does not have a name.
		p.addStart(3, "12.00.01"); // Does not have a name.
		p.addStart(4, "12.00.01"); // Does not have a name.
		p.addStart(5, "12.00.01"); // Does not have a name.
		p.addStart(6, "12.00.01"); // Does not have a name.
		p.addStart(7, "12.00.01"); // Does not have a name.

		ArrayList<Participant> result = p.getParticipants(Constants.UNREGISTERED);
		assertEquals(6, result.size());
		assertEquals(Constants.NONAME, result.get(0).getName());
	}
	
	@Test
	public void testGetRegisteredParticipants() {
		p.addStart(1, "12.00.00");
		p.addStart(2, "12.00.00");
		p.addStart(3, "12.00.00");
		p.addStart(4, "12.00.00");
		p.addParticipantName(2, "Has a name");
		p.addParticipantName(3, "Has a name2");
		p.addParticipantName(4, "Has a name3");
		ArrayList<Participant> result = p.getParticipants(Constants.REGISTERED);
		assertEquals(3, result.size());
		assertEquals("Has a name", result.get(0).getName());
		assertEquals("Has a name2", result.get(1).getName());
		assertEquals("Has a name3", result.get(2).getName());
	}

	@Test
	public void testFindMaximumNrOfLaps() {
		p = new ParticipantsList(Constants.ParticipantType.LAP);
		p.addParticipantName(0, "Ola Persson");
		p.addParticipantName(1, "Per Andersson");

		p.addStart(0, "12.00.00");
		p.addFinishTime(0, "13.00.00");
		p.addFinishTime(0, "14.00.00");
		p.addFinishTime(0, "15.00.00");

		p.addStart(1, "12.00.00");
		p.addFinishTime(1, "14.00.00");
		p.addFinishTime(1, "15.00.00");

		assertEquals(3, p.findMaxNrOfLaps(Constants.NO_RACE_CLASS));
	}

	@Test
	public void testAddRaceClass() {
		p = new ParticipantsList(Constants.ParticipantType.MARATHON);
		p.addRaceClass("Junior");
		p.addRaceClass("Senior");
		p.addRaceClass("Junior");
		ArrayList<String> classes = p.getRaceClasses();
		// There are 3 because it holds the NO_RACE_CLASS as well.
		assertEquals("All race classes were not added", 3, classes.size());
		
		assertTrue("Does not have empty class", classes.contains(Constants.NO_RACE_CLASS));
		assertTrue("Junior was not included", classes.contains("Junior"));
		assertTrue("Senior was not included", classes.contains("Senior"));
	}
}


