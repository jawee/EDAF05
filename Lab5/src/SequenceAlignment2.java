import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class SequenceAlignment2 {
	private HashMap<String, String> sequenceMap;
	private ArrayList<String> nameList;
	private ArrayList<String> blosumList = new ArrayList<String>();
	private int blosumMatrix[][];

	public SequenceAlignment2(File f) {
		read(f);
		blosum(new File("BLOSUM62.txt"));
		HashSet<String> visited = new HashSet<String>();
		for (String x : sequenceMap.keySet()) {
			if (!visited.contains(x)) {
				for (String y : sequenceMap.keySet()) {
					if (!x.equals(y) && !visited.contains(y)) {
						SequenceAlignmentt(sequenceMap.get(x),
								sequenceMap.get(y), x, y);
					}
				}
			}
			visited.add(x);

		}
	}

	private void blosum(File f) {
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String s = scan.nextLine();
		while (s.startsWith("#")) {
			s = scan.nextLine();
		}
		String[] slist = s.trim().split(" ");
		for (int i = 0; i < slist.length - 1; i++) {
			if (i % 2 == 0) {
				blosumList.add(slist[i]);
			}
		}
		int temp[][] = {
				{ 4, -1, -2, -2, 0, -1, -1, 0, -2, -1, -1, -1, -1, -2, -1, 1,
						0, -3, -2, 0, -2, -1, 0 },
				{ -1, 5, 0, -2, -3, 1, 0, -2, 0, -3, -2, 2, -1, -3, -2, -1, -1,
						-3, -2, -3, -1, 0, -1 },
				{ -2, 0, 6, 1, -3, 0, 0, 0, 1, -3, -3, 0, -2, -3, -2, 1, 0, -4,
						-2, -3, 3, 0, -1 },
				{ -2, -2, 1, 6, -3, 0, 2, -1, -1, -3, -4, -1, -3, -3, -1, 0,
						-1, -4, -3, -3, 4, 1, -1 },
				{ 0, -3, -3, -3, 9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1,
						-1, -2, -2, -1, -3, -3, -2 },
				{ -1, 1, 0, 0, -3, 5, 2, -2, 0, -3, -2, 1, 0, -3, -1, 0, -1,
						-2, -1, -2, 0, 3, -1 },
				{ -1, 0, 0, 2, -4, 2, 5, -2, 0, -3, -3, 1, -2, -3, -1, 0, -1,
						-3, -2, -2, 1, 4, -1 },
				{ 0, -2, 0, -1, -3, -2, -2, 6, -2, -4, -4, -2, -3, -3, -2, 0,
						-2, -2, -3, -3, -1, -2, -1, },
				{ -2, 0, 1, -1, -3, 0, 0, -2, 8, -3, -3, -1, -2, -1, -2, -1,
						-2, -2, 2, -3, 0, 0, -1 },
				{ -1, -3, -3, -3, -1, -3, -3, -4, -3, 4, 2, -3, 1, 0, -3, -2,
						-1, -3, -1, 3, -3, -3, -1 },
				{ -1, -2, -3, -4, -1, -2, -3, -4, -3, 2, 4, -2, 2, 0, -3, -2,
						-1, -2, -1, 1, -4, -3, -1 },
				{ -1, 2, 0, -1, -3, 1, 1, -2, -1, -3, -2, 5, -1, -3, -1, 0, -1,
						-3, -2, -2, 0, 1, -1 },
				{ -1, -1, -2, -3, -1, 0, -2, -3, -2, 1, 2, -1, 5, 0, -2, -1,
						-1, -1, -1, 1, -3, -1, -1 },
				{ -2, -3, -3, -3, -2, -3, -3, -3, -1, 0, 0, -3, 0, 6, -4, -2,
						-2, 1, 3, -1, -3, -3, -1 },
				{ -1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4, 7,
						-1, -1, -4, -3, -2, -2, -1, -2 },
				{ 1, -1, 1, 0, -1, 0, 0, 0, -1, -2, -2, 0, -1, -2, -1, 4, 1,
						-3, -2, -2, 0, 0, 0 },
				{ 0, -1, 0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1, 1,
						5, -2, -2, 0, -1, -1, 0 },
				{ -3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1, 1, -4,
						-3, -2, 11, 2, -3, -4, -3, -2 },
				{ -2, -2, -2, -3, -2, -1, -2, -3, 2, -1, -1, -2, -1, 3, -3, -2,
						-2, 2, 7, -1, -3, -2, -1 },
				{ 0, -3, -3, -3, -1, -2, -2, -3, -3, 3, 1, -2, 1, -1, -2, -2,
						0, -3, -1, 4, -3, -2, -1 },
				{ -2, -1, 3, 4, -3, 0, 1, -1, 0, -3, -4, 0, -3, -3, -2, 0, -1,
						-4, -3, -3, 4, 1, -1 },
				{ -1, 0, 0, 1, -3, 3, 4, -2, 0, -3, -3, 1, -1, -3, -1, 0, -1,
						-3, -2, -2, 1, 4, -1 },
				{ 0, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, 0,
						0, -2, -1, -1, -1, -1, -1 } };

		blosumMatrix = temp;
	}

	private void read(File f) {
		sequenceMap = new HashMap<String, String>();
		nameList = new ArrayList<String>();
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			StringBuilder sb = new StringBuilder();
			if (s.startsWith(">")) {
				s = s.substring(1, s.length() - 1);
				String[] slist = s.split(" ");
				s = slist[0];
				nameList.add(s);
				sb.append(scan.nextLine());
				sb.append(scan.nextLine());
				sb.append(scan.nextLine());
				sequenceMap.put(s, sb.toString());
			}
		}
	}

	public void SequenceAlignmentt(String x, String y, String namex,
			String namey) {
		int delta = -4;
		int m = x.length();
		int n = y.length();
		int[][] alpha = blosumMatrix;
		int[][] M = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			M[i][0] = i * delta;
		}
		for (int j = 0; j <= n; j++) {
			M[0][j] = j * delta;
		}

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int a = blosumList.indexOf(Character.toString(x.charAt(i - 1)));
				int b = blosumList.indexOf(Character.toString(y.charAt(j - 1)));
				M[i][j] = max(alpha[a][b] + M[i - 1][j - 1], delta
						+ M[i - 1][j], delta + M[i][j - 1]);
			}
		}

		String AlignmentA = "";
		String AlignmentB = "";
		int i = x.length();
		int j = y.length();
		while (i > 0 && j > 0) {
			int score = M[i][j];
			int scoreDiag = M[i - 1][j - 1];
			int scoreUp = M[i][j - 1];
			int scoreLeft = M[i - 1][j];
			int a = blosumList.indexOf(Character.toString(x.charAt(i - 1)));
			int b = blosumList.indexOf(Character.toString(y.charAt(j - 1)));
			if (score == scoreDiag + alpha[a][b]) {
				AlignmentA = Character.toString(x.charAt(i - 1)) + AlignmentA;
				AlignmentB = Character.toString(y.charAt(j - 1)) + AlignmentB;
				i--;
				j--;
			} else if (score == scoreLeft + delta) {
				AlignmentA = Character.toString(x.charAt(i - 1)) + AlignmentA;
				AlignmentB = "-" + AlignmentB;
				i--;
			} else if (score == scoreUp + delta) {
				AlignmentB = Character.toString(y.charAt(j - 1)) + AlignmentB;
				AlignmentA = "-" + AlignmentA;
				j--;
			}
		}
		while (i > 0) {
			AlignmentA = Character.toString(x.charAt(i - 1)) + AlignmentA;
			AlignmentB = "-" + AlignmentB;
			i = i - 1;
		}
		while (j > 0) {
			AlignmentA = "-" + AlignmentA;
			AlignmentB = Character.toString(y.charAt(j - 1)) + AlignmentB;
			j = j - 1;
		}
		System.out.println(namex + "--" + namey + ": " + M[m][n]);
		System.out.println(AlignmentA);
		System.out.println(AlignmentB);
	}

	private int max(int i, int j, int k) {
		int min = Math.max(i, j);
		return Math.max(min, k);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SequenceAlignment2(new File("HbB_FASTAs.in"));
	}

}
