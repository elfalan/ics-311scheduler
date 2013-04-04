package graph;

import java.util.ArrayList;

public class Path {

	ArrayList <Edge> visited = new ArrayList<Edge>();
	int cost = 0;

	public Path(){}
	
	public Path(int c, ArrayList <Edge> Edges){
		visited = Edges;
		cost = c;
	}


	public ArrayList <Edge> getPath(){
		return visited;
	}

	public int getCost(){
		return cost;
	}

	public void readOut(){
		System.out.println("Total Cost: " + cost);
		for(int x = 0; x < visited.size(); x++){
			visited.get(x).getReadout();
		}
	}

}