package wiki;
import java.util.Comparator;

public class Node implements Comparable<Node>{
	int key;
	double score;
	
	public Node(int key, double score){
		this.key = key;
		this.score = score;
	}
	
	public int compareTo(Node other){
		return (new Double(this.score).compareTo(other.score))*-1;
	}

	public String toString(){
		return key + ": " + score;
	}
	
	
	
	
}
