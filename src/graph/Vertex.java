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
	
//private ArrayList <Edge> sortByWeight(ArrayList<Edge> edges){
//		
//		for(int i = 0; i < edges.size(); i++){
//			for(int j = 1; j < (edges.size()-i); j++){
//				if(edges.get(j-1).weight > edges.get(j).weight){
//					Edge temp = edges.remove(j-1);
//					edges.add(j,temp);
//				}
//			}
//		}			
//
//		return edges;
//	}
	
//	Vertex(String label,Edge[] ingoing, Edge [] outgoing){
//		
//	}
}
