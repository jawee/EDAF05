import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ClosestPair {
	private ArrayList<Point> pointlist = new ArrayList<Point>();

	public ClosestPair(File filename) {
		read(filename);
		DaC();
	}

	private void read(File filename) {
		Scanner scan = null;
		try {
			scan = new Scanner(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] splitLine = line.replaceAll("\\s+", " ").trim().split(" ");

			if (splitLine.length == 3) {
				try {
					String id = splitLine[0];

					double x = Double.parseDouble(splitLine[1]);
					double y = Double.parseDouble(splitLine[2]);
					pointlist.add(new Point(id, x, y));
				} catch (Exception e) {
					continue;
				}
			}
		}

	}

	private void DaC() {
		Collections.sort(pointlist);
		long start = System.currentTimeMillis();
		System.out.println(pointlist.size() + " "
				+ FindClosestPair(pointlist) + " "
				+ (System.currentTimeMillis() - start));
	}

	private double FindClosestPair(List<Point> xlist) {
		int n = xlist.size();
		// Termination condition
		if (n == 1) {
			return Integer.MAX_VALUE;
		}
		if (n == 2) {
			return xlist.get(0).distanceToPoint(xlist.get(1));
		}
		// divide
		List<Point> ptsByXLeft = xlist.subList(0, xlist.size() / 2);
		List<Point> ptsByXRight = xlist.subList(xlist.size() / 2, xlist.size());
		// conquer
		double minL = FindClosestPair(ptsByXLeft);
		double minR = FindClosestPair(ptsByXRight);
		// Combine
		double min = (minL < minR) ? minL : minR;
		double middleLineX = (ptsByXRight.get(0).x + ptsByXLeft.get(ptsByXLeft.size() - 1).x) / 2;
		List<Point> yStrip = new ArrayList<Point>();
		for (Point p : xlist) {
			if (Xdistance(p, middleLineX) < min) {
				yStrip.add(p);
			}
		}
		Collections.sort(yStrip, PointYComparator);
		for (int i = 0; i < yStrip.size(); i++) {
			for (int j = i; j < yStrip.size(); j++) { // j < (min + i) &&
				double distance = distance(yStrip.get(i), yStrip.get(j));
				if (i != j && distance < min) {
					min = distance;
				}
			}
		}

		return min;

	}

	private double Xdistance(Point p, double x) {
		return Math.abs(p.x - x);
	}

	private double distance(Point p, Point q) {
		return Math.hypot(p.x - q.x, p.y - q.y);
	}

	public final Comparator<Point> PointYComparator = new Comparator<Point>() {
		public int compare(Point p, Point q) {
			return (int) (p.y - q.y);
		}

	};

	private class Point implements Comparable<Point>, Comparator<Point> {
		public String id;
		public double x;
		public double y;

		public Point(String id, double x, double y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return id + " " + x + " " + y;
		}

		public double distanceToPoint(Point p) {
			return Math.hypot(x - p.x, y - p.y);
		}

		public int compareTo(Point q) {
			return (int) (q.x - this.x);
		}

		public int compare(Point p, Point q) {
			return (int) (p.y - q.y);
		}

	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		File dir = new File("files/");
		for (File child : dir.listFiles()) {
			new ClosestPair(child);
		}
		System.out.println(System.currentTimeMillis() - start);
//		 new ClosestPair(new File("files/pla85900.tsp"));
	}
}