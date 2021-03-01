package algo_storage;


import java.util.*;

public class CatalinaOfBabel {

	public static void tarjanHelper(ArrayList[] adjacencyList, int pointNode) {

		underlyingDFS[pointNode] = ind;
		numericalDFS[pointNode] = ind;

		ind++;
		dfsStack.push(pointNode);

		visitedArr[pointNode] = true;

		for (int i=0; i<adjacencyList[pointNode].size(); i++) {

			int next = (Integer)adjacencyList[pointNode].get(i);


			if (numericalDFS[next] == unvisit)
				tarjanHelper(adjacencyList, next);

			if (visitedArr[next])
				underlyingDFS[pointNode] = Math.min(underlyingDFS[next], underlyingDFS[pointNode]);
		}

		if (underlyingDFS[pointNode] == numericalDFS[pointNode]) {

			int curSz = 0;

			while (true) {
				int previousDFSStack = dfsStack.pop();

				visitedArr[previousDFSStack] = false;

				curSz++;
				if (pointNode == previousDFSStack) break;

			}

			resolute = Math.max(resolute, curSz);
		}
	}

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);

		nodesNum = Integer.parseInt(stdin.nextLine().trim());

		adjList = new ArrayList[nodesNum];

		for (int i = 0; i< nodesNum; i++)
			adjList[i] = new ArrayList<Integer>();

		numericalDFS = new int[nodesNum];
		Arrays.fill(numericalDFS, unvisit);

		underlyingDFS = new int[nodesNum];
		visitedArr = new boolean[nodesNum];
		dfsStack = new Stack<>();
		ind = 0;

		HashSet[] langUnderstood = new HashSet[nodesNum];
		String[] langSpoke = new String[nodesNum];

		for (int i = 0; i< nodesNum; i++) {
			Scanner s = new Scanner(stdin.nextLine());
			s.next();
			langSpoke[i] = s.next();
			langUnderstood[i] = new HashSet<String>();

			langUnderstood[i].add(langSpoke[i]);

			while (s.hasNext())
				langUnderstood[i].add(s.next());
		}

		for (int i = 0; i< nodesNum; i++) {
			for (int j = 0; j< nodesNum; j++) {
				if (i == j) continue;

				if (langUnderstood[j].contains(langSpoke[i]))
					adjList[i].add(j);
			}
		}

		finNum = 0;
		resolute = 0;

		for (int i = 0; i< nodesNum; i++)
			if (numericalDFS[i] == unvisit)
				tarjanHelper(adjList, i);

		System.out.println(nodesNum - resolute);
	}


	static int unvisit = -1;
	static int nodesNum;

	static ArrayList[] adjList;

	static int[] numericalDFS;
	static int[] underlyingDFS;

	static boolean[] visitedArr;

	static Stack<Integer> dfsStack;
	static int ind;
	static int finNum;

	static int resolute;
}