package csci2020u.lab06;

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


public class Main extends Application {

	private final static int initWinWidth = 1280;
	private final static int initWinHeight = 600;
	private Canvas canvas;


	@Override
	public void start(Stage primaryStage) throws Exception {

		Group rootGroup = new Group();
		Scene scene = new Scene(rootGroup, initWinWidth, initWinHeight,
							 Color.BLACK);
		
		canvas = new Canvas();
		canvas.setWidth(initWinWidth);
		canvas.setHeight(initWinHeight);

		rootGroup.getChildren().add(canvas);

		primaryStage.setTitle("Lab 06");
		primaryStage.setScene(scene);
		primaryStage.show();

		// Draw the charts
		drawBarChart();
		drawPieChart();
	}

	private void drawBarChart() {

		// Data
		double[] avgHousingPricesByYear = {
			247381.0, 264171.4, 287715.3, 294736.1,
			308431.4, 322635.9, 340253.0, 363153.7
		};
		double maxHousingPrice = max(avgHousingPricesByYear);
		double[] avgCommercialPricesByYear = {
			1121585.3, 1219479.5, 1246354.2, 1295364.8,
			1335932.6, 1472362.0, 1583521.9, 1613246.3
		};
		double maxCommercialPrice = max(avgCommercialPricesByYear);

		// Drawing
		GraphicsContext gc = canvas.getGraphicsContext2D();
		double maxWidth = canvas.getWidth() / 2;
		double maxHeight = canvas.getHeight();
		double widthPadding = maxWidth / 10;
		double heightPadding = maxHeight / 10;
		double barWidth = (maxWidth - 2*widthPadding) / 10 / 2;
		double maxBarHeight = maxHeight - 2*heightPadding;
		double max = (maxHousingPrice > maxCommercialPrice) ? maxHousingPrice : 
					maxCommercialPrice;
		gc.clearRect(0, 0, maxWidth, maxHeight);

		// Avg Housing Prices
		gc.setFill(Color.RED);
		for (int i = 0; i < avgHousingPricesByYear.length; i++) {
			double barHeight = avgHousingPricesByYear[i] / max * maxBarHeight;
			gc.fillRect(widthPadding + i*(barWidth*2 + barWidth/2), maxHeight - 
						heightPadding - barHeight, barWidth, barHeight);
		}

		// Avg Commerical Prices
		gc.setFill(Color.BLUE);
		for (int i = 0; i < avgCommercialPricesByYear.length; i++) {
			double barHeight = avgCommercialPricesByYear[i] / max * 
							maxBarHeight;
			gc.fillRect(widthPadding + barWidth + i*(barWidth*2 + barWidth/2), 
						maxHeight - heightPadding - barHeight, barWidth, 
						barHeight);
		}

        // arcs
        //gc.setFill(Color.DARKCYAN);
        //gc.setStroke(Color.DARKCYAN);
        /*
        ArcType.ROUND - pie shaped
        ArcType.CHORD - endpoint connected to startpoint with line
         */
        //gc.strokeArc(50, 300, 100, 75, 115.0, 45.0, ArcType.ROUND);
		//gc.strokeArc(50, 500, 100, 75, 135.0, 180.0, ArcType.ROUND);
	}

	private void drawPieChart() {

		// Data
		String[] ageGroups = {
			"18-25", "26-35", "36-45", "46-55", "56-65", "65+"
		};
		int[] purchasesByAgeGroup = {
			648, 1021, 2453, 3173, 1868, 2247
		};
		double totalPurchases = (double)sum(purchasesByAgeGroup);
		Color[] pieChartColors = {
			Color.AQUA, Color.GOLD, Color.DARKORANGE, 
			Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
		};

		// Drawing
		GraphicsContext gc = canvas.getGraphicsContext2D();
		double maxWidth = canvas.getWidth() / 2;
		double maxHeight = canvas.getHeight();
		double widthPadding = maxWidth / 10;
		double heightPadding = maxHeight / 10;
		double pieSliceWidth = maxWidth - 2*widthPadding;
		double pieSliceHeight = maxHeight - 2*heightPadding;
		double x = maxWidth + widthPadding;
		double y = heightPadding;
		gc.clearRect(maxWidth, 0, canvas.getWidth(), maxHeight);

		double startAngle = 0.0;
		for (int i = 0; i < purchasesByAgeGroup.length; i++) {
			double pieSliceArcExtent = purchasesByAgeGroup[i] / totalPurchases * 360;
			gc.setFill(pieChartColors[i]);
			gc.fillArc(x, y, pieSliceWidth, pieSliceHeight, startAngle, 
					pieSliceArcExtent, ArcType.ROUND);
			startAngle += pieSliceArcExtent;
		}

	}

	private int sum(int[] arr) {

		int total = 0;

		for (int i = 0; i < arr.length; i++) {
			total += arr[i];
		}

		return total;

	}

	private double max(double[] arr) {

		double maxVal = arr[0];

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > maxVal) maxVal = arr[i];
		}

		return maxVal;

	}

	public static void main(String[] args) {
		launch(args);
	}
}
