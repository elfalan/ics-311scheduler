package graph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class DataHandeler{

	public DataHandeler(){

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


	//
	//		for (int i= 0; i < 6; i++){
	//			Vertex V = new Vertex(i); 
	//			Vertices.add(V);
	//		}
	//
	//		for (int i= 0; i < 6; i++){
	//			System.out.println("this is vertex" + i +": " + Vertices.get(i).getTag());	
	//		}
	//
	//		System.out.println("total vertex count: " + Vertices.size());	
	//
	//	}

	public void fileOneReader(String patharg, ThemeParkGraph g){
		try{
			FileInputStream fstream = new FileInputStream(patharg); 

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int count = 0;

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {			
				if (count == 0){ //executes for the first line only
					int [] farr = new int[3];
					farr = tokenizeInputString(strLine);
					System.out.println("printing farr token vals: ");
					for(int x = 0; x < farr.length; x++){
						System.out.println(farr[x]);
					}
					g.setVtotal(farr[0]);
					g.setEtotal(farr[1]);	

					//Create all vertices
					g.Vertices = makeVertices(g.Vertices,g.totalVertices);
					count++;
				}

				//create all edges

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
		int [] intTokens = new int [3];
		System.out.println("currLine: " + currLine);


		currLine = currLine.replace("\t","_");
		System.out.println("currLine: " + currLine);
		String [] st = currLine.split("_");

		System.out.println("StringTokens ("+ st.length +")");
		for (int i = 0; i < st.length ; i++){
			System.out.println( i+1 +":" + st[i]);
		}
		// if the string contains the time token
		if (st.length == 3){
			String timetoken = st[2];
			timetoken = timetoken.replace("00:", "");
			timetoken = timetoken.replace(":00", "");
			intTokens[2] = Integer.parseInt(timetoken);
		}

		intTokens[0] = Integer.parseInt(st[0]);
		intTokens[1] = Integer.parseInt(st[1]);



		System.out.println("intTokens ("+ intTokens.length +")");
		for (int i = 0; i < intTokens.length ; i++){
			System.out.println(i+1 +":" + intTokens[i]);
		}

		return intTokens;
	}

	public ArrayList<Vertex> makeVertices(ArrayList<Vertex> list, int total){
		for (int i= 0; i < total; i++){
			Vertex V = new Vertex(i); 
			list.add(V);
		}
		return list;

	}

}


