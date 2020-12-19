package algo_storage.online_assessments;

import java.util.*;
import java.util.stream.Collectors;

public class CrederaOA {

	public static void main(String[] args) {
//		System.out.println(securePassword("foobar123!"));
//		System.out.println(airplaneSeats(2, "1A 2F 1C"));
//		System.out.println(airplaneSeats(1, "1B 1E 1H"));
		System.out.println(removeDuplicateChars("aaaa", new int[]{3, 4, 5, 6}));
	}

	public static int removeDuplicateChars(String S, int[] C){
//		int totalCost = 0;
//		List<Integer> list = Arrays.stream(C).boxed().collect(Collectors.toList());
//		String s = S;
//
//		for(int i = 0; i < s.length() - 1; i++){
//			int j = i + 1;
//
//			if(s.charAt(i) == s.charAt(j)){
//				if(list.get(i) <= list.get(j)) {
//					totalCost += list.get(i);
//					list.remove(i);
//					s = deleteCharAt(s, i);
//					i--;
//				} else {
//					totalCost += list.get(j);
//					list.remove(j);
//					s = deleteCharAt(s, j);
//					i--;
//				}
//			}
//
//		}
//
//		return totalCost;

		String s = S;
		int total = C[0];
		int costMaximal = C[0];
		for (int i = 1; i < s.length(); ++i) {

			if (s.charAt(i) != s.charAt(i - 1)) {
				total -= costMaximal;
				costMaximal = 0;
			}
			total += C[i];

			costMaximal = Math.max(costMaximal, C[i]);
		}
		return total - costMaximal;
	}

	public static String deleteCharAt(String str, int index){
		return str.substring(0, index) + str.substring(index+1);
	}


	public static boolean securePassword(String S){
		if (S.length() < 6)
			return false;

		boolean hasDigit, hasLowerCase, hasUpperCase, hasSepcial;
		hasDigit = hasLowerCase = hasUpperCase = hasSepcial = false;

		for(char c : S.toCharArray()){
			if(c == 32)
				return false;

			if(Character.isDigit(c) && !hasDigit)
				hasDigit = true;

			if(Character.isLowerCase(c) && !hasLowerCase)
				hasLowerCase = true;

			if(Character.isUpperCase(c) && !hasUpperCase)
				hasUpperCase = true;

			if("!@#$%^&*()_".contains(String.valueOf(c)) && !hasSepcial)
				hasSepcial = true;
		}

		return hasDigit && hasLowerCase && hasUpperCase && hasSepcial;
	}


	public static void printReverseDigits(int N) {
		int enable_print = N % 10;
		while (N > 0) {
			if (enable_print == 0 && N % 10 != 0) {
				enable_print = 1;
			}
			if (enable_print != 0){
				System.out.print(N % 10);
			}
			N = N / 10;
		}
	}

	public static int airplaneSeats(int N, String S){
		int totalFamilies = 0;
		HashSet<String> allocatedSeats = new HashSet<>(Arrays.asList(S.split(" ")));
		String[] cols = {"A", "B", "C", "break", "D", "E", "F", "G", "break", "H", "J", "K"};

		for(int i = 1; i <= N; i++){
			int familyAlloc = 0;
			for(int j = 0; j < cols.length; j++){
				String col = cols[j];

				if(familyAlloc == 4) {
					totalFamilies++;
					familyAlloc = 0;
				}

				if(allocatedSeats.contains(i + col)) {
					familyAlloc = 0;
					continue;
				}

				if(col.equals("break") && familyAlloc < 2)
					familyAlloc = 0;
				else if (col.equals("break") && familyAlloc > 2)
					j++;

				if(!col.equals("break"))
					familyAlloc++;
			}
		}

		return totalFamilies;
	}


}
