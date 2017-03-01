package main.java.util;

/**
 * A collection of constants used across the system.
 * @author hek09lli
 *
 */
public class Constants {
	public static final int EN_MASSE = -100;
	public static final int REGISTER_STARTS = 0;
	public static final int REGISTER_FINISHES = 1;
	public static final String EMPTY = "--.--.--";

	public static final String NOSTART = "Start?";
	public static final String NOFINISH = "Finish?";


	public static final String NONAME = "Name missing?";
	public static final boolean UNREGISTERED = false;
	public static final boolean REGISTERED = true;
	public static final String START_TIME = "Start";
	public static final String FINISH_TIME = "Finish";
	public static final String NO_RACE_CLASS = "No class";
	
	public static final String SEVERAL_STARTS = "Several start times?";
	public static final String SEVERAL_FINISH = "Several finish times?";
	public static final String IMPOSSIBLE_TOTAL = "Impossible total time?";
	public static final String IMPOSSIBLE_LAP = "Impossible lap time?";

	// Messages
	public static final String PARTICIPANT_NOT_FOUND = "Participant was not found";
	public static final String ERROR_READER = "ERROR reading line";

	public enum ParticipantType {
		MARATHON, LAP, STAGE
	}

}
