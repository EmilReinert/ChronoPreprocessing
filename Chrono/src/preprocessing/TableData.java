package preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class TableData {
	
	private ArrayList<Item> items;
	private ArrayList<Link> links;
	private String[] labels;
	
	
	
	public TableData(String[] l) {
		//must be created with label knowledge so that each added item can adapt
		labels = l;
		cleanLabels();
		// create empty 2b filled item container
		items = new ArrayList<>();
	}
	
	private void cleanLabels() {
		// removing white spaces and newlines from labels
		for (int i =0; i< labels.length; i++)
			labels[i]= labels[i].replaceAll("\\s","");
		labels[0] = "Nummer"; // Because the first row isnt named yet. TODO tell to change table
			
	}
	
	public int getLength() {
		// returns amount of defined labels = columns
		return labels.length;
	}
	
	public int getDepth() {
		// returns amount of held items = rows
		return items.size();
	}
	
	public void AddRow(String[] row) {
		// converts a string row with contents to matching
		// (label, node) pair row <= an Item
		Item I = new Item();
		if(testLength(row)) {
			
			for (int i = 0; i < row.length; i++) 
			{				
				// Node INCLUSION
				//cleaning
				String node_data = cleanContent(row[i]);
				//link validation
				if(i < 16) 
					I.AddNode(new Node(labels[i], node_data)); //default add node data 
				if(i==16)	{ // Eräuterungstext
					if(testLink(node_data+".txt","Beschreibungstexte"))
						I.AddNode(new Node(labels[i], node_data));
					else I.AddNode(new Node(labels[i], ""));
				}
				if(i>16)
					if(testLink(node_data+".jpg","Abbildungen"))
						I.AddNode(new Node(labels[i], node_data));
					else I.AddNode(new Node(labels[i], ""));
				
					
				
			}
			for (int i = row.length; i<labels.length; i++) // fill last ones that are cut off
			{
				I.AddNode(new Node(labels[i], "")); // EMPTY NODE
			}
			//push back item
			AddItem(I);
		}
		else
			System.out.println("Cant add Row that violated the Table Dimensions!");
	}
	
	public void AddItem(Item I) {
		items.add(I);
	}
	
	public void print() {
		System.out.println("Length: "+getLength()+", Depth: "+getDepth());
		System.out.println(Arrays.toString(labels));
		for(Item I: items)
			System.out.println(I.toJSON());
	}

	private boolean testLength(String[] a) {
		// making sure we dont add a row that violated the defined label length
		if (a.length <= 0) {
			System.out.println("length is invalid");
			return false;
		}
		if(a.length > labels.length) {
			System.out.println("length is invalid");
			return false;
		}
		return true;
	}
	
	
	// DEVELOPMENT METHODS
	private String cleanContent(String rawData) {
		
		// cleans raw string data according to json requrements
		// namely makes sure that there are no unallowed signs
		String cleanWord = rawData;
		//replace ' to *
		cleanWord = clean(cleanWord, "'", "*");
//		cleanWord = clean(cleanWord, "ä", "&auml;");
//		cleanWord = clean(cleanWord, "Ä", "&Auml;");
//		cleanWord = clean(cleanWord, "ö", "&ouml;");
//		cleanWord = clean(cleanWord, "Ö", "&Ouml;");
//		cleanWord = clean(cleanWord, "ü", "&uuml;");
//		cleanWord = clean(cleanWord, "Ü", "&Uuml;");
//		cleanWord = clean(cleanWord, "ß", "&szlig;");
		
		
		return cleanWord;
	}
	

	private boolean testLink(String node_data, String path) {
		// tests linkage from table to existing file
		if(node_data==""||node_data ==" ")return false;
		if(node_data.contains("ä")||node_data.contains("ü")||node_data.contains("ö"))return false;
		String fullpath = "Datenbankdateien/"+path+"/"+node_data;
		File f = new File(fullpath);
		System.out.println(f.exists());
		return f.exists();
	}

	
	private String clean(String word, String replace, String replaceby) {
		// replaces chars/strings by another one if included in word
		if(word.contains(replace)) {
			return word.replace(replace, replaceby);}
		return word;
	}
	
	public ArrayList<Links> makeLinks() {
		// iterates over all items to calculate
		// returns a list of links for each label!
		ArrayList<Links> links = new ArrayList<>();
		
		// making links by comparing each item value to all others
		// and adding link if not already exists
		for (int l = 0; l <labels.length; l++) {
			Links labelLinks = new Links(labels[l]);
			

			// calls link function upon all IxI data
			// where j=/=i
			// and links accordingly
			for(int i = 0; i < getDepth(); i++) {
				for(int j = 0; j< getDepth(); j++) {
					if(j==i)
						break;
					if(items.get(i).getNode(l).empty() ||items.get(i).getNode(l).empty()) // checks if either one is empty
						break;
					if(items.get(i).getNode(l).SameContent(items.get(j).getNode(l))) { // CHECK IF THEY LINK
						labelLinks.link(i+1,j+1); // take number values actually get item at position 0 = Number but im lazy
						System.out.println("linking "+ i+ " - " +j);
						System.out.println(items.get(i).getNode(l).getContent()+" and " +items.get(j).getNode(l).getContent());
					}
					
				}
			}
			links.add(labelLinks);
		}
		
		return links;
	}
	
	public String toJSON() {
		String json = "";
		for(int i = 0; i < items.size(); i++) {
			json += items.get(i).toJSON();
			if(i<items.size()-1) // split by commas
				json += ", ";
		}
		
		return json;
	}
	
}
