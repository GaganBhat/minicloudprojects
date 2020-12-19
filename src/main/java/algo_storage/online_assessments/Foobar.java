package algo_storage.online_assessments;

public class Foobar {

	public static void main(String[] args) {
		System.out.println(i_love_lance_janice("#@#@#@"));
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


}
