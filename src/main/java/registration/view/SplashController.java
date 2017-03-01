package main.java.registration.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
/**
 * Controller class for the view that prompts the user about station type and ID.
 * @author hek09lli
 *
 */
public class SplashController implements Initializable {

	RegistrationApplication regApp;

	@FXML
	private ChoiceBox choiceBox;

	@FXML
	private Button registrationButton;

	@FXML
	private TextField IDField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceBox.setItems(FXCollections.observableArrayList("Start", "Finish (or lap time)"));
		choiceBox.getSelectionModel().selectFirst();
	}

	public void startRegistrationButtonAction() {
		int choice = choiceBox.getSelectionModel().getSelectedIndex();
		try {
			int id = Integer.parseInt(IDField.getText());
			System.out.println();
			regApp.showRegistrationView(choice, id);
		} catch (NumberFormatException e) {
			// do nothing
			System.out.println("Bad station number");
		}
	}

	public void coupleApplication(RegistrationApplication app) {
		regApp = app;
	}
	
	public void submitIDFieldAction(ActionEvent event) {
		startRegistrationButtonAction();
	}
}
