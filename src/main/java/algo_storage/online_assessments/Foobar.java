package algo_storage.online_assessments;

public class Foobar {

	public static void main(String[] args) {
//		System.out.println(i_love_lance_janice("#@#@#@"));
		System.out.println(bunny_prisoner_locating(112300000,100123000));
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


}
