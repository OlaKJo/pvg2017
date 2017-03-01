package main.java.registration.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main program for loading and switching FXML scenes.
 * @author hek09lli
 *
 */
public class RegistrationApplication extends Application
{
	private Stage theStage;
	private Scene mainScene;
	private Scene splashScene;
	private RegistrationController registrationController;

	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			theStage = primaryStage;
			theStage.setMinHeight(400);
			theStage.setMinWidth(600);
			FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("SplashViewLayout.fxml"));
			Parent splashRoot = (Parent) splashLoader.load();
			splashScene = new Scene(splashRoot, 600, 400);
			SplashController splashController = splashLoader.getController();
			splashController.coupleApplication(this);
			
			FXMLLoader registrationLoader = new FXMLLoader(getClass().getResource("RegistrationViewLayout.fxml"));
			Parent registrationRoot = (Parent) registrationLoader.load();
			mainScene = new Scene(registrationRoot, 600, 400);
			registrationController = registrationLoader.getController();
			
			theStage.setScene(splashScene);
			
			theStage.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts showing the main view to get station type and station ID
	 * @param stationType the station type as chosen by roll down menu
	 * @param stationID the station ID as an entered integer
	 */
	public void showRegistrationView(int stationType, int stationID) {

		theStage.setScene(mainScene);
		registrationController.initRegistrationProgram(stationType, stationID);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}
}
