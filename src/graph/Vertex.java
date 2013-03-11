package graph;

import java.util.ArrayList;

public class Vertex {

	int vTag;
	ArrayList Attractions = new ArrayList<Attraction>();
	Edge[] inEdges;
	Edge[] outEdges;
	
	public Vertex(){
		
	}
	public void setAttractions(ArrayList<Attraction> attractionList){
		Attractions = attractionList;
	}
	public Vertex (int tag){
		vTag = tag;
	}
	
	public int getTag(){
		return vTag;
	}
	
//	Vertex(String label,Edge[] ingoing, Edge [] outgoing){
//		
//	}
}
