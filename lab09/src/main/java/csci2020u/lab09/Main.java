package csci2020u.lab09;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Iterator;


public class Main extends Application {

	private static final int WIN_WIDTH = 1280;
	private static final int WIN_HEIGHT = 720;
	private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=";
	private final static String API_KEY = "&apikey=ONZ9SN8990XVME6L";
	private Canvas canvas;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Application window contents
		Group root = new Group();
		Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);

		// Drawing canvas
		canvas = new Canvas();
		canvas.setWidth(WIN_WIDTH);
		canvas.setHeight(WIN_HEIGHT);
		//canvas.widthProperty().bind(primaryStage.widthProperty());
		//canvas.heightProperty().bind(primaryStage.heightProperty());
		root.getChildren().add(canvas);

		// Application window
		primaryStage.setTitle("Lab 9");
		primaryStage.setScene(scene);
		primaryStage.show();


		try {
			// Plot Apple's closing prices
			plotClosingPrices("AAPL", Color.BLUE);
			// Plot Google's closing prices
			plotClosingPrices("GOOG", Color.RED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the Json data and plots the closing prices of each
	 * month of the specified company
	 *
	 * @param 
	 */
	private void plotClosingPrices(String stockTicker, Color color) {

		Set<Map.Entry<String, JsonElement>> stockData = downloadStockPrices(stockTicker);
		TreeMap<String, Float> closingPriceData = getClosingPrices(stockData);

		drawLinePlot(closingPriceData.values(), color);
	}

	/**
	 * Gets the closing price data from the stocks data
	 *
	 * @param data The stocks data
	 */
	private TreeMap<String, Float> getClosingPrices(Set<Map.Entry<String, JsonElement>> data) {

		TreeMap<String, Float> closingPrices = new TreeMap<>();

		// Iterate over stock data
		for (Map.Entry<String, JsonElement> entry : data) {
			closingPrices.put(entry.getKey(), entry.getValue().getAsJsonObject().get("4. close").getAsFloat());
		}

		return closingPrices;
	}

	/**
	 * Gets the stock data of a company as a set of entries
	 *
	 * @param stockTicker The stock ticker code of the company to get the stocks data of
	 */
	private Set<Map.Entry<String, JsonElement>> downloadStockPrices(String stockTicker) {

		// Obtain JSON data
		JsonObject jsonRoot = JsonDownloader.download(API_URL + stockTicker + API_KEY);
		JsonObject values = jsonRoot.get("Monthly Time Series").getAsJsonObject();

		return values.entrySet();
	}

	/**
	 *
	 */
	private void drawLinePlot(Collection<Float> data, Color lineColor) {

		// Drawing variables
		double width = canvas.getWidth();
		double height = canvas.getHeight();
		double widthPadding = width / 10;
		double heightPadding = height / 10;
		double maxWidth = width - 2*widthPadding;
		double maxHeight = height - 2*heightPadding;
		double xDist = maxWidth / data.size();
		float highestNum = getHighestNum(data);

		// Draw axes
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		// Vertical axis
		gc.strokeLine(widthPadding, heightPadding, widthPadding, height-heightPadding);
		// Horizontal Axis
		gc.strokeLine(widthPadding, height-heightPadding, width-widthPadding, height-heightPadding);

		// Iterate over data
		Iterator<Float> iter = data.iterator();
		int i = 0;
		float prevNum = iter.next();
		float currNum;
		while (iter.hasNext()) {
			currNum = iter.next();
			//System.out.println(currNum);
			plotLine(widthPadding+i*xDist, height-heightPadding-maxHeight*(prevNum/highestNum), widthPadding+(i+1)*xDist, height-heightPadding-maxHeight*(currNum/highestNum), lineColor);
			prevNum = currNum;
			i++;
		}
	}

	/**
	 * Draw a line connecting two coordinates
	 *
	 * @param x1 The x-coordinate of the first value
	 * @param y1 The y-coordinate of the first value
	 * @param x2 The x-coordinate of the second value
	 * @param y2 The y-coordinate of the second value
	 */
	private void plotLine(double x1, double y1, double x2, double y2, Color lineColor) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(lineColor);
		gc.strokeLine(x1, y1, x2, y2);
	}

	/**
	 * Iterates over a Collection of floating-point values and 
	 * returns the highest value in that collection
	 *
	 * @param data The collection to find the highest value in
	 */
	private float getHighestNum(Collection<Float> data) {

		// Iterate over data
		Iterator<Float> iter = data.iterator();
		float highestNum = iter.next();
		float currNum;
		while (iter.hasNext()) {
			currNum = iter.next();
			if (currNum > highestNum) {
				highestNum = currNum;
			}
		}

		return highestNum;
	}

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
