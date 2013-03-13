package graph;

import java.util.ArrayList;

public class Scheduler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ThemeParkGraph Graph = new ThemeParkGraph(); 
		DataHandeler dh = new DataHandeler();
		
		Conductor c = new Conductor(Graph);//conductor is the interface for graph and user-input
		
		String [] attrSelect = new String [3]; //raw input select
		
		
		try{
			//retrieve the file path arguments 
			if(args.length != 2) {
				System.err.println("Invalid command line, two argument required");
				System.exit(1);
			}
		/**
		 *Data Extraction from files 
		 */
			String filePath1 = args[0];
			String filePath2 = args[1];
			//String filePath2 = args[1];
			
			//file one reader specific to file path 1.
			//reads and tokenizes items, then sets vertices edges and weights. 
			System.out.println("***************Main******************");
			System.out.println("Extrapolating data from file 1...");
			dh.fileOneReader(filePath1, Graph);
			
			System.out.println("Total vertices: " + Graph.totalVertices);
			System.out.println("Total edges: " + Graph.totalEdges);
			
			System.out.println("created "+ Graph.Vertices.size() + " vertices...");
			System.out.println("Graphs vertices Readout:");
			for (int i = 0 ; i < Graph.Vertices.size(); i ++){
				System.out.println(Graph.Vertices.get(i).vTag);
			}
			System.out.println("created "+ Graph.Edges.size() + " edges...");
			System.out.println("Graphs edges Readout of newly Created Edges:");
			for (int i = 0 ; i < Graph.Edges.size(); i ++){
				System.out.println(Graph.Edges.get(i).getReadout());
			}
			
			for(int i = 0; i < Graph.Vertices.size(); i++){
				Graph.Vertices.get(i).edgeOutRead();
			}
			
			
			System.out.println("\n");
			System.out.println("***************Main******************");
			System.out.println("Extrapolating data from file 2...");
			dh.fileTwoReader(filePath2, Graph);
			System.out.println("***************Main******************");
			
			System.out.println("Attractions in Vertices, check for not null");
			for(int i = 0; i < Graph.Vertices.size(); i++){
				for(int j = 0; j < Graph.Vertices.get(i).Attractions.size(); j++){
				System.out.println("Vertex (" + Graph.Vertices.get(i).vTag+") "+Graph.Vertices.get(i).Attractions.get(j).name);
				}
			}
			
		/**
		 * 	Manual feed of Attraction Selection and start time 
		 */
//			System.out.println();
//			attrSelect[0] = Graph.Attractions.get(0).name;
//			attrSelect[1] = Graph.Attractions.get(1).name;
//			attrSelect[2] = Graph.Attractions.get(2).name;
//			
//			System.out.println("selection 1: " + attrSelect[0]);//jurassic park
//			System.out.println("selection 2: " + attrSelect[1]);//splash mountain
//			System.out.println("selection 3: " + attrSelect[2]);//harry potter
			
			c.selector();
			
			
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}
