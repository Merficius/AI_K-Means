import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Plot extends Application {
	
	@Override
	public void start(Stage stage) {
		//double[] variances = new double[12];//obtaining bestK
		//for (int bestK = 0; bestK < 12; bestK++) {//obtaining bestK
		
		double minimumVariance = 1000000000000000000000000000000000000000000000000000.0;
		int    k               = 7;
		int    size            = 100;//fixed size
		Stage  stage2          = new Stage();
		Stage  stage3          = new Stage();
		Stage  stage4          = new Stage();
		stage.setTitle("K-means pre");
		stage2.setTitle("K-means before means");
		stage3.setTitle("K-means random");
		stage4.setTitle("K-means after");
		final NumberAxis                   xAxis  = new NumberAxis(-1000, 1000, 100);
		final NumberAxis                   yAxis  = new NumberAxis(-1000, 1000, 100);
		final NumberAxis                   xAxis2 = new NumberAxis(-1000, 1000, 100);
		final NumberAxis                   yAxis2 = new NumberAxis(-1000, 1000, 100);
		final NumberAxis                   xAxis3 = new NumberAxis(-1000, 1000, 100);
		final NumberAxis                   yAxis3 = new NumberAxis(-1000, 1000, 100);
		final NumberAxis                   yAxis4 = new NumberAxis(-1000, 1000, 100);
		final NumberAxis                   xAxis4 = new NumberAxis(-1000, 1000, 100);
		final ScatterChart<Number, Number> sc     = new ScatterChart<Number, Number>(xAxis, yAxis);
		final ScatterChart<Number, Number> sc2    = new ScatterChart<Number, Number>(xAxis2, yAxis2);
		final ScatterChart<Number, Number> sc3    = new ScatterChart<Number, Number>(xAxis3, yAxis3);
		final ScatterChart<Number, Number> sc4    = new ScatterChart<Number, Number>(xAxis4, yAxis4);
		xAxis.setLabel("Values in X");
		yAxis.setLabel("Values in Y");
		xAxis2.setLabel("Values in X");
		yAxis2.setLabel("Values in Y");
		xAxis3.setLabel("Values in X");
		yAxis3.setLabel("Values in Y");
		xAxis4.setLabel("Values in X");
		yAxis4.setLabel("Values in Y");
		sc.setTitle("K-means clustering pre");
		sc2.setTitle("K-means clustering before means");
		sc3.setTitle("K-means clustering random Points");
		sc4.setTitle("K-means clustering after");
		XYChart.Series   seriesAllPointsFinal    = new XYChart.Series();
		XYChart.Series[] seriesRandomPointsFinal = new XYChart.Series[k];
		for (int i = 0; i < k; i++) {
			seriesRandomPointsFinal[i] = new XYChart.Series();
		}
		
		XYChart.Series[] seriesBeforeMeansFinal = new XYChart.Series[k + 1];
		XYChart.Series[] seriesFinal            = new XYChart.Series[k + 1];
		for (int i = 0; i < k + 1; i++) {
			seriesBeforeMeansFinal[i] = new XYChart.Series();
			seriesFinal[i] = new XYChart.Series();
		}
		double totalVariance   = 0;
		double averageVariance = 0;
		//loops
		for (int l = 0; l < 10000; l++) {
/*			Stage stage2 = new Stage();
			Stage stage3 = new Stage();
			Stage stage4 = new Stage();*/
			
			//int size = 5;
			Point2D[][] clusterPoints = new Point2D[k][size];
			Point2D[]   allPoints     = new Point2D[size];
			Point2D[]   randomPoints;
			
			/*for (int i = 0; i < size; i++) {//fixed values for testing
				allPoints[i] = new Point2D.Double();
				allPoints[i].setLocation(55.0 + accumulator, 250.0);
				accumulator += 35;
			}*/
			for (int i = 0; i < size; i++) {//random points
				allPoints[i] = new Point2D.Double();
				if (Math.random() < .66) {//negative x
					if (Math.random() < .66)//negative y
						allPoints[i].setLocation(Math.random() * -1000, Math.random() * -1000);//size of graph
					else
						allPoints[i].setLocation(Math.random() * -1000, Math.random() * 1000);//size of graph
				}
				
				else {//positive x
					if (Math.random() > .33)//positive y
						allPoints[i].setLocation(Math.random() * 1000, Math.random() * 1000);//size of graph
					else
						allPoints[i].setLocation(Math.random() * 1000, Math.random() * -1000);//size of graph
				}
				
			}
			//double [] coordinates;
			/*stage.setTitle("K-means pre");
			stage2.setTitle("K-means before means");
			stage3.setTitle("K-means random");
			stage4.setTitle("K-means after");
			final NumberAxis                   xAxis  = new NumberAxis(-1000, 1000, 100);
			final NumberAxis                   yAxis  = new NumberAxis(-1000, 1000, 100);
			final NumberAxis                   xAxis2 = new NumberAxis(-1000, 1000, 100);
			final NumberAxis                   yAxis2 = new NumberAxis(-1000, 1000, 100);
			final NumberAxis                   xAxis3 = new NumberAxis(-1000, 1000, 100);
			final NumberAxis                   yAxis3 = new NumberAxis(-1000, 1000, 100);
			final NumberAxis                   yAxis4 = new NumberAxis(-1000, 1000, 100);
			final NumberAxis                   xAxis4 = new NumberAxis(-1000, 1000, 100);
			final ScatterChart<Number, Number> sc     = new ScatterChart<Number, Number>(xAxis, yAxis);
			final ScatterChart<Number, Number> sc2    = new ScatterChart<Number, Number>(xAxis2, yAxis2);
			final ScatterChart<Number, Number> sc3    = new ScatterChart<Number, Number>(xAxis3, yAxis3);
			final ScatterChart<Number, Number> sc4    = new ScatterChart<Number, Number>(xAxis4, yAxis4);
			xAxis.setLabel("Values in X");
			yAxis.setLabel("Values in Y");
			xAxis2.setLabel("Values in X");
			yAxis2.setLabel("Values in Y");
			xAxis3.setLabel("Values in X");
			yAxis3.setLabel("Values in Y");
			xAxis4.setLabel("Values in X");
			yAxis4.setLabel("Values in Y");
			sc.setTitle("K-means clustering pre");
			sc2.setTitle("K-means clustering before means");
			sc3.setTitle("K-means clustering random Points");
			sc4.setTitle("K-means clustering after");*/
			
			XYChart.Series   seriesAllPoints    = new XYChart.Series();
			XYChart.Series[] seriesRandomPoints = new XYChart.Series[k];
			XYChart.Series[] seriesBeforeMeans  = new XYChart.Series[k + 1];
			XYChart.Series[] series             = new XYChart.Series[k + 1];
			seriesAllPoints.setName("Data without clustering");
			//coordinates =  Sample.createCoordinates(size, 500);
			for (int i = 0; i < size; i++) {
				seriesAllPoints.getData().add(new XYChart.Data(allPoints[i].getX(), allPoints[i].getY()));
			}
			randomPoints = Sample.selectRandomPoints(size, k, allPoints);
			for (int i = 0; i < k; i++) {
				seriesBeforeMeans[i] = new XYChart.Series();
				seriesBeforeMeans[i].getData().add(new XYChart.Data(randomPoints[i].getX(), randomPoints[i].getY()));
				seriesBeforeMeans[i].setName(i + 1 + " clustering");
				series[i] = new XYChart.Series();
				series[i].getData().add(new XYChart.Data(randomPoints[i].getX(), randomPoints[i].getY()));
				series[i].setName(i + 1 + " clustering");
				seriesRandomPoints[i] = new XYChart.Series();
				seriesRandomPoints[i].getData().add(new XYChart.Data(randomPoints[i].getX(), randomPoints[i].getY()));
				seriesRandomPoints[i].setName(i + 1 + " clustering");
			}
			int kCluster;
			//Assign clusters
			//System.out.println(Sample.repeatedValues);
			for (int i = 0; i < size; i++) {
				if (!Sample.repeatedValues.contains(i)) {
					kCluster = Sample.assignPointToCluster(k, randomPoints, allPoints[i]);
					seriesBeforeMeans[kCluster].getData().add(new XYChart.Data(allPoints[i].getX(), allPoints[i].getY()));
					series[kCluster].getData().add(new XYChart.Data(allPoints[i].getX(), allPoints[i].getY()));
					clusterPoints[kCluster][i] = new Point2D.Double();
					clusterPoints[kCluster][i].setLocation(allPoints[i].getX(), allPoints[i].getY());
					//Sample.repeatedValues.add(i);
				}
			}
			
			//Sample.printPoints(randomPoints, clusterPoints, k, size);
			//System.out.println("-------------");
			Sample.addRandomPoints(clusterPoints, randomPoints, k, size);
			
			//Sample.printPoints(randomPoints, clusterPoints, k, size);
			//Calculate mean for each cluster
			Point2D[] means = new Point2D[k];
			for (int i = 0; i < k; i++) {
				means[i] = new Point2D.Double();
			}
			means = Sample.means(clusterPoints, k, size);
			
			//Sample.printPoints(means, clusterPoints, k, size);
			
			//Add means to seriesBeforeMeans
			seriesBeforeMeans[k] = new XYChart.Series();
			series[k] = new XYChart.Series();
			for (int i = 0; i < k; i++) {
				seriesBeforeMeans[k].getData().add(new XYChart.Data(means[i].getX(), means[i].getY()));
				series[k].getData().add(new XYChart.Data(means[i].getX(), means[i].getY()));
			}
			seriesBeforeMeans[k].setName("Means");
			series[k].setName("Means");
			
			//Measure and cluster again with mean values
			Point2D[] meansBefore = new Point2D[k];
			for (int i = 0; i < k; i++) {
				meansBefore[i] = new Point2D.Double();
			}
			
			do {
				meansBefore = means;
				Sample.repeatKMeans(series, k, size, clusterPoints, means);
				series[k].getData().clear();
				means = Sample.means(clusterPoints, k, size);
				for (int i = 0; i < k; i++) {
					series[k].getData().add(new XYChart.Data(means[i].getX(), means[i].getY()));
				}
					
					/*for (int i = 0; i < k; i++) {
						System.out.print("MEANSBEFORE:     " + meansBefore[i]);
						System.out.println("MEANS:         " + means[i]);
					}*/
				
			} while (!Arrays.equals(meansBefore, means));
			/*for (int i = 0; i < k; i++) {
				System.out.print("MEANSBEFORE:     ");
				System.out.println(means[i]);
			}
			
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < size; j++) {
					System.out.print("i: " + i + " j: " + j);
					System.out.println(clusterPoints[i][j]);
				}
			}*/
			
			double[] variance;
			totalVariance = 0;
			
			variance = Sample.calculateVariance(means, clusterPoints, k, size);
			
			for (int i = 0; i < k; i++) {
				System.out.println("Variance of " + (i + 1) + " group: " + variance[i]);
				totalVariance += variance[i];
			}
			System.out.println("Total variance = " + totalVariance);
			if (minimumVariance > totalVariance) {
				minimumVariance = totalVariance;
				seriesFinal = series;
				seriesBeforeMeansFinal = seriesBeforeMeans;
				seriesRandomPointsFinal = seriesRandomPoints;
				seriesAllPointsFinal = seriesAllPoints;
			}
			System.out.println("minimum Variance = " + minimumVariance);
			
			Sample.repeatedValues = new ArrayList<>();
			averageVariance += totalVariance;
			System.out.println("averageVariancePre =" + l + " " + averageVariance);
		}
		averageVariance = averageVariance / 10000;
		System.out.println("averageVariance = " + averageVariance);
		
		sc4.getData().addAll(seriesFinal);
		Scene scene4 = new Scene(sc4, 1000, 1000);
		stage4.setScene(scene4);
		stage4.show();
		
		
		sc2.getData().addAll(seriesBeforeMeansFinal);
		Scene scene2 = new Scene(sc2, 1000, 1000);
		scene2.getStylesheets().add("Chart.css");
		stage2.setScene(scene2);
		stage2.show();
		
		
		sc3.getData().addAll(seriesRandomPointsFinal);
		Scene scene3 = new Scene(sc3, 1000, 1000);
		stage3.setScene(scene3);
		stage3.show();
		
		sc.getData().addAll(seriesAllPointsFinal);
		Scene scene = new Scene(sc, 1000, 1000);
		stage.setScene(scene);
		stage.show();
		
		//variances[bestK] = averageVariance;//bestk
		//}
		
		
		/*//obtaining bestk
		System.out.println("\n\n\n\nVariances:");
		for (int i = 0; i < 12; i++) {
			System.out.println(variances[i]);
		}
		
		//lineChart for bestk
		Stage stage5 = new Stage();
		stage5.setTitle("bestK");
		//defining the axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("K");
		yAxis.setLabel("Variances");
		//creating the chart
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		
		lineChart.setTitle("BestK");
		//defining a series
		XYChart.Series series = new XYChart.Series();
		series.setName("k against variation");
		//populating the series with data
		for (int i = 0; i < 12; i++) {
			series.getData().add(new XYChart.Data(i + 1, variances[i]));
		}
		
		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().add(series);
		
		stage5.setScene(scene);
		stage5.show();*/
		
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}