package algo_storage;

import java.util.*;
import java.io.*;

class Chess {
	public static void main(String[] args) throws IOException
	{
		boolean[][] matrix = new boolean[8][8];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = false;
			}
		}

		int count = 0;
		Scanner s = new Scanner(System.in);
		char[][] inputBoard = new char[8][8];
		for(int i = 0; i < 8; i++){
			String line = s.nextLine();
			char[] arr = line.toCharArray();
			inputBoard[i] = arr;

//			for(int j = 0; j < arr.length; j++){
//				if(arr[j] == '*'){
//					horizHelper (i, j, matrix);
//					vertHelper(i, j, matrix);
//					diagHelper(i, j, matrix);
//					matrix[i][j] = false;
//				}
//			}
		}


		boolean isValid = true;
		for(int i = 0; i < inputBoard.length; i++){
			for(int j = 0; j < inputBoard.length; j++){
				if (!isValid(i, j, inputBoard)) {
					isValid = false;
				}
			}
		}

		if (isValid)
			System.out.println("valid");
		else
			System.out.println("invalid");

	}

	public static boolean isValid(int i, int j, char[][] matrix){
		return vertHelper(i, j, matrix) &&
				horizHelper(i, j, matrix) &&
				diagHelper(i, j, matrix);
	}

	public static boolean vertHelper(int x, int y, char[][] matrix) {
		for(int i = 0; i < matrix.length; i++){
//			if(i != x && matrix[i][y] == '*'){
//				return false;
//			}
			if(i != y && matrix[x][i] == '*'){
				return false;
			}
		}
		return true;
	}

	public static boolean horizHelper(int x, int y, char[][] matrix) {
		for(int j = 0; j < matrix[x].length; j++){
//			if(j != y && matrix[x][j] == '*'){
//				return false;
//			}
			if(j != x && matrix[j][y] == '*'){
				return false;
			}
		}
		return true;
	}

	public static boolean diagHelper(int x, int y, char[][] matrix) {

		for(int i = x, j = y; i < matrix.length && j < matrix[i].length; i++, j++){
			if(i != x && j != y && matrix[i][j] == '*')
				return false;
		}

		for(int i = x, j = y; i >= 0 && j < matrix[i].length; i--, j++){
			if(i != x && j != y && matrix[i][j] == '*')
				return false;
		}

		for(int i = x, j = y; i > -1 && j > -1; i--, j--){
			if(i != x && j != y && matrix[i][j] == '*')
				return false;
		}

		for(int i = x, j = y; i < matrix.length && j > -1; i++, j--){
			if(i != x && j != y && matrix[i][j] == '*')
				return false;
		}

		return true;
	}
}
