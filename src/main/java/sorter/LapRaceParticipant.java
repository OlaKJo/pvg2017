package main.java.sorter;

import java.util.ArrayList;
import java.util.List;
import main.java.util.Constants;

/**
 * The LapRaceParticipant is a subclass to Participant.
 * LapRaceParticipants handles times and other info for a participant in
 * a lap race.
 * @author team03
 */
public class LapRaceParticipant extends Participant {

	protected List<String> finishTimes = new ArrayList<String>();
	
	
	public LapRaceParticipant(int startNumber, String raceClass) {
		super(startNumber, raceClass);
		finishTimes.add(Constants.NOFINISH);
	}
	
	public LapRaceParticipant(int startNumber) {
		super(startNumber);
		finishTimes.add(Constants.NOFINISH);
	}

	/**
	 * Adds timeStamp. (Times will not be ordered so sorting will have to be
	 * done later). No limit to number of timeStamps YET DIFFERENT
	 * 
	 * @param timeStamp
	 */
	public void addFinishTime(String finishTime) {
		if (finishTimes.get(0) == Constants.NOFINISH) {
			finishTimes.set(0, finishTime);
			return;
		}
		finishTimes.add(finishTime);
	}

	/**
	 * Returns the number of laps that the participant has made
	 * 
	 * @return number of laps for the participant, 0 if start time is missing
	 */
	public int getNumberOfLaps() {
		if (finishTimes.get(0) == Constants.NOFINISH) {
			return 0;
		}

		return finishTimes.size();
	}

	/**
	 * Returns a copy of lapfinishTimes
	 * 
	 * @return ArrayList<String> of finishTimes
	 */
	public List<String> getFinishTimes() {
		List<String> copyStamps = new ArrayList<String>();
		for (String s : finishTimes) {
			copyStamps.add(s);
		}
		return copyStamps;
	}

	/**
	 * Sorts the laptimes in the lapfinishTimes list
	 */
	public void sortFinishTimes() {
		finishTimes = TimeHandler.sortTimes(getFinishTimes(), startTime);
	}

	/**
	 * Temporary the toString for marathon. Will have to be moved later.
	 * Start nbr; Name; Total time; Start time; Finish time
	 * 
	 * @return The result for a marathon participant.
	 */

	public String printLaps(int laps) {
		sortFinishTimes();
		StringBuilder sb = new StringBuilder();

		sb.append(printStartNumberAndName()+ "; ");
		sb.append(printInfo());

		// Antal varv
		sb.append(getNumberOfLaps() + "; ");

		// Totaltid;
		sb.append(printTotalTime() + "; ");

		// Varvtider
		appendLaps(laps, sb);

		// Starttid
		String start = getStartTime();
		sb.append(start + "; ");

		// Varvningstider
		appendLapTimeStamps(laps, sb);

		// Måltid
		String finish = getLapFinish(laps);
		sb.append(finish);

		sb.append(errorHandler.print());
		
		return sb.toString();

	}
	
	/**
	 * Generates participants results in sorted format
	 * @param laps
	 * @return results in sortedList format 
	 */
	public String printSortedLaps(int laps) {
		sortFinishTimes();

		StringBuilder sb = new StringBuilder();

		sb.append(printStartNumberAndName()+ "; ");
		sb.append(printInfo());

		// Antal varv
		sb.append(getNumberOfLaps() + "; ");

		// Totaltid;
		sb.append(printTotalTime() + "; ");

		// Varvtider
		appendLaps(laps, sb);
		sb.replace(sb.length()-2, sb.length(), "");
		sb.append(errorHandler.print());
		
		return sb.toString();

	}

	/**
	 * Appends lap time stamps to print string
	 * 
	 * @param laps number of laps
	 * @param sb the string builder to append to
	 */
	private void appendLapTimeStamps(int laps, StringBuilder sb) {
		for (int i = 0; i < laps - 1; i++) {
			if (i < finishTimes.size()) {
				String s = finishTimes.get(i);
				if (!s.equals(Constants.NOFINISH))
					sb.append(finishTimes.get(i) + "; ");
				else
					sb.append("; ");
			} else {
				sb.append("; ");
			}
		}
	}

	/**
	 * Appends lap times to print string
	 * 
	 * @param laps number of laps
	 * @param sb the string builder to append to
	 */
	private void appendLaps(int laps, StringBuilder sb) {
		if (finishTimes.size() > 0) {
			String lapTime = TimeHandler.calcLapTime(getStartTime(), finishTimes.get(0) + "; ");
			if (!TimeHandler.reasonableLapTime(lapTime )) {
				errorHandler.setImpossibleLapTimeError();
			}
			sb.append(lapTime + "; ");
			for (int i = 1; i <= laps - 1; i++) {
				if (i < finishTimes.size()) {
					lapTime = TimeHandler.calcLapTime(finishTimes.get(i - 1), finishTimes.get(i));
					sb.append(lapTime + "; ");
					
					if (!TimeHandler.reasonableLapTime(lapTime)) {
						errorHandler.setImpossibleLapTimeError();
					}
				} else {
					sb.append("; ");
				}
			}
	
		} else {
			for (int i = 1; i <= laps; i++) {
				sb.append("; ");
			}
		}
	}

	/**
	 * Gets the finishTime no i.
	 * 
	 * @param i the index of the finishTime to get.
	 * @return the finishTime in string.
	 */
	public String getSpecificFinishTime(int i) { // TODO: change to private
		return finishTimes.get(i);
	}

	/**
	 * Returns the finish time if there is such a time, otherwise returns a
	 * string "Finish?"
	 * 
	 * @return String containing finish time
	 */
	public String getFinishTime() {
		if (finishTimes.isEmpty()) {
			return Constants.NOFINISH;
		}
		return finishTimes.get(finishTimes.size() - 1);
	}

	/**
	 * Returns finish time for lap race
	 * @param laps number of laps
	 * @return finish lap time
	 */
	private String getLapFinish(int laps) {
	    if (finishTimes.size() < laps && !finishTimes.get(0).equals(Constants.NOFINISH)) {
			return "";
		}
		return finishTimes.get(finishTimes.size() - 1);
	}

}
