
## Introduction to LRU and LFU Caching: Concepts, Implementations, and Practical Use Cases

Understanding LRU and LFU Caching: Exploring Concepts, Implementations, and Real-World Applications

Caching is a fundamental technique used to improve the performance and efficiency of computer systems by storing frequently accessed data in a readily accessible location. Among the various caching strategies, Least Recently Used (LRU) and Least Frequently Used (LFU) are two of the most common and effective methods. These caching algorithms are widely adopted in numerous applications, from web browsers to database systems, to enhance speed and reduce latency.

In this article, we will delve deeply into the workings of LRU and LFU caching mechanisms. We’ll explore the theoretical foundations of each approach, highlighting their differences and the scenarios where each is most effective. Understanding these concepts is crucial for software developers and engineers who aim to optimize the performance of their applications.

This cache and other already implemented in my [simple cache library](https://github.com/alxkm/cache).

## Why Review LRU and LFU Caching?

Given the prevalence of caching in modern computing, a thorough understanding of LRU and LFU is essential. These algorithms are not only pivotal in everyday applications but also serve as a basis for more advanced caching techniques. By reviewing LRU and LFU, we can gain insights into:

1. **Performance Optimization**: Learn how these caching strategies can significantly reduce data retrieval times.

2. **Resource Management**: Understand how efficient caching can minimize memory usage and improve system resource allocation.

3. **Implementation Challenges**: Explore the common challenges faced during the implementation of these algorithms and how to overcome them.

## Implementing LRU and LFU Caches

In this comprehensive review, we will provide detailed implementations of LRU and LFU caches. We’ll guide you through the process of coding these algorithms from scratch, ensuring that you grasp the underlying principles and logic. Additionally, we will discuss the importance of these implementations in real-world scenarios, helping you apply these concepts effectively in your projects.

Implementing an LRU (Least Recently Used) cache in Java can be done using various approaches. One of the most common methods is by using a combination of a **LinkedHashMap **and overriding its **removeEldestEntry **method to automatically remove the least recently accessed entries when the cache reaches its maximum capacity.

## LRU

![Example of LRU cache for 3 elements](https://cdn-images-1.medium.com/max/2000/1*WLb2Qn-VLvMbhTR1ZVLBDA.jpeg)

Here’s a straightforward implementation of an LRU cache in Java using **LinkedHashMap**:

    import java.util.LinkedHashMap;
    import java.util.Map;
    
    public class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;
    
        public LRUCache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }
    
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    
        public static void main(String[] args) {
            LRUCache<Integer, String> cache = new LRUCache<>(3);
    
            cache.put(1, "one");
            cache.put(2, "two");
            cache.put(3, "three");
            System.out.println("Cache: " + cache);
    
            // Access key 1
            cache.get(1);
            System.out.println("Cache after accessing key 1: " + cache);
    
            // Add another element, which should evict the least recently used (key 2)
            cache.put(4, "four");
            System.out.println("Cache after adding key 4: " + cache);
        }
    }

## Explanation

1. **Class Declaration**: The **LRUCache **class extends **LinkedHashMap**. This class is generic, with **K** as the type for keys and **V** as the type for values.

2. **Constructor**: The constructor accepts an **int** parameter specifying the maximum capacity of the cache. The **super(capacity, 0.75f, true)** call initializes the **LinkedHashMap **with the specified capacity, a load factor of 0.75, and access order (the **true **parameter).

3. **removeEldestEntry**: This method is overridden to provide the logic for removing the eldest entry when the size of the map exceeds the specified capacity. The **size()** method returns the current size of the map, and the method returns **true **if the size is greater than the capacity, causing the eldest entry to be removed.

4. **Main Method**: The **main **method demonstrates the usage of the **LRUCache**. It creates an instance of **LRUCache **with a capacity of 3, adds some elements, accesses an element to change its order, and then adds another element to trigger the eviction of the least recently used element.

## Key Points

* **Access Order**: By setting the access order to **true **in the **LinkedHashMap **constructor, the map maintains the order based on the most recently accessed elements.

* **Eviction Policy**: The **removeEldestEntry **method is called by **LinkedHashMap **after each **put **and **putAll **operation. If it returns **true**, the eldest entry is removed.

* **Capacity Management**: This implementation ensures that the cache does not exceed the specified capacity, maintaining only the most recently accessed elements.

This implementation is efficient and leverages the built-in functionality of **LinkedHashMap **to provide a simple and effective LRU cache.

## LFU

Implementing an LFU (Least Frequently Used) cache in Java is a bit more complex than an LRU cache because it requires tracking the frequency of access for each element. A common approach involves using a combination of data structures, such as a **HashMap **for storing the values and their frequencies, and a **TreeMap **or **LinkedHashMap **to maintain the frequency order.

