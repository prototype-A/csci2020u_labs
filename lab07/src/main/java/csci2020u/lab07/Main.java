package csci2020u.lab07;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Scanner;


public class Main extends Application {

	private final static int initWinWidth = 800;
	private final static int initWinHeight = 480;
	private Canvas canvas;
	private ArrayList<String> warningTypes;
	private TreeMap<String, Integer> data;
	private double dataSize = 0.0;
	private String fileName;
	private File file;


	@Override
	public void start(Stage primaryStage) throws Exception {

		fileName = "weather-warnings-2015.csv";


		Group rootGroup = new Group();
		Scene scene = new Scene(rootGroup, initWinWidth, initWinHeight, Color.BLACK);

		// Drawing canvas
		canvas = new Canvas();
		canvas.setWidth(initWinWidth);
		canvas.setHeight(initWinHeight);
		rootGroup.getChildren().add(canvas);

		try {
			// Open and read .csv file contents
			file = new File(fileName);
			data = loadCSVData(file);

			// Draw the pie chart from the .csv data
			drawPieChart();

		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace();
		}

		// Application window
		primaryStage.setTitle(getNameFromFile(file));
		primaryStage.setScene(scene);
		primaryStage.show();		
	}

	private TreeMap<String, Integer> loadCSVData(File file) throws IOException {
	
		// Add warnings
		String[] WARNING_TYPES = { "FLASH FLOOD", "SEVERE THUNDERSTORM", "SPECIAL MARINE", "TORNADO" }; 
		warningTypes = new ArrayList<String>();
		for (String warning: WARNING_TYPES) {
			warningTypes.add(warning);
		}

		TreeMap<String, Integer> dataMap = new TreeMap<>();

		// Read file contents
		Scanner scanner = new Scanner(file);

		String[] currLine = new String[6];
		while (scanner.hasNext()) {
			currLine = scanner.nextLine().split(",");
			incrementCount(currLine[5], dataMap);
		}

		return dataMap;
	}

	/**
	 * Increments the count of a word in a TreeMap<String, Integer> object
	 *
	 *
	 * @param word The word to increment the count of
	 * @param map The TreeMap<String, Integer> that keeps the count of words
	 */
	private void incrementCount(String word, TreeMap<String, Integer> map) {
		if (map.containsKey(word)) {
			// Map already contains word and its count: Increment the count by 1
			map.put(word, map.get(word) + 1);
		} else {
			// Add new word to map with its count at 1
			map.put(word, 1);
		}

		dataSize++;
	}

	private void drawPieChart() {

		Color[] colors = { Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON };
		GraphicsContext gc = canvas.getGraphicsContext2D();
		double maxWidth = canvas.getWidth();
		double maxHeight = canvas.getHeight();
		double widthPadding = maxWidth / 14;
		double heightPadding = maxHeight / 10;
		double legendX = widthPadding;
		double legendY = 2*heightPadding;
		double legendXSize = maxWidth / 16;
		double legendYSize = maxHeight / 16;
		int legendLabelSize = (int)maxHeight / 34;
		double legendLabelX = widthPadding + legendXSize + maxWidth / 80;
		double legendSpacing = legendYSize + maxHeight / 16;
		double legendLabelYComp = legendYSize / 1.7;
		double pieX = maxWidth - widthPadding - 1.0/2.0 * maxWidth;
		double pieY = heightPadding;
		double pieSliceWidth = 1.0/2.0 * maxWidth;
		double pieSliceHeight = maxHeight - 2*heightPadding;
		gc.clearRect(0, 0, maxWidth, maxHeight);

		int color = 0;
		double startAngle = 0.0;
		for (String warning: data.keySet()) {

			// Set data color
			gc.setFill(colors[color]);

			// Legend
			Font font = new Font("Arial", legendLabelSize);
			gc.setFont(font);
			gc.fillRect(legendX, legendY, legendXSize, legendYSize);
			gc.fillText(warning, legendLabelX, legendY + legendLabelYComp);
			legendY += legendSpacing;

			// Pie slice
			double pieSliceArcExtent = data.get(warning) / dataSize * 360;
			gc.fillArc(pieX, pieY, pieSliceWidth, pieSliceHeight, startAngle, 
					pieSliceArcExtent, ArcType.ROUND);
			startAngle += pieSliceArcExtent;
			color++;
		}
	}

	private String getNameFromFile(File file) {

		String fileName = file.getName().replace("-", " ");
		String name = "";

		// Capitalize words
		name += Character.toUpperCase(fileName.charAt(0));
		for (int i = 1; i < fileName.length(); i++) {
			name += (fileName.charAt(i-1) == ' ') ? Character.toUpperCase(fileName.charAt(i)) : fileName.charAt(i);
		}

		// Strip extension
		name = name.substring(0, fileName.lastIndexOf("."));

		// Replace hyphens with spaces
		return name;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
