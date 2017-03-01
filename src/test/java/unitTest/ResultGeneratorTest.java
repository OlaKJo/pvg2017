package test.java.unitTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.sorter.ParticipantsList;
import main.java.sorter.ResultGenerator;
import main.java.sorter.TimeHandler;
import main.java.util.Constants;
import main.java.util.Constants.ParticipantType;

public class ResultGeneratorTest {

	private static String resultPath = "src/test/java/unitTest/result";
	private ParticipantsList tl;
	private ResultGenerator resultGenerator;

	@Before
	public void setup() {
		TimeHandler.minLapTimeLimit = 0; // no impossible lap time
		TimeHandler.minTotalTimeLimit = 4*900000; // 1 h

		tl = new ParticipantsList();
		tl.addParticipantName(1, "Name 1");
		tl.addStart(1, "11.11.11");
		tl.addFinishTime(1, "13.13.13");
		tl.addParticipantName(2, "Name 2");
		tl.addStart(2, "12.12.12");
		tl.addFinishTime(2, "16.16.16");

		resultGenerator = new ResultGenerator(tl);
	}

	@After
	public void teardown() {
		File file = new File(resultPath + ".txt");
		file.delete();
		file = new File(resultPath + "sorted.txt");
		file.delete();
	}


	@Test
	public void printToFile() {
		ParticipantsList pl = new ParticipantsList(Constants.ParticipantType.MARATHON);
		ResultGenerator rg = new ResultGenerator(pl);

		rg.initiateFinishReader("src/test/java/unitTest/story17/finishTimes");
		rg.initiateParticipantReader("src/test/java/unitTest/story17/exampleParticipants");
		rg.initiateStartReader("src/test/java/unitTest/story17/startTimes");

		// tl.addStart(3, "12.05.00");
		// resultGenerator = new ResultGenerator(tl);
		rg.printFile("src/test/java/unitTest/story17/resultGeneratorResult", ParticipantType.MARATHON);

		// test total time as well
		// and printLine method indirectly
		List<String> expectedLines = null;
		List<String> actualLines = null;
		try {
			expectedLines = Files.readAllLines(Paths.get("src/test/java/unitTest/story17/expectedResult.txt"));
			actualLines = Files.readAllLines(Paths.get("src/test/java/unitTest/story17/resultGeneratorResult.txt"));

		} catch (IOException e) {
			fail("Could not read files.");
		}

		assertEquals("The content in the files should match", expectedLines, actualLines);

		// Remove result file.
		File file = new File(resultPath);
		file.delete();
	}

	@Test
	public void generateBasicMarathonResult() {
		ResultGenerator s = new ResultGenerator(new ParticipantsList());

		s.initiateStartReader("src/test/java/unitTest/acceptance/story5/starts.txt");
		s.initiateFinishReader("src/test/java/unitTest/acceptance/story5/finishes.txt");
		s.initiateParticipantReader("src/test/java/unitTest/acceptance/story5/participants.txt");

		s.printFile(resultPath, ParticipantType.MARATHON);

		List<String> expectedLines = null;
		List<String> actualLines = null;

		try {
			expectedLines = Files
					.readAllLines(Paths.get("src/test/java/unitTest/acceptance/story5/expectedResults.txt"));
			actualLines = Files.readAllLines(Paths.get(resultPath));
		} catch (IOException e) {
			fail("Could not read files");
		}

		assertEquals("The content in the files should match", expectedLines, actualLines);

	}

	@Test
	public void canFindStartFilesInFolder() {
		String[] result = ResultGenerator.getFilenames("src/test/java/unitTest/registered_times", 0);
		String[] expected = { "start_1", "start_2", "start_3", "start_4" };
		assertArrayEquals(expected, result);
	}

	@Test
	public void canFindFinishFilesInFolder() {
		String[] result = ResultGenerator.getFilenames("src/test/java/unitTest/registered_times", 1);
		String[] expected = { "finish_1", "finish_2", "finish_3", "finish_4" };
		assertArrayEquals(expected, result);
	}

