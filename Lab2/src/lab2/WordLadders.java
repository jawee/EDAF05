package lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class WordLadders {
	private ArrayList<String> wordList;
	private HashMap<String, LinkedList<String>> map;

	public WordLadders(String filename) {
		wordList = new ArrayList<String>();
		map = new HashMap<String, LinkedList<String>>();
		read(filename);
		createAdjacentList();
	}

	private void read(String filename) {
		File f = new File(filename);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scan.hasNext()) {
			String temp = scan.nextLine();
			wordList.add(temp);
			map.put(temp, new LinkedList<String>());
		}
		scan.close();

	}

	public void createAdjacentList() {
		for (int i = 0; i < wordList.size(); i++) {
			char second = wordList.get(i).charAt(1);
			char third = wordList.get(i).charAt(2);
			char fourth = wordList.get(i).charAt(3);
			char fifth = wordList.get(i).charAt(4);

			for (int j = 0; j < wordList.size(); j++) {
				if (i != j) {
					char[] c = wordList.get(j).toCharArray();
					ArrayList<Character> charList = new ArrayList<Character>();
					for (char ch : c) {
						charList.add(ch);
					}
					if (charList.contains(second)) {
						charList.remove(charList.indexOf(second));
						if (charList.contains(third)) {
							charList.remove(charList.indexOf(third));
							if (charList.contains(fourth)) {
								charList.remove(charList.indexOf(fourth));
								if (charList.contains(fifth)) {
									map.get(wordList.get(i)).addLast(
											wordList.get(j));
								}
							}
						}
					}

				}
			}

		}
	}

	public void printToFile(String inputfile, String outputfile) {
		File f = new File(inputfile);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<String> wordPairs = new ArrayList<String>();
		while (scan.hasNextLine()) {
			wordPairs.add(scan.nextLine());
		}

		try {
			FileWriter fstream = new FileWriter(outputfile);
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i = 0; i < wordPairs.size(); i++) {
				String[] words = wordPairs.get(i).split(" ");
				out.write(calculateDistance(words[0], words[1]) + "\n");
			}
			out.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			e.printStackTrace();

		}

	}

	public void findDistances(String filename) {
		File f = new File(filename);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<String> wordPairs = new ArrayList<String>();
		while (scan.hasNextLine()) {
			wordPairs.add(scan.nextLine());
		}
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < wordPairs.size(); i++) {
			String[] words = wordPairs.get(i).split(" ");
			System.out.println(calculateDistance(words[0], words[1]));
		}
		System.out.println("Tid: " + (System.currentTimeMillis() - startTime));
	}

	private int calculateDistance(String START, String END) {
		int level = 0;
		HashSet<String> visited = new HashSet<String>(); //lista för noder som redan besökts, hashset så att en nod endast kan finnas en gång
		Queue<String> queue = new LinkedList<String>(); // kö för noder att gå genom
		queue.add(START);
		while (!queue.isEmpty()) {
			Queue<String> temp = new LinkedList<String>(); //templista för noder i nästa lager
			for (String s : queue) {
				if (s.equals(END)) {
					return level;
				}
				for (String s2 : map.get(s)) {
					if (visited.add(s2)) {
						temp.add(s2);
					}

				}
			}
			queue = temp;
			level++;
		}
		return -1;

	}

	public static void main(String[] args) {
		WordLadders w = new WordLadders("words-5757.dat");
		w.findDistances("words-5757-test.in");
	}

}
