Metaspace tuning

You can manage the Metaspace memory region using the following JVM flags:

-XX:MetaspaceSize — Specifies the minimum size of the Metaspace region.
-XX:MaxMetaspaceSize — Specifies the maximum size of the Metaspace region.
-XX:MinMetaspaceFreeRatio — Specifies the minimum percentage of reserved memory after garbage collection.
-XX:MaxMetaspaceFreeRatio — Specifies the maximum percentage of reserved memory after garbage collection.


The heap is divided into two main areas:

Young Generation: This area is further divided into Eden space and two Survivor spaces (From and To). Objects are initially allocated in the Eden space. When the Eden space becomes full, a minor garbage collection is triggered, and surviving objects are moved to one of the Survivor spaces. Objects that survive multiple garbage collection cycles in the Survivor spaces are eventually promoted to the Old Generation.
Old Generation: This area is also known as the Tenured Generation. It is used to store long-lived objects that have survived multiple garbage collection cycles in the Young Generation. Major garbage collections are performed in the Old Generation to reclaim memory from unreachable objects.


Heap tuning

To tune the Java heap:

Set initial and maximum heap size (-Xms, -Xmx).
Adjust Young Generation size (-Xmn).
Choose garbage collection algorithm (-XX:+UseSerialGC, -XX:+UseParallelGC, -XX:+UseG1GC).
Configure garbage collection threads (-XX:ParallelGCThreads).
Enable adaptive sizing policies (-XX:+UseAdaptiveSizePolicy).
Enable garbage collection logging (-Xloggc).
Generate heap dump on OutOfMemoryError (-XX:+HeapDumpOnOutOfMemoryError).
Use compressed object pointers (-XX:+UseCompressedOops).
Fine-tune G1GC parameters (-XX:G1HeapRegionSize, -XX:MaxGCPauseMillis).
-XX:NewSize: Specifies the minimum size of the Eden segment in the Young Generation.
-XX:MaxNewSize: Specifies the maximum size of the Eden segment in the Young Generation.