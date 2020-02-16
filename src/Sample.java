import javafx.scene.chart.XYChart;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sample {
	public static List<Integer> repeatedValues = new ArrayList<>();
	
	public static double[] createCoordinates(int size, int limit) {
		double[] coordinates = new double[size * 2];
		double   temp;
		
		for (int i = 0; i < size * 2; i++) {
			temp = Math.random() * limit;
			coordinates[i] = temp;
			System.out.println(temp);
		}
		return coordinates;
	}
	
	public static Point2D[] selectRandomPoints(int size, int k, Point2D[] coordinates) {
		Point2D[] points = new Point2D[k];
		int       temp;
		
		for (int i = 0; i < k; i++) {
			points[i] = new Point2D.Double();
			do {
				temp = (int) (Math.random() * size);
			} while (repeatedValues.contains(temp));
			points[i] = coordinates[temp];
			repeatedValues.add(temp);
		}
		return points;
	}
	
	public static int assignPointToCluster(int k, Point2D[] randomPoints, Point2D point) {
		double kValue = 100000000000000000000.0;
		int    min    = 0;
		
		for (int j = 0; j < k; j++) {
			if (point.distance(randomPoints[j]) < kValue) {
				min = j;
				kValue = point.distance(randomPoints[j]);
			}
		}
		
		return min;
	}
	
	public static void addRandomPoints(Point2D[][] matrix, Point2D[] array, int k, int size){
		for(int i = 0; i < k; i++){
			matrix[i][repeatedValues.get(i)] = array[i];
		}
	}
	
	public static Point2D[] means(Point2D[][] points, int k, int size){
		Point2D[] means = new Point2D[k];
		
		for (int i = 0; i < k; i++) {
			means[i] = new Point2D.Double();
			double x = 0.0, y = 0.0;
			int counter = 0;
			for (int j = 0; j < size; j++) {
				if(points[i][j] != null){
					x += points[i][j].getX();
					y += points[i][j].getY();
					counter++;
				}
			}
			if (counter != 0) {
				x /= counter;
				y /= counter;
			}
			means[i].setLocation(x, y);
		}
		return means;
	}
	
	public static void printPoints(Point2D[] means, Point2D[][] printPoints, int k, int size){
		for(int i = 0; i < k; i++){
			for(int j = 0; j<size; j++){
				if (printPoints[i][j] != null)
					System.out.println("i = " + i + " j = " + j + " " + printPoints[i][j].getX() + " " + printPoints[i][j].getY());
			}
			System.out.println(means[i]);
		}
	}
	
	public static void repeatKMeans(XYChart.Series[] series, int k, int size, Point2D[][] clusterPoints,  Point2D[] means){
		int kCluster;
		List<Point2D> blackList = new ArrayList<>();
		
		for (int i = 0; i < k; i++) {//clear series
			series[i].getData().clear();
		}
		for(int i = 0; i < k; i++) {
			for (int j = 0; j < size; j++) {
				//System.out.println(clusterPoints[i][j]);
				//System.out.println(!blackList.contains(clusterPoints[i][j]));
				if (clusterPoints[i][j] != null && !blackList.contains(clusterPoints[i][j])) {
					kCluster = Sample.assignPointToCluster(k, means, clusterPoints[i][j]);
					if (clusterPoints[kCluster][j] == null)
						clusterPoints[kCluster][j] = new Point2D.Double();
					series[kCluster].getData().add(new XYChart.Data(clusterPoints[i][j].getX(), clusterPoints[i][j].getY()));
					blackList.add(clusterPoints[i][j]);
					//System.out.println("Entering............ kCluster = " + kCluster + " i = " + i + " j = " + j + " " + clusterPoints[kCluster][j] + " " + clusterPoints[i][j] + " " + !clusterPoints[kCluster][j].equals(clusterPoints[i][j]));
					if(!clusterPoints[kCluster][j].equals(clusterPoints[i][j])) {
						clusterPoints[kCluster][j].setLocation(clusterPoints[i][j].getX(), clusterPoints[i][j].getY());
						//System.out.println(clusterPoints[kCluster][j]);
						clusterPoints[i][j] = null;
					}
					
					
					/*System.out.println(i + " " +  j);
					System.out.println(kCluster + " " +  j);
					System.out.println(clusterPoints[kCluster][j]);*/
					//System.out.println(clusterPoints[kCluster][j] != null);
				}
			}
		}
		/*for (int l = 0; l < k; l++) {
			System.out.println("SERIES.................. " + series[l].getData());
		}*/
	}
	
	public static double[] calculateVariance(Point2D[] means, Point2D[][] clusterPoints, int k, int size){
		Point2D point;
		int counter = 0;
		double variance = 0.0;
		double[] varianceArray = new double[k];
		
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < size; j++) {
				point = clusterPoints[i][j];
				if(point != null) {
					variance += point.distance(means[i]) * point.distance(means[i]);
					//System.out.println(variance);
					counter++;
				}
			}
			variance /= counter;
			varianceArray[i] = variance;
			variance = 0;
			counter = 0;
		}
		return varianceArray;
	}

}
