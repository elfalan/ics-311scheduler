package graph;

public class Edge {

	Vertex [] path = new Vertex[2]; //two input vertices, source vi, dest vj
	int weight;
	
	public Edge(Vertex v1, Vertex v2, int w){
		path[0] = v1;
		path[1] = v2;
		weight = w;
	}
	
	public void setPath(Vertex v1, Vertex v2){
		path[0] = v1;
		path[1] = v2;
	}
	public void setWeight(int w){
		weight = w;
	}
	
	public Vertex getSource(){
		return path[0];
	}
	public Vertex getDest(){
		return path[1];
	}
	
	public int getWeight(){
		return weight;
	}
	
}
