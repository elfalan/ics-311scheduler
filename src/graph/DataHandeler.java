package graph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
public class DataHandeler{

	public DataHandeler(){

	}

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
				int [] farr = new int[3];
				if (count == 0){ //executes for the first line only
					farr = tokenizeInputString(strLine);
					System.out.println("printing farr token vals - 1st line: ");
					for(int x = 0; x < farr.length; x++){
						System.out.println(farr[x]);
					}
					g.setVtotal(farr[0]);
					g.setEtotal(farr[1]);	
					//Create all vertices
					g.Vertices = makeVertices(g.Vertices,g.totalVertices);
					count++;
					
				}
				else{
					//create all edges
					farr = tokenizeInputString(strLine);
					System.out.println("printing farr token vals:");
					for(int x = 0; x < farr.length; x++){
						System.out.println(farr[x]);
					}
					// input to constructor vertices a,b and weight
					//index starts at 0. retrieving V by index which is input val-1
					Edge e = new Edge(g.Vertices.get((farr[0]-1)),g.Vertices.get((farr[1]-1)),farr[2]);  
					g.Edges.add(e);

				}

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
		int [] Tokens = new int [3];
		//DateFormat df = new SimpleDateFormat("HH.mm.ss");
		//Date time = null; 

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
//			try {
//				time = (Date) df.parse(timetoken);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			timetoken = timetoken.substring(0,5);
			timetoken = timetoken.replace(":","");
			Tokens[2] = Integer.parseInt(timetoken);
		}

		Tokens[0] = Integer.parseInt(st[0]);
		Tokens[1] = Integer.parseInt(st[1]);

		System.out.println("intTokens ("+ Tokens.length +")");
		for (int i = 0; i < Tokens.length ; i++){
			System.out.println(i+1 +":" + Tokens[i]);
		}
		return Tokens;
	}

	public ArrayList<Vertex> makeVertices(ArrayList<Vertex> list, int total){
		for (int i= 0; i < total; i++){
			Vertex V = new Vertex(i+1); //vtag is set from constructor
			list.add(V);
		}
		return list;
	}

}


