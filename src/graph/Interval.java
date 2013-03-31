package graph;

public class Interval {
	int startT = 0;
	int endT = 0;
	
	public Interval(){
	}
	
	public Interval(int t1, int t2){
		startT = t1;
		endT = t2;
	}
	
	public int getStart(){
		return startT;
	}
	public int getEnd(){
		return endT;
	}
}
