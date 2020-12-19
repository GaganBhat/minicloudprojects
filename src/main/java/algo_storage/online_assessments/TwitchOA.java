package algo_storage.online_assessments;

import java.io.*;
import java.util.*;

public class TwitchOA {

	public static void main(String[] args) {
		System.out.println("Final Solution: " + solution("A->B->C->D,E", "A,F", "swap"));
	}

	public static String solution(String drops, String dropsToOperateOn, String operation) {

		TreeMap<String, ArrayList<String>> sortedMap = new TreeMap<>();
		String[] s = drops.split(",");
		for(String drop : s){
			ArrayList<String> dropList = buildList(drop);
			sortedMap.put(dropList.get(0), dropList);
		}

		System.out.println(sortedMap.toString());

		if(operation.equals("add")){
			sortedMap = add(sortedMap, dropsToOperateOn);
		} else if (operation.equals("remove")){
			sortedMap = remove(sortedMap, dropsToOperateOn);
		} else if (operation.equals("swap")){
			sortedMap = swap(sortedMap, dropsToOperateOn);
		}

		if(sortedMap.isEmpty())
			return "INVALID";

		return convertToString(sortedMap);
	}

	public static TreeMap<String, ArrayList<String>> remove(TreeMap<String, ArrayList<String>> currentMap, String dropsToOperateOn) {

		Collection<ArrayList<String>> values = currentMap.values();

		TreeMap<String, ArrayList<String>> newMap = new TreeMap<>();
		boolean found = false;
		for(ArrayList<String> list : values){
			if(list.contains(dropsToOperateOn)) {
				list.remove(list.indexOf(dropsToOperateOn));
				found = true;
			}
			if(!list.isEmpty())
				newMap.put(list.get(0), list);
		}

		if(!found)
			return new TreeMap<>();

		return newMap;
	}


	public static TreeMap<String, ArrayList<String>> add(TreeMap<String, ArrayList<String>> currentMap, String dropsToOperateOn){
		Collection<ArrayList<String>> values = currentMap.values();
		String strBeingAddedTo = dropsToOperateOn.split(",")[0];
		String strBeingTaken = dropsToOperateOn.split(",")[1];

		ArrayList<String> listBeingAddedTo = null;
		ArrayList<String> listBeingTakenFrom = null;

		for(ArrayList<String> list : values){
			if(list.contains(strBeingAddedTo))
				listBeingAddedTo = list;
			else if (list.contains(strBeingTaken))
				listBeingTakenFrom = list;
		}

		if(listBeingAddedTo == null || listBeingTakenFrom == null)
			return new TreeMap<>();

		System.out.println("listBeingAddedTo: " + listBeingAddedTo.toString());
		System.out.println("listBeingTakenFrom: " + listBeingTakenFrom.toString());

		if(listBeingAddedTo.indexOf(strBeingAddedTo) + 1 != listBeingAddedTo.size())
			return new TreeMap<>();

		for(int i = listBeingTakenFrom.indexOf(strBeingTaken); i < listBeingTakenFrom.size(); i++){
			listBeingAddedTo.add(listBeingTakenFrom.get(i));
		}

		listBeingTakenFrom.subList(listBeingTakenFrom.indexOf(strBeingTaken), listBeingTakenFrom.size()).clear();

		currentMap.clear();

		currentMap.put(listBeingAddedTo.get(0), listBeingAddedTo);

		if(!listBeingTakenFrom.isEmpty())
			currentMap.put(listBeingTakenFrom.get(0), listBeingTakenFrom);

		System.out.println("listBeingAddedTo: " + listBeingAddedTo.toString());
		System.out.println("listBeingTakenFrom: " + listBeingTakenFrom.toString());

		return currentMap;
	}


	public static String convertToString(TreeMap<String, ArrayList<String>> currentMap){
		String s = "";
		Collection<ArrayList<String>> values = currentMap.values();
		for(ArrayList<String> list : values){
			for(int i = 0; i < list.size(); i++){
				s += list.get(i);
				if(i != list.size() - 1)
					s += "->";
			}
			if(values.size() > 1)
				s += ",";
		}

		if(s.charAt(s.length() - 1) == ',')
			s = s.substring(0, s.length() - 1);

		return s;
	}

	public static TreeMap<String, ArrayList<String>> swap(TreeMap<String, ArrayList<String>> currentMap, String dropsToOperateOn) {
		Collection<ArrayList<String>> values = currentMap.values();
		String strSwap1 = dropsToOperateOn.split(",")[0];
		String strSwap2 = dropsToOperateOn.split(",")[1];

		ArrayList<String> listSwap1 = null;
		ArrayList<String> listSwap2 = null;

		for (ArrayList<String> list : values) {
			if (list.contains(strSwap1))
				listSwap1 = list;
			if (list.contains(strSwap2))
				listSwap2 = list;
		}

		if(listSwap1 == null || listSwap2 == null)
			return new TreeMap<>();

		listSwap1.set(listSwap1.indexOf(strSwap1), strSwap2);
		listSwap2.set(listSwap2.indexOf(strSwap2), strSwap1);

		currentMap.clear();

		currentMap.put(listSwap1.get(0), listSwap1);
		currentMap.put(listSwap2.get(0), listSwap2);

		return currentMap;
	}


	public static ArrayList<String> buildList(String drop){
		String[] characters = drop.split("->");
		ArrayList<String> dropsList = new ArrayList<String>(Arrays.asList(characters));
		return dropsList;
	}

}
