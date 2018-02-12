package csci2020u.lab04;

import org.apache.commons.validator.routines.EmailValidator;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

	private TextField usernameField;
	private PasswordField passwordField;
	private TextField fullNameField;
	private TextField emailField;
	private Label invalidEmailLabel;
	private TextField phoneNumField;
	private DatePicker birthDatePicker;
	private Button registerButton;


	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Application window title
		primaryStage.setTitle("Lab 04");

		// Initialize the GridPane
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setHgap(10);
		gp.setVgap(10);

		// Initialize input fields
		usernameField = new TextField();
		passwordField = new PasswordField();
		fullNameField = new TextField();
		emailField = new TextField();
		phoneNumField = new TextField();
		birthDatePicker = new DatePicker();

		// Initialize error labels
		invalidEmailLabel = new Label();

		// Register button
		registerButton = new Button("Register");
		registerButton.setDefaultButton(true);
		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!EmailValidator.getInstance(true).isValid(emailField.getText())) {
					// Validate email and phone # input
					invalidEmailLabel.setText("Invalid E-Mail Address");
					invalidEmailLabel.setTextFill(new Color(1, 0, 0, 1.0));
				} else {
					// Print results
					System.out.println("\nNew Registrant: " + 
										"\nUsername: " + usernameField.getText() + 
										"\nPassword: " + passwordField.getText() + 
										"\nFull Name: " + fullNameField.getText() + 
										"\nE-Mail: " + emailField.getText() + 
										"\nPhone #: " + phoneNumField.getText() + 
										"\nDate of Birth: " + 
										birthDatePicker.getEditor().getText());

					// Clear input fields
					usernameField.clear();
					passwordField.clear();
					fullNameField.clear();
					emailField.clear();
					invalidEmailLabel.setText("");
					phoneNumField.clear();
					birthDatePicker.getEditor().clear();
				}
			}
		});

		// Add Labels
		gp.add(new Label("Username: "), 0, 0);
		gp.add(new Label("Password: "), 0, 1);
		gp.add(new Label("Full Name: "), 0, 2);
		gp.add(new Label("E-mail: "), 0, 3);
		gp.add(new Label("Phone #: "), 0, 4);
		gp.add(new Label("Birth Date: "), 0, 5);
		gp.add(invalidEmailLabel, 2, 3);

		// Add input fields
		gp.add(usernameField, 1, 0);
		gp.add(passwordField, 1, 1);
		gp.add(fullNameField, 1, 2);
		gp.add(emailField, 1, 3);
		gp.add(phoneNumField, 1, 4);
		gp.add(birthDatePicker, 1, 5);

		// Add register button
		gp.add(registerButton, 1, 6);

		// Create the application scene
		Scene scene = new Scene(gp, 600, 400);

		// Set the application scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
