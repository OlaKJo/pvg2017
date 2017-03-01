package test.java.unitTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.java.sorter.ParticipantReader;
import main.java.sorter.ParticipantsList;
import main.java.util.Constants;

public class ParticipantReaderTest {
	ParticipantReader reader;
	ParticipantsList list;

	@Before
	public void setUp() {
		list = new ParticipantsList(Constants.ParticipantType.MARATHON);
	}

	@Test
	public void testReadParticipants() throws NumberFormatException, IOException {
		reader = new ParticipantReader("./src/test/java/unitTest/exampleParticipants");
		reader.read(list);
		assertEquals(list.getParticipantName(3), "Chris Csson");		
		assertEquals(list.getParticipantName(2), "Bengt Bsson");
		assertEquals(list.getParticipantName(1), "Anders Asson");
		
		ArrayList<String> classes = list.getRaceClasses();
		assertEquals("Classes were not added", 4, classes.size());		
	}

	@Test(expected = NumberFormatException.class)
	public void testBadParticipants() throws NumberFormatException, IOException {
		reader = new ParticipantReader("./src/test/java/unitTest/badParticipants");
		reader.read(list);
	}
}