![Example of LFU cache for 3 elements](https://cdn-images-1.medium.com/max/2000/1*Koi4OZ9jbmemjNfAcOPTjg.jpeg)

Below is an example of an LFU cache implementation in Java:

    import java.util.HashMap;
    import java.util.LinkedHashSet;
    import java.util.Map;
    
    public class LFUCache<K, V> {
        private final int capacity;
        private int minFrequency;
        private final Map<K, V> cache;
        private final Map<K, Integer> keyFrequency;
        private final Map<Integer, LinkedHashSet<K>> frequencyList;
    
        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.minFrequency = 0;
            this.cache = new HashMap<>();
            this.keyFrequency = new HashMap<>();
            this.frequencyList = new HashMap<>();
        }
    
        public V get(K key) {
            if (!cache.containsKey(key)) {
                return null;
            }
            int frequency = keyFrequency.get(key);
            keyFrequency.put(key, frequency + 1);
            frequencyList.get(frequency).remove(key);
    
            if (frequencyList.get(frequency).isEmpty()) {
                frequencyList.remove(frequency);
                if (minFrequency == frequency) {
                    minFrequency++;
                }
            }
    
            frequencyList.computeIfAbsent(frequency + 1, k -> new LinkedHashSet<>()).add(key);
            return cache.get(key);
        }
    
        public void put(K key, V value) {
            if (capacity <= 0) {
                return;
            }
    
            if (cache.containsKey(key)) {
                cache.put(key, value);
                get(key);  // Update the frequency of the key
                return;
            }
    
            if (cache.size() >= capacity) {
                K evict = frequencyList.get(minFrequency).iterator().next();
                frequencyList.get(minFrequency).remove(evict);
                if (frequencyList.get(minFrequency).isEmpty()) {
                    frequencyList.remove(minFrequency);
                }
                cache.remove(evict);
                keyFrequency.remove(evict);
            }
    
            cache.put(key, value);
            keyFrequency.put(key, 1);
            minFrequency = 1;
            frequencyList.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        }
    
        public static void main(String[] args) {
            LFUCache<Integer, String> lfuCache = new LFUCache<>(3);
    
            lfuCache.put(1, "one");
            lfuCache.put(2, "two");
            lfuCache.put(3, "three");
            System.out.println("Cache after adding 1, 2, 3: " + lfuCache.cache);
    
            lfuCache.get(1);
            lfuCache.get(1);
            lfuCache.get(2);
            System.out.println("Cache after accessing 1 twice and 2 once: " + lfuCache.cache);
    
            lfuCache.put(4, "four");
            System.out.println("Cache after adding 4 (should evict key 3): " + lfuCache.cache);
    
            lfuCache.get(4);
            lfuCache.get(4);
            lfuCache.put(5, "five");
            System.out.println("Cache after adding 5 (should evict key 2): " + lfuCache.cache);
        }
    }

## Explanation

1. **Class Declaration**: The **LFUCache **class uses generics with **K** for keys and **V** for values.

2. **Constructor**: Initializes the capacity, minimum frequency, and the necessary data structures (**cache, keyFrequency**, and **frequencyList**).

3. **Data Structures**:

* **cache**: Stores the actual key-value pairs.

* **keyFrequency**: Tracks the frequency of access for each key.

* **frequencyList**: A map where the key is the frequency and the value is a **LinkedHashSet **of keys with that frequency.

1. **get(K key)**: Retrieves the value associated with the key, updates the frequency of the key, and adjusts the **frequencyList**.

2. **put(K key, V value)**: Adds a new key-value pair to the cache or updates an existing key. If the cache is at capacity, it evicts the least frequently used key. It then updates the frequency of the key.

3. **Eviction Policy**: When the cache reaches its capacity, the least frequently used key is removed. If multiple keys have the same frequency, the least recently added/used key among them is evicted (due to **LinkedHashSet **maintaining insertion order).

4. **Main Method**: Demonstrates the usage of the **LFUCache**. It adds elements to the cache, accesses them to change their frequencies, and adds more elements to trigger evictions.

This implementation ensures that the least frequently used items are evicted first and handles ties by evicting the least recently added/used items among those with the same frequency.

## Utilizing Google Guava for Caching

To further enrich our exploration, we will also examine how to implement LRU and LFU caching using Google Guava, a powerful open-source Java-based library. Google Guava provides a robust framework for caching, making it easier to implement and manage these algorithms in your applications. We will cover:

1. **Setting Up Google Guava**: Step-by-step instructions for integrating Guava into your project.

2. **Implementing LRU and LFU**: Practical examples and code snippets demonstrating how to use Guava for LRU and LFU caching.

3. **Performance Considerations**: Analysis of the performance benefits and trade-offs when using Guava for caching.

Google Guava provides a simple and efficient way to implement an LRU cache through its **CacheBuilder **class. Below is an example of how to implement an LRU cache using Google Guava.

### Conclusion

By the end of this article, you will have a solid understanding of LRU and LFU caching mechanisms, practical implementation skills, and the ability to leverage Google Guava for advanced caching solutions. Whether you are a novice programmer or an experienced developer, this in-depth review will enhance your knowledge and expertise in caching strategies, empowering you to optimize your applications effectively.

Full example you can find on [Github](https://github.com/alxkm/cache).
