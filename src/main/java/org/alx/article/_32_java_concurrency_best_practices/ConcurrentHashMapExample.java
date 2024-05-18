package org.alx.article._32_java_concurrency_best_practices;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {
    static <K, V> void concurrentHashMapExample(K key1, V value1, K key2, V value2, V newValue, int initialCapacity, int loadFactor) {
        // Initialization
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();

        // Adding and Updating Elements
        map.put(key1, value1);
        map.put(key2, value2);

        // Retrieving Elements
        V value = map.get(key1);

        // Removing Elements
        V removedValue = map.remove(key1);

        // Iterating over Elements
        for (Map.Entry<K, V> entry : map.entrySet()) {
            // Process each key-value pair
        }

        // Atomic Operations
        map.putIfAbsent(key1, value); // Adds if absent
        map.replace(key1, newValue); // Replaces value

        // Specifying Concurrency Level
        int concurrencyLevel = 16;
        ConcurrentHashMap<K, V> newMap = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }
}