	@Test
	public void findMaximumNbrOfLaps() {
		tl = new ParticipantsList(ParticipantType.LAP);
		tl.addParticipantName(1, "Name 1");
		tl.addStart(1, "11.11.11");
		tl.addFinishTime(1, "13.13.13");
		tl.addParticipantName(2, "Name 2");
		tl.addStart(2, "12.12.12");
		tl.addFinishTime(2, "16.16.16");
		resultGenerator = new ResultGenerator(tl);
		tl.addFinishTime(1, "13.14.13");
		tl.addFinishTime(2, "16.17.16");
		tl.addFinishTime(2, "16.18.16");
		assertEquals(3, resultGenerator.findMaximumNbrOfLaps(Constants.NO_RACE_CLASS));

	}

	@Test
	public void testResultSorted() {
		ResultGenerator s = new ResultGenerator(new ParticipantsList());

		s.initiateStartReader("src/test/java/unitTest/acceptance/story5/starts.txt");
		s.initiateFinishReader("src/test/java/unitTest/acceptance/story5/finishes.txt");
		s.initiateParticipantReader("src/test/java/unitTest/acceptance/story5/participants.txt");

		s.printFile(resultPath, ParticipantType.MARATHON);

		List<String> expectedLines = null;
		List<String> actualLines = null;

		try {
			expectedLines = Files
					.readAllLines(Paths.get("src/test/java/unitTest/acceptance/story18/expectedSortedResult.txt"));
			actualLines = Files.readAllLines(Paths.get(resultPath + "sorted.txt"));
		} catch (IOException e) {
			fail("Could not read files");
		}

		assertEquals("The content in the files should match", expectedLines, actualLines);

	}

	// Not yet implemented 20170213 Ola och Joachim
	@Test
	public void printsWithCorrectNbrOfLaps() {
		String folder = "src/test/java/unitTest/resultGenerator/";
		tl = new ParticipantsList(ParticipantType.LAP);
		tl.addParticipantName(1, "Name 1");
		tl.addParticipantName(2, "Name 2");
		tl.addStart(1, "11.11.11");
		tl.addFinishTime(1, "13.11.11");

		tl.addStart(2, "12.16.16");
		tl.addFinishTime(2, "16.16.16");
		tl.addFinishTime(2, "17.16.16");
		resultGenerator = new ResultGenerator(tl);

		resultGenerator.printFile(folder + "results", ParticipantType.LAP); // creates two files


		// test total time as well
		// and printLine method indirectly
		List<String> expectedLines = null;
		List<String> actualLines = null;
		try {

			expectedLines = Files.readAllLines(Paths.get(folder + "expectedLapResultSorted.txt"));
			actualLines = Files.readAllLines(Paths.get(folder + "resultssorted.txt")); // sorted

		} catch (IOException e) {
			fail("Could not read files.");
		}

		

		// ignores header
		expectedLines.remove(0);
		actualLines.remove(0);
		assertEquals("The content in the files should match", expectedLines, actualLines);

		// Remove result file.
		File file = new File(resultPath);
		file.delete();
	}

//	@Test
//	public void printsWithPersonsNotFinishedLap() {
//		tl = new ParticipantsList(ParticipantType.LAP);
//		tl.addParticipantName(1, "Name 1");
//		tl.addParticipantName(2, "Name 2");
//		tl.addStart(1, "11.11.11");
//		tl.addFinishTime(1, "11.21.11");
//
//		tl.addStart(2, "12.16.16");
//		tl.addFinishTime(2, "13.16.16");
//		resultGenerator = new ResultGenerator(tl);
//		resultGenerator.printFile(resultPath, ParticipantType.LAP); // creates
//																	// two files
//		// test total time as well
//		// and printLine method indirectly
//		List<String> expectedLines = null;
//		List<String> actualLines = null;
//		try {
//
//			expectedLines = Files.readAllLines(Paths.get("src/test/java/unitTest/expectedLapResultNoPlacement.txt"));
//			actualLines = Files.readAllLines(Paths.get(resultPath + "sorted.txt")); // sorted
//
//		} catch (IOException e) {
//			fail("Could not read files.");
//		}
//
//		// ignores header
////		expectedLines.remove(0);
////		actualLines.remove(0);
//		assertEquals("The content in the files should match", expectedLines, actualLines);
//
//		// Remove result file.
//		// File file = new File(resultPath);
//		// file.delete();
//	}

}
