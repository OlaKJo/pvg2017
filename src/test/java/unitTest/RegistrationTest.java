package test.java.unitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import main.java.registration.Registration;
import main.java.util.Constants;

public class RegistrationTest {
	
	Registration reg;
	
	@Before
	public void setUp() throws Exception {
		reg = new Registration(23);
	}

	@Test
	public void hasStartNumber() {
		
		assertEquals(23, reg.getStartNbr());
	}

	@Test
	public void hasStartTime() {
		LocalTime time = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss");
		assertEquals(time.format(formatter), reg.getStartTime());
	}
	
	@Test
	public void startTimeWithoutNumber() {
		reg = new Registration();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss");
		assertEquals(LocalTime.now().format(formatter), reg.getStartTime());
	}

	@Test
	public void printRegistration() {
		LocalTime time = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss");
		assertEquals("23; " + time.format(formatter), reg.toString());
	}
	
	@Test
	public void isValidRegistration() {
		assertTrue(reg.isValid());
		
		reg.setStartNbr(-101);
		assertEquals(-101, reg.getStartNbr());
		assertFalse(reg.isValid());
		
		reg.setStartNbr(Constants.EN_MASSE);
		assertTrue(reg.isValid());
	}
}
