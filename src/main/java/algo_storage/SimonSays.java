package algo_storage;

import java.util.*;

class SimonSays {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		// code here from scanner input

		s.nextLine();
		StringBuilder output = new StringBuilder();
		while(s.hasNextLine()){
			String next = s.nextLine();
			if(next.startsWith("Simon says")){
				output.append(next.substring(11, next.length()) + "\n");
			}
		}

		System.out.println(output.toString());
	}
}
