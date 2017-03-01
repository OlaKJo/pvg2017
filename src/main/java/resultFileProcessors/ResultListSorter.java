package main.java.resultFileProcessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.sorter.LapRaceParticipant;
import main.java.sorter.Participant;
import main.java.sorter.TimeHandler;
import main.java.util.Constants.ParticipantType;

/**
 * Sorts Arraylist of Participant by either total time or number of laps and
 * time.
 * 
 * @author Team03
 *
 */
public class ResultListSorter {

	/**
	 * 
	 * @param pathToRead
	 *            is the path to result file to be converted to html table
	 */
	public ResultListSorter(ArrayList<Participant> unsorted, ParticipantType type) {
		switch (type) {
		case MARATHON:
			sortByTotalTime(unsorted);
			break;
		case LAP:
			sortByNbrOfLapsAndTime(unsorted);
			break;
		default:
			break;
		}

	}

	/**
	 * Sorts the Array by time, using Collections.sort(...), the Array is
	 * updated by reference
	 * 
	 * @param unsorted
	 *            an unsorted ArrayList<Participant>
	 * 
	 */
	public void sortByTotalTime(ArrayList<Participant> unsorted) {
		orderByTime(unsorted);
	}

	/**
	 * Sorts the Array by number of laps and then by time with participants that
	 * have the same number of laps , using Collections.sort(...), the Array is
	 * updated by reference
	 * 
	 * @param unsorted
	 *            an unsorted ArrayList<Participant>
	 * 
	 */
	public void sortByNbrOfLapsAndTime(ArrayList<Participant> unsorted) {
		orderByNumberOfLapsAndTime(unsorted);

	}

	private static void orderByTime(List<Participant> participants) {

		Collections.sort(participants, new Comparator<Object>() {

			public int compare(Object o1, Object o2) {
				return compareTotalTime(o1, o2);
			}
		});
	}

	private static void orderByNumberOfLapsAndTime(List<Participant> participants) {

		Collections.sort(participants, new Comparator<Object>() {

			public int compare(Object o1, Object o2) {
				Integer x1 = ((LapRaceParticipant) o1).getNumberOfLaps();
				Integer x2 = ((LapRaceParticipant) o2).getNumberOfLaps();

				int sComp = x2.compareTo(x1);

				if (sComp != 0) {
					return sComp;
				} else {
					return compareTotalTime(o1, o2);
				}
			}

			/**
			 * @param o1
			 * @param o2
			 * @return
			 */

		});
	}

	private static int compareTotalTime(Object o1, Object o2) {
		String y1 = TimeHandler.calcTotalTime(((Participant) o1).getStartTime(), ((Participant) o1).getFinishTime());
		String y2 = TimeHandler.calcTotalTime(((Participant) o2).getStartTime(), ((Participant) o2).getFinishTime());

		// If time is unreasonable it will be sorted to last place, without
		// changing the participants actual time
		if (!TimeHandler.reasonableTotalTime(y1)) {
			y1 = "00.00.00";
		}
		if (!TimeHandler.reasonableTotalTime(y2)) {
			y2 = "00.00.00";
		}

		if (TimeHandler.reasonableTotalTime(y1) && TimeHandler.reasonableTotalTime(y2)) {
			return y1.compareTo(y2);
		} else if (TimeHandler.reasonableTotalTime(y1) && !TimeHandler.reasonableTotalTime(y2)) {
			return -1;
		} else if (!TimeHandler.reasonableTotalTime(y1) && TimeHandler.reasonableTotalTime(y2)) {
			return 1;
		} else {
			return 0;
		}
	}

}
