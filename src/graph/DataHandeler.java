package graph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class DataHandeler{

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
			
			
			
//			FileInputStream fstream = new FileInputStream(args[0]); 
//
//			// Get the object of DataInputStream
//			DataInputStream in = new DataInputStream(fstream);
//			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			String strLine;
//			//Read File Line By Line
//			while ((strLine = br.readLine()) != null)   {
//				// Print the content on the console
//
//				System.out.println (strLine);
//			}
//			//Close the input stream
//			in.close();
			
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

	public void fileOneReader(String patharg){
		try{
		FileInputStream fstream = new FileInputStream(patharg); 

		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			// Print the content on the console
			tokenizeInputString(strLine);
			//System.out.println()
			//System.out.println (strLine);
		}
		//Close the input stream
		in.close();
	}catch (Exception e){//Catch exception if any
		System.err.println("Error: " + e.getMessage());}
		
	
	}
	/*
	 *support method for reader:gets the current line from file 1, and tokenize items,
	 *all items are integers (format last item which to be read as int)
	 */
	public int [] tokenizeInputString(String inLine){
		String currLine = inLine; 
		String [] stringTokens = currLine.split("//s");
		int [] intTokens = new int [stringTokens.length];

		for(int x = 0; x < stringTokens.length; x++){
			if (x == 2){
				stringTokens[2] = stringTokens[2].substring(3,4);
			}

			int intToken = Integer.parseInt(stringTokens[x]);
			intTokens[x] = intToken;			
		}
		return intTokens;
	}
}

	
