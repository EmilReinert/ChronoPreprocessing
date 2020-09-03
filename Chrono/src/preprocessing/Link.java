package preprocessing;

public class Link {
	private String source;
	private String target;
	
	public Link(String s, String t){
		source = s;
		target = t;
	}
	
	public String getSource() {
		return source;
	}
	
	public String getTarget() {
		return target;
	}
	
	public boolean equivalent(Link other) {
		if(other.getSource()==source) {
			if(other.getTarget()==target) {
				//System.out.println("MATCH");
				return true;}
			return false;
		}
		if(other.getSource()==target) {
			if(other.getTarget()==source) {
				//System.out.println("MATCH");
				return true;}
			return false;
		}
		return false;
		
	}

	public String toJSON() {
		return '"' + "source" + '"' + ": " + '"' + source + '"' +", "+
				'"' + "target" + '"' + ": " + '"' + target + '"';
	}
}
