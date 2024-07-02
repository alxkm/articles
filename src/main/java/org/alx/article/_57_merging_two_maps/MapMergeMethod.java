package org.alx.article._57_merging_two_maps;

import java.util.HashMap;
import java.util.Map;

public class MapMergeMethod {
    public static void main(String[] args) {
        Map<Integer, Person> map1 = new HashMap<>();
        map1.put(1, new Person(1, "Alice"));
        map1.put(2, new Person(2, "Bob"));

        Map<Integer, Person> map2 = new HashMap<>();
        map2.put(3, new Person(3, "Charlie"));
        map2.put(4, new Person(4, "David"));
        map2.put(2, new Person(2, "John"));

        Map<Integer, Person> mergedMap = new HashMap<>(map1);

        map2.forEach((key, value) ->
                mergedMap.merge(key, value, (existingValue, newValue) ->
                        new Person(existingValue.getId(), existingValue.getName() + " & " + newValue.getName())
                )
        );

        // Print the merged map
        mergedMap.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
