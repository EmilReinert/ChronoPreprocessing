package preprocessing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	private TableData t;
	
    public TableData readCSV(String csvFile) {
    	
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            
            // First Line reveals Lables for Table
            line = br.readLine();
            t = new TableData(line.split(cvsSplitBy));
            
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] element = line.split(cvsSplitBy);
                t.AddRow(element);

                
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;

    }

}
