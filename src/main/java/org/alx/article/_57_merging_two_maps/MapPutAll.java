package org.alx.article._57_merging_two_maps;

import java.util.HashMap;
import java.util.Map;

public class MapPutAll {
    public static void main(String[] args) {
        Map<Integer, Person> map1 = new HashMap<>();
        map1.put(1, new Person(1, "Alice"));
        map1.put(2, new Person(2, "Bob"));

        Map<Integer, Person> map2 = new HashMap<>();
        map2.put(3, new Person(1, "Charlie"));
        map2.put(4, new Person(4, "David"));

        // Merge maps
        Map<Integer, Person> mergedMap = new HashMap<>(map1);
        mergedMap.putAll(map2);

        System.out.println("Merged map: " + mergedMap);
    }
}

