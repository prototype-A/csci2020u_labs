package csci2020u.lab05;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	TableView<StudentRecord> studentTable;


	@Override
	public void start(Stage primaryStage) throws Exception {

		// Set the window title
		primaryStage.setTitle("Student Marks");

		// Grades table
		studentTable = new TableView<>();
		studentTable.setItems(DataSource.getAllMarks());
		studentTable.setEditable(false);
		
		// Grades table columns
		TableColumn<StudentRecord, String> sidCol = new TableColumn<>("SID");
		sidCol.setMinWidth(200);
		sidCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));

		TableColumn<StudentRecord, Float> asmtsMarkCol = new TableColumn<>("Assignments");
		asmtsMarkCol.setMinWidth(120);
		asmtsMarkCol.setCellValueFactory(new PropertyValueFactory<>("assignmentsMark"));

		TableColumn<StudentRecord, Float> midtermMarkCol = new TableColumn<>("Midterm");
		midtermMarkCol.setMinWidth(120);
		midtermMarkCol.setCellValueFactory(new PropertyValueFactory<>("midtermMark"));

		TableColumn<StudentRecord, Float> finalExamMarkCol = new TableColumn<>("Final Exam");
		finalExamMarkCol.setMinWidth(120);
		finalExamMarkCol.setCellValueFactory(new PropertyValueFactory<>("examMark"));

		TableColumn<StudentRecord, Float> finalMarkCol = new TableColumn<>("Final Exam");
		finalMarkCol.setMinWidth(120);
		finalMarkCol.setCellValueFactory(new PropertyValueFactory<>("finalMark"));

		TableColumn<StudentRecord, Character> letterGradeCol = new TableColumn<>("Letter Grade");
		letterGradeCol.setMinWidth(120);
		letterGradeCol.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

		// Set columns in table
		studentTable.getColumns().setAll(sidCol, asmtsMarkCol, midtermMarkCol, 
								finalExamMarkCol, finalMarkCol, letterGradeCol);


		// Add student record section
		GridPane addSection = new GridPane();
		addSection.setPadding(new Insets(10, 10, 10, 10));
		addSection.setVgap(10);
		addSection.setHgap(10);
		
		Label sidLabel = new Label("SID:");
		addSection.add(sidLabel, 0, 0);
		TextField sidField = new TextField();
		sidField.setPromptText("Student ID");
		addSection.add(sidField, 1, 0);

		Label asmtsMarkLabel = new Label("Assignments:");
		addSection.add(asmtsMarkLabel, 2, 0);
		TextField asmtsMarkField = new TextField();
		asmtsMarkField.setPromptText("Assignments Mark/100");
		addSection.add(asmtsMarkField, 3, 0);

		Label midtermMarkLabel = new Label("Midterm:");
		addSection.add(midtermMarkLabel, 0, 1);
		TextField midtermMarkField = new TextField();
		midtermMarkField.setPromptText("Midterm Mark/100");
		addSection.add(midtermMarkField, 1, 1);

		Label examMarkLabel = new Label("Final Exam:");
		addSection.add(examMarkLabel, 2, 1);
		TextField examMarkField = new TextField();
		examMarkField.setPromptText("Final Exam Mark/100");
		addSection.add(examMarkField, 3, 1);

		// Add new student record button
		Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Get field information
				String sid = sidField.getText();
				float asmtsMark = Float.parseFloat(asmtsMarkField.getText());
				float midtermMark = Float.parseFloat(midtermMarkField.getText());
				float examMark = Float.parseFloat(examMarkField.getText());
				
				StudentRecord newStudentRecord = new StudentRecord(sid, asmtsMark, midtermMark, examMark);

				// Add student record info to table
				studentTable.getItems().add(newStudentRecord);

				// Clear the fields
				sidField.setText("");
				midtermMarkField.setText("");
				asmtsMarkField.setText("");
				examMarkField.setText("");
			}
		});
		addSection.add(addButton, 1, 4);


		BorderPane layout = new BorderPane();
		layout.setCenter(studentTable);
		layout.setBottom(addSection);

		// Create the application scene
		Scene scene = new Scene(layout, 800, 600);

		// Set the application scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
