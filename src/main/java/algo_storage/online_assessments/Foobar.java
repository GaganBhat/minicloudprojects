package algo_storage.online_assessments;

import java.util.HashSet;
import java.util.Set;

public class Foobar {

	public static void main(String[] args) {
//		System.out.println(i_love_lance_janice("#@#@#@"));
//		System.out.println(bunny_prisoner_locating(112300000,100123000));
		System.out.println(ion_flux_relabeling(5, new int[]{19, 14, 28}));
	}


	public static String i_love_lance_janice(String x){
		String s = "";
		for(int i = 0; i < x.length(); i ++){
			if(Character.isLowerCase(x.charAt(i)))
				s += (char) ('z' - (x.charAt(i) - 'a'));
			else
				s += x.charAt(i);
		}
		return s;
	}


	public static String bunny_prisoner_locating(long x, long y){

		long baseVal = (long) (0.5 * x *(x+1));
		long additive = x;

		for(long i = 0; i < y-1; i++){
			baseVal += additive;
			additive++;
		}

		return String.valueOf(baseVal);
	}


	public static int[] ion_flux_relabeling(int h, int[] q){

		int starter = (int) Math.pow(2,h) - 1;
		int[] resArray = new int[q.length];

		for (int i = 0 ; i < q.length ; i++) {
			if (q[i] < starter && q[i] >=1)
				resArray[i] = findNode(starter,q[i],starter-1);
			else
				resArray[i] = -1;
		}

		return resArray;
	}

	public static int findNode(int starter, int target, int underNode) {

		underNode /= 2;
		int right = starter - 1;
		int left = starter - 1 - underNode--;

		if (right == target || left == target)
			return starter;
		else {
			if (target <= left)
				return findNode(left,target,underNode);
			else
				return findNode(right,target,underNode);
		}
	}



}
