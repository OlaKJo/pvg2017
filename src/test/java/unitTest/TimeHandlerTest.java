package test.java.unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.sorter.TimeHandler;
import main.java.util.Constants;

public class TimeHandlerTest {

	@Test
	public void test() {
		String start = "01.01.02";
		String finish = "02.10.01";
		String expected = "01.08.59";
		assertEquals(expected, TimeHandler.calcTotalTime(start, finish));
	}

	@Test
	public void testNegativeTimeDifference() {
		String start = "23.00.00";
		String finish = "00.10.00";
		String expected = "01.10.00"; //assumes date shift since finish < start
		assertEquals(expected,TimeHandler.calcTotalTime(start, finish));
	}
	@Test
	public void testNoTimeDifference() {
		String start = "00.00.00";
		String finish = "00.00.00";
		String expected = "00.00.00"; 
		assertEquals(expected,TimeHandler.calcTotalTime(start, finish));
	}

	@Test
	public void testNoStartTime() {
		String start = "--.--.--";
		String finish = "00.00.00";
		String expected = "--.--.--"; 
		assertEquals(expected,TimeHandler.calcTotalTime(start, finish));
	}
	@Test
	public void testNoFinishTime() {
		String start = "00.00.00";
		String finish = "--.--.--";
		String expected = "--.--.--"; 
		assertEquals(expected,TimeHandler.calcTotalTime(start, finish));
	}
	@Test
	public void testNoStartTimeNoFinishTime() {
		String start = "--.--.--";
		String finish = "--.--.--";
		String expected = "--.--.--"; 
		assertEquals(expected,TimeHandler.calcTotalTime(start, finish));
	}
	
	@Test
	public void testSortedList() {
		List<String> unsortedList = new ArrayList<String>();
		unsortedList.add("12.00.00");
		unsortedList.add("12.34.11");
		unsortedList.add("12.31.10");
		unsortedList.add("14.32.23");
		unsortedList.add("13.55.01");
		List<String> expectedList = new ArrayList<String>();
		
		expectedList.add("12.00.00");
		expectedList.add("12.31.10");
		expectedList.add("12.34.11");
		expectedList.add("13.55.01");
		expectedList.add("14.32.23");
		
		TimeHandler.sortTimes(unsortedList, "12.00.00");
		assertEquals(expectedList, unsortedList);

	}
	
	@Test
	public void testSortingListWithTimesOverMidnight(){
		List<String> midnightList = new ArrayList<String>();
		midnightList.add("22.00.00");
		midnightList.add("22.30.00");
		midnightList.add("23.00.00");
		midnightList.add("00.00.00");
		midnightList.add("00.30.00");
		midnightList.add("02.00.00");
		
		List<String> expectedTimeStamps = new ArrayList<String>();
		expectedTimeStamps.add("22.00.00");
		expectedTimeStamps.add("22.30.00");
		expectedTimeStamps.add("23.00.00");
		expectedTimeStamps.add("00.00.00");
		expectedTimeStamps.add("00.30.00");
		expectedTimeStamps.add("02.00.00");
		
		TimeHandler.sortTimes(midnightList, "22.00.00");
		assertEquals(expectedTimeStamps, midnightList);
		
	}
	
	@Test
	public void testEmptyStartTime() {
		assertEquals(Constants.EMPTY, TimeHandler.calcTotalTime(Constants.EMPTY, "22.00.00"));
		assertEquals(Constants.EMPTY, TimeHandler.calcTotalTime(null, "22.00.00"));
		
	}
	
//	@Test 
//	public void testUnreasonableTotalTime(){
//		String start = "00.02.00";
//		String finish = "00.14.00";
//		String expected = "Orimlig totaltid"; 
//		assertEquals(expected,TotalTimeCalculator.calcTotalTime(start, finish));
//	}
//	
	// this test is relevant for error handling
//	@Test
//	public void testLargeTime() {
//		String start = "00.00.00";
//		String finish = "30.00.00";
//		String expected = "Not correct time format!"; 
//		assertEquals(expected,TotalTimeCalculator.calcTotalTime(start, finish));
//	}
}
