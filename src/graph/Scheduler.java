package graph;

public class Scheduler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ThemeParkGraph Graph = new ThemeParkGraph(); 
		DataHandeler dh = new DataHandeler();
		
		try{
			//retrieve the file path arguments 
			if(args.length != 1) {
				System.err.println("Invalid command line, exactly one argument required");
				System.exit(1);
			}
			
			String filePath1 = args[0];
			//String filePath2 = args[1];
			
			//file one reader specific to file path 1.
			//reads and tokenizes items, then sets vertices edges and weights. 
			dh.fileOneReader(filePath1, Graph);
			System.out.println("Total vertices: " + Graph.totalVertices);
			System.out.println("Total edges: " + Graph.totalEdges);
			
			System.out.println("created "+ Graph.Vertices.size() + " vertices...");
			System.out.println("Graphs vertices Readout:");
			for (int i = 0 ; i < Graph.Vertices.size(); i ++){
				System.out.println(Graph.Vertices.get(i).vTag);
			}
			System.out.println("created "+ Graph.Edges.size() + " edges...");
			System.out.println("Graphs edges Readout:");
			for (int i = 0 ; i < Graph.Edges.size(); i ++){
				System.out.println(Graph.Edges.get(i).getReadout());
			}
			
			
			
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}
