package preprocessing;

public class Node {
	private String label;
	private String content;
	
	public Node(String l, String c) {
		label = l;
		content = c;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean empty() {
		if(content.contentEquals("")) {
			return true;
		}
		return false;
	}
	
	public boolean SameContent(Node other) {
		//checks if two nodes hold same content
		if(other.content.contentEquals(content)) {
			return true;
		}
		return false;
	}
	
	public String toJSON() {
		return '"' + label + '"' + ": " + '"' + content + '"';
	}
}
