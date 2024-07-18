
## Introduction to Caching with Google Guava: A Simple and Flexible Solution

Unlocking Efficiency: Exploring Caching with Google Guava for Simple and Versatile Solutions

![[Source](https://cdn.mos.cms.futurecdn.net/RZpjoB2ryfQmiYzJdE7RCe-1024-80.jpg.webp)](https://cdn-images-1.medium.com/max/2000/0*tO35RVf3NW3a5y4U.jpg)

In today’s computing landscape, a plethora of caching solutions such as Redis and Hazelcast are available, offering powerful and distributed caching capabilities. However, there are scenarios where a simpler, more lightweight caching mechanism is desirable. In such cases, Google Guava emerges as a perfect solution, providing a flexible, well-tested, and mature caching library that can be easily integrated into any Java application.

In our [previous article](https://medium.com/@alxkm/introduction-to-lru-and-lfu-caching-concepts-implementations-and-practical-use-cases-ab90f2e168bd) we reviewed what is LRU and LFU cache and how it works. Implemented simple versions of these caches.

Google Guava stands out due to its simplicity and ease of use. Unlike more complex caching solutions, Guava allows developers to implement efficient caching with minimal configuration. This makes it an ideal choice for applications that do not require the extensive features of distributed caches but still benefit from robust caching mechanisms.

Moreover, Guava’s caching capabilities are not only practical but also serve as excellent learning tools. For study purposes, exploring Guava’s cache implementation can provide deep insights into how efficient caching works. Guava’s straightforward constructors and APIs make it easy to experiment and understand caching concepts without the overhead of more complex systems.

In this article, we will delve into implementing an LFU (Least Frequently Used) cache using Google Guava. We will explore how Guava provides the flexibility to build custom caching strategies while leveraging its powerful and reliable core. Whether you are a student looking to learn about caching or a developer seeking a simple yet effective caching solution, Google Guava offers a perfect balance of simplicity and functionality.

LRU and LFU and other, cache are already implemented in my [simple cache library](https://github.com/alxkm/cache).

LRU cache:

![](https://cdn-images-1.medium.com/max/2000/0*S21OELPs6C3kTpD7.jpeg)

## LRU cache with Google Guava

Building an LRU cache from scratch is an interesting and relatively straightforward task. In a previous article, we’ve already developed one ourselves, which you can check out for reference. It’s worth noting that LeetCode features a medium-level task to build an LRU cache, suggesting that while it’s not trivial, it’s certainly achievable.

Fortunately, Google Guava offers a simple and efficient solution for implementing an LRU cache through its **CacheBuilder **class. Let's explore an example of how to implement an LRU cache using Google Guava.

[Github link](https://www.google.com/url?sa=i&url=https%3A%2F%2Fgithub.com%2Fgoogle%2Fguava&psig=AOvVaw1To9IoZKwoytJWxcftEjrE&ust=1721337843386000&source=images&cd=vfe&opi=89978449&ved=0CBUQjhxqFwoTCJDjqKuBr4cDFQAAAAAdAAAAABAE)

### Maven Dependency

First, ensure you have added the Guava dependency to your **pom.xml** if you're using Maven:

    <dependency>
        <groupId>com.google.guava</groupId>
        <artifactId>guava</artifactId>
        <version>31.1-jre</version>
    </dependency>

### Implementation

In our implementation, we’ll employ **CacheBuilder.newBuilder()**. This method offers a versatile builder pattern, facilitating the configuration of various cache settings. Let's delve deeper into its simplicity and flexibility.

Here’s how you can implement an LRU cache using Guava:

    import com.google.common.cache.Cache;
    import com.google.common.cache.CacheBuilder;
    
    import java.util.concurrent.TimeUnit;
    
    public class GuavaLRUCache<K, V> {
        private final Cache<K, V> cache;
    
        public GuavaLRUCache(int capacity) {
            this.cache = CacheBuilder.newBuilder()
                    .maximumSize(capacity)
                    .expireAfterAccess(10, TimeUnit.MINUTES) // Optional: to auto-remove entries after some idle time
                    .build();
        }
    
        public void put(K key, V value) {
            cache.put(key, value);
        }
    
        public V get(K key) {
            return cache.getIfPresent(key);
        }
    
        public void invalidate(K key) {
            cache.invalidate(key);
        }
    
        public long size() {
            return cache.size();
        }
    
        public static void main(String[] args) {
            GuavaLRUCache<Integer, String> lruCache = new GuavaLRUCache<>(3);
    
            lruCache.put(1, "one");
            lruCache.put(2, "two");
            lruCache.put(3, "three");
            System.out.println("Cache after adding 1, 2, 3: " + lruCache.cache.asMap());
    
            // Access key 1
            lruCache.get(1);
            System.out.println("Cache after accessing key 1: " + lruCache.cache.asMap());
    
            // Add another element, which should evict the least recently used (key 2 or 3)
            lruCache.put(4, "four");
            System.out.println("Cache after adding key 4: " + lruCache.cache.asMap());
        }
    }

### Explanation

1. **Cache Initialization**:

* **CacheBuilder.newBuilder()**: Creates a new **CacheBuilder **instance.

* **maximumSize(capacity)**: Sets the maximum number of entries the cache can hold. Once the cache reaches this size, the least recently used entries are removed.

* **expireAfterAccess(10, TimeUnit.MINUTES)**: Optional setting to remove entries after they have not been accessed for a specified period.

### **2. Methods**:

* **put(K key, V value)**: Adds a key-value pair to the cache.

* **get(K key)**: Retrieves the value associated with the key if it is present in the cache.

* **invalidate(K key)**: Removes the key-value pair from the cache.

* **size()**: Returns the current size of the cache.

### Demonstration

Demonstrates the usage of the **GuavaLRUCache**. It adds elements to the cache, accesses an element to update its usage, and adds another element to trigger eviction of the least recently used element.

### Key Points

* **Automatic Eviction**: Guava’s **CacheBuilder **handles the eviction of the least recently used entries once the maximum size is reached.

* **Optional Expiration**: Entries can be configured to expire after a certain period of inactivity using the **expireAfterAccess **method.

* **Thread-Safety**: Guava caches are thread-safe and handle concurrent access efficiently.

Using Google Guava simplifies the implementation of an LRU cache, allowing you to leverage its powerful and efficient caching mechanisms with minimal code.

## LFU cache with Guava

Google Guava does not directly provide an LFU (Least Frequently Used) cache out of the box. However, we can build an LFU cache by extending Guava’s capabilities. To achieve this, we can use Guava’s **CacheBuilder **along with some custom logic to keep track of usage frequencies.

LFU cache example:

![](https://cdn-images-1.medium.com/max/2000/0*h5INNzGu_1Gn9Pwt.jpeg)

Here’s an implementation example of an LFU cache using Guava:

## Implementation

Building an LFU cache from scratch can be challenging, even earning a “hard” label on platforms like LeetCode. While I’ve covered a basic LFU cache in a previous article, using Google Guava makes the process much simpler.

Guava’s **CacheBuilder.newBuilder()** construction streamlines the setup, offering a straightforward approach to configuring the cache. By chaining methods like **expireAfterAccess(10, TimeUnit.MINUTES)**, we can easily define additional features like expiration time. This simplicity allows us to focus on the core logic of our LFU cache implementation without getting bogged down in intricate setup details.

Here’s how you can implement an LFU cache with Guava:

    import com.google.common.cache.Cache;
    import com.google.common.cache.CacheBuilder;
    
    import java.util.HashMap;
    import java.util.LinkedHashSet;
    import java.util.Map;
    import java.util.concurrent.TimeUnit;
    
    public class GuavaLFUCache<K, V> {
        private final int capacity;
        private final Cache<K, V> cache;
        private final Map<K, Integer> frequencies;
        private final Map<Integer, LinkedHashSet<K>> frequencyList;
        private int minFrequency;
    
        public GuavaLFUCache(int capacity) {
            this.capacity = capacity;
            this.cache = CacheBuilder.newBuilder()
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build();
            this.frequencies = new HashMap<>();
            this.frequencyList = new HashMap<>();
            this.minFrequency = 0;
        }
    
        public V get(K key) {
            if (!cache.asMap().containsKey(key)) {
                return null;
            }
            updateFrequency(key);
            return cache.getIfPresent(key);
        }
    
        public void put(K key, V value) {
            if (capacity <= 0) {
                return;
            }
    
            if (cache.asMap().containsKey(key)) {
                cache.put(key, value);
                updateFrequency(key);
                return;
            }
    
            if (cache.size() >= capacity) {
                evictLFU();
            }
    
            cache.put(key, value);
            frequencies.put(key, 1);
            frequencyList.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFrequency = 1;
        }
    
        private void updateFrequency(K key) {
            int frequency = frequencies.get(key);
            frequencies.put(key, frequency + 1);
            frequencyList.get(frequency).remove(key);
    
            if (frequencyList.get(frequency).isEmpty()) {
                frequencyList.remove(frequency);
                if (minFrequency == frequency) {
                    minFrequency++;
                }
            }
    
            frequencyList.computeIfAbsent(frequency + 1, k -> new LinkedHashSet<>()).add(key);
        }
    
        private void evictLFU() {
            K evict = frequencyList.get(minFrequency).iterator().next();
            frequencyList.get(minFrequency).remove(evict);
            if (frequencyList.get(minFrequency).isEmpty()) {
                frequencyList.remove(minFrequency);
            }
            cache.invalidate(evict);
            frequencies.remove(evict);
        }
    
        public static void main(String[] args) {
            GuavaLFUCache<Integer, String> lfuCache = new GuavaLFUCache<>(3);
    
            lfuCache.put(1, "one");
            lfuCache.put(2, "two");
            lfuCache.put(3, "three");
            System.out.println("Cache after adding 1, 2, 3: " + lfuCache.cache.asMap());
    
            // Access key 1
            lfuCache.get(1);
            lfuCache.get(1);
            lfuCache.get(2);
            System.out.println("Cache after accessing 1 twice and 2 once: " + lfuCache.cache.asMap());
    
            // Add another element, which should evict the least frequently used (key 3)
            lfuCache.put(4, "four");
            System.out.println("Cache after adding key 4 (should evict key 3): " + lfuCache.cache.asMap());
    
            lfuCache.get(4);
            lfuCache.get(4);
            lfuCache.put(5, "five");
            System.out.println("Cache after adding key 5 (should evict key 2): " + lfuCache.cache.asMap());
        }
    }

## Explanation

1. **Class Declaration**: **GuavaLFUCache **class uses generics with **K** for keys and **V** for values.

2. **Constructor**: Initializes the capacity, Guava cache, frequency map, frequency list, and minimum frequency.

3. **Data Structures**:

* **cache**: Guava's cache to store key-value pairs.

* **frequencies**: Maps keys to their frequencies.

* **frequencyList**: Maps frequencies to a set of keys having that frequency.

* **minFrequency**: Tracks the current minimum frequency in the cache.

**4. get(K key)**: Retrieves the value associated with the key if present and updates the key’s frequency.

**5. put(K key, V value)**: Adds a new key-value pair or updates an existing key. If the cache reaches capacity, it evicts the least frequently used key.

**6. updateFrequency(K key)**: Updates the frequency of a key and manages the frequency lists.

**7. evictLFU()**: Evicts the least frequently used key from the cache.

### Demonstration

Demonstrates the usage of the **GuavaLFUCache**. It adds elements, accesses them to change their frequencies, and adds more elements to trigger eviction of the least frequently used elements.

## Key Points

* **Frequency Management**: The **frequencies **map keeps track of how often each key is accessed, and the **frequencyList **maintains the order of keys based on their frequencies.

* **Eviction Policy**: The cache evicts the least frequently used element when it reaches its capacity.

* **Guava Cache Integration**: Guava is used to manage the actual storage and expiration of cache entries, while custom logic handles the LFU aspect.

This implementation integrates Guava’s caching capabilities with custom frequency management logic to create an effective LFU cache without additional effort from our side, everything what we like.

## Conclusion

In this article, we’ve explored how to implement both LRU and LFU caches using Google Guava. Through our journey, we’ve learned that constructing these caches is not overly challenging, thanks to Guava’s provision of straightforward builders for cache setup and utilization. By leveraging Guava, we’ve obtained a solution from scratch that efficiently manages cache operations.

However, for those who prefer a hands-on approach and enjoy building things from scratch, I encourage you to check out my previous article where we developed an LRU cache independently. Additionally, as always, you can find the solutions and code snippets on my GitHub repository for further exploration and reference. With this newfound understanding and access to resources, you’re well-equipped to integrate caching mechanisms seamlessly into your Java applications.

LRU and LFU and other, cache are already implemented in my [simple cache library](https://github.com/alxkm/cache).
