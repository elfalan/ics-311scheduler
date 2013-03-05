package graph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class Scheduler{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		 try{
			 //retrieve the file path arguments 
			 if(args.length != 1) {
				  System.err.println("Invalid command line, exactly one argument required");
				  System.exit(1);
				}
				FileInputStream fstream = new FileInputStream(args[0]); 
	        
	         	// Get the object of DataInputStream
	            DataInputStream in = new DataInputStream(fstream);
	                BufferedReader br = new BufferedReader(new InputStreamReader(in));
	            String strLine;
	            //Read File Line By Line
	            while ((strLine = br.readLine()) != null)   {
	              // Print the content on the console
	          
	            	System.out.println (strLine);
	            }
	            //Close the input stream
	            in.close();
	            }catch (Exception e){//Catch exception if any
	              System.err.println("Error: " + e.getMessage());
	            }
    
	
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
