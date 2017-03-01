package main.java.sorter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import main.java.util.Constants;

/**
 * A class of static methods used for calculating the difference between two
 * different entered times and sorting lists of times. The format used for times
 * is hh.mm.ss .
 *
 * @author team03
 */

public class TimeHandler {

	private static long millisecTime;
	public static int minLapTimeLimit;
	public static int minTotalTimeLimit;

	/**
	 * If finish time < start time it will return date shift. KEEP THAT IN MIND
	 * 
	 * @param start the start time
	 * @param finish the finish/lap time
	 * @return time the time difference
	 */
	public static String calcTotalTime(String start, String finish) {
		SimpleDateFormat df = new SimpleDateFormat("HH.mm.ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date startTime = null;
		Date finishTime = null;
		// Date limitTime = null; // orimlig starttid ska inte kollas här
		try {
			startTime = df.parse(start);
			finishTime = df.parse(finish);
			// limitTime = df.parse("00.15.00");
		} catch (ParseException | NullPointerException e) {
			return Constants.EMPTY;
		}

		millisecTime = finishTime.getTime() - startTime.getTime();
		// long millisecLimit = limitTime.getTime();
		// if (millisecTime < millisecLimit){
		// return "Orimlig totaltid";
		// }
		Date date = new Date(millisecTime);
		String totalTime = df.format(date);
		if (totalTime == "00.00.00") {
			return Constants.EMPTY;
		}
		return totalTime;
	}

	/**
	 * Method which takes in the timeStamps list and returns the same list
	 * sorted.
	 * 
	 * @param timeStamps
	 * @return timeStamps sorted
	 */
	public static List<String> sortTimes(List<String> timeStamps, String startTime) {
		Collections.sort(timeStamps);

		while (startTime != Constants.NOSTART && timeStamps.get(0).compareTo(startTime) < 0) {
			timeStamps.add(timeStamps.remove(0));
		}

		return timeStamps;

	}

	/**
	 * Checks if a lap time is above a specified lap time limit
	 * @param time The time for the lap
	 * @return false if below limit, else true
	 */
	public static boolean reasonableLapTime(String time) {

		Date lapTime;
		try {
			lapTime = calculateTotalTimeAndDate(time);
		} catch (ParseException | NullPointerException e) {
			return true;
		}

		return lapTime.getTime() >= minLapTimeLimit;
	}

	/**
	 * Checks if total time is above a specified total time limit
	 * @param time The total time
	 * @return false if below limit, else true
	 */
	public static boolean reasonableTotalTime(String time) {
		Date totTime;
		try {
			totTime = calculateTotalTimeAndDate(time);
		} catch (ParseException | NullPointerException e) {
			return true;
		}

		return totTime.getTime() >= minTotalTimeLimit;
	}

	/**
	 * Converts time to Date format
	 * @param time The time string to convert
	 * @return the time in Date format
	 * @throws ParseException
	 */
	private static Date calculateTotalTimeAndDate(String time) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("HH.mm.ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date totTime = df.parse(time);

		return totTime;
	}

	/**
	 * Calculates the difference between two lap time stamps
	 * @param time1 First lap time stamp
	 * @param time2 Second lap time stamp
	 * @return the time between laps
	 */
	public static String calcLapTime(String time1, String time2) {
		String totalTime = calcTotalTime(time1, time2);
		return totalTime.equals(Constants.EMPTY) ? "" : totalTime;
	}

}
