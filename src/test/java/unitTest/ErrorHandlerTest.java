package test.java.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.sorter.ErrorHandler;
import main.java.util.Constants;

public class ErrorHandlerTest {
	
	@Test
	public void testMultipleStartTimes() {
		ErrorHandler eh = new ErrorHandler();
		eh.addMultipleStartTimeError("12.00.00");
		assertEquals(eh.print(), "; " + Constants.SEVERAL_STARTS + " 12.00.00");
	}
	
	@Test
	public void testMultipleFinishTimes() {
		ErrorHandler eh = new ErrorHandler();
		eh.addMultipleFinishTimeError("13.00.00");
		assertEquals(eh.print(), "; " + Constants.SEVERAL_FINISH + " 13.00.00");
	}
	
	@Test
	public void testMultipleFinishAndStartTimesAndImpossibleTotalTime() {
		ErrorHandler eh = new ErrorHandler();
		eh.addMultipleStartTimeError("11.01.00");
		eh.addMultipleFinishTimeError("14.03.00");
		eh.addMultipleStartTimeError("12.00.20");
		eh.setImpossibleTotalTimeError();
		eh.addMultipleStartTimeError("13.00.00");
		eh.addMultipleFinishTimeError("15.00.00");
		
		assertEquals(eh.print(), "; " + Constants.IMPOSSIBLE_TOTAL + "; " + Constants.SEVERAL_STARTS + " 11.01.00 12.00.20 13.00.00; " + Constants.SEVERAL_FINISH + " 14.03.00 15.00.00");
	}

	@Test
	public void testImpossibleTotalTime() {
		ErrorHandler eh = new ErrorHandler();
		eh.setImpossibleTotalTimeError();
		assertEquals(eh.print(), "; " + Constants.IMPOSSIBLE_TOTAL);
		
	}
}
