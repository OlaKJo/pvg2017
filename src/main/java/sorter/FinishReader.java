package main.java.sorter;

import java.util.Arrays;

/**
 * Adds finish times to participants with the corresponding start number
 * 
 * @author Team03
 */
public class FinishReader extends Reader {

	public FinishReader(String fileToRead) {
		super(fileToRead);
	}

	/**
	 * Adds a finish time to corresponding start number in data structure.
	 * 
	 * @param participantList the data structure handling participants
	 * @param startNumber the number of the participant
	 * @param finishTime the time to be saved
	 * @return true if successful, which it will always be if not crashed
	 */
	@Override
	protected boolean add(ParticipantsList participantsList, int startNumber, String[] finishTime) {
		participantsList.addFinishTime(startNumber, finishTime[0]);
		return true;
	}

	/**
	 * @param participantsList
	 *            the data structure where the found information will be stored
	 * @param input
	 *            Input line to be processed
	 */
	@Override
	protected boolean processInput(ParticipantsList participantsList, String input) throws NumberFormatException {
		try {
			String[] parsedData = Parser.parse(input);
			int startNumber = Integer.parseInt(parsedData[0]);
			return add(participantsList, startNumber, Arrays.copyOfRange(parsedData, 1, parsedData.length));
		} catch (NumberFormatException e) {
			System.err.println("Startnumber could not be parsed from line: " + input);
			throw e;
		}
	}
}
