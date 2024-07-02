package org.alx.article._57_merging_two_maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;

public class ApacheCollectionUtils {
    public static void main(String[] args) {
        Map<Integer, Person> map1 = new HashMap<>();
        map1.put(1, new Person(1, "Alice"));
        map1.put(2, new Person(2, "Bob"));

        Map<Integer, Person> map2 = new HashMap<>();
        map2.put(3, new Person(3, "Charlie"));
        map2.put(4, new Person(4, "David"));

        Object[] array = new Object[map2.size() * 2];
        List<Person> personList = map2.values().stream().toList();
        for (int i = 0, j = 0; i < map2.size(); i++) {
            array[j++] = personList.get(i).getId();
            array[j++] = personList.get(i);

        }

        // Merge maps using Apache Commons Collections
        Map<Integer, Person> mergedMap = MapUtils.putAll(new HashMap<>(map1), array);
        System.out.println("Merged map: " + mergedMap);
    }
}