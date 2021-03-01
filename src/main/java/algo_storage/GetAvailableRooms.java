package algo_storage;

import java.util.*;

public class GetAvailableRooms {

	public static void main(String[] args) {
		String test = "6 2\n" +
				"2 5";
		Scanner sc = new Scanner(test);

		int totalLength = sc.nextInt();

		int partNums = sc.nextInt();

		partNums +=2;

		int[] parts = new int[partNums];

		parts[0] = 0;
		for(int i=1;i<partNums-1;i++){
			parts[i] = sc.nextInt();
		}

		parts[partNums-1] = totalLength;

		int currentRoom;
		boolean found = false;

		for(int i=1;i<=totalLength;i++){
			found = false;
			for(int j=0;j<partNums;j++){
				for(int k=j+1;k<partNums;k++){
					currentRoom = parts[k]-parts[j];
					if(currentRoom==i){
						System.out.print(i+" ");
						found = true;
						break;
					}

				}
				if(found)
					break;
			}

		}
	}
}