package main.java.sorter;

import main.java.util.Constants;

/**
 * Abstract class Participant, can be instantiated as LapRaceParticipant or
 * MarathonParticipant. Is created by reading data from input text files, and
 * contains the information regarding one participant
 * 
 * @author team03
 *
 */
public abstract class Participant {

	protected int startNumber;
	protected String name;
	protected String startTime = Constants.NOSTART;
	protected ErrorHandler errorHandler = new ErrorHandler();
	protected String[] extraInfo;
	protected String raceClass;

	/**
	 * Participant with a race class
	 * 
	 * @param startNumber
	 * @param raceClass
	 */
	public Participant(int startNumber, String raceClass) {
		this.name = Constants.NONAME;
		this.startNumber = startNumber;
		this.raceClass = raceClass;
	}

	/**
	 * Participant without race class
	 * 
	 * @param startNumber
	 */
	public Participant(int startNumber) {
		this.name = Constants.NONAME;
		this.startNumber = startNumber;
		this.raceClass = Constants.NO_RACE_CLASS;
	}

	/**
	 * Returns start number
	 * @return the startNumber of Participant
	 */
	public int getStartNumber() {
		return startNumber;
	}

	/**
	 * ArrayList<String> startTimes = new ArrayList<String>();
	 * 
	 * @param name
	 *            the name of this Participant
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns name
	 * @return name of this Participant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds a finish time depending on race type.
	 * 
	 * @param finishTime
	 *            on format HH.mm.ss
	 */
	public abstract void addFinishTime(String finishTime);

	/**
	 * Checks if the participant is registered.
	 * 
	 * @return true if the participant has a name
	 */
	public boolean isRegistered() {
		return name != "" && name != Constants.NONAME;
	}

	/**
	 * Checks if participant is in raceClass
	 * 
	 * @param raceClass
	 * @return if belongs to raceClass
	 */
	public boolean isRaceClass(String raceClass) {
		return this.raceClass.equals(raceClass);
	}

	public abstract String getFinishTime();

	/**
	 * Returns the start time if there is such a time, otherwise returns a
	 * string "Start?"
	 * 
	 * @return String containing start time
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time of the Participant. If the participant already has a
	 * start time the ErrorHandler is notified.
	 * 
	 * @param startTime
	 *            the start time
	 */
	public void setStartTime(String startTime) {
		if (this.startTime == Constants.NOSTART) {
			this.startTime = startTime;
		} else {
			errorHandler.addMultipleStartTimeError(startTime);
		}
	}

	/**
	 * Returns the start number and name of the participant in separated by a
	 * "; ".
	 * 
	 * @return the start number and name of the participant in separated by a
	 *         "; ".
	 */
	protected String printStartNumberAndName() {
		return startNumber + "; " + name;
	}

	/**
	 * Calculates the total time of the participant. If the time is unreasonable
	 * the ErrorHandler is notified.
	 * 
	 * @return The calculated total time.
	 */
	protected String printTotalTime() {
		String totalTime = TimeHandler.calcTotalTime(getStartTime(), getFinishTime());
		if (!TimeHandler.reasonableTotalTime(totalTime)) {
			errorHandler.setImpossibleTotalTimeError();
		}
		return totalTime;
	}

	/**
	 * Returns a string containing extra info for the participant
	 * 
	 * @return a string of info
	 */
	protected String printInfo() {
		StringBuilder stb = new StringBuilder();
		if (extraInfo != null) {
			for (String info : extraInfo) {
				stb.append(info + "; ");
			}
		}
		return stb.toString();
	}

	/**
	 * Specify the different extra fields for a participant, stored as an array
	 * of strings. Contains empty fields if info not specified for given
	 * participant.
	 * 
	 * @param info
	 */
	public void setExtraInfo(String[] info) {
		extraInfo = info;
	}
}