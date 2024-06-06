package org.alx.article._42_choosing_the_right_collection_in_java;

import java.util.Comparator;
import java.util.Map;

// Custom comparator for sorting keys based on values
class CustomComparator implements Comparator<String> {
    private Map<String, Integer> map;

    // Constructor to initialize map reference
    public CustomComparator(Map<String, Integer> map) {
        this.map = map;
    }

    // Compare method to compare keys based on values in the map
    @Override
    public int compare(String key1, String key2) {
        // Compare values associated with the keys
        int valueCompare = map.get(key1).compareTo(map.get(key2));

        // If values are equal, compare keys lexicographically
        if (valueCompare == 0) {
            return key1.compareTo(key2);
        }
        return valueCompare; // Return comparison result
    }
}