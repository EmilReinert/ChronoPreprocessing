package preprocessing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Preprocessing {
	
	public static void main(String[] args) throws IOException {
		
		
		// READING TABLE DATA
		CSVReader read = new CSVReader(); //creating new reader
		TableData table = read.readCSV //read doc table
				("Tabelle Chronographie.csv");
		
		 // CREATING LINKS: LINKED BY ID = Nummer
		ArrayList<Links> links = table.makeLinks();
		
		// storing table data
		safeJSON(toJSON(table, links), "chronodata");
		
		System.out.println("DONE");
		// reading, converting and saving docx files
		//DocxToTxt dtxt = new DocxToTxt("C:\\Users\\Emil Reinert\\Documents\\WebDevelopment\\Data\\Datenbankdateien\\Beschreibungstexte",	"C:\\Users\\Emil Reinert\\Documents\\WebDevelopment\\test\\Data\\Beschreibungsdateien");
		
	}
	
	public static void safeJSON(String document, String filename) 
			  throws IOException {
			    BufferedWriter writer = new BufferedWriter(new FileWriter(filename+".json"));
			    writer.write(document);
			     
			    writer.close();
			}
	
	public static String toJSON(TableData t, ArrayList<Links> l) {
		String json = "chrono = '{";
		json += '"'+"items"+'"'+":[";
		json+=t.toJSON();
		json += "],";
		

		json += '"'+"links"+'"'+":{";
		json+= toJSON(l);
		json += "}";
		
		
		json += "}'";
		return json;
	}
	
	public static String toJSON(ArrayList<Links>l) {
		String json = "";
		for(int i = 0; i<l.size(); i++) {
			json += l.get(i).toJSON();
			if(i<l.size()-1) // split by commas
				json += ", ";
		}
		return json;
	}
}
