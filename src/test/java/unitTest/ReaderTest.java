package test.java.unitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.sorter.FinishReader;
import main.java.sorter.MarathonParticipant;
import main.java.sorter.Parser;
import main.java.sorter.Participant;
import main.java.sorter.ParticipantReader;
import main.java.sorter.ParticipantsList;
import main.java.sorter.StartReader;
import main.java.sorter.TimeHandler;
import main.java.util.Constants;

public class ReaderTest {
	private StartReader startReader;
	private FinishReader finishReader;

	@Before
	public void setUp() throws Exception {
		startReader = new StartReader("./src/test/java/unitTest/exampleInput");
		finishReader = new FinishReader("./src/test/java/unitTest/exampleInput");
		
		TimeHandler.minLapTimeLimit = Integer.parseInt("900000");
		TimeHandler.minTotalTimeLimit = Integer.parseInt("900000");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void readFirstLineOfStart() throws IOException {
		String theStartLine = startReader.readLine();
		assertEquals("1; 12.00.00", theStartLine);
	}

	@Test
	public void readFirstLineOfFinish() throws IOException {
		String theFinishLine = startReader.readLine();
		assertEquals("1; 12.00.00", theFinishLine);
	}

	@Test
	public void readLineAndCompareToBadResult() throws IOException {
		String incorrectInput = "3; 15.00.01";
		assertNotEquals(incorrectInput, startReader.readLine());
		assertNotEquals(incorrectInput, finishReader.readLine());
	}

	@Test
	public void readEndOfLine() throws IOException {
		for (int i = 0; i < 7; i++) {
			startReader.readLine();
			finishReader.readLine();
		}
		assertEquals(null, startReader.readLine());
		assertEquals(null, finishReader.readLine());
	}

	@Test
	public void readLines() throws NumberFormatException, IOException {
		ParticipantsList participantsList = new ParticipantsList();
		ParticipantReader pr = new ParticipantReader("./src/test/java/unitTest/exampleParticipants");

		pr.read(participantsList);
		startReader.read(participantsList);
		finishReader.read(participantsList);
		assertEquals(participantsList.getStart(2), "12.01.00");
		assertEquals(participantsList.getFinish(2), "12.01.00");
		assertEquals(participantsList.getFinish(4), "Finish?");
		assertEquals(participantsList.getParticipantName(3), "Chris Csson");
		assertEquals(participantsList.getParticipantName(2), "Bengt Bsson");
		assertEquals(participantsList.getParticipantName(1), "Anders Asson");
	}

	@Test
	public void readLinesDifferentCategories() throws IOException {
		ParticipantReader pr = new ParticipantReader("./src/test/java/unitTest/exampleParticipantsDifferentCategories");
		String headers = pr.getHeaders();
		String[] parsedHeaders = Parser.parse(headers);
		assertEquals(parsedHeaders[0], "Start nbr");
		assertEquals(parsedHeaders[1], "Name");
		assertEquals(parsedHeaders[2], "Team");
		assertEquals(parsedHeaders[3], "MC");
		assertEquals(parsedHeaders[4], "Handicap");
	}

	@Test
	public void readExtraInfoTest() throws IOException {
		ParticipantReader pr = new ParticipantReader("./src/test/java/unitTest/exampleParticipantsDifferentCategories");
		ParticipantsList participantsList = new ParticipantsList(Constants.ParticipantType.MARATHON);
		startReader.read(participantsList);
		finishReader.read(participantsList);
		pr.read(participantsList);
		ArrayList<Participant> participants = participantsList.getParticipants(Constants.REGISTERED);
		String expected = "1; Anders Asson; Aklubb; AMK;; 00.00.00; 12.00.00; 12.00.00; Impossible total time?";
		String expected2 = "3; Chris Csson; ; CMK;; 00.00.00; 12.05.00; 12.05.00; Impossible total time?";
		MarathonParticipant p1 = (MarathonParticipant) participants.get(0);
		MarathonParticipant p2 = (MarathonParticipant) participants.get(2);
		assertEquals("", expected, p1.printResult());
		assertEquals("", expected2, p2.printResult());

		
	}
}
