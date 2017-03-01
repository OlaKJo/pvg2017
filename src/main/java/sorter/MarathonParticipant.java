package main.java.sorter;

import main.java.util.Constants;

/**
 * MarathonParticipant is a subclass to Participant.
 * MarathonParticipant handles times and other info for a participant in
 * a marathon race.
 * @author team03
 *
 */
public class MarathonParticipant extends Participant {
	private String finishTime;

	/**
	 * Constructor
	 * 
	 * @param startNumber
	 *            unique start number of Participant
	 */
	public MarathonParticipant(int startNumber, String raceClass) {
		super(startNumber, raceClass);
		finishTime = Constants.NOFINISH;
	}
	
	public MarathonParticipant(int startNumber ) {
		super(startNumber);
		finishTime = Constants.NOFINISH;
	}

	/**
	 * Adds finishTime, if there's no goal time.
	 * 
	 * @param finishTime
	 *            on format HH.mm.ss
	 */
	public void addFinishTime(String newFinishTime) {
		if (finishTime == Constants.NOFINISH) {
			finishTime = newFinishTime;
		} else {
			errorHandler.addMultipleFinishTimeError(newFinishTime);
		}
	}

	/**
	 * Prints a result line for participant
	 * 
	 * For registered participants
	 * 
	 * @return Start nbr; Name; Total time; Start time; Finish time
	 * 
	 *         For unregistered participants
	 * @return Start nbr; Time; Time type
	 */
	
	public String printResult() {
		StringBuilder sb = new StringBuilder();

		sb.append(printStartNumberAndName() + "; ");
		sb.append(printInfo());

		sb.append(printTotalTime() + "; ");

		sb.append(getStartTime() + "; ");
		sb.append(getFinishTime());
	
		sb.append(errorHandler.print());
		
		return sb.toString();
	}
	
	/**
	 * Returns result string in sorted list format
	 * 
	 * @return result string in sorted list format
	 */
	public String printSortedResult() {
		StringBuilder sb = new StringBuilder();

		sb.append(printStartNumberAndName() + "; ");
		sb.append(printInfo());
		
		sb.append(printTotalTime());
		
		sb.append(errorHandler.print());
		
		return sb.toString();
	}

	/**
	 * Returns the finish time for the participant
	 * @return finishTime the finish time
	 */
	@Override
	public String getFinishTime() {
		return finishTime;
	}

}
