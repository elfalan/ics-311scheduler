package graph;

import java.util.ArrayList;

public class attraction {
	String name;
	ArrayList <String> intervalTimes = new ArrayList <String>();
	String owner;
	String [] attributes;
	public attraction (){
		
	}

	public void setAttributes(String o, String n, ArrayList <String> t){
		owner = o;
		name = n;
		intervalTimes = t;
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
		
		System.out.println("intervals: ");
		for (int i = 0; i < intervalTimes.size(); i++){
			 System.out.println(intervalTimes.get(i));
		}
	}
	// need to add methods to convert to proper values
}
