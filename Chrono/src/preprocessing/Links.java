package preprocessing;

import java.util.ArrayList;


public class Links {
	String label;
	ArrayList<Link> topicLinks;
	
	public Links(String l) {
		label = l;
		topicLinks = new ArrayList<>();
	}
	
	
	public void link(int aID, int bID) {
		// makes new Link for both nodes and adds
		Link a = new Link(Integer.toString(aID),Integer.toString(bID));
		link(a);
	}
	
	public void link(Link l) {
		if(exists(l)) {
			return;
		}
		topicLinks.add(l);
	}
	
	public boolean exists(Link l) {
		// checks weather link l already exists
		// by comparing to already existent links
		for(Link a: topicLinks) {
			if(l.equivalent(a)) {
				System.out.println("equiv: " + l.toJSON()+" and "+a.toJSON());
				return true;
			}
		}
		return false;
		
	}
	
	public String toJSON() {
		String json ='"'+ label + '"' + ": [";
		
		for(int i = 0; i<topicLinks.size(); i++) {
			json += "{ ";
			json += topicLinks.get(i).toJSON();
			json += " }";
			if(i<topicLinks.size()-1)
				json += ", ";
		}

		json += "]";
		return json;
	}
}
