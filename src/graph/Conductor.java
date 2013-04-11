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
		int start = a.intervalList.get(0).startT;
		int end = a.intervalList.get(0).endT;
		
		int hrs = (a.duration/100) * 100;
		int mins = 0;

		if(a.duration < 100){
			mins = a.duration;
		}
		if(a.duration > 100){
			mins = a.duration - hrs;
		}
		int endTime = a.intervalList.get(0).endT - hrs - mins ;
		if(endTime%100 >= 60){
			endTime = endTime - (endTime%100 - 30);
		}

		System.out.println("duration total:" + a.duration);
		System.out.println("duration hrs:" + hrs);
		System.out.println("duration mins:" + mins);
		System.out.println("interval: " + start + " -- " + end);
		System.out.println("actual interval: " + start + " -- " + endTime);
		
	
		
		//		int [][] inteverval = new int [a.intervalList.size()][]; //initiated to the size of availability times



		try{

			Scanner scan = new Scanner(System.in);

			while(loop != false){
				System.out.println("For optimized shcedule, " +
						"please specify a start time between [" + start + " and "+ endTime + "]. " +
						"\n(format must be hh:mm:ss)");

				temp = scan.next();
				temp = temp.substring(0,5);
				temp = temp.replace(":","");
				t = Integer.parseInt(temp);


				if (((t < start)||(t > endTime))||(t%100 >= 60)){
					System.err.println("Invalid Input:" + t);	
					System.err.println("Input start time is out of bounds." +
							" Please specify a start-time between " + start + " and " + endTime);
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
	public int adjustTime(int t){
		if(t%100 >= 60){
			int temp = (t/100)*100;
			t = (temp+100) + (t%100 - 60);
		}
		return t;
	}
	
	public int adjustTimeSub(int t){
		if(t%100 >= 60){
				t = t - (t%100 - 30);
		}
		return t;
	}

	public int updateTime(Attraction a, int TK){
		int d = a.duration;
		System.out.println("update time... " + d + " + " + TK);
		TK = TK + d;
		TK = adjustTime(TK);
		System.out.println("update time...total: " + TK);
		return TK;
	}

	public boolean intervalCheck(int tk, Attraction a, boolean result){
		Interval current = new Interval();
		int end = 0;
		int start = 0;
		int duration = 0;
		int sfd = 0;
		int efd = 0;
		int diff = 0;
		int loopC = 0;
		String interval = "";

		System.out.println("+++++++++++++++++++");
		System.out.println("Current Time:" + tk);
		System.out.println("Attraction: " + a.name);
		
		for(int i = 0; i < a.intervalList.size(); i ++){ 
			current  = a.intervalList.get(0);
			interval = "Interval (" + i +") "+  current.startT + " -- " + current.endT;
			System.out.println(interval);
			
			//check that after subtracting the duration time is adjusted
			duration = a.duration;
			end = current.endT - duration;
			
			end = adjustTimeSub(end);
			
			//int firstdigit = end/100;
			//int numcheck = (firstdigit*100) + 59;
			
			//if (end > numcheck){
			//	end = adjustTime(end);
			//}
			
			start = current.startT;
			
			interval = "Actual range Interval (" + (i) +") "+  start + " -- " + end;
			System.out.println(interval);

			//	efd = end/100;
			//	efd = (efd*100); //beginning of end hr
			
			//interval total
			diff = end - start;
			int hrs = (diff/100)*100;
			int mins = 0;

			if(diff < 100){
				mins = diff;
			}
			if(diff > 100){
				mins = diff - hrs;
			}

			System.out.println("difference: " + "hrs: " + hrs + " mins: " + mins);

			
				tk = adjustTime(tk);
				efd = start + hrs + mins;
				efd = adjustTime(efd);

		//		if(((tk > start)&&(tk < sfd))||((tk > efd)&&(tk < end))){ //tk is between (start/start+max) and (end/end max) 
				if((tk >= start)&&(tk <= efd)){
					//if (tk <= end-duration){ //check if there is enough time left over for the ride duration
						System.out.println("*******Interval Range GOOD*******");
						result = true;//passed check, continue signal is true
						break;
					}
					else{
						result = false;
						System.out.println("^^^^^^^^Interval NO MATCH^^^^^^^^");
						//remove the non matching interval
						a.intervalList.remove(0);
					}
				}

		return result;
	}

	//returns path with least weighted cost
	public Path generateShortestPath(ArrayList <Vertex> Vertices,ArrayList<Edge> Edges,Attraction a1, Attraction a2){

		int current = Integer.parseInt(a1.owner);
		int next = Integer.parseInt(a2.owner); 

		//retrieve actual vertex
		Vertex Vs  = Vertices.get(current-1);//source vertex
		Vertex UD = Vertices.get(next-1); //ultimate destination vertex of next attraction

		Edge sourceE = new Edge();  //arbitrary edge
		//int subs = V1.outEdges.size();
		ArrayList<Edge> visitSet = new ArrayList<Edge>(); //collection of edges 
		ArrayList <Edge> fset = new ArrayList<Edge>();//final set of edges that will be saved
		//Map<Integer, ArrayList<Edge>> map = new TreeMap<Integer, ArrayList<Edge>>();
		ArrayList <Path> pathsCollection = new ArrayList <Path>();
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

				fset = recurseDepthSearch(visitSet,sourceE,UD); //visit initialized with first edge
				System.out.println("Path #" + (x+1));

				for(int y = 0; y < fset.size();y++){	
					fset.get(y).getReadout();
					totalweight = totalweight + fset.get(y).weight;
				}
				System.out.println("total weight: " + totalweight);
				//map.put(totalweight,path);	
				Path p = new Path(totalweight,fset);
				pathsCollection.add(p);

				//zero out variables
				fset.removeAll(Edges);
				visitSet.removeAll(Edges);
				totalweight = 0;
				p = null;

			}

			//System.out.println("weights: " + map.keySet().toString());
			System.out.println("\nSorted Generated Paths: " );

			pathsCollection = sortByCost(pathsCollection);
			for(int z = 0; z < pathsCollection.size(); z ++){
				pathsCollection.get(z).readOut();
			}

		}
		catch(Exception e){
			System.err.println("Error in gen method:" + e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace().toString());
			e.printStackTrace();
		}
		return pathsCollection.get(0); //return the shortest path

	}

	public ArrayList<Edge> recurseDepthSearch(ArrayList<Edge> pathSet, Edge e, Vertex UD){
		boolean ks = true;
		Vertex Vs = e.getSource();
		Vertex Vd = e.getDest(); 
		
		ArrayList <Edge> rejects = new ArrayList<Edge>(); 


		if(Vd.equals(UD)){
			//System.out.println("Adding Edge to Set:"); 
			//e.getReadout();
			//pathSet.add(e); //add edge and quit
			System.out.println("Found Destination@1");
			//return pathSet;
			ks = false;
		}
		if(Vs.equals(UD)){
			System.out.println("Found Destination@1.1");
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
					rejects.add(Vd.outEdges.get(x));
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
					
					for(int r = 0; r < rejects.size(); r++){
						Vd.addOutEdge(rejects.get(r));
						}
					
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
				
				for(int r = 0; r < rejects.size(); r++){
				Vd.addOutEdge(rejects.get(r));
				}
				
			}
		}


		return pathSet;
	}


	/*
	 * Method for Edges: returns the short edge
	 */
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


	/*
	 * Method for Path: returns paths sorted by least total cost
	 */
	public ArrayList <Path> sortByCost(ArrayList <Path> paths){
		for(int i = 0; i < paths.size(); i++){
			for(int j = 1; j < (paths.size()-i); j++){
				if((paths.get(j-1).getCost() > (paths.get(j).getCost()))){
					Path p = paths.remove(j-1);
					paths.add(j,p);
				}
			}
		}			
		return paths;
	}


	public void attractionPrint(ArrayList <Attraction> a){
		System.out.println("Attraction printout:");
		for (int i = 0; i < a.size(); i++){
			System.out.println(a.get(i).owner +" , "+ a.get(i).name +" , "+ a.get(i).duration);
		}
	}
}


