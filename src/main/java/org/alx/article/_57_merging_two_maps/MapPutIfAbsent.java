package org.alx.article._57_merging_two_maps;

import java.util.HashMap;
import java.util.Map;

public class MapPutIfAbsent {
    public static void main(String[] args) {
        Map<Integer, Person> map1 = new HashMap<>();
        map1.put(1, new Person(1, "Alice"));
        map1.put(2, new Person(2, "Bob"));

        Map<Integer, Person> map2 = new HashMap<>();
        map2.put(3, new Person(3, "Charlie"));
        map2.put(4, new Person(4, "David"));

        // Merge maps using putIfAbsent method
        Map<Integer, Person> mergedMap = new HashMap<>(map1);
        map2.forEach((key, value) -> mergedMap.putIfAbsent(key, value));

        System.out.println("Merged map: " + mergedMap);
    }
}
