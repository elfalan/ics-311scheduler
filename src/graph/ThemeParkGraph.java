package graph;

import java.util.ArrayList;

public class ThemeParkGraph {

	ArrayList <Vertex> Vertices = new ArrayList<Vertex>();
	ArrayList <Edge> Edges = new ArrayList<Edge>();
	int totalVertices;
	int totalEdges;
	
	public ThemeParkGraph(){
		
	}
	public void setVtotal(int total){
		totalVertices = total;
	}
	public void setEtotal(int total){
		totalEdges = total;
	}
	
}
