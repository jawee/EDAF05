package lab1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StableMarriageTest {
	private String resultfile = "resultat";
	private String in = "sm-worst-5.in";
	private String correct = "sm-worst-5.out";
	
	@Before
	public void init(){
		StableMarriage sm = new StableMarriage(in);
		System.out.println(sm.solve());
		sm.toFile(resultfile);
	}
	
	@After
	public void cleanUp(){
		new File(resultfile).delete();
	}

	@Test
	public void testAcceptanceTest() {
		
		try {
			
			Scanner scanRes = new Scanner(new File(resultfile));
			Scanner scanCor = new Scanner(new File(correct));
			
			while(scanRes.hasNext() && scanCor.hasNext()){
				assertTrue(scanRes.nextLine().equals(scanCor.nextLine()));
			}
			assertEquals(scanRes.hasNext(), scanCor.hasNext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
