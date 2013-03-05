package graph;

import java.util.ArrayList;

public class Vertex {

	int vTag;
	ArrayList Attrset = new ArrayList<attraction>();
	Edge[] inEdges;
	Edge[] outEdges;
	
	public Vertex(){
		
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
