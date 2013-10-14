import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SpanningUSA {
	static HashMap<String, LinkedList<Edge>> list = new HashMap<String, LinkedList<Edge>>();

	public static int getMST() {
		int mst = 0;
		HashSet<String> visitedV = new HashSet<String>();
		String initialCity = list.keySet().iterator().next();
		visitedV.add(initialCity);
		PriorityQueue<Edge> edgesQ = new PriorityQueue<Edge>();
		edgesQ.addAll(list.get(initialCity));
		while (!edgesQ.isEmpty()) {
			Edge e = edgesQ.remove();
			if (!visitedV.contains(e.target)) {
				mst += e.weight;
				visitedV.add(e.target);
				edgesQ.addAll(list.get(e.target));
			}
		}
		return mst;
	}

	public static void read(String filename) {
		File f = new File(filename);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		boolean readingDistances = false;
		while (scan.hasNext()) {
			String line = scan.nextLine();
			if (!readingDistances && line.contains("--")) {
				readingDistances = true;
			}
			if(!readingDistances) {
				list.put(line.trim(), new LinkedList<Edge>());
			}
			if (readingDistances) {
				String[] citySplit = line.split("--");
				citySplit[0] = citySplit[0].trim();
				String[] distanceSplit = citySplit[1].split("\\[");
				distanceSplit[1] = distanceSplit[1].substring(0,
						distanceSplit[1].length() - 1); // - ]
				list.get(citySplit[0]).add(new Edge(citySplit[0], distanceSplit[0].trim(), Integer
								.parseInt(distanceSplit[1])));
				list.get(distanceSplit[0].trim()).add(
						new Edge(distanceSplit[0].trim(), citySplit[0], Integer
								.parseInt(distanceSplit[1])));
			} 
		}
		scan.close();
	}

	public static void main(String[] args) {

		read("USA-highway-miles.in");
		long start = System.currentTimeMillis();
		System.out.println(getMST());
		System.out.println(System.currentTimeMillis() - start);
	}

	/*
	 * Duckburg "Gotham City" Metropolis Duckburg--"Gotham City" [2324]
	 * Duckburg–-Metropolis [231] “Gotham City”-–Metropolis [2298]
	 */
}