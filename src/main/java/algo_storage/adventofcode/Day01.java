package algo_storage.adventofcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day01 {

	public static void main(String[] args) throws Exception{
		System.out.println(getProductOfTwoSum("day01input.txt", 2020));
		System.out.println(getProductOfThreeSum("day01input.txt", 2020));
	}


	public static int getProductOfTwoSum(String inputFile, int total) throws FileNotFoundException {
		HashSet<Integer> set = new HashSet<>();

		Scanner s = new Scanner(new File(inputFile));

		while (s.hasNextLine()){
			int curr = Integer.parseInt(s.nextLine());
			if (set.contains(total - curr))
				return curr * (total - curr);
			else set.add(curr);
		}

		return 0;
	}

	public static int getProductOfThreeSum(String inputFile, int total) throws FileNotFoundException {
		List<Integer> allNums = new ArrayList<>();

		Scanner s = new Scanner(new File(inputFile));

		while (s.hasNextLine())
			allNums.add(Integer.parseInt(s.nextLine()));

		int first, second, third;
		first = second = third = 0;
		for (int i = 0; i < allNums.size(); i++){
			first = allNums.get(i);

			HashSet<Integer> set = new HashSet<>();
			int reducedTotal = total - first;

			for(int j = i; j < allNums.size(); j++){
				second  = allNums.get(j);
				if (set.contains(reducedTotal - second))
					return first * second * (reducedTotal - second);
				else set.add(second);
			}
		}

		return 0;
	}

}
