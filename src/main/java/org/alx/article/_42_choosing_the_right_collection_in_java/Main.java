package org.alx.article._42_choosing_the_right_collection_in_java;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        // Create a TreeMap with custom comparator
        Map<String, Integer> map = new HashMap<>();
        map.put("John", 30);
        map.put("Alice", 25);
        map.put("Bob", 35);

        // Instantiate TreeMap with custom comparator
        Map<String, Integer> customSortedMap = new TreeMap<>(new CustomComparator(map));

        // Add all elements from original map to custom sorted map
        customSortedMap.putAll(map);

        // Print the custom sorted map
        for (Map.Entry<String, Integer> entry : customSortedMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}