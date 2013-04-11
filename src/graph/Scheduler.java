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
		int endTime = 0;


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
			//			System.out.println("Attraction Selection:" + attrSelect.size());
			//			System.out.println("Graph Vertices: " + Graph.Vertices.size());
			//			System.out.println("Graph Edges: " + Graph.Edges.size());
			try{
				boolean signal = true; //used for interval check, if continue is true execute path generation
				int i = 0;

				Attraction a_0 = attrSelect.get(i);
				Attraction a_p = attrSelect.get(i+1);
				
				
				ArrayList <Vertex> VerticesC1 = Graph.Vertices;
				ArrayList <Edge> EdgesC1 = Graph.Edges;
				
				//interval for a_0 is good
				timeKeep = c.updateTime(a_0,timeKeep); //calls adjust time
				
				System.out.println("current time after (" +  (i) + ") attraction added:" + timeKeep);
				attrSelect.remove(a_0);//remove the added item
				
				System.out.println("\n Finding Shortest Path (init) >>>");		
				Path p = c.generateShortestPath(VerticesC1,EdgesC1, a_0, a_p);
				
				System.out.println("Cost of Path from " + i  + " to " + (i+1)  + ": " + p.cost);
				timeKeep = timeKeep + p.cost;
				timeKeep = c.adjustTime(timeKeep);
				System.out.println("Current Timekeep: " + timeKeep);

				
				a_0 = a_p; //first item becomes second
				a_p = attrSelect.get(1); //get third item
				
				

				while(!(attrSelect.size() == 0)){
					
					
					
					System.out.println("\n Finding Shortest Path>>>");
					System.out.println("\nFrom: " + a_0.name +"  to  "+ a_p.name);
					System.out.println("\n ******List Size: " + attrSelect.size() + "\n Remaining to be added:");
					for(int q = 0; q < attrSelect.size(); q++){
						System.out.println( (1+q) + ". " + attrSelect.get(q).name);
					}
					
					ArrayList <Vertex> VerticesC2 = new ArrayList <Vertex>();
					ArrayList <Edge> EdgesC2 = new ArrayList <Edge>();
					VerticesC2 = Graph.Vertices;
					EdgesC2 = Graph.Edges;
					
					Path p1 = c.generateShortestPath(VerticesC2,EdgesC2, a_0, a_p);

					System.out.println("Checking interval...");	
					signal = c.intervalCheck(timeKeep, a_p, signal);
					
					if (signal != false){	
						timeKeep = c.updateTime(a_p,timeKeep);
						timeKeep = c.adjustTime(timeKeep);
						System.out.println("current time after (" +  (a_p.name) + ") attraction added:" + timeKeep);
						System.out.println("Cost of Path: " + p1.cost);
						timeKeep = timeKeep + p1.cost;
						timeKeep = c.adjustTime(timeKeep);
						System.out.println("Current Timekeep: " + timeKeep);

						attrSelect.remove(a_0);
						a_0 = a_p;
						a_p = attrSelect.get(0);
						
						System.out.println("\n ******List Size: " + attrSelect.size());
						
						if(attrSelect.size() == 1){
							System.out.println("last attraction is being added...");
							timeKeep = c.updateTime(a_0, timeKeep);
							System.out.println("current time after (" +  (a_p.name) + ") attraction added:" + timeKeep);
							//System.out.println("Cost of Path: " + p1.cost);
							//timeKeep = timeKeep + p1.cost;
							timeKeep = c.adjustTime(timeKeep);
							System.out.println("Current Timekeep: " + timeKeep);
							attrSelect.remove(a_0);
							endTime = timeKeep;
							System.out.println("\n ******List Size: " + attrSelect.size());
							System.out.println("End-Time: " + endTime);
						}

						
					}

					else if (signal == false){
						System.err.println("Attraction (" + a_p.name + ") was rejected");
						//shuffle this attraction to the end of the list
						Attraction temp = a_p;
						attrSelect.remove(a_p);
						//int last = attrSelect.size()-1;
						a_p = attrSelect.get(0);
						attrSelect.add(temp);
						System.err.println("Current Timekeep: " + timeKeep);
					}
				
					//reset values for next loop cycle
					p1 = null;
					VerticesC2 = null;
					EdgesC2 = null;
				}

			}
			catch(Exception e){

				System.err.println("error in shortest path method \n" + e.getCause() + e.getStackTrace().toString());

			}



		}catch (Exception e){//Catch exception if any
			System.err.println("Error in Main: " + e.getMessage());
		}
	}

}
