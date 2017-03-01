package main.java.sorter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Readers handles the reading of time and participant files to import to data
 * the structure ParticipantList.
 * Abstract class, can be implemented as Start-, Finish-, Participant-Reader
 * 
 * @author team03
 *
 */
public abstract class Reader {

	private BufferedReader bufferedReader;

	/**
	 * Adds content (start time OR finish time OR name) corresponding to a start
	 * number to the data structure.
	 * 
	 * @param participantList
	 *            the data structure handling participants
	 * @param startNumber
	 *            the number of the participant
	 * @param content
	 *            the time or name to be saved
	 * @return boolean, false if special case, otherwise true
	 */
	protected abstract boolean add(ParticipantsList participantsList, int startNumber, String[] content);

	public Reader(String fileToRead) {
		try {
			bufferedReader = new BufferedReader(new FileReader(fileToRead));
		} catch (FileNotFoundException e) {
			System.out.println("Could not read file: " + fileToRead);
			System.out.println("Make sure file exists. See manual for help.");
			System.exit(1);
		}

	}

	/**
	 * Read all lines in file, and store them in data structure. Stops if add
	 * returns false.
	 * 
	 * @param participantList
	 *            the data structure where the found information will be stored
	 * @throws IOException
	 */
	public void read(ParticipantsList participantsList) throws IOException, NumberFormatException {
		String line;
		while ((line = readLine()) != null) {
			if (!processInput(participantsList, line)) {
				break;
			}
		}

	}

	protected abstract boolean processInput(ParticipantsList participantsList, String line);

	/**
	 * Reads the next line in chosen file using bufferReader.
	 * 
	 * @return next line from buffer. Catches IOException
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		String line = "";
		try {
			line = bufferedReader.readLine();

		} catch (IOException e) {
			System.err.println("One line could not be interpreted.");
			throw e;
		}
		return line;
	}
}
