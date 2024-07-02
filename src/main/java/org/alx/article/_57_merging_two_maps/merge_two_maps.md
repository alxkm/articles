
## How to merge two maps in Java

Java Map Merging: Efficient Techniques for Combining Two Maps into One

![](https://cdn-images-1.medium.com/max/2000/1*oxQAqm_oDH-5DqJRlLLsMA.jpeg)

Maps are fundamental data structures in Java, offering a convenient way to store key-value pairs. However, there are scenarios where you may need to combine or merge multiple maps to consolidate data or perform operations efficiently. Merging two maps involves bringing together their entries while handling conflicts such as duplicate keys.

In this article, we embark on a journey to explore the intricacies of merging two maps in Java. We’ll delve into various strategies, techniques, and best practices to seamlessly merge maps while ensuring data integrity and optimal performance. Whether you’re a novice Java developer or a seasoned expert, understanding map merging is essential for building robust and scalable applications.

Join us as we unravel the mysteries of map merging, uncovering the tools and methodologies that empower you to harness the full potential of Java’s map collections. Let’s dive in and discover the art of merging maps in Java.

Let’s assume that we need to merge next class:

    public class Person {
        private final int id;
        private final String name;
    
        public int getId() {
            return id;
        }
    
        public String getName() {
            return name;
        }
    
        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }
    
        @Override
        public String toString() {
            return "Person{id=" + id + ", name='" + name + '\'' + '}';
        }
    }

## Using Map#.putAll() method

This approach leverages the **Map#putAll()** method available in Java’s Map interface to merge two maps efficiently. The **Map#putAll()** method copies all the mappings from the specified map into the target map, effectively combining their entries.

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
    
    // Result of output
    // Merged map: {
    // 1=Person{id=1, name='Alice'}, 
    // 2=Person{id=2, name='Bob'}, 
    // 3=Person{id=1, name='Charlie'}, 
    // 4=Person{id=4, name='David'}}

### **Advantages**

* Simple and straightforward implementation.

* Efficient for merging large maps as it iterates through each entry only once.

### **Limitations**

* If there are duplicate keys, the **Map#.putAll()** method will overwrite the existing values in the target map with those from the source map. This behavior might not be suitable for all use cases, especially if you need to preserve values from both maps.

The **Map#.putAll()** method provides a convenient way to merge maps in Java by copying entries from one map into another. While it’s effective for straightforward merging operations, it’s essential to consider how duplicate keys are handled, as the method overwrites existing values in the target map.

## Using Map#.merge() method

The **Map#.merge()** method is a powerful tool in Java that allows for more sophisticated merging of maps. It provides control over how to handle duplicate keys by specifying a remapping function to determine the value for the merged key.


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
    
    // Result of merge
    // 
    // 4: Person{id=4, name='David'}
    // 1: Person{id=1, name='Alice'}
    // 2: Person{id=2, name='Bob & John'}
    // 3: Person{id=3, name='Charlie'}


## Using Java Stream.concat

This approach utilizes the Java Stream API to merge two maps by concatenating their entry streams and collecting them into a new map. It provides a functional programming paradigm for map merging, leveraging the power and flexibility of streams.

* **Concatenate Entry Streams:** Start by concatenating the entry streams of both maps using the **Stream.concat()** method method. This creates a single stream containing all key-value pairs from both maps.

* **Collect into Map:** Use the **Collectors.toMap()** collector to collect the entries of the concatenated stream into a new map. You provide mapping functions to extract keys and values from the stream elements, and the collector constructs the resulting map.

* **Result:** After the stream processing, you obtain a new map containing entries merged from both original maps. If there are any duplicate keys, the last encountered value in the stream will overwrite the existing value in the resulting map.


    Map<Integer, Person> map1 = new HashMap<>();
    map1.put(1, new Person(1, "Alice"));
    map1.put(2, new Person(2, "Bob"));
    
    Map<Integer, Person> map2 = new HashMap<>();
    map2.put(3, new Person(3, "Charlie"));
    map2.put(4, new Person(4, "David"));
    
    // Merge maps using Java Stream API
    Map<Integer, Person> mergedMap = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    
    System.out.println("Merged map: " + mergedMap);
    
    // Result of merge
    //
    // Merged map: {
    // 1=Person{id=1, name='Alice'}, 
    // 2=Person{id=2, name='Bob'}, 
    // 3=Person{id=3, name='Charlie'}, 
    // 4=Person{id=4, name='David'}

### **Advantages**

* Provides a functional and concise way to merge maps using the Stream API.

* Offers flexibility in handling map entries, allowing custom mapping functions for keys and values.

### **Limitation**

* Similar to **Map#putAll()** method. If there are duplicate keys, the last encountered value in the stream will overwrite existing values in the resulting map.

