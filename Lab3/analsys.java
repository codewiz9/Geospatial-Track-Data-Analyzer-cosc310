package Lab3;

import java.util.ArrayList;
import Lab3.searching;
import Lab3.Sorting;
import Lab3.avrage;
import json.BikeDataRecord;
import json.BikeDataReader;
public class analsys {

    //code to sort data
    public static ArrayList<BikeDataRecord> sortData(String filePath, int sortCriteria) throws Exception {
        ArrayList<BikeDataRecord> records = BikeDataReader.parse(filePath);
        BikeDataRecord.sortCritira = sortCriteria; // 1=heartrate, 8=degC
        Sorting.bubbleSort(records);
        return records;
    }
    
    //code to anlyise sorted data
    public static void analysis(ArrayList<BikeDataRecord> TempSortedRecords, ArrayList<BikeDataRecord> HRSortedRecords, String GISDataPathth) throws Exception {
        searching search = new searching();
        int tempNeedle = (int) Math.round(avrage.getAverage(GISDataPathth, "temp"));
        int hrNeedle   = (int) Math.round(avrage.getAverage(GISDataPathth, "hr"));
        // index in searching.java: 9=temp(degC), 2=heartrate
        ArrayList<BikeDataRecord> tempRecords = search.binarySearch(tempSortedRecords, tempNeedle, 9);
        ArrayList<BikeDataRecord> hrRecords   = search.binarySearch(hrSortedRecords, hrNeedle, 2);
        System.out.println("Temp search results: " + tempRecords.size());
        System.out.println("HR search results: " + hrRecords.size());
    }
    
    public static void main(String[] args) throws Exception {
        //calls sortData and analysis
        //sort and serch for day one
        ArrayList<BikeDataRecord> sortedRecords = sortData("json/day1.json");
        System.out.println("Sorted records: " + sortedRecords.size());//for debugging
        analysis(sortedRecords, "json/day1.json");
    }


}
