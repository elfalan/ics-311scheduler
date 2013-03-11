package graph;

import java.util.ArrayList;

public class Attraction {
	String name;
	ArrayList <String> times = new ArrayList <String>();
	//ArrayList <Integer> intervals = new ArrayList <Integer>();
	ArrayList <Interval> intervalList = new ArrayList<Interval>();
	Interval interval = new Interval();
	String owner;
	String [] attributes;
	int duration = 0;
	
	public Attraction (){
		
	}

	//intial attributes, raw formfat
	public void setAttributes(String o, String n, ArrayList <String> t){
		owner = o;
		name = n;
		times = t;
	}
	public void setDuration(int d){
		duration = d;
	}
	
	public void printattr(){
		System.out.println(attributes[0]);
		System.out.println(attributes[1]);
		System.out.println(attributes[2]);
		System.out.println(attributes[3]);
		System.out.println(attributes[4]);
		System.out.println(attributes[5]);
		
	}
	
	public void printout(){
		System.out.println("Attraction Printout");
		System.out.println("owner: " + owner);
		System.out.println("name: " + name);
		System.out.println("duration: " + duration);
		
		System.out.println("intervals: ");
		for (int i = 0; i < intervalList.size(); i++){
			 System.out.println(intervalList.get(i).startT+ "," + intervalList.get(i).endT);
		}
		System.out.println("+++++++++++++++++++++++++++++++\n");
	}
	
	public void convertTime(){
		
	}

	
}
