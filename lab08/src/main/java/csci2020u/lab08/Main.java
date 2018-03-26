package csci2020u.lab08;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.StringTokenizer;


public class Main extends Application {

	private String currFileName;
	private TableView<StudentRecord> studentTable;


	@Override
	public void start(Stage primaryStage) throws Exception {

		// Menu
		Menu fileMenu = new Menu("File");

		// Menu Items
		MenuItem newMenuItem = new MenuItem("New");
		newMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newMenuItem.setOnAction(event -> newFile());
		MenuItem openMenuItem = new MenuItem("Open");
		openMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		openMenuItem.setOnAction(event -> load());
		MenuItem saveMenuItem = new MenuItem("Save");
		saveMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		saveMenuItem.setOnAction(event -> save());
		MenuItem saveAsMenuItem = new MenuItem("Save As...");
		saveAsMenuItem.setOnAction(event -> saveAs());
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		exitMenuItem.setOnAction(event -> System.exit(0));

		fileMenu.getItems().setAll(newMenuItem, new SeparatorMenuItem(), openMenuItem, new SeparatorMenuItem(), saveMenuItem, saveAsMenuItem, new SeparatorMenuItem(), exitMenuItem);

		// Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().setAll(fileMenu);

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
		layout.setTop(menuBar);
		layout.setCenter(studentTable);
		layout.setBottom(addSection);

		// Set the window title
		primaryStage.setTitle("Lab 08");

		// Create the application scene
		Scene scene = new Scene(layout, 800, 600);

		// Set the application scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Initializes the table to an empty table
	 */
	private void newFile() {
		currFileName = null;
		studentTable.setItems(FXCollections.observableArrayList());
	}

	/**
	 * Saves all data to the currently-opened file
	 */
	private void save() {

		if (currFileName == null) {
			saveAs();
		}

		try {
			// Save to current file
			BufferedWriter fileOut = new BufferedWriter(new PrintWriter(new File(currFileName)));
			writeToFile(fileOut, studentTable.getItems());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Allows the user to save the current data to a file with 
	 * a user-specified file name and location
	 */
	private void saveAs() {

		try {
			// Open save prompt
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save as...");
			fileChooser.setInitialFileName("*.csv");
			fileChooser.setInitialDirectory(new File("."));
			File outFile = fileChooser.showSaveDialog(null);
			currFileName = outFile.getName();

			// Save to file
			BufferedWriter fileOut = new BufferedWriter(new PrintWriter(outFile));
			writeToFile(fileOut, studentTable.getItems());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load data from a .csv file to display
	 */
	private void load() {

		try {

			// Display open file prompt
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open file");
			fileChooser.setInitialFileName("*.csv");
			fileChooser.setInitialDirectory(new File("."));
			BufferedReader fileReader = new BufferedReader(new FileReader(fileChooser.showOpenDialog(null)));

			// Read data
			ObservableList<StudentRecord> data = FXCollections.observableArrayList();
			String line;
			StringTokenizer tokens;
			while ((line = fileReader.readLine()) != null) {
				tokens = new StringTokenizer(line, ",");
				data.add(new StudentRecord(tokens.nextToken(), Float.parseFloat(tokens.nextToken()), Float.parseFloat(tokens.nextToken()), Float.parseFloat(tokens.nextToken())));
			}

			// Display data
			studentTable.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the items of the ObservableList of StudentRecords
	 * to file
	 *
	 * @param writer The BufferedWriter output stream to write to
	 * @param data The ObservableList of StudentRecord data to write
	 *
	 * @throws IOException if an I/O error occurs while writing to file
	 */
	private void writeToFile(BufferedWriter writer, ObservableList<StudentRecord> data) throws IOException {

		// Iterate over and save table data as comma-separated values
		Iterator<StudentRecord> dataIter = data.iterator();
		StudentRecord currRecord;
		while (dataIter.hasNext()) {
			currRecord = dataIter.next();
			writer.write(currRecord.getStudentID() + "," + currRecord.getAssignmentsMark() + "," + currRecord.getMidtermMark() + "," + currRecord.getExamMark() + "\n");
		}

		// Close the writer after we're done with it
		writer.close();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
