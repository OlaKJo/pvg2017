package test.java.unitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.java.sorter.ParticipantsList;
import main.java.sorter.ResultGenerator;
import main.java.sorter.TimeHandler;
import main.java.util.Constants;

public class RunAcceptanceTest {
	@Before
	public void setup() {
		// Ignore timelimits.
		TimeHandler.minLapTimeLimit = 0;
		TimeHandler.minTotalTimeLimit = 0;
	}

	@Test
	public void testAcceptanceStory05() {
		String folder = "src/test/java/unitTest/acceptance/story5/";
		String resultPath = folder + "results";
		String expectedResultPath = folder + "expectedResults.txt";

		ParticipantsList pl = new ParticipantsList(Constants.ParticipantType.MARATHON);
		ResultGenerator rg = new ResultGenerator(pl);
		rg.initiateParticipantReader(folder + "participants.txt");
		rg.initiateFinishReader(folder + "finishes.txt");
		rg.initiateStartReader(folder + "starts.txt");
		rg.printFile(resultPath, Constants.ParticipantType.MARATHON);

		assertFilesEquals("Story 5", resultPath, expectedResultPath);
	}
	@Test
	public void testAcceptanceStory06() {
		TimeHandler.minTotalTimeLimit = 1000*60*20; // 20 min

		String folder = "src/test/java/unitTest/acceptance/story6/";
		String resultPath = folder + "results";
		String expectedResultPath = folder + "expectedResults.txt";

		ParticipantsList pl = new ParticipantsList(Constants.ParticipantType.MARATHON);
		ResultGenerator rg = new ResultGenerator(pl);
		rg.initiateParticipantReader(folder + "participants.txt");
		rg.initiateFinishReader(folder + "finishes.txt");
		rg.initiateStartReader(folder + "starts.txt");
		rg.printFile(resultPath, Constants.ParticipantType.MARATHON);

		assertFilesEquals("Story 6", resultPath, expectedResultPath);
	}

	@Test
	public void testAcceptanceStory09() {

		String folder = "src/test/java/unitTest/acceptance/story9/";
		String resultPath = folder + "results";
		String expectedResultPath = folder + "expectedResults.txt";

		ParticipantsList pl = new ParticipantsList(Constants.ParticipantType.LAP);
		ResultGenerator rg = new ResultGenerator(pl);
		rg.initiateParticipantReader(folder + "participants.txt");
		rg.initiateFinishReader(folder + "finishes.txt");
		rg.initiateStartReader(folder + "starts.txt");
		rg.printFile(resultPath, Constants.ParticipantType.LAP);

		assertFilesEquals("Story 09", resultPath, expectedResultPath);
	}

	@Test
	public void testAcceptanceStory13() {
		String folder = "src/test/java/unitTest/acceptance/story13/";
		String resultPath = folder + "results";
		String sortedPath = folder + "resultssorted.txt";
		String expectedResultPath = folder + "expectedResults.txt";

		String expectedSortedPath = folder + "expectedSortedResults.txt";

		ParticipantsList pl = new ParticipantsList(Constants.ParticipantType.LAP);
		ResultGenerator rg = new ResultGenerator(pl);
		rg.initiateParticipantReader(folder + "participants.txt");
		rg.initiateFinishReader(folder + "finishes_1.txt");
		rg.initiateFinishReader(folder + "finishes_2.txt");
		rg.initiateStartReader(folder + "starts.txt");
		rg.printFile(resultPath, Constants.ParticipantType.LAP);

		assertFilesEquals("Story 13", resultPath, expectedResultPath);
		assertFilesEquals("Story 13 Sorted", sortedPath, expectedSortedPath);
	}

	@Test
	public void testAcceptanceStory18MARATHON() {
		String folder = "src/test/java/unitTest/acceptance/story18/";
		String resultPath = folder + "results";
		String expectedResultPath = folder + "results_MARATHON_sorted.txt";

		ParticipantsList pl = new ParticipantsList(Constants.ParticipantType.MARATHON);
		ResultGenerator rg = new ResultGenerator(pl);
		rg.initiateParticipantReader(folder + "participants.txt");
		rg.initiateFinishReader(folder + "finish_1");
		rg.initiateFinishReader(folder + "finish_2");
		rg.initiateStartReader(folder + "start_1");
		rg.printFile(resultPath, Constants.ParticipantType.MARATHON);

		assertFilesEquals("Story 18", resultPath + "sorted.txt", expectedResultPath);
	}	
	
	@Test
	public void testAcceptanceStory18LAP() {
		String folder = "src/test/java/unitTest/acceptance/story18/";
		String resultPath = folder + "results";
		String expectedResultPath = folder + "results_LAP_sorted.txt";

		ParticipantsList pl = new ParticipantsList(Constants.ParticipantType.LAP);
		ResultGenerator rg = new ResultGenerator(pl);
		rg.initiateParticipantReader(folder + "participants.txt");
		rg.initiateFinishReader(folder + "finish_1");
		rg.initiateFinishReader(folder + "finish_2");
		rg.initiateFinishReader(folder + "finish_3");
		rg.initiateStartReader(folder + "start_1");
		rg.printFile(resultPath, Constants.ParticipantType.LAP);

		assertFilesEquals("Story 18", resultPath + "sorted.txt", expectedResultPath);
	}	

	private void assertFilesEquals(String story, String result, String expected) {
		List<String> expectedLines = null;
		List<String> actualLines = null;
		String results = "";
		try {
			expectedLines = Files.readAllLines(Paths.get(expected));
			actualLines = Files.readAllLines(Paths.get(result));
		} catch (IOException e) {
			e.printStackTrace();
			fail("Could not read files.");
		}

		int i = 0;
		boolean keep = actualLines.size() != expectedLines.size();
		results += story + "\n---------------------------------------------------\n";
		while (i < actualLines.size() && i < expectedLines.size()) {
			if (!actualLines.get(i).equals(expectedLines.get(i))) {
				results += "Diff:\n";
				results += "Actual:\t\t" + actualLines.get(i) + "\n";
				results += "Expected:\t" + expectedLines.get(i) + "\n\n";
				keep = true;
			}
			i++;
		}
		if (!keep) {
			// Remove result file.
			File file = new File(result);
			file.delete();
		} else {
			results += "The result file: " + result + " was not deleted.\n------------------------------\n";
			System.out.println(results);
		}

		assertEquals("The content in the files should match", expectedLines, actualLines);

	}
}