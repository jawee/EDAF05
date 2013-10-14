import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MaximumFlow {
	HashMap<Integer, HashMap<Integer, Integer>> flowMap;
	HashMap<Integer, String> nodeMap;
	int[][] capacityMatrix;
	int[][] flowMatrix;
	private int START = 0;
	private int END = 54;
	ArrayList<LinkedList<Integer>> pathList = new ArrayList<LinkedList<Integer>>();

	public MaximumFlow() {
		flowMap = new HashMap<Integer, HashMap<Integer, Integer>>();
		nodeMap = new HashMap<Integer, String>();
		read();
		maximumFlow();
	}

	private void maximumFlow() {
		for (int i = 0; i < flowMatrix.length; i++) {
			for (int j = 0; j < flowMatrix[i].length; j++) {
				flowMatrix[i][j] = 0;
			}
		}
//		ArrayList<ArrayList<Integer>> pathList = BFS(0, 54);
		LinkedList<Integer> visited = new LinkedList<Integer>();
		visited.add(START);
		DFS(visited);
		System.out.println(pathList);
		

	}

	private ArrayList<ArrayList<Integer>> BFS(int START, int END) {
		ArrayList<ArrayList<Integer>> pathList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		HashSet<Integer> visited = new HashSet<Integer>();
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(START);
		while (!queue.isEmpty()) {
			Queue<Integer> temp = new LinkedList<Integer>();
			for (int s : queue) {
				if (s == END) {
					ArrayList<Integer> tempPath = path;
					tempPath.add(s);
					pathList.add(tempPath);
				} else {
					for (int s2 : getConnected(s)) {
						if (visited.add(s2)) {
							temp.add(s2);
						}
					}
				}
			}
			queue = temp;
		}
		return pathList;
	}
	
    private void DFS(LinkedList<Integer> visited) {
        LinkedList<Integer> nodes = getConnected(visited.getLast());
        // examine adjacent nodes
        for (int node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node == END) {
                visited.add(node);
                LinkedList<Integer> tempList = visited;
                pathList.add(tempList);
                visited.removeLast();
                break;
            }
        }
        // in breadth-first, recursion needs to come after visiting adjacent nodes
        for (int node : nodes) {
            if (visited.contains(node) || node== END) {
                continue;
            }
            visited.addLast(node);
            DFS(visited);
            visited.removeLast();
        }
    }

	private LinkedList<Integer> getConnected(int s) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < capacityMatrix[s].length; i++) {
			if (capacityMatrix[s][i] > 0 && i != s) {
				list.add(i);
			}
		}
		return list;
	}

	private void read() {
		Scanner scan = null;
		try {
			scan = new Scanner(new File("rail.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int n = scan.nextInt();
		capacityMatrix = new int[n][n];
		flowMatrix = new int[n][n];
		scan.nextLine();

		for (int i = 0; i < n; i++) {
			nodeMap.put(i, scan.nextLine());
			flowMap.put(i, new HashMap<Integer, Integer>());
		}
		n = scan.nextInt();
		for (int i = 0; i < n; i++) {
			int a = scan.nextInt();
			int b = scan.nextInt();
			int c = scan.nextInt();
			if (c < 0) { // infinite flow if capacity is -1
				c = Integer.MAX_VALUE;
			}
			capacityMatrix[a][b] = c;
			capacityMatrix[b][a] = c;
			flowMap.get(a).put(b, c);
			scan.nextLine();
		}
	}

	public static void main(String[] args) {
		new MaximumFlow();
	}
}
