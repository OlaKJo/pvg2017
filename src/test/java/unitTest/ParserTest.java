package test.java.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.sorter.Parser;

public class ParserTest {
	
	@Test
	public void correctParseSubstring() {
		String toParse = "123; 12.00.15";
		String firstString = "123";
		String secondString = "12.00.15";
		assertEquals(firstString, Parser.parse(toParse)[0]);
		assertEquals(secondString, Parser.parse(toParse)[1]);
	}
	@Test 
	public void parseName() {
		String firstString = "15";
		String secondString = "Calle Kitemäster";
		String toParse = firstString + "; " + secondString;
		assertEquals(firstString, Parser.parse(toParse)[0]);
		assertEquals(secondString, Parser.parse(toParse)[1]);
	}
	
	
	@SuppressWarnings("deprecation")
	@Test (expected = NullPointerException.class)
	public void outOfBoundsTest() {
		Parser.parse("");		
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected = NullPointerException.class)
	public void illegalInputNullTest() {
		Parser.parse(null);		
	}
}
