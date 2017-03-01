package main.java.sorter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Adds participants' information (such as name) to participants with the
 * corresponding start number
 * 
 * @author team03
 *
 */
public class ParticipantReader extends Reader {

	private String headers;

	public ParticipantReader(String fileToRead) throws IOException {
		super(fileToRead);
		headers = readLine();

	}

	/**
	 * Adds a name to corresponding start number in data structure. Handles
	 * special cases.
	 * 
	 * @param participantList
	 *            the data structure handling participants
	 * @param startNumber
	 *            the number of the participant
	 * @param content
	 *            the name to be saved
	 * @return true if successful, which it will always be if not crashed
	 */
	@Override
	protected boolean add(ParticipantsList participantsList, int startNumber, String[] content) {
		participantsList.addParticipantName(startNumber, content[0]);
		participantsList.addParticipantInfo(startNumber, Arrays.copyOfRange(content, 1, content.length));
		return true;
	}

	/**
	 * Determines if a line can be parsed or not. Lines that do not contain ";"
	 * are treated as a race class for participants below this line
	 * 
	 * @param participantsList
	 *            the list of participants
	 * @param line
	 *            the current line
	 * @return true if a race class was found, else false
	 */
	@Override
	protected boolean processInput(ParticipantsList participantsList, String line) {
		if (!line.contains(";")) {
			participantsList.addRaceClass(line);
			return true;
		} else {
			try {
				String[] parsedData = Parser.parse(line);
				int startNumber = Integer.parseInt(parsedData[0]);
				boolean tryAdd = add(participantsList, startNumber,
						Arrays.copyOfRange(parsedData, 1, parsedData.length));
				return tryAdd;
			} catch (NumberFormatException e) {
				System.err.println("Startnumber could not be parsed from line: " + line);
				throw e;
			}
		}
	}

	/**
	 * Returns the Headlines parsed from file.
	 * 
	 * @return String array contain in headers
	 */
	public String getHeaders() {
		return headers;
	}

}
