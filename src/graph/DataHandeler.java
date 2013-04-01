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
/**
 * The DataHandeler takes the information from the file inputs and parses them into the custom objects
 */
	public DataHandeler(){

	}
	/**
	 * code for handling File 1, vertex and edge data
	 */
	public void fileOneReader(String patharg, ThemeParkGraph g){
		try{
			FileInputStream fstream = new FileInputStream(patharg); 

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int count = 0;
			int [] farr = new int[3];
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {	

				if (count == 0){ //executes for the first line only
					farr = tokenizeInput1String(strLine);
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
					farr = tokenizeInput1String(strLine);
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
			
			for(int y = 0; y <g.Edges.size(); y++){
				int sourceV = g.Edges.get(y).getSource().getTag();
				g.Vertices.get(sourceV-1).addOutEdge(g.Edges.get(y));
			}
		
			
			
			
			//garbage collection
			farr = null;
				
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());}

	}


	/*
	 *support method for reader:gets the current line from file 1, and tokenize items,
	 *all items are integers (format last item which to be read as int)
	 */
	public int [] tokenizeInput1String(String inLine){
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
	/**
	 * Code for handling file 2, Attractions and time intervals
	 */

	public void fileTwoReader(String patharg, ThemeParkGraph g){
		try{
			FileInputStream fstream = new FileInputStream(patharg); 

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int count = 0;
			int i = 0;
			int j = 0;
			int max = 0;
			ArrayList <Attraction> attrList = new ArrayList<Attraction>();

			String [] strar = new String [6];

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {	

				if (count == 0){ //executes for the first line only
					strar = tokenizeInput2String(strLine);

					max = Integer.parseInt(strar[0]); //cast string to int
					System.out.println("Creating (" + max + ") Attractions - 1st line: ");
					while(j <= max){ //max is total attractions to create
						Attraction a = new Attraction();
						attrList.add(a);
						j++;
					}
					count++;
				}
				else{
					//pull Attraction names and intervals, add to Attractions
					strar = tokenizeInput2String(strLine);
					System.out.println("printing strar token vals");
					for(int x = 0; x < strar.length; x++){
						System.out.println("\n" + strar[x]);
					}


					ArrayList <String> times = new ArrayList<String>();
					for(int t = 2; t < strar.length; t++){
						times.add(strar[t]);
						System.out.println("added time val:"+strar[t]);
					}

					attrList.get(i).setAttributes(strar[0],strar[1], times);
					System.out.println("added attributes to new Attraction ");
				
					//attrList.get(i).printout();
					i++;

				}
			}//end of readline
			//Close the input stream
			in.close();
			strar = null;
		
			/**
			 * convert attraction interval from string to int
			 */
			String timeToken = "";
			String time1 = "";
			String time2 = "";
			//covert intervals to int format
			for(int k = 0; k < attrList.size()-1;k++){
				//times is a string arraylist, needs to be split and coverted to int
				for(int z = 0; z < attrList.get(k).times.size(); z++){	
					if(z == 0){
					timeToken = attrList.get(k).times.get(z);
					timeToken = timeToken.substring(0,5);
					timeToken = timeToken.replace(":","");
					int d = Integer.parseInt(timeToken);
					attrList.get(k).setDuration(d);
					//System.out.println("duration set, iteration: " + z);
					}
					else{
					timeToken = attrList.get(k).times.get(z);
					//System.out.println("timetoken: " + timeToken);
					//start time
				    time1 = timeToken.substring(0, 5);
					time1 = time1.replace(":","");
					//System.out.println("start time" + time1);
					int t1 = Integer.parseInt(time1);
					//end time
					time2 = timeToken.substring(9, 14);
					time2 = time2.replace(":","");
					int t2 = Integer.parseInt(time2);
					
					Interval I = new Interval(t1,t2);
					attrList.get(k).intervalList.add(I);
					}
				}
				System.out.println("+++++++++++++++++++++++++++++++\n");
				System.out.println("Attraction ("+ (k+1) + ")");
				attrList.get(k).printout();
			}
			
			attrList.remove(7);//fix null added at end
			g.Attractions = attrList; //graph holds a reference to full list of attractions for selections
			System.out.println("total attractions: " + g.Attractions.size());
			
			
			/**
			 * Connect up pieces of graph, edges with vertices, attractions to vertices
			 */
			System.out.println("\n");
			System.out.println("Setting attractions to Vertices");	
			
			int owner = 0;
			for(int y = 0; y < attrList.size()-1; y++){
				owner = ((Integer.parseInt(attrList.get(y).owner))-1); //get the owner of current attraction, owner-1 refers to array index at
				g.Vertices.get(owner).Attractions.add(attrList.get(y)); //add current attraction to owner
				
				System.out.println("Vertex (" + g.Vertices.get(owner).vTag + ") has recieved attraction " + attrList.get(y).name);
			}
			
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());}

	}	

	public String [] tokenizeInput2String(String currLine){
		String [] tokens = new String [6];
		String input = currLine;
		System.out.println("tokenize: current line" + input);
		input = input.replace("\t","_");
		System.out.println("tokenize: current line" + input);
		tokens = input.split("_");

		return tokens;
	}
	
	
	
	


}


