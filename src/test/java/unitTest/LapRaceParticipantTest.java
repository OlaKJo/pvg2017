package test.java.unitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.java.sorter.LapRaceParticipant;
import main.java.sorter.TimeHandler;

public class LapRaceParticipantTest {

	LapRaceParticipant p;

	@Before
	public void setUp() {
		p = new LapRaceParticipant(1);
		TimeHandler.minLapTimeLimit = 9000;
		TimeHandler.minTotalTimeLimit = 9000;
	}

	@Test
	public void testGetName() {
		p.setName("Arne Arnesson");
		assertEquals("Arne Arnesson", p.getName());
	}

	@Test
	public void testGetNumber() {
		assertEquals(1, p.getStartNumber());
	}

	@Test
	public void testGetStartTime() {
		p.setStartTime("12.00.00");
		assertEquals("12.00.00", p.getStartTime());
	}

	@Test
	public void getFinishTime() {
		p.addFinishTime("13.00.00");
		assertEquals("13.00.00", p.getFinishTime());
	}

	@Test
	public void getFinishTimeAfterAddStartTime() {
		p.addFinishTime("13.00.00");
		p.setStartTime("12.12.12");
		assertEquals("13.00.00", p.getFinishTime());
	}

	@Test
	public void getLapOneFinishTime() { // total time if marathon
		p.setStartTime("12.00.00");
		p.addFinishTime("12.33.10");
		assertEquals("12.33.10", p.getSpecificFinishTime(0));
	}
	// ^ for all Participants

	@Test
	public void getFinishTimesTest() {
		p.setStartTime("12.00.00");
		p.addFinishTime("12.33.10");
		p.addFinishTime("12.44.44");
		p.addFinishTime("13.30.30");
		p.addFinishTime("13.31.00");
		assertEquals("13.30.30", p.getSpecificFinishTime(2));
		assertEquals("13.31.00", p.getSpecificFinishTime(3));
	}

	@Test
	public void getNbrOfLapsTest() {
		p.setStartTime("12.00.00");
		p.addFinishTime("12.33.10");
		p.addFinishTime("12.44.44");
		p.addFinishTime("13.30.30");
		p.addFinishTime("13.31.00");
		assertEquals(4, p.getNumberOfLaps());
	}

	@Test
	public void numberOfLapsNoStartTimeTest() {
		p.addFinishTime("13.00.00");
		assertEquals(1, p.getNumberOfLaps());
	}

	@Test
	public void testMultipleStartAndFinishTimes() {
		p.setName("A A");
		p.setStartTime("12.00.00");
		p.addFinishTime("12.33.00");
		p.addFinishTime("12.53.00");
		p.addFinishTime("13.30.30");
		p.addFinishTime("13.50.00");
		String expected = "1; A A; 4; 01.50.00; 00.33.00; 00.20.00; 00.37.30; 00.19.30; 12.00.00; 12.33.00; 12.53.00; 13.30.30; 13.50.00";

		assertEquals(expected, p.printLaps(4));
	}
	
	@Test
	public void testMoreLapsThanRecorded() {
		p.setName("A A");
		p.setStartTime("12.00.00");
		p.addFinishTime("12.33.00");
		p.addFinishTime("12.53.00");
		p.addFinishTime("13.30.30");
		p.addFinishTime("13.50.00");
		String expected = "1; A A; 4; 01.50.00; 00.33.00; 00.20.00; 00.37.30; 00.19.30; ; 12.00.00; 12.33.00; 12.53.00; 13.30.30; 13.50.00; ";
		assertEquals(expected, p.printLaps(5));
	}

	@Test
	public void testNoStartprintLaps() {
		p.addFinishTime("00.05.00");
		p.setName("Name");
		String expected = "1; Name; 1; --.--.--; ; ; Start?; 00.05.00; ";
		assertEquals(expected, p.printLaps(2));
	}

	@Test
	public void testNoFinishprintLaps() {
		p.setStartTime("00.05.00");
		p.setName("Name");
		String expected = "1; Name; 0; --.--.--; ; ; 00.05.00; ; Finish?";
		assertEquals(expected, p.printLaps(2));
	}

	@Test
	public void testSeveralStartTimes() {
		p.setStartTime("00.05.00");
		p.setStartTime("00.06.00");
		p.addFinishTime("01.00.00");
		p.setName("Name");
		String expected = "1; Name; 1; 00.55.00; 00.55.00; ; 00.05.00; 01.00.00; ; Several start times? 00.06.00";
		assertEquals(expected, p.printLaps(2));
	}

	@Test
	public void testImpossibleLapTime() {
		p.setStartTime("00.00.05");
		p.addFinishTime("00.00.07");
		p.addFinishTime("00.50.07");
		p.setName("Hans");
		String expected = "1; Hans; 2; 00.50.02; 00.00.02; 00.50.00; 00.00.05; 00.00.07; 00.50.07; Impossible lap time?";
		assertEquals(expected, p.printLaps(2));
	}

	@Test
	public void testExtraInformation() {
		String[] info = { "Aklubb", "ATM", "", "Glutenintolerant" };
		String expected = "1; Alice Asson; Aklubb; ATM; ; Glutenintolerant; 4; 01.50.00; 00.33.00; 00.20.00; 00.37.30; 00.19.30; 12.00.00; 12.33.00; 12.53.00; 13.30.30; 13.50.00";
		p.setName("Alice Asson");
		p.setExtraInfo(info);

		p.setStartTime("12.00.00");
		p.addFinishTime("12.33.00");
		p.addFinishTime("12.53.00");
		p.addFinishTime("13.30.30");
		p.addFinishTime("13.50.00");
		assertEquals("Didn't print expected extra information", expected, p.printLaps(4));
		
		
	}
}
