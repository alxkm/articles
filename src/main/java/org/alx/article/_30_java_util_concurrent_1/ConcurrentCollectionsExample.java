package org.alx.article._30_java_util_concurrent_1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentCollectionsExample {
    static void concurrentMapSimpleExample() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 123);
        int value = map.get("key");
    }

    static void concurrentLinkedQueueSimpleExample() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.offer("element1");
        String element = queue.poll();
    }

    static void copyOnWriteArrayListSimpleExample() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("element1");
        String element = list.get(0);
    }

    static void blockingQueueSimpleExample() throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.put("element1"); // Blocking operation if queue is full
        String element = queue.take(); // Blocking operation if queue is empty
    }
}
