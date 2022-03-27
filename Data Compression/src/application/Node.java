package application;

public class Node implements Comparable<Node>{
	
	int value;
	int frequency;
	Node left, right;
	
	public Node(int value, int frequency, Node left, Node right) {
        this.value = value;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
	
	public boolean isLeaf() {
        return (left == null) && (right == null);
    }

	@Override
	public int compareTo(Node n) {
        return this.frequency - n.frequency;
    }
	
	
}
