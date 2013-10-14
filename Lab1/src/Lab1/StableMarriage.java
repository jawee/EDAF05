package lab1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class StableMarriage {
	int n;
	LinkedList<Integer>[] menPref;
	ArrayList<Integer>[] womenPref;
	String[] names;
	int[] partner;
	
	public StableMarriage(String filename){
		read(filename);
	}
	
	//läs
	@SuppressWarnings("unchecked")
	public void read(String filename){
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String s;
		do{
			s = scan.nextLine();
		}while(s.startsWith("#"));
		n = Integer.parseInt(s.substring(2));
		
		names = new String[n*2];
		menPref = new LinkedList[n*2];
		womenPref = new ArrayList[n*2];
		for(int i = 0; i < n*2; i+=2){
			menPref[i] = new LinkedList<Integer>();
			womenPref[i+1] = new ArrayList<Integer>();
		}
		for(int i = 0; i < 2*n; i++){
			names[i] = scan.nextLine().split(" ")[1];
		}
		scan.nextLine();
		for(int i = 0; i < n*2; i+=2){
			String[] splitMen = scan.nextLine().split(" ");
			String[] splitWomen = scan.nextLine().split(" ");
			for(int j = 0; j < n; j++){
				menPref[i].add(Integer.parseInt(splitMen[j+1])-1);
				womenPref[i+1].add(Integer.parseInt(splitWomen[j+1])-1);
			}
		}
		/*for(int i = 0; i < n*2; i+=2){
			System.out.println(menPref[i].toString() + "     " + womenPref[i+1].toString());
		}*/
		partner = new int[n*2];
		for(int i = 0; i < n*2; i++){
			partner[i] = -1;
		}
	}
	

	public long solve(){
		long time = System.nanoTime();
		int m = 0;
		while(partner[m] == -1 && !menPref[m].isEmpty()){
			int w = menPref[m].poll();
			
			if(partner[w] == -1){
				partner[w] = m;
				partner[m] = w;
			} else if(womenPref[w].indexOf(m) < womenPref[w].indexOf(partner[w])) {
				int ex = partner[w];
				partner[ex] = -1;
				partner[w] = m;
				partner[m] = w;
				m = ex;
			}
			m = (m+2)%(n*2);
			for(int i=0;i<n;i++){
				if(partner[m] != -1)
					m = (m+2)%(n*2);
			}
		}
		return System.nanoTime() - time;
	}
	
	public void toFile(String filename){
		try {
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			
			out.append(toString());
			out.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			e.printStackTrace();
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < n; i++){
			if(partner[i*2] != -1)
				sb.append(names[i*2]).append(" -- ").append(names[partner[i*2 ]]).append("\n");
			else
				sb.append(names[i*2]).append(" -- ").append("free").append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		StableMarriage sm = new StableMarriage("sm-illiad.in");
		sm.solve();
		System.out.println(sm);
	}
}
