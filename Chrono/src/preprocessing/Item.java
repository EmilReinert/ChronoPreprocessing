package preprocessing;

import java.util.ArrayList;

public class Item {
	private ArrayList<Node> nodes;
	
	public Item() {
		nodes = new ArrayList<>();
	}
	public Item(ArrayList<Node> n) {
		nodes = n;
	}
	public Node getNode(int i) {
		//returns node at i
		return nodes.get(i);
	}
	
	public Node getNode(String a) {
		//returns node with label a
		for(Node n: nodes) {
			if(n.getLabel()== a)
				return n;
		}
		System.err.println("Cant find node with label "+a);
		return null;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public void AddNode(Node n) {
		nodes.add(n);
	}
	
	public int getLength() {
		// returns length of defined data nodes
		return nodes.size();
	}
	
	public String toJSON() {
		String it = "{ ";
		for (int i = 0; i < nodes.size(); i++) {
			it += nodes.get(i).toJSON();
			if(i<nodes.size()-1)
				it += ", ";
		}
		it += " }";
		return it;
	}
}
