package graph;

import java.util.ArrayList;

public class Scheduler{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Vertex> Vertices = new ArrayList<Vertex>();
		
		//ThemeParkGraph graph;
		for (int i= 0; i < 6; i++){
		Vertex V = new Vertex(i); 
		Vertices.add(V);
		}
		
		for (int i= 0; i < 6; i++){
		System.out.println("this is vertex" + i +": " + Vertices.get(i).getTag());	
		}
		
		System.out.println("total vertex count: " + Vertices.size());	
		
	}

}
