 import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class EdmondKarp {
	int s; // source
	int t; // sink
	int m;
	int[] P;

	public EdmondKarp(int[][] capacityMatrix,
			LinkedList<LinkedList<Integer>> neighbourMatrix, int source,
			int sink) {
		s = source;
		t = sink;
		int f = 0; // maxFlow
		int[][] F = new int[capacityMatrix.length][capacityMatrix[0].length]; // Residual capacity
		while (true) {
			BreadthFirstSearch(capacityMatrix, neighbourMatrix, s, t, F);
			if (m == 0) {
				break;
			}
			f = f + m;
			int v = t;
			while (v != s) {
				int u = P[v];
				F[u][v] = F[u][v] + m;
				F[v][u] = F[v][u] - m;
				v = u;
			}
		}
		
		//utskrift
		ArrayList<Integer> reachable = new ArrayList<Integer>();
		ArrayList<Integer> unreachable = new ArrayList<Integer>();
		for (int i = 1; i < capacityMatrix.length; i++) {
			if (P[i] != -1) {
				reachable.add(i);
			} else {
				unreachable.add(i);
			}
		}
		for (Integer i : reachable) {
			for (Integer j : unreachable) {
				if (neighbourMatrix.get(i).contains(j)) {
					System.out
							.println(i + " " + j + " " + capacityMatrix[i][j]);
				}
			}
		}
		System.out.println(f);

	}

	private void BreadthFirstSearch(int[][] capacityMatrix,
			LinkedList<LinkedList<Integer>> neighbourMatrix, int s2, int t2,
			int[][] f) {
		int[] P = new int[capacityMatrix.length];
		for (int i = 0; i < P.length; i++) {
			P[i] = -1;
		}
		P[s] = -2;
		int[] M = new int[capacityMatrix.length];
		M[s] = Integer.MAX_VALUE;
		LinkedList<Integer> Q = new LinkedList<Integer>();
		Q.push(s);
		while (Q.size() > 0) {
			int u = Q.pop();
			for (int v : neighbourMatrix.get(u)) {
				if (capacityMatrix[u][v] - f[u][v] > 0 && P[v] == -1) {
					P[v] = u;
					M[v] = Math.min(M[u], capacityMatrix[u][v] - f[u][v]);
					if (v != t) {
						Q.push(v);
					} else {
						this.m = M[t];
						this.P = P;
						return;
					}
				}
			}
		}
		this.m = 0;
		this.P = P;
	}

	public static void main(String[] args) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File("rail.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int n = scan.nextInt();
		int[][] capacityMatrix = new int[n][n];
		LinkedList<LinkedList<Integer>> neighbourMatrix = new LinkedList<LinkedList<Integer>>();
		for (int i = 0; i < n; i++) {
			neighbourMatrix.add(i, new LinkedList<Integer>());
		}
		scan.nextLine();

		for (int i = 0; i < n; i++) {
			scan.nextLine();
		}
		n = scan.nextInt();
		for (int i = 0; i < n; i++) {
			int a = scan.nextInt();
			int b = scan.nextInt();
			int c = scan.nextInt();
			if (c < 0) { // infinite flow if capacity is -1
				c = Integer.MAX_VALUE;
			}
			neighbourMatrix.get(a).add(b);
			neighbourMatrix.get(b).add(a);
			capacityMatrix[a][b] = c;
			capacityMatrix[b][a] = c;
			scan.nextLine();
		}

		new EdmondKarp(capacityMatrix, neighbourMatrix, 0, 54);
	}
}
