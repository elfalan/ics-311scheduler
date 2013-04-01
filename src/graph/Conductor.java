package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Conductor {
	int [] selection;
	String [] temp;
	ArrayList <Attraction> attrUser = new ArrayList <Attraction>(); //sub list of user selected attractions

	ThemeParkGraph g = new ThemeParkGraph();
	public Conductor(ThemeParkGraph tpg){
		g = tpg;
	}
	/*
	 * process user input from console
	 */
	public ArrayList <Attraction> selector(){
		boolean inRange = false;
		boolean inLoop = true;
		try{
			System.out.println();
			Scanner scan = new Scanner(System.in);
			int x = g.Attractions.size();

			while (inLoop != false){
				//print out selections
				for(int i = 0; i < g.Attractions.size(); i++){
					System.out.println((i+1) + "- " + g.Attractions.get(i).name);
				}
				//open scanner, retrieve selections
				System.out.println("\nplease enter up to ("+ x + ") selections for attractions separated by commas:");
				String userin = scan.next();
				temp = userin.split(",");
				selection = new int[temp.length];

				System.out.println("your selection was: ");
				for(int j = 0; j < temp.length; j++){
					selection[j] = Integer.parseInt(temp[j]);
					System.out.print(selection[j]+" ; ");
				}

				for(int k = 0; k < selection.length; k++){
					if((selection[k] > x)||(selection[k] < 1)){	
						inRange = false;
						System.err.println("Invalid input:" +selection[k]);
						if (k == selection.length-1){
							System.err.println("Please Try again.");
						}
					}	
					else{
						inRange = true;
					}
				}	

				if (inRange != false){
					inLoop = false;
					//scan.close();
					break;
				}
			}

			System.out.println("\nAttractions:");
			for(int q = 0; q < selection.length; q++){
				attrUser.add(g.Attractions.get(selection[q]-1));
				System.out.println(attrUser.get(q).name);
			}

		}
		catch(Exception e){
			System.err.println("failed on selector input");
		}
		return attrUser;
	}


	public int setStartTime(Attraction a){
		int t = 0;
		boolean loop = true;
		String temp = "";
		int constraint1 = a.intervalList.get(0).startT;
		int endTime = a.intervalList.get(0).endT;

		int hrs = (a.duration/100) * 100;
		int mins = 0;

		if(a.duration < 100){
			mins = a.duration;
		}
		if(a.duration > 100){
			mins = a.duration - hrs;
		}

		int constraint2 = a.intervalList.get(0).endT - hrs - mins ;
		if(constraint2%100 >= 60){
			constraint2 = constraint2 - (constraint2%100 - 30);
		}

		System.out.println("duration total:" + a.duration);
		System.out.println("duration hrs:" + hrs);
		System.out.println("duration mins:" + mins);
		//		int [][] inteverval = new int [a.intervalList.size()][]; //initiated to the size of availability times



		try{

			Scanner scan = new Scanner(System.in);

			while(loop != false){
				System.out.println("For optimized shcedule, " +
						"please specify a start time between [" + constraint1 + " and "+ constraint2 + "]. " +
						"\n(format must be hh:mm:ss)");

				temp = scan.next();
				temp = temp.substring(0,5);
				temp = temp.replace(":","");
				t = Integer.parseInt(temp);


				if (((t < constraint1)||(t > constraint2))||(t%100 >= 60)){
					System.err.println("Invalid Input:" + t);	
					System.err.println("Input start time is out of bounds." +
							" Please specify a start-time between " + constraint1 + " and " + constraint2);
					loop = true;

				}

				else{
					loop = false;
				}
			}
		}
		catch(Exception e){
			System.err.println("Error:" + e);
		}
		return t;

	}
	/*
	 * This sort is meant for the intial sorting of the users selection
	 * sort largest to smallest by ride duration
	 */
	public ArrayList<Attraction> SortByDuration(ArrayList <Attraction> attrList){

		for(int i = 0; i < attrList.size(); i++){
			for(int j = 1; j < (attrList.size()-i); j++){
				if(attrList.get(j-1).duration < attrList.get(j).duration){
					Attraction a = attrList.remove(j-1);
					attrList.add(j,a);
				}
			}
		}			
		return attrList;
	}

	/**
	 * Main Computation Algorithms
	 */
	private int adjustTime(int t){
		if(t%100 >= 60){
			int temp = (t/100)*100;
			t = (temp+100) + (t%100 - 60);
		}
		return t;
	}

	public int updateTime(Attraction a, int TK){
		int d = a.duration;
		TK = TK + d;
		TK = adjustTime(TK);
		return TK;
	}

	public void generateShortestPath(ArrayList <Vertex> Vertices,ArrayList<Edge> Edges,Attraction a1, Attraction a2){

		int current = Integer.parseInt(a1.owner);
		int next = Integer.parseInt(a2.owner); 

		//retrieve actual vertex
		Vertex Vs  = Vertices.get(current-1);//source vertex
		Vertex UD = Vertices.get(next-1); //ultimate destination vertex of next attraction

		Edge sourceE = new Edge();  //arbitrary edge
		//int subs = V1.outEdges.size();
		ArrayList<Edge> visitSet = new ArrayList<Edge>(); //collection of edges 
		ArrayList <Edge> path = new ArrayList<Edge>();
		Map<Integer, ArrayList<Edge>> map = new TreeMap<Integer, ArrayList<Edge>>();
		int totalweight = 0;

		try{
			System.out.println();
			System.out.println("ultm Dest Vertex UD: " + UD.getTag());

			System.out.println("Source Vertex Vs: " + Vs.getTag());

			System.out.println("Vs edges:" + Vs.outEdges.size());
			Vs.outEdges = sortByWeight(Vs.outEdges);

			Vs.edgeOutRead();

			for(int x = 0; x < Vs.outEdges.size(); x++){

				sourceE = Vs.outEdges.get(x);	
				System.out.println("**Top Level** Sending source Edge: ");
				sourceE.getReadout();
				visitSet.add(sourceE);

				path = recurseDepthSearch(visitSet,sourceE,UD); //visit initialized with first edge
				System.out.println("Path #" + (x+1));

				for(int y = 0; y < path.size();y++){	
					path.get(y).getReadout();
					totalweight = totalweight + path.get(y).weight;
				}
				System.out.println("total weight: " + totalweight);
				map.put(totalweight,path);	

				//zero out variables
				path.removeAll(Edges);
				visitSet.removeAll(Edges);
				totalweight = 0;

			}

			System.out.println("weights: " + map.keySet().toString());



		}
		catch(Exception e){
			System.err.println("Error in gen method:" + e.getMessage() + "\n" + e.getCause());
			e.printStackTrace();
		}

	}

	public ArrayList<Edge> recurseDepthSearch(ArrayList<Edge> pathSet, Edge e, Vertex UD){
		boolean ks = true;
		Vertex Vs = e.getSource();
		Vertex Vd = e.getDest(); 


		if(Vd.equals(UD)){
			//System.out.println("Adding Edge to Set:"); 
			//e.getReadout();
			//pathSet.add(e); //add edge and quit
			System.out.println("Found Destination@1");
			//return pathSet;
			ks = false;
		}
		if(Vs.equals(UD)){
			System.out.println("Found Destination@1");
			pathSet.remove(0);
			ks = false;
		}
		//else if (!Vd.equals(UD)){
		else{	
			//remove cycles
			System.out.println("Current edges of V " + Vd.getTag());
			for(int z = 0; z < Vd.outEdges.size(); z++){
				Vd.outEdges.get(z).getReadout();	
			}
			//filter out
			for(int x = 0; x < Vd.outEdges.size(); x++){
				if (Vd.outEdges.get(x).getDest().equals(Vs)){
					Vd.outEdges.remove(x);
				}
			}
			System.out.println("After filter Vd:");
			for(int z = 0; z < Vd.outEdges.size(); z++){
				Vd.outEdges.get(z).getReadout();	
			}
			//check for direct path
			for(int y = 0; y < Vd.outEdges.size(); y++){
				if (Vd.outEdges.get(y).getDest().equals(UD)){
					System.out.println("Adding Edge to Set:"); 
					Vd.outEdges.get(y).getReadout();
					pathSet.add(Vd.outEdges.get(y));

					System.out.println("found Destination@2");
					ks = false;
					break;
				}
			}
			if (ks != false){ //if keep searching is true

				System.out.println("Adding Edge to Set:"); 
				Vd.outEdges.get(0).getReadout();
				pathSet.add(Vd.outEdges.get(0));
				System.out.println("making recursive call, sending edge:");
				Vd.outEdges.get(0).getReadout();
				recurseDepthSearch(pathSet,Vd.outEdges.get(0),UD); //take smallest weight as new edge for search, Dijkstra's Algorithm
			}
		}


		return pathSet;
	}


	//returns the short edge
	private ArrayList <Edge> sortByWeight(ArrayList<Edge> edges){

		for(int i = 0; i < edges.size(); i++){
			for(int j = 1; j < (edges.size()-i); j++){
				if(edges.get(j-1).weight > edges.get(j).weight){
					Edge temp = edges.remove(j-1);
					edges.add(j,temp);
				}
			}
		}			

		return edges;
	}



	//	public ArrayList<Attraction> SortByVertex(ArrayList <Attraction> attrList){
	//		
	//		for(int i = 0; i < attrList.size(); i++){
	//			for(int j = 1; j < (attrList.size()-i); j++){
	//				if(!(attrList.get(j-1).owner.equals(attrList.get(j).owner))){
	//					Attraction a = attrList.remove(j-1);
	//					attrList.add(j,a);
	//				}
	//			}
	//		}			
	//		return attrList;
	//	}


	public void attractionPrint(ArrayList <Attraction> a){
		System.out.println("Attraction printout:");
		for (int i = 0; i < a.size(); i++){
			System.out.println(a.get(i).name +" , "+ a.get(i).duration);
		}
	}
}


