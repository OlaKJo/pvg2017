package main.java.sorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import main.java.util.Constants;
import main.java.util.Constants.ParticipantType;

/**
 * A collection of Participants objects. Can find information on times, name and
 * more based on start number.
 * 
 * @author team03
 *
 */
public class ParticipantsList {

	/** Participants are stored in a hashmap, with key start number */
	private HashMap<Integer, Participant> participants = new HashMap<Integer, Participant>();

	private Constants.ParticipantType mode;
	private LinkedList<String> raceClasses;

	public ParticipantsList() {
		this.mode = Constants.ParticipantType.MARATHON;
		raceClasses = new LinkedList<String>();
		raceClasses.add(Constants.NO_RACE_CLASS);
	}

	public ParticipantsList(Constants.ParticipantType mode) {
		this.mode = mode;
		raceClasses = new LinkedList<String>();
		raceClasses.add(Constants.NO_RACE_CLASS);
	}

	/**
	 * Adds start time to Participant object with corresponding start number.
	 * 
	 * @param startNumber
	 * @param time
	 */
	public void addStart(int startNumber, String time) {
		participant(startNumber).setStartTime(time);
	}

	/**
	 * Adds finish time to Participant object with corresponding start number.
	 * 
	 * @param startNumber
	 * @param time
	 */
	public void addFinishTime(int startNumber, String time) {
		participant(startNumber).addFinishTime(time);
	}

	/**
	 * Adds name to Participant object with corresponding start number.
	 * 
	 * @param startNumber
	 * @param name
	 */
	public void addParticipantName(int startNumber, String name) {
		participant(startNumber).setName(name);
	}

	/**
	 * 
	 * @param startNumber
	 * @return A participant with the corresponding start number. Either
	 *         existing or new.
	 */
	private Participant participant(int startNumber) {
		Participant p = participants.get(startNumber);
		if (p == null) {
			switch (mode) {
			case MARATHON:
				p = new MarathonParticipant(startNumber, raceClasses.getLast());
				break;
			case LAP:
				p = new LapRaceParticipant(startNumber, raceClasses.getLast());
				break;
			case STAGE:
			default:
				throw new IllegalArgumentException("Not implemented");
			}

			participants.put(startNumber, p);
		}
		return p;
	}

	/**
	 * 
	 * @param startNumber
	 * @return Returns the start time for the participant with corresponding
	 *         start number or Constants.EMPTY if the participant doesn't exist.
	 */
	public String getStart(int startNumber) {
		Participant p = participants.get(startNumber);
		if (p == null) {
			return Constants.EMPTY;
		}
		return p.getStartTime();
	}

	/**
	 * 
	 * @param startNumber
	 * @return Returns the finish time for the participant with corresponding
	 *         start number or Constants.EMPTY if the participant doesn't exist.
	 */
	public String getFinish(int startNumber) {
		Participant p = participants.get(startNumber);
		if (p == null) {
			return Constants.EMPTY;
		}
		return p.getFinishTime();
	}

	/**
	 * 
	 * @param startNumber
	 * @return Returns Participant object's name if it exists, otherwise
	 *         Constants.NONAME.
	 */
	public String getParticipantName(int startNumber) {
		Participant p = participants.get(startNumber);

		if (p == null)
			return Constants.PARTICIPANT_NOT_FOUND;

		return p.getName();
	}

	/**
	 * @param mode
	 *            Toggles if unregistered or registered participants should be
	 *            returned, true means only registered participants are
	 *            returned.
	 * @return Returns an ArrayList of all registered start numbers.
	 */
	public ArrayList<Participant> getParticipants(boolean mode) {
		ArrayList<Participant> result = new ArrayList<Participant>();
		for (Participant p : participants.values()) {
			if (mode) {
				if (p.isRegistered()) {
					result.add(p);
				}
			} else {
				if (!p.isRegistered()) {
					result.add(p);
				}
			}
		}
		return result;
	}

	/**
	 * Get all registered participants for a certain race class.
	 * 
	 * @param raceClass
	 *            is the race class
	 * @return All the registered participants for race class
	 */
	public ArrayList<Participant> getParticipants(String raceClass) {
		ArrayList<Participant> result = new ArrayList<Participant>();
		for (Participant p : participants.values()) {
			if (p.isRaceClass(raceClass) && p.isRegistered()) {
				result.add(p);
			}
		}
		return result;
	}

	/**
	 * Returns the maximum number of laps for the participants, if mode is LAP
	 * race
	 * 
	 * @param raceClass
	 *            the race class (each race class can have different maximum
	 *            number of laps)
	 * @return maximum number of laps
	 * @throws IllegalArgumentException
	 *             if not lap race
	 */
	public int findMaxNrOfLaps(String raceClass) {
		if (mode != ParticipantType.LAP) {
			throw new IllegalArgumentException("Can only compute maximum nr of laps for lap races! Type was : " + mode);
		}

		int max = 0;

		for (Participant p : getParticipants(raceClass)) {
			LapRaceParticipant lp = (LapRaceParticipant) p;
			max = lp.getNumberOfLaps() > max ? lp.getNumberOfLaps() : max;
		}

		return max;
	}

	/**
	 * Adds a participant
	 * @param startNumber the start number
	 * @param info Name and additional information if available
	 */
	public void addParticipantInfo(int startNumber, String[] info) {
		Participant p = participants.get(startNumber);
		p.setExtraInfo(info);
	}

	/**
	 * Add a raceClass, unless it already exists.
	 * 
	 * @param raceClass
	 *            Name of the race class to be added.
	 */
	public void addRaceClass(String raceClass) {
		if (!raceClasses.contains(raceClass)) {
			raceClasses.addLast(raceClass);
		}
	}

	/**
	 * Returns a copy of the raceClasses list.
	 * 
	 * @return ArrayList with raceclasses.
	 */
	public ArrayList<String> getRaceClasses() {
		ArrayList<String> copy = new ArrayList<>();
		for (String raceClass : raceClasses) {
			copy.add(raceClass);
		}
		return copy;
	}
}
