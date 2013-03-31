package graph;

import java.util.ArrayList;
/*
 * Abtract Vertex class
 */
public class Vertex {

	int vTag;
	ArrayList <Attraction> Attractions = new ArrayList<Attraction>();
	ArrayList<Edge> outEdges = new ArrayList<Edge>();
	
	public Vertex(){
		
	}
	public void addOutEdge(Edge e){
		outEdges.add(e);
	}
	
	public void edgeOutRead(){
		for(int i =0; i < outEdges.size();i++){
			System.out.println("Source: " + outEdges.get(i).getSource().vTag +", Dest:" 
		+ outEdges.get(i).getDest().vTag +", Weight: " + outEdges.get(i).getWeight());
		}
	}
//	public void addInEdge(Edge e){
//		inEdges[0] = e;
//	}
	
	
	
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
