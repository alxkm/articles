package org.alx.article._57_merging_two_maps;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOf {
    public static void main(String[] args) {
        Map<Integer, Person> map1 = new HashMap<>();
        map1.put(1, new Person(1, "Alice"));
        map1.put(2, new Person(2, "Bob"));

        Map<Integer, Person> map2 = new HashMap<>();
        map2.put(3, new Person(3, "Charlie"));
        map2.put(4, new Person(4, "David"));

        // Merge maps
        Map<Integer, Person> mergedMap = Stream.of(map1, map2)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> new Person(v1.getId(), v2.getName())));

        System.out.println("Merged map: " + mergedMap);
    }
}
