package assignment1;

import static org.junit.Assert.assertArrayEquals;

public class Main {
	public static void main(String [] args) {
		// call your test methods here// SortTools.isSorted() etc.
		int[] x = new int[]{10, 20, 30, 40, 50, 60, 75, 80, 90, 100, 200, 2000, 3000};
		int[] z = new int[] {1,20, 30, 40, 0, 0, -1};
		int[] expected = new int[]{10, 20, 30, 35};
		int[] a = new int[] {5};
		
		//System.out.println(SortTools.find(x, 13, 3000));
		
		
		
		int[] y = SortTools.insertGeneral(x, 7, 50);
		 
		
		System.out.println(SortTools.insertInPlace(expected, 3, 10));
		
		// System.out.println(SortTools.find(x, 5, 20));
		 
		 for(int i = 0; i < expected.length; i++) {
			 System.out.println(expected[i]);
		 }
	}
}