package org.kyll.test;

import java.util.Random;

public class Test {
	public static void main(String... args) throws Exception {
		Random r = new Random(System.currentTimeMillis());

		int sum = 0;
		int[][] resultMatrix = new int[2][2];

		int[][] initMatrix = new int[4][4];
		for (int i = 0; i < initMatrix.length; i++) {
			for (int j = 0; j < initMatrix[i].length; j++) {
				initMatrix[i][j] = r.nextInt(100);
				if (i > 0 && i < initMatrix.length - 1 && j > 0 && j < initMatrix[i].length - 1) {
					resultMatrix[i - 1][j - 1] = initMatrix[i][j];
					sum += initMatrix[i][j];
				}
			}
		}

		print(initMatrix);
		System.out.println("=========");
		print(resultMatrix);
		System.out.println("=========");
		System.out.println(sum);
	}

	private static void print(int[][] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			for (int j = 0; j < arrays[i].length; j++) {
				System.out.print(arrays[i][j] + " ");
			}
			System.out.println();
		}
	}
}
