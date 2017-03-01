package main.java.sorter;

import java.util.ArrayList;
import java.util.List;

import main.java.util.Constants;

/**
 * Handles errors in time registrations specified by configuration, such as
 * impossibly fast times or multiple times.
 * 
 * @author team03
 *
 */
public class ErrorHandler {

	private List<String> multipleStartTimes = new ArrayList<String>();
	private List<String> multipleFinishTimes = new ArrayList<String>();
	private boolean impossibleTotalTime = false;
	private boolean impossibleLapTime = false;

	public String print() {
		StringBuilder sb = new StringBuilder();

		if (impossibleTotalTime) {
			sb.append("; " + Constants.IMPOSSIBLE_TOTAL);
		}

		if (impossibleLapTime) {
			sb.append("; " + Constants.IMPOSSIBLE_LAP);
		}

		for (String s : multipleStartTimes) {
			sb.append(s);
		}

		for (String s : multipleFinishTimes) {
			sb.append(s);
		}

		return sb.toString();
	}

	/**
	 * Adds an error message about multiple start times if multiple start times
	 * exist
	 * 
	 * @param time
	 *            the time to check
	 */
	public void addMultipleStartTimeError(String time) {
		if (multipleStartTimes.size() == 0) {
			multipleStartTimes.add("; " + Constants.SEVERAL_STARTS);
		}

		multipleStartTimes.add(" " + time);
	}

	/**
	 * Adds an error message about multiple finish times if multiple finish
	 * times exist
	 * 
	 * @param time
	 *            the time to check
	 */
	public void addMultipleFinishTimeError(String time) {
		if (multipleFinishTimes.size() == 0) {
			multipleFinishTimes.add("; " + Constants.SEVERAL_FINISH);
		}

		multipleFinishTimes.add(" " + time);
	}

	/**
	 * Adds an error message about impossible total time if total time is below
	 * total time limit
	 * 
	 * @param time
	 *            the time to check
	 */
	public void setImpossibleTotalTimeError() {
		impossibleTotalTime = true;
	}

	/**
	 * Adds an error message about impossible lap time if any lap time is below
	 * lap time limit
	 * 
	 * @param time
	 *            the time to check
	 */
	public void setImpossibleLapTimeError() {
		impossibleLapTime = true;

	}
}
