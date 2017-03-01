package main.java.registration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import main.java.util.Constants;


/**
 * Handles each registration object. Tying start time to a start number.
 * @author Team03
 */
public class Registration {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss");

	private int startNbr;
	private java.time.LocalTime time;

	/**
	 * Initializing registration object
	 * @param startNbr
	 */
	public Registration(int startNbr) {
		this.startNbr = startNbr;
		time = LocalTime.now();
	}
	/**
	 * Is used for preregistrations
	 */
	public Registration() {
		time = LocalTime.now();
	}
	
	/**
	 * used to set startNumber for preregistrations
	 * @param startNbr
	 */
	public void setStartNbr(int startNbr) {
	    this.startNbr = startNbr;
	}
	
	/**
	 * used to get startNumber for preregistrations
	 * @return start number of the registration object
	 */
	public int getStartNbr() {
		return startNbr;
	}
	
	/**
	 * used to get startTime for the registrations in a specified format
	 * @return the time coupled with the current registration object in format HH.mm.ss
	 */
	public String getStartTime() {
		return time.format(formatter);
	}

	/**
	 * Returns a string containing start number and the registered time
	 * @return string with start number and registered time
	 */
	@Override
	public String toString() {
		return startNbr + "; " + getStartTime();
	}
	
	/**
	 * Returns true if startNbr is neither negative nor equal to the value for mass start
	 * @return boolean if the start number is valid. 
	 */
	public boolean isValid() {
		return startNbr > 0 || startNbr == Constants.EN_MASSE;
	}
}
