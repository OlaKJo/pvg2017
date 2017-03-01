package test.java.unitTest;

import static org.junit.Assert.*;

import org.junit.*;
import main.java.sorter.MarathonParticipant;
import main.java.sorter.TimeHandler;
import main.java.util.Constants;

public class MarathonParticipantTest {
	
	
	MarathonParticipant p;
	
	@Before
	public void setUp(){
		p = new MarathonParticipant(1);
		TimeHandler.minLapTimeLimit = 900000;
		TimeHandler.minTotalTimeLimit = 900000;
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
		p.setStartTime("12.00.00");
		p.addFinishTime("13.00.00");
		assertEquals("13.00.00", p.getFinishTime());
	}


	@Test
	public void testNoFinishTime() {
		p.setName("A A");
		p.setStartTime("12.00.00");
		String expected = "1; A A; --.--.--; 12.00.00; Finish?";
		assertEquals(expected, p.printResult());
	}

	@Test
	public void testUnresonableTime() {
	p.setName("A A");
	p.setStartTime("12.00.00");
	p.addFinishTime("12.00.10");
	String expected = "1; A A; 00.00.10; 12.00.00; 12.00.10; " + Constants.IMPOSSIBLE_TOTAL;
	String actual = p.printResult();
	assertEquals(expected, actual);
	}

	@Test
	public void testMultipleStartFinishTimesAndImpossibleTotalTime() {
		p.setName("A A");
		p.setStartTime("12.00.00");
		p.addFinishTime("12.02.00");
		p.addFinishTime("12.44.00");
		p.setStartTime("12.05.00");
		p.addFinishTime("13.30.30");
		p.addFinishTime("13.31.00");
		String expected = "1; A A; 00.02.00; 12.00.00; 12.02.00; " + Constants.IMPOSSIBLE_TOTAL + "; " + Constants.SEVERAL_STARTS + " 12.05.00; " + Constants.SEVERAL_FINISH + " 12.44.00 13.30.30 13.31.00";
		assertEquals(expected, p.printResult());
	}

	@Test
	public void testUnregisteredStartprintResult() {
		p.setStartTime("00.00.01");
		String expected = "1; " + Constants.NONAME + "; --.--.--; 00.00.01; Finish?";
		assertEquals(expected, p.printResult());
	}

	@Test
	public void testUnregisteredFinishprintResult() {
		p.addFinishTime("00.00.01");
		String expected = "1; " + Constants.NONAME + "; --.--.--; Start?; 00.00.01";
		assertEquals(expected, p.printResult());
	}

	@Test

	public void testUnregisteredStartAndFinishprintResult() {
		p.setStartTime("00.00.01");
		p.addFinishTime("05.00.02");
		String expected = "1; " + Constants.NONAME + "; 05.00.01; 00.00.01; 05.00.02";
		assertEquals(expected, p.printResult());
	}

	@Test
	public void testIfParticipantIsRegistered() {
		assertFalse("The participant was marked registered", p.isRegistered());
		p.setName("Assar Assarsson");
		assertTrue("The participant was not marked registered", p.isRegistered());
	}
	
	@Test
	public void testExtraInformation() {
		String[] info = {"Aklubb", "ATM", "", "Glutenintolerant"};
		String expected = "1; Alice Asson; Aklubb; ATM; ; Glutenintolerant; 05.00.01; 00.00.01; 05.00.02";
		p.setName("Alice Asson");
		p.setExtraInfo(info);
		p.setStartTime("00.00.01");
		p.addFinishTime("05.00.02");
		assertEquals("Didn't print extpected extra information", expected, p.printResult());
	}
}
