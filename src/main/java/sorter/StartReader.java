package main.java.sorter;

import java.util.Arrays;

import main.java.util.Constants;

/**
 * Adds start times to participants with the corresponding start number
 * 
 * @author Team03
 */
public class StartReader extends Reader {

	public StartReader(String fileToRead){
		super(fileToRead);
	}

	/**
	 * Adds a start time to corresponding start number in data structure.
	 * Handles special cases.
	 * @param participantList the data structure handling participants
	 * @param startNumber the number of the participant
	 * @param startTime the time to be saved
	 * @return boolean, false if special case (mass start) is registered, otherwise true
	 */
	@Override
	protected boolean add(ParticipantsList participantsList, int startNumber, String[] startTime) {
		if (specialCase(startNumber)) {
			for (Participant p: participantsList.getParticipants(Constants.REGISTERED)) {
				p.setStartTime(startTime[0]);
			}
			return false;
		} else {
			participantsList.addStart(startNumber, startTime[0]);
			return true;
		}
		
	}

	/**
	 * Checks if the start number indicates mass start.
	 * 
	 * @param startNumber the number that is checked for being special
	 * @return true if start number is code for massStart, otherwise false
	 */
	private boolean specialCase(int startNumber){
		return startNumber == Constants.EN_MASSE;
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
