package egovframework.example.cmmn;

import com.csvreader.CsvReader;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GisUtil {

    public  void  csvUtil (File files){
    List<String> cities = new ArrayList<>();
//    URL url = CSVTest.class.getResource("locations.csv");
    File file = new File(files.toURI());
        try (FileReader reader = new FileReader(file)) {
        CsvReader locations = new CsvReader(reader);
        locations.readHeaders();
        while (locations.readRecord()) {
            cities.add(locations.get("CITY"));
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
