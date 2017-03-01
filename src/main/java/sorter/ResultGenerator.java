package main.java.sorter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.StringTokenizer;

import main.java.resultFileProcessors.HTMLResult;
import main.java.resultFileProcessors.ResultListSorter;
import main.java.util.Constants;
import main.java.util.Constants.ParticipantType;

/**
 * Generates results based on configuration file. Will generate results with all
 * data, including possible error messages and a sorted result file in placement
 * order. These two versions are also saved in HTML format.
 * 
 * @author team03
 *
 */
public class ResultGenerator {
	private ParticipantsList participantsList;
	private String extraInfoHeadlines = "";

	/**
	 * Constructs a new Sorter, using a predefined ParticipantsList
	 * 
	 * @param participantsList
	 */
	public ResultGenerator(ParticipantsList participantsList) {
		this.participantsList = participantsList;
	}

	/**
	 * Prints content of participantsList into a result file. Uses
	 * toString()-method on Participant.
	 * 
	 * @param filename
	 *            path of the result file.
	 */
	public void printFile(String filename, ParticipantType participantType) {
		File file = new File(filename);
		File sortedfile = new File(filename + "sorted.txt");
		PrintWriter writer = null;
		PrintWriter sortedWriter = null;
		try {
			writer = new PrintWriter(file);
			sortedWriter = new PrintWriter(sortedfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;

		}

		generateResults(sortedWriter, participantType, writer);

		ArrayList<Participant> unregistered = participantsList.getParticipants(Constants.UNREGISTERED);
		if (unregistered.size() > 0) {
			writer.println("Non-existing start numbers");
			writer.println("StartNumber; Time; Time type");

			printParticipants(participantType, writer, unregistered, Constants.NO_RACE_CLASS);

		}
		writer.close();
		sortedWriter.close();
	}

	/**
	 * Prints the header for the results file.
	 * 
	 * @param participantType
	 *            An enum constant which is specified in Constants
	 * @param writer
	 *            PrintWriter used for the regular unsorted file
	 * @param sortedWriter
	 *            PrintWriter used for the sorted file
	 * @param raceClass
	 *            A String containing the class to which the participants belong
	 * 
	 */
	private void printHeader(ParticipantType participantType, PrintWriter writer, PrintWriter sortedWriter,
			String raceClass) {
		switch (participantType) {
		case MARATHON:
			writer.println(extraInfoHeadlines + "; Total time; Start time; Finish time");
			sortedWriter.println("Plac; " + extraInfoHeadlines + "; Total time");
			break;

		case LAP:
			String lapHeader = generateLapHeader(findMaximumNbrOfLaps(raceClass));
			writer.println(lapHeader);
			String sortedLapHeader = generateSortedLapHeader(findMaximumNbrOfLaps(raceClass));
			sortedWriter.println(sortedLapHeader);
			break;
		default:
			break;
		}
	}

	/**
	 * Assisting method to printHeader. Generates header for the results file,
	 * specifically for lap race
	 * 
	 * @param nbrOfLaps
	 *            The maximum number of laps that will be printed
	 * @return A string containing the header
	 */
	private String generateLapHeader(int nbrOfLaps) {
		// StartNumber; Name; #Lap; Total time; Lap time 1; Lap time 2; Lap time
		// 3; Start; Lap 1; Lap 2; Finish

		StringBuilder sb = new StringBuilder();
		sb.append(extraInfoHeadlines + "; #Lap; Total time; ");
		// append lap times
		for (int i = 1; i <= nbrOfLaps; i++) {
			sb.append("Lap time " + i + "; ");
		}
		// append start time
		sb.append("Start; ");
		// append lap times
		for (int i = 1; i < nbrOfLaps; i++) {
			sb.append("Lap " + i + "; ");
		}
		sb.append("Finish");
		return sb.toString();
	}

	/**
	 * Assisting method to printHeader. Generates a sorted header for the
	 * results file, specifically for lap race
	 * 
	 * @param nbrOfLaps
	 *            The maximum number of laps that will be printed
	 * @return A string containing the header
	 */
	private String generateSortedLapHeader(int nbrOfLaps) {
		// Plac; StartNumber; Name; #Lap; Total time; Lap time 1; Lap time 2;
		// Lap time 3
		StringBuilder sb = new StringBuilder();
		sb.append("Plac; " + extraInfoHeadlines + "; #Lap; Total time; ");
		// append lap times
		for (int i = 1; i <= nbrOfLaps; i++) {
			sb.append("Lap time " + i + "; ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}

	/**
	 * This method will loop through all participants in participantsList and
	 * print the results for each participant, one unsorted print and one
	 * sorted.
	 * 
	 * @param sortedWriter
	 *            PrintWriter used for the sorted file
	 * @param participantType
	 *            An enum constant which is specified in Constants
	 * @param writer
	 *            PrintWriter used for the regular unsorted file
	 */
	private void generateResults(PrintWriter sortedWriter, ParticipantType participantType, PrintWriter writer) {
		for (String raceClass : participantsList.getRaceClasses()) {
			ArrayList<Participant> participants = participantsList.getParticipants(raceClass);

			if (participants.size() > 0 && raceClass != Constants.NO_RACE_CLASS) {
				writer.println(raceClass);
				sortedWriter.println(raceClass);
			}

			if (participants.size() > 0) {
				printHeader(participantType, writer, sortedWriter, raceClass);
			}

			printParticipants(participantType, writer, participants, raceClass);

			printSortedParticipants(sortedWriter, participantType, raceClass);
		}
	}

	/**
	 * Sub method to generateResults that prints info for the sorted file
	 * 
	 * @param sortedWriter
	 *            PrintWriter used for the sorted file
	 * @param participantType
	 *            An enum constant which is specified in Constants
	 * @param raceClass
	 *            A String containing the class to which the participants belong
	 */
	private void printSortedParticipants(PrintWriter sortedWriter, ParticipantType participantType, String raceClass) {
		ArrayList<Participant> sortedParticipantList = participantsList.getParticipants(raceClass);
		new ResultListSorter(sortedParticipantList, participantType);
		int counter = 1;
		for (Participant p : sortedParticipantList) {
			switch (participantType) {
			case MARATHON:
				MarathonParticipant mp = (MarathonParticipant) p;
				if (TimeHandler.reasonableTotalTime(TimeHandler.calcTotalTime(mp.getStartTime(), mp.getFinishTime()))) {
					sortedWriter.println(counter++ + "; " + mp.printSortedResult());
				} else {
					sortedWriter.println("; " + mp.printSortedResult());
				}
				break;
			case LAP:
				LapRaceParticipant lp = (LapRaceParticipant) p;
				if (TimeHandler.reasonableTotalTime(TimeHandler.calcTotalTime(lp.getStartTime(), lp.getFinishTime()))) {
					sortedWriter.println(counter++ + "; " + lp.printSortedLaps(findMaximumNbrOfLaps(raceClass)));
				} else {
					sortedWriter.println("; " + lp.printSortedLaps(findMaximumNbrOfLaps(raceClass)));
				}
				break;
			case STAGE:
				// todo
				break;
			default:
				break;

			}

		}
	}

	/**
	 * Generates HTML files from result lists
	 * 
	 * @param participantType
	 * @param pathToRead
	 * @param pathToWrite
	 */
	public void printHTMLFile(ParticipantType participantType, String pathToRead, String pathToWrite) {
		// unsorted results with all time stamps
		HTMLResult html = new HTMLResult(pathToRead);
		html.createHTMLFile(pathToWrite);
		// sorted results
		String pathToReadSorted = pathToRead + "sorted.txt";
		String pathToWriteSorted = pathToWrite + "_sorted.html";
		HTMLResult htmlSorted = new HTMLResult(pathToReadSorted);
		htmlSorted.createHTMLFile(pathToWriteSorted);
	}

	/**
	 * Sub method to generateResults that prints info for the unsorted file
	 * 
	 * @param participantType
	 *            An enum constant which is specified in Constants
	 * @param writer
	 *            PrintWriter used for the sorted file
	 * @param participants
	 *            A list of participants to be printed
	 * @param raceClass
	 *            A String containing the class to which the participants belong
	 */
	private void printParticipants(ParticipantType participantType, PrintWriter writer,
			ArrayList<Participant> participants, String raceClass) {
		for (Participant p : participants) {
			switch (participantType) {
			case MARATHON:
				MarathonParticipant mp = (MarathonParticipant) p;
				writer.println(mp.printResult());
				break;
			case LAP:
				LapRaceParticipant lp = (LapRaceParticipant) p;
				writer.println(lp.printLaps(findMaximumNbrOfLaps(raceClass)));
				break;
			case STAGE:
				// todo
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Return filenames for all start or finish files, sorted by filename.
	 * 
	 * @param path
	 *            - directory of time files.
	 * @param token
	 *            - declares to look for start or finish.
	 * @return String[] with the filenames of each file.
	 */
	public static String[] getFilenames(String path, int token) {
		File dir = new File(path);
		final int t = token;
		String[] filenames = findFilenames(dir, t);

		ArrayList<String> filenamesAsArray = new ArrayList<String>();

		for (String fileName : filenames) {
			filenamesAsArray.add(fileName);
		}

		Collections.sort(filenamesAsArray);

		filenames = new String[filenamesAsArray.size()];
		filenames = filenamesAsArray.toArray(filenames);

		return filenames;
	}

	/**
	 * Finds the relevant file names and returns them in a vector. Orerides a
	 * method in FileNameFilter.
	 * 
	 * @param dir
	 *            the directory where files are to be searched for
	 * @param t
	 *            the constant which decides the kind file that is read
	 */
	private static String[] findFilenames(File dir, final int t) {
		return dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (t == Constants.REGISTER_STARTS) {
					return name.startsWith("start_");
				} else if (t == Constants.REGISTER_FINISHES) {
					return name.startsWith("finish_");
				}
				return false;
			}
		});
	}

	/**
	 * Executes sorting of registered files. Prints the result into a new file.
	 * 
	 * @param args
	 *            input from terminal, not used.
	 */
	public static void main(String[] args) {
		String configFilePath = "./Config_DoNotMoveOrDeleteThis";

		if (args != null) {
			if (args.length > 0) {
				configFilePath = args[0];
			}
		}

		Properties p = new Properties();

		readConfigFile(configFilePath, p);

		String raceType = p.getProperty("raceType", "marathon");
		String startClosesAt = p.getProperty("startClosesAt", null);
		String nameFilePath = p.getProperty("nameFilePath", "./participants.txt");
		String timeFilesDir = p.getProperty("timeFilesDir", "./");
		String timeFileNames = p.getProperty("timeFileNames", null);
		String resultPath = p.getProperty("resultPath", "./result_");
		String minTimeLimit = p.getProperty("minTimeLimit", "900000");
		String minLapLimit = p.getProperty("minLapLimit", "900000");

		TimeHandler.minLapTimeLimit = Integer.parseInt(minLapLimit);
		TimeHandler.minTotalTimeLimit = Integer.parseInt(minTimeLimit);

		ParticipantType participantType = getParticipantTypeFromConfig(raceType);

		ResultGenerator resultGenerator = new ResultGenerator(new ParticipantsList(participantType));

		String[] fileNames = null;
		if (timeFileNames != null) {
			// fileNames = timeFileNames.split(";");
			StringTokenizer tok = new StringTokenizer(timeFileNames, ";:., ");
			int a = 0;
			fileNames = new String[tok.countTokens()];
			while (tok.hasMoreTokens()) {
				fileNames[a] = tok.nextToken();
				a++;
			}
		}

		String[] startTimes = getStartFileNames(timeFilesDir, timeFileNames, fileNames);
		String[] finishTimes = getFinishFileNames(timeFilesDir, timeFileNames, fileNames);

		// Keep this order of execution, necessary for mass start
		resultGenerator.initiateParticipantReader(nameFilePath);
		for (int i = 0; i < finishTimes.length; i++) {
			String finishPath = timeFilesDir + "/" + finishTimes[i];
			resultGenerator.initiateFinishReader(finishPath);
		}

		for (int i = 0; i < startTimes.length; i++) {
			String startPath = timeFilesDir + "/" + startTimes[i];
			resultGenerator.initiateStartReader(startPath);
		}

		if (!resultPath.equals("./result_")) {
			resultGenerator.printFile(resultPath, participantType);
			String pathToRead = resultPath;
			String pathToWrite = resultPath + ".html";
			resultGenerator.printHTMLFile(participantType, pathToRead, pathToWrite);

		} else {

			resultGenerator.printFile(resultPath + participantType + ".txt", participantType);
			resultGenerator.printHTMLFile(participantType, resultPath + participantType + ".txt",
					resultPath + "HTML_" + participantType + ".html");

		}
	}

	/**
	 * Private method
	 * @param timeFilesDir
	 * @param timeFileNames
	 * @param fileNames
	 * @return
	 */
	private static String[] getFinishFileNames(String timeFilesDir, String timeFileNames, String[] fileNames) {
		String[] finishTimes;
		if (timeFileNames == null) {
			finishTimes = getFilenames(timeFilesDir, Constants.REGISTER_FINISHES);
		} else {

			ArrayList<String> finishTimesArray = new ArrayList<String>();
			for (String s : fileNames) {
				if (s.startsWith("finish_")) {
					finishTimesArray.add(s);
				}
			}

			finishTimes = finishTimesArray.toArray(new String[finishTimesArray.size()]);
		}
		return finishTimes;
	}

	/**
	 * Private method
	 * @param timeFilesDir
	 * @param timeFileNames
	 * @param fileNames
	 * @return
	 */
	private static String[] getStartFileNames(String timeFilesDir, String timeFileNames, String[] fileNames) {
		String[] startTimes;
		if (timeFileNames == null) {
			startTimes = getFilenames(timeFilesDir, Constants.REGISTER_STARTS);
		} else {

			ArrayList<String> startTimesArray = new ArrayList<String>();
			for (String s : fileNames) {
				if (s.startsWith("start_")) {
					startTimesArray.add(s);
				}
			}

			startTimes = startTimesArray.toArray(new String[startTimesArray.size()]);

		}
		return startTimes;
	}

	/**
	 * Reads config file and makes settings based on it
	 * @param configFilePath where config file is found
	 * @param p properties
	 */
	private static void readConfigFile(String configFilePath, Properties p) {
		try {
			p.load(new FileInputStream(configFilePath));
		} catch (Exception e) {
			System.out.println("Could not find the Configuration file: " + configFilePath);
			System.exit(-1);
		}
	}

	/**
	 * Determines participant type (race mode) from configurations 
	 * @param raceType
	 * @return
	 */
	private static ParticipantType getParticipantTypeFromConfig(String raceType) {
		ParticipantType participantType;

		if (raceType.equals("marathon")) {
			participantType = ParticipantType.MARATHON;
		} else {
			participantType = ParticipantType.LAP;
		}
		return participantType;

	}

	/**
	 * Creates a ParticipantReader which will save participant data found in
	 * participantPath to the data structure
	 * 
	 * @param participantPath
	 */
	public void initiateParticipantReader(String participantPath) {
		try {
			ParticipantReader participantReader = new ParticipantReader(participantPath);
			extraInfoHeadlines = participantReader.getHeaders();

			participantReader.read(participantsList);
		} catch (Exception e) {
			System.err.println("From file: " + participantPath);
			System.exit(3);
		}

	}

	/**
	 * Creates a FinishReader which will save finish times found in finishPath
	 * to the data structure
	 * 
	 * @param finishPath
	 */
	public void initiateFinishReader(String finishPath) {
		FinishReader finishReader = new FinishReader(finishPath);
		try {
			finishReader.read(participantsList);
		} catch (Exception e) {
			System.err.println("From file: " + finishPath);
			System.exit(3);
		}

	}

	/**
	 * Creates a StartReader which will save start times found in starthPath to
	 * the data structure
	 * 
	 * @param startPath
	 */
	public void initiateStartReader(String startPath) {
		StartReader startReader = new StartReader(startPath);
		try {
			startReader.read(participantsList);
		} catch (Exception e) {
			System.err.println("From file: " + startPath);
			System.exit(3);
		}
	}

	/**
	 * Returns the maximum number of laps of any participant.
	 * 
	 * @return an int of the maximum number of laps
	 */

	public int findMaximumNbrOfLaps(String raceClass) {
		return participantsList.findMaxNrOfLaps(raceClass);

	}
}
