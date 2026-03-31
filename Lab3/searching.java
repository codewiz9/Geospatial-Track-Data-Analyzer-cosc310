package Lab3;

import java.util.ArrayList;
import java.util.Iterator;

import json.BikeDataRecord;

public class searching {
    
    public ArrayList<BikeDataRecord> linearSearch(ArrayList<BikeDataRecord> haystack, long needle) {
        ArrayList<BikeDataRecord> hits = new ArrayList<>();
        Iterator<BikeDataRecord> it = haystack.iterator();
        while(it.hasNext()) {
            BikeDataRecord r = it.next();
            if (r.getTimestamp()==needle) {
                hits.add(r);
            }
        }
        return hits;
    }

    // only valid indices are 2, 7, 8, 9
    public ArrayList<BikeDataRecord> linearSearch(int val, int index) {
        ArrayList<BikeDataRecord> hits = new ArrayList<>();
        Iterator<BikeDataRecord> it = hits.iterator();
        while(it.hasNext()) {
            BikeDataRecord r = it.next();
            switch (index) {
                case 2: if (r.getHeartrate()==val) { hits.add(r); } break;
                case 7: if (r.getPow()==val) { hits.add(r); } break;
                case 8: if (r.getCad()==val) { hits.add(r); } break;
                case 9: if (r.getDegC()==val) { hits.add(r); } break;
            }
        }
        return hits;
    }
    
    public ArrayList<BikeDataRecord> linearSearch(float val, int index) {
        // to do: search by the other fields that are floats
        return null;
    }

    // no need for index b/c we are only searching by timestamp (it's the only long field!)
    public ArrayList<BikeDataRecord> binarySearch(ArrayList<BikeDataRecord> haystack, int start, int end, long needle) { //make copie that will checl for the avrages 
        if (start <= end) {
            int mid = (start + end) / 2;
            if (haystack.get(mid).getTimestamp() == needle) {
                ArrayList<BikeDataRecord> hits = new ArrayList<>();
                hits.add(haystack.get(mid));
                // look left
                int i = mid-1;
                while (i>=0 && haystack.get(i).getTimestamp()==needle) {
                    hits.add(haystack.get(i));
                    i--;
                }
                // look right
                i = mid+1;
                while (i<haystack.size() && haystack.get(i).getTimestamp()==needle) {
                    hits.add(haystack.get(i));
                    i++;
                }
                return hits;
            } else if (haystack.get(mid).getTimestamp() > needle) {
                return binarySearch(haystack, start, mid-1, needle);
            } else {
                return binarySearch(haystack, mid+1, end, needle);
            }
        } else {
            return new ArrayList<>(); // not found
        }
    }

    public ArrayList<BikeDataRecord> binarySearch(ArrayList<BikeDataRecord> haystack, long needle) { //return all hits if i expand range
        return binarySearch(haystack, 0, haystack.size()-1, needle);
    }

    public ArrayList<BikeDataRecord> binarySearch(ArrayList<BikeDataRecord> haystack, int start, int end, long minNeedle, long maxNeedle) {
        if (start <= end) {
            int mid = (start + end) / 2;
            long ts = haystack.get(mid).getTimestamp();
            if (ts >= minNeedle && ts <= maxNeedle) {
                ArrayList<BikeDataRecord> hits = new ArrayList<>();
                hits.add(haystack.get(mid));
                int i = mid - 1;
                while (i >= 0) {
                    long t = haystack.get(i).getTimestamp();
                    if (t < minNeedle || t > maxNeedle) break;
                    hits.add(haystack.get(i--));
                }
                i = mid + 1;
                while (i < haystack.size()) {
                    long t = haystack.get(i).getTimestamp();
                    if (t < minNeedle || t > maxNeedle) break;
                    hits.add(haystack.get(i++));
                }
                return hits;
            } else if (ts > maxNeedle) {
                return binarySearch(haystack, start, mid - 1, minNeedle, maxNeedle);
            } else {
                return binarySearch(haystack, mid + 1, end, minNeedle, maxNeedle);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<BikeDataRecord> binarySearch(ArrayList<BikeDataRecord> haystack, long minNeedle, long maxNeedle) {
        return binarySearch(haystack, 0, haystack.size()-1, minNeedle, maxNeedle);
    }

    private int getValueForIndex(BikeDataRecord r, int index) {
        switch (index) {
            case 2: return r.getHeartrate();
            case 7: return r.getPow();
            case 8: return r.getCad();
            case 9: return r.getDegC();
            default: return Integer.MIN_VALUE;
        }
    }

    // Returns all records where field value >= needle.
    // haystack must already be sorted by the same index field.
    public ArrayList<BikeDataRecord> binarySearch(ArrayList<BikeDataRecord> haystack, int start, int end, int needle, int index) {
        ArrayList<BikeDataRecord> hits = new ArrayList<>();
        if (haystack.isEmpty() || start > end) {
            return hits;
        }

        int low = Math.max(0, start);
        int high = Math.min(end, haystack.size() - 1);
        int firstAtOrAbove = -1;

        // lower_bound: first position with value >= needle
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int val = getValueForIndex(haystack.get(mid), index);
            if (val >= needle) {
                firstAtOrAbove = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (firstAtOrAbove == -1) {
            return hits;
        }

        for (int i = firstAtOrAbove; i <= end && i < haystack.size(); i++) {
            BikeDataRecord r = haystack.get(i);
            if (getValueForIndex(r, index) >= needle) {
                hits.add(r);
            }
        }
        return hits;
    }

    public ArrayList<BikeDataRecord> binarySearch(ArrayList<BikeDataRecord> haystack, int needle, int index) {
        return binarySearch(haystack, 0, haystack.size()-1, needle, index);
    }

}

