package Lab3;

import java.util.ArrayList;

import json.BikeDataReader;
import json.BikeDataRecord;

public class avrage {
    public static double getAverage(String filePath, String valueName) throws Exception {
        ArrayList<BikeDataRecord> records = BikeDataReader.parse(filePath);
        if (records.isEmpty()) {
            return 0.0;
        }

        long total = 0;
        String key = valueName.toLowerCase();
        for (BikeDataRecord record : records) {
            if (key.equals("temp") || key.equals("temperature")) {
                total += record.getDegC();
            } else if (key.equals("hr") || key.equals("heartrate") || key.equals("heart_rate")) {
                total += record.getHeartrate();
            } else {
                throw new IllegalArgumentException("Unsupported value: " + valueName + " (use temp or hr)");
            }
        }
        return (double) total / records.size();
    }

    public static void main(String[] args) throws Exception {    
        
    }
}