Using the Java Stream API for map merging offers a modern and expressive approach, particularly suited for scenarios where functional programming is preferred. It provides a high-level abstraction over map manipulation, facilitating cleaner and more maintainable code. However, it’s essential to be mindful of how duplicate keys are handled, as the last encountered value will prevail in the resulting map.

## Using Map#putIfAbsent() method

This approach employs the **putIfAbsent()** method available in the **Map **interface to merge two maps while ensuring that existing values are not overwritten. Instead of blindly copying entries from one map to another, it selectively adds entries to the target map only if the keys are not already present.

If the key does not exist in the target map, add the key-value pair using the **Map#putIfAbsent()** method. This ensures that existing values in the target map are not overwritten by values from the second map.

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
    
    // Result of merge
    //
    // Merged map: {
    // 1=Person{id=1, name='Alice'}, 
    // 2=Person{id=2, name='Bob'}, 
    // 3=Person{id=3, name='Charlie'}, 
    // 4=Person{id=4, name='David'}}

### **Advantages**

* Provides control over how duplicate keys are handled during map merging.

* Ensures that existing values in the target map are not overwritten by values from the second map.

### **Limitations**

* Requires iterating over each entry of the second map, which may lead to performance overhead for large maps.

Using the **Map#putIfAbsent()** method offers a fine-grained approach to merging maps in Java, allowing you to selectively add entries from one map to another while preserving existing data. This method is particularly useful when you need to merge maps while avoiding overwriting existing values, ensuring data integrity and consistency in your application.

## Using Stream.of

Let’s review Stream.of method to merge two maps:

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
    
    // Result of merge
    //
    // Merged map: {
    // 1=Person{id=1, name='Alice'}, 
    // 2=Person{id=2, name='Bob'}, 
    // 3=Person{id=3, name='Charlie'}, 
    // 4=Person{id=4, name='David'}}

### Advantages

* The method is concise and leverages the expressive power of the Stream API, making the code easy to read and understand for those familiar with Java streams.

* The use of a merge function allows for custom handling of duplicate keys, providing flexibility in how collisions are resolved.

* The approach aligns with functional programming paradigms, which can be beneficial for writing more declarative and less error-prone code.

### Limitations

* The method involves converting maps to streams and then back to a map, which may introduce some performance overhead, especially for large maps.

* Flattening streams and collecting them can consume additional memory, which might be a concern for very large datasets.

* While the merge function handles key collisions, the logic used in the merge function ((v1, v2) -> new Person(v1.getId(), v2.getName()))) is specific and may not fit all use cases. Different merging logic might be needed based on specific requirements.

* This method creates a new merged map rather than modifying one of the original maps in place. If in-place modification is needed, additional steps would be required.

## Using third-party libraries like Apache Commons Collections

This approach leverages third-party libraries such as Apache Commons Collections to simplify the process of merging two maps in Java. Libraries like Apache Commons Collections provide utility methods that extend the functionality of core Java collections, offering additional features and convenience methods for common tasks, but it works with array.

    import org.apache.commons.collections4.MapUtils;
    import java.util.HashMap;
    import java.util.Map;
    
    public class MergeMapsExample {
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
    
    // Output
    //
    // Merged map: {
    // 1=Person{id=1, name='Alice'}, 
    // 2=Person{id=2, name='Bob'}, 
    // 3=Person{id=3, name='Charlie'}, 
    // 4=Person{id=4, name='David'}
    // }

### **Advantages**

* Provides a high-level and intuitive API for map merging, reducing the need for manual iteration and merging logic.

* Offers additional features and customization options not available in standard Java collections, such as advanced merging strategies and handling of edge cases.

### **Limitations**

* Need to use flat structure like array, need to pre transform initial map.

* Adds a dependency on an external library, increasing project complexity and potentially introducing compatibility issues.

* May require familiarity with the library’s API and documentation to utilize effectively.

Using third-party libraries like Apache Commons Collections can streamline the process of merging maps in Java, offering convenience, flexibility, and additional features beyond what is available in the standard Java collections framework. While introducing a dependency on external libraries, this approach can significantly simplify map merging tasks, allowing developers to focus on higher-level application logic.

## **Conclusion**

Merging maps in Java is a common task encountered in various programming scenarios, and choosing the right method can significantly impact code simplicity, performance, and maintainability. Throughout this article, we explored four different approaches to merge maps.

* **Map#.putAll() Method: **Simple and direct merges with no need for duplicate key handling.

* **Map#.merge() Method: **Handling duplicate keys with custom logic.

* **Stream.concat: **Functional and declarative merging with custom duplicate handling.

* **Map#putIfAbsent() Method: **Preserving existing entries and only adding new ones.

* **Stream.of: **Combining multiple maps in a functional style, similar to **Stream.concat**.

* **Using Third-Party Libraries like Apache Commons Collections: **Simplified and readable merging with external utilities.
