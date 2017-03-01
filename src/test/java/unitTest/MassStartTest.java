package test.java.unitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import main.java.sorter.ParticipantsList;
import main.java.sorter.ResultGenerator;
import main.java.util.Constants.ParticipantType;

public class MassStartTest {
	
	@Test
	public void testResultFileWithMassStart() {
		ResultGenerator rg = new ResultGenerator(new ParticipantsList());
		rg.initiateParticipantReader("src/test/java/unitTest/acceptance/massStart/participants.txt");
		rg.initiateFinishReader("src/test/java/unitTest/acceptance/massStart/finishes.txt");
		rg.initiateStartReader("src/test/java/unitTest/acceptance/massStart/starts.txt");		

		rg.printFile("src/test/java/unitTest/acceptance/massStart/results.txt", ParticipantType.MARATHON);
		List<String> expected = null;
		List<String> actual = null;
		try {
			expected = Files.readAllLines(Paths.get("src/test/java/unitTest/acceptance/massStart/expectedResults.txt"));
			actual = Files.readAllLines(Paths.get("src/test/java/unitTest/acceptance/massStart/results.txt"));
		} catch (IOException e) {
			fail("Could not read files.");
		}
		assertEquals("The content in the files should match", expected, actual);
		// clean up
		File file = new File("src/test/java/unitTest/acceptance/massStart/results.txt");
		file.delete();
	}

}
