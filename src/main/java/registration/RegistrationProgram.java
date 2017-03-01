package main.java.registration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import main.java.util.Constants;

/**
 * Registration program - used to create registrations and preregistrations.
 * @author Team03
 *
 */
public class RegistrationProgram {

	private Path file;
	private Path preRegFile;

	/**
	 * Registration program, used to write registration files
	 * @param stationType The specified station type
	 * @param id The station ID chosen by user
	 */
	public RegistrationProgram(int stationType, int id) {

		preRegFile = Paths.get("preregtimes");
		String path = "registered_times/";

		if (stationType == Constants.REGISTER_STARTS) {
			path += "start_" + id;
		} else if (stationType == Constants.REGISTER_FINISHES) {
			path += "finish_" + id;
		}

		file = Paths.get(path);
		File f = file.toFile();
		if (!f.exists()) {
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates new registration
	 * @param startNbr Start number associated with the registration
	 * @return the created Registration object
	 */
	public static Registration register(int startNbr) {
		return new Registration(startNbr);
	}
	
	/**
	 * Creates new mass start registration 
	 * @return the created Registration object
	 */
	public static Registration massRegistration() {
		return new Registration(Constants.EN_MASSE);
	}

	/**
	 * Writes registration to file
	 * @param registration The registration object to be saved
	 */
	public void saveRegistration(Registration registration) {
		if (registration.isValid()) {
			try {
				Files.write(file, Arrays.asList(registration.toString()), Charset.forName("UTF-8"),
						StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Writes pregistration to file, in case of system crash this is saved as backup
	 * @param registration The registration object to be saved
	 */
	public void savePreRegistration(Registration registration) {

		try {
			Files.write(preRegFile, Arrays.asList(registration.toString()), Charset.forName("UTF-8"),
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clears pregregistration backup
	 */
	public void clearPreRegistration() {
		try {
			Files.write(preRegFile, Arrays.asList(""), Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
