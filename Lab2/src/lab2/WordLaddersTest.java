package lab2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WordLaddersTest {
	private String resultfile = "resultat";
	
	@After
	public void cleanUp(){
		new File(resultfile).delete();
	}

	@Test
	public void test5757() {		
		WordLadders w = new WordLadders("words-5757.dat");
		w.printToFile("words-5757-test.in", resultfile);
		try {
			
			Scanner scanRes = new Scanner(new File(resultfile));
			Scanner scanCor = new Scanner(new File("words-5757-test.out"));
			
			while(scanRes.hasNext() && scanCor.hasNext()){
				assertTrue(scanRes.nextLine().equals(scanCor.nextLine()));
			}
			assertEquals(scanRes.hasNext(), scanCor.hasNext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test250() {		
		WordLadders w = new WordLadders("words-250.dat");
		w.printToFile("words-250-test.in", resultfile);
		try {
			
			Scanner scanRes = new Scanner(new File(resultfile));
			Scanner scanCor = new Scanner(new File("words-250-test.out"));
			
			while(scanRes.hasNext() && scanCor.hasNext()){
				assertTrue(scanRes.nextLine().equals(scanCor.nextLine()));
			}
			assertEquals(scanRes.hasNext(), scanCor.hasNext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test10() {		
		WordLadders w = new WordLadders("words-10.dat");
		w.printToFile("words-10-test.in", resultfile);
		try {
			
			Scanner scanRes = new Scanner(new File(resultfile));
			Scanner scanCor = new Scanner(new File("words-10-test.out"));
			
			while(scanRes.hasNext() && scanCor.hasNext()){
				assertTrue(scanRes.nextLine().equals(scanCor.nextLine()));
			}
			assertEquals(scanRes.hasNext(), scanCor.hasNext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
