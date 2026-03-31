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
            } else if (key.equals("power")) {
                total += record.getPow();
            } else if (key.equals("tempo")) {
                // "tempo" is treated as cadence in this project
                total += record.getCad();
            } else if (key.equals("cadence")) {
                total += record.getCad();
            } else if (key.equals("altitude")) {
                total += record.getAlt();
            } else if (key.equals("latitude")) {
                total += record.getLat();
            } else if (key.equals("longitude")) {
                total += record.getLng();
            } else {
                throw new IllegalArgumentException("Unsupported value: " + valueName + " (use temp, hr, power, tempo/cadence)");
            }
        }
        return (double) total / records.size();
    }

    public static void main(String[] args) throws Exception {    
        
    }
}
