package Lab3;

import java.util.ArrayList;
import json.BikeDataRecord;
import json.BikeDataReader;
public class analsys {

    private static long averageFromRecords(ArrayList<BikeDataRecord> records, String valueName) {
        if (records == null || records.isEmpty()) {
            return 0L;
        }

        long total = 0L;
        String key = valueName.toLowerCase();
        for (BikeDataRecord record : records) {
            if (key.equals("power")) {
                total += record.getPow();
            } else if (key.equals("tempo") || key.equals("cadence")) {
                // In this codebase, "tempo" is treated as cadence (rpm).
                total += record.getCad();
            } else {
                throw new IllegalArgumentException("Unsupported value for subset average: " + valueName);
            }
        }

        return Math.round((double) total / records.size());
    }

    //code to sort data
    public static ArrayList<BikeDataRecord> sortData(String filePath, int sortCriteria) throws Exception {
        ArrayList<BikeDataRecord> records = BikeDataReader.parse(filePath);
        BikeDataRecord.sortCritira = sortCriteria; // 1=heartrate, 8=degC
        Sorting.bubbleSort(records);
        return records;
    }

    public static long getAvgBeforeSearch(String filePath, String valueName) throws Exception {
        ArrayList<BikeDataRecord> records = BikeDataReader.parse(filePath);
        if (records.isEmpty()) {
            return 0L;
        }
        long avg = Math.round(avrage.getAverage(filePath, valueName));
        return avg;
    }

    public static void LogicHelper(String GISDataPathth, long tempoAvg, long powerAvg, int hrNeedle, int tempNeedle, int day) throws Exception {
        long avgPowerBefore = Math.round(avrage.getAverage(GISDataPathth, "power"));
        long avgTempoBefore = Math.round(avrage.getAverage(GISDataPathth, "tempo"));

        //temp to power
        if (avgPowerBefore > powerAvg) {
            long powerDiff = avgPowerBefore - powerAvg;
            System.out.println("day " + day + ": When the tempoeture was below " + tempNeedle + " celcius the power was: " + powerAvg + " watts which was "+ powerDiff + " watts slower than the average: " + avgPowerBefore + " watts");
        } else {
            long powerDiff = powerAvg - avgPowerBefore;
            System.out.println("day " + day + ": When the tempoeture was below " + tempNeedle + " celcius the power was: " + powerAvg + " watts which was "+ powerDiff + " watts faster than the average: " + avgPowerBefore + " watts");
        }

        //hr to tempo
        if (avgTempoBefore > tempoAvg) {
            long tempoDiff = avgTempoBefore - tempoAvg;
            System.out.println("day " + day + ": When the heart rate was below " + hrNeedle + "bpm, the tempo was: " + tempoAvg + "bpm which was "+ tempoDiff + " beats per minute slower than the average: " + avgTempoBefore + "bpm");
        } else {
            long tempoDiff = tempoAvg - avgTempoBefore;
            System.out.println("day " + day + ": When the heart rate was below " + hrNeedle + "bpm, the tempo was: " + tempoAvg + "bpm which was "+ tempoDiff + " beats per minute faster than average: " + avgTempoBefore + "bpm");
        }

    }

    
    //code to anlyise sorted data
    public static void analysis(ArrayList<BikeDataRecord> TempSortedRecords, ArrayList<BikeDataRecord> HRSortedRecords, String GISDataPathth, int day) throws Exception {
        searching search = new searching();
        int tempNeedle = (int) Math.round(avrage.getAverage(GISDataPathth, "temp"));
        int hrNeedle   = (int) Math.round(avrage.getAverage(GISDataPathth, "hr"));
        // index in searching.java: 8=temp(degC), 1=heartrate
        ArrayList<BikeDataRecord> tempRecords = search.binarySearch(TempSortedRecords, tempNeedle, 9);
        ArrayList<BikeDataRecord> hrRecords   = search.binarySearch(HRSortedRecords, hrNeedle, 2);
        System.out.println("Temp search results: " + tempRecords.size());
        System.out.println("HR search results: " + hrRecords.size());

        // averages for the "sorted/filtered" (above-average) subsets
        long avgPowerAboveAvgTemp = averageFromRecords(tempRecords, "power");
        long avgTempoAboveAvgHr   = averageFromRecords(hrRecords, "tempo");
        LogicHelper(GISDataPathth, avgTempoAboveAvgHr, avgPowerAboveAvgTemp, hrNeedle, tempNeedle, day);
    }
    
    public static void main(String[] args) throws Exception {
        //calls sortData and analysis
        //day one
        ArrayList<BikeDataRecord> sortedHrRecords = sortData("json/day1.json", 1);
        ArrayList<BikeDataRecord> sortedTempRecords = sortData("json/day1.json", 8);
        analysis(sortedTempRecords, sortedHrRecords, "json/day1.json", 1);

        //day two
        ArrayList<BikeDataRecord> sortedHrRecords2 = sortData("json/day2.json", 1);
        ArrayList<BikeDataRecord> sortedTempRecords2 = sortData("json/day2.json", 8);
        analysis(sortedTempRecords2, sortedHrRecords2, "json/day2.json", 2);
        
        //day three
        ArrayList<BikeDataRecord> sortedHrRecords3 = sortData("json/day3.json", 1);
        ArrayList<BikeDataRecord> sortedTempRecords3 = sortData("json/day3.json", 8);
        analysis(sortedTempRecords3, sortedHrRecords3, "json/day3.json", 3);

        //day four
        ArrayList<BikeDataRecord> sortedHrRecords4 = sortData("json/day4.json", 1);
        ArrayList<BikeDataRecord> sortedTempRecords4 = sortData("json/day4.json", 8);
        analysis(sortedTempRecords4, sortedHrRecords4, "json/day4.json", 4);
        
    }


}
