package graph;

import java.util.ArrayList;
import java.util.Stack;

public class Scheduler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ThemeParkGraph Graph = new ThemeParkGraph(); 
		DataHandeler dh = new DataHandeler();

		Conductor c = new Conductor(Graph);//conductor is the interface for graph and user-input
		int timeKeep = 0;

		ArrayList <Attraction> attrSelect = new ArrayList<Attraction>(); //raw input select
		int startTime = 0;


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
				Graph.Edges.get(i).getReadout();
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
			 * 	Seed Attraction Selection and start time 
			 */
			//use input console to get selection
			attrSelect = c.selector();

			System.out.println("Start-Time set to : " + startTime); //preliminary check
			System.out.println();
			c.attractionPrint(attrSelect);

			attrSelect = c.SortByDuration(attrSelect);
			System.out.println("**********After Sort**********");
			System.out.println();
			c.attractionPrint(attrSelect);
			System.out.println();

			//start time must be set after intial sort of attractions to see if it fits interval
			startTime = c.setStartTime(attrSelect.get(0));//send the first attraction (longest duration) as constraint for start time
			System.out.println("Start Time succefully set to: " + startTime);
			
			/**
			 * generate schedule
			 */
			timeKeep = startTime;
			System.out.println("Attraction Selection:" + attrSelect.size());
			System.out.println("Graph Vertices: " + Graph.Vertices.size());
			System.out.println("Graph Edges: " + Graph.Edges.size());

		//	for (int i = 0; i < attrSelect.size(); i++){
			timeKeep = c.updateTime(attrSelect.get(0),timeKeep);
			System.out.println("current time after (" +  1 + ") attraction added:" + timeKeep);
			
			
			try{
			c.generateShortestPath(Graph.Vertices,Graph.Edges, attrSelect.get(0), attrSelect.get(1));
			}
			catch(Exception e){
				
				System.err.println("error in shortest path method \n" + e.getCause() + e.getStackTrace().toString());
			}
		//	}



		}catch (Exception e){//Catch exception if any
			System.err.println("Error in Main: " + e.getMessage());
		}
	}

}
