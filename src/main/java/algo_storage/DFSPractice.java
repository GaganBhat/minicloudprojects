package algo_storage;

import java.util.ArrayList;
import java.util.List;

public class DFSPractice {

	public static void main(String[] args) {
		findAllSubsets(new int[]{0,1,2});
	}

	public static void findAllSubsets(int[] nums){
		List<List<Integer>> list = new ArrayList<>();
		backtrackHelper(list, new ArrayList<>(), nums, 0);
		System.out.println(list.toString());
	}


	private static void backtrackHelper(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
		list.add(new ArrayList<>(tempList));
		System.out.println(tempList);
		for(int i = start; i < nums.length; i++){
			tempList.add(nums[i]);
			backtrackHelper(list, tempList, nums, i + 1);
			tempList.remove(tempList.size() - 1);
		}
	}

}
