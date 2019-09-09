// SortTools.java 
/*
 * EE422C Project 1 submission by
 * Pulkit Mahajan
 * pm28838
 * 16190
 * Spring 2019
 */

package assignment1;

import java.util.Arrays;

public class SortTools {
	/**
	 * This method tests to see if the given array is sorted.
	 * 
	 * @param x is the array
	 * @param n is the size of the input to be checked
	 * @return true if array is sorted
	 */
	public static boolean isSorted(int[] x, int n) {
		// stub only, you write this!
		// TODO: complete it
		if (x.length <= 0) {
			return false;
		}
		if (n <= 0) {
			return false;
		}

		for (int i = 0; i < (n - 1); i++) {
			if (x[i] > x[i + 1]) {
				return false;
			}
		}
		return true;

	}

	/**
	 * This method returns the index of value v within array x.
	 * 
	 * @param x is the array
	 * @param n is the size of the input to be checked
	 * @param v is the value to be searched for within x
	 * @return index of v if v is contained within x. -1 if v is not contained
	 *         within x
	 */
	public static int find(int[] x, int n, int v) {
		// stub only, you write this!
		// TODO: complete it
		int middle = n / 2;
		if (middle < n) {
			if (v == x[middle]) {
				return middle;
			}
			if (v < x[middle]) {
				return find(x, middle, v);
			}
			if (v > x[middle]) {
				return findRightArray(x, middle, n, v);
			}

		}
		return -1;
	}

	/**
	 * @param x    is the array
	 * @param low  is the lower bound (originally the middle of the previous
	 *             function)
	 * @param high is the upper bound (n)
	 * @param v    is the value to be searched for within x
	 * @return index of v if v is contained within x. -1 if v is not contained
	 *         within x
	 */
	public static int findRightArray(int[] x, int low, int high, int v) {
		int middle = (low + high) / 2;
		if (middle < high) {
			if (v == x[middle]) {
				return middle;
			}
			if (v > x[middle]) {
				middle++;
				return findRightArray(x, middle, high, v);
			}
			if (v < x[middle]) {
				return find(x, middle, v);
			}
		}

		return -1;
	}

	/**
	 * This method returns a newly created array containing the first n elements of
	 * x, and v.
	 * 
	 * @param x is the array
	 * @param n is the number of elements to be copied from x
	 * @param v is the value to be added to the new array
	 * @return a new array containing the first n elements of x, and v
	 */
	public static int[] insertGeneral(int[] x, int n, int v) {
		// stub only, you write this!
		// TODO: complete it
		boolean added = false;
		int temp = 0;

		if (find(x, n, v) == -1) {
			int[] y = new int[n + 1];

			int i = n - 1;
			int j = n;
			while (i > -1 && j > -1) {
				if (!added && (v > x[i])) {
					y[j] = v;
					added = true;
					j--;
				}

				else {
					y[j] = x[i];
					i--;
					j--;
				}
			}

			if (j > -1) {
				if (!added) {
					y[j] = v;

				} else {
					y[j] = x[j];
				}

			}
			return y;
		} else {
			int[] y = new int[n];
			for (int i = 0; i < n; i++) {
				y[i] = x[i];
			}
			return y;
		}
	}

	/**
	 * This method inserts a value into the array and ensures it's still sorted
	 * 
	 * @param x is the array
	 * @param n is the number of elements in the array
	 * @param v is the value to be added
	 * @return n if v is already in x, otherwise returns n+1
	 */
	public static int insertInPlace(int[] x, int n, int v) {
		// stub only, you write this!
		// TODO: complete it
		int temp;
		if (find(x, n, v) == -1) {
			if (x[n - 1] < v) {
				x[n] = v;
				return n + 1;
			} else {
				for (int i = 0; i < n + 1; i++) {
					if (v <= x[i]) {
						temp = x[i];
						x[i] = v;
						v = temp;
					}
				}
				return n + 1;
			}
		} else {
			return n;
		}

	}

	/**
	 * This method sorts a given array using insertion sort
	 * 
	 * @param x is the array to be sorted
	 * @param n is the number of elements of the array to be sorted
	 */
	public static void insertSort(int[] x, int n) {
		int temp;
		int j;
		for (int i = 1; i < n; i++) {
			j = i - 1;
			temp = x[i];

			while (x[j] > temp && j >= 0) {
				x[j + 1] = x[j];
				j = j - 1;
			}
			x[j + 1] = temp;
		}
	}
}
