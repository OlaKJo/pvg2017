package main.java.registration.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.registration.Registration;
import main.java.registration.RegistrationProgram;
import main.java.util.Constants;

/**
 * Controller class for the registration view.
 * @author hek09lli
 *
 */
public class RegistrationController implements Initializable {

	@FXML
	private Label prereg1;
	@FXML
	private TextField preregnbr1;
	@FXML
	private Button registrationButton;
	@FXML
	private Button massStartButton;
	@FXML
	private Button closeButton;
	@FXML
	private Button clearButton;
	@FXML
	private Button savePreRegButton;
	@FXML
	private TextField startNumberField;
	@FXML
	private Label massStartLabel;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private Label label4;
	@FXML
	private Label label5;
	@FXML
	private Label stationTypeLabel;
	private SimpleStringProperty stationTypeText;
	@FXML
	private Label stationIdLabel;
	private SimpleStringProperty stationIdText;

	private SimpleStringProperty[] labelTexts;
	private Label[] labels;
	private RegistrationProgram regProgram;// = new RegistrationProgram();
	private Registration savedReg;

	/**
	 * @param location the URL 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelTexts = new SimpleStringProperty[5];
		labels = new Label[5];
		

		labels[0] = label1;
		labels[1] = label2;
		labels[2] = label3;
		labels[3] = label4;
		labels[4] = label5;

		for (int i = 0; i < 5; i++) {
			labelTexts[i] = new SimpleStringProperty("");
			labels[i].textProperty().bind(labelTexts[i]);
		}
		
		stationTypeText = new SimpleStringProperty("");
		stationTypeLabel.textProperty().bind(stationTypeText);
		
		stationIdText = new SimpleStringProperty("");
		stationIdLabel.textProperty().bind(stationIdText);
		
		clearButton.setDisable(true);
		savePreRegButton.setDisable(true);
		preregnbr1.setEditable(false);
		
		registrationButton.setDefaultButton(true);
	}

	/**
	 * Registers a time to a start number entered by user. Start number has to be larger than 0, and can be duplicate.
	 * Click the button when it's empty or contain non-integer values, the current time will be saved for a preregistration.
	 * 
	 * @param event button click event in RegistrationView GUI
	 */
	@FXML
	// Handles a registration click
	public void registrationButtonAction(ActionEvent event) {
		String startNumber = startNumberField.getText();
		try {
			if (Integer.parseInt(startNumberField.getText()) == Constants.EN_MASSE) {
				clearField(startNumberField);
				return;
			}
		} catch (Exception e) {
			if(prereg1.getText().isEmpty()){
				savedReg = new Registration();
				prereg1.setText(savedReg.getStartTime());
				regProgram.savePreRegistration(savedReg);
				clearButton.setDisable(false);
				savePreRegButton.setDisable(false);
				preregnbr1.setEditable(true);
				System.out.println("Preregistration or string entered");
				return;
			}
			return;
		}
		
		Registration reg = RegistrationProgram.register(Integer.parseInt(startNumber));
		regProgram.saveRegistration(reg);

		if (reg.isValid()) {
			updateRecentRegistrations(reg.toString());
			massStartButton.setDisable(true);
		}

		clearField(startNumberField);

	}

	// Clears the textfields to allow user to quickly re-type in them.
	private void clearField(TextField tf) {
		tf.clear();
		tf.requestFocus();
	}

	/**
	 * Sends a Registration with startNumber -100 to the start file, indicating mass start. This method will stop all further interaction with the GUI.
	 * 
	 * @param event
	 */
	public void massStartButtonAction(ActionEvent event) {
		Registration reg = RegistrationProgram.register(Constants.EN_MASSE);
		regProgram.saveRegistration(reg);
		startNumberField.setEditable(false);
		registrationButton.setDisable(true);
		massStartButton.setDisable(true);
		massStartLabel.setText("Mass start registrered at: " + reg.getStartTime());
		massStartLabel.setVisible(true);
		closeButton.setVisible(true);
		closeButton.requestFocus();
		clearButton.setDisable(true);
		savePreRegButton.setDisable(true);
		preregnbr1.setEditable(false);
		
	}

	/**
	 * Allows closing the window after mass start.
	 * 
	 * @param event
	 */
	public void closeButtonAction(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	// Updates the five fields with the most recent valid registrations
	private void updateRecentRegistrations(String newRegistration) {
		for (int i = 4; i > 0; i--) {
			labelTexts[i].set(labelTexts[i - 1].get());
		}
		labelTexts[0].set(newRegistration);
	}

	
	// Initializes if GUI as either a start or finish station
	public void initRegistrationProgram(int stationType, int stationID) {
		regProgram = new RegistrationProgram(stationType, stationID);

		if (stationType == Constants.REGISTER_STARTS) {
			massStartButton.setVisible(true);
		}
		
		// update fields corresponding to input station
		if (stationType == 0) {
			stationTypeText.set("Start station");
		} else {
			stationTypeText.set("Finish station");
		}
		stationIdText.set("Station ID: " + stationID);
	}

	@FXML
	/**
	 * Is run when clicking the "Spara" button. Takes the saved registration and applies the new start number.
	 * The registration is then saved in the main file, and the temp "anti-crash"-file is cleared.
	 */
	public void preregnbr1Action() {
		
		int startNumber;
		try {
			startNumber = Integer.parseInt(preregnbr1.getText());
			if (startNumber == Constants.EN_MASSE) {
				return;
			}
		} catch(Exception e) {
			return;
		} finally {
			clearField(preregnbr1);
		}
		try {
			savedReg.setStartNbr(startNumber);
		} catch (NumberFormatException e) {
			preregnbr1.clear();
			preregnbr1.requestFocus();
			return;
		}
		try {
			regProgram.saveRegistration(savedReg);

			if (savedReg.isValid()) {
				updateRecentRegistrations(savedReg.toString());
				prereg1.setText("");
				massStartButton.setDisable(true);
			}

			// Enable user to type again, without re-selecting field
			clearField(preregnbr1);
			regProgram.clearPreRegistration();
		} catch (NumberFormatException e) {
			// do nothing
			System.out.println("User tried to enter String");
		}
	}

	
	@FXML
	// Clears the pre-registration data
	public void clearButtonAction() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Clear?");
		alert.setHeaderText("Do you really want to clear the pre-registrations?");
		//alert.setContentText("");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			clearField(preregnbr1);
			prereg1.setText("");
			regProgram.clearPreRegistration();
			savePreRegButton.setDisable(true);
			clearButton.setDisable(true);
			preregnbr1.setEditable(false);
		} else {
			System.out.println("User's mind was changed");
		}
	}

	@FXML
	public void savePreReg() {
		if (preregnbr1.getText().equals("")) {
			return;
		}
		savePreRegButton.setDisable(true);
		clearButton.setDisable(true);
		preregnbr1.setEditable(false);
		preregnbr1Action();
	}
}
