package csci2020u.lab05;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
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

		BorderPane layout = new BorderPane();
		layout.setCenter(studentTable);

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
