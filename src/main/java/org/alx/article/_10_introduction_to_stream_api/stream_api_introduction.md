# Introduction to Java Stream API for Beginners

Introduction

Java Stream API is a powerful tool introduced in Java 8 for processing collections of objects in a functional style. It provides a concise and expressive way to perform operations such as filtering, mapping, and reducing on collections. In this article, we’ll explore the basics of the Java Stream API and learn how to use it effectively.

Firstly we need to clarify basic moments and terminology of Stream API.

A Stream in Java represents a sequence of elements from a source, such as a collection, an array, or an I/O resource. It does not store data; instead, it provides a pipeline for processing data elements sequentially or in parallel.

Streams can be created from various data sources using factory methods provided by the Stream interface. Common methods for creating streams include stream() and parallelStream() methods of collections and arrays.


Stream operations divided by Intermediate and Terminal Operations:

- Intermediate operations transform or filter the elements of a stream and return a new stream. Examples include filter(), map(), and sorted().
- Terminal operations produce a result or side-effect and terminate the stream. Examples include forEach(), collect(), and reduce().
Basic Stream Operations consist of:

Filtering: Use the filter() method to select elements based on a predicate.

```java
// Create a list of integers
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Filter even numbers
List<Integer> evenNumbers = numbers.stream()
        .filter(n -> n % 2 == 0)       // Filter out odd numbers
        .collect(Collectors.toList()); // Collect into a list

// Print the filtered numbers
System.out.println("Even numbers: " + evenNumbers);

//Output: Even numbers: [2, 4, 6, 8, 10]
```
Mapping: Use the map() method to transform elements into another form.

```java
// Create a list of Person records
List<Person> persons = Arrays.asList(
        new Person("Alice"),
        new Person("Bob"),
        new Person("Charlie")
);

// Map Person records to their names
List<String> names = persons.stream()
        .map(Person::name)             // Map Person records to their names
        .collect(Collectors.toList()); // Collect the names into a list

// Print the list of names
System.out.println("Names: " + names);

//Output: Names: [Alice, Bob, Charlie]


// Person record
record Person(String name) {}
```

Reduction: Use the reduce() method to perform a reduction operation on the elements of the stream.


```java
// Create a list of integers
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Use reduce to calculate the sum of all integers in the list
// Initial value is 0, BinaryOperator adds two integers
int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b); 


// Print the sum
System.out.println("Sum: " + sum);
//Output: Sum: 15
```

Stream operations can be chained together to form a pipeline. Each operation in the pipeline is lazy-evaluated, meaning elements are processed only when needed.

Example of map pipelines. List of Persons (with transactions), and for each person we calculate average transactions value, and collection result to Map<String, Double>, name and average transactions values.


```java
// Define a record for Person with name and list of transactions
record Person(String name, List<Integer> transactions) {}

// Create a list of persons with their transactions
List<Person> persons = List.of(
        new Person("Alice", List.of(100, 200, 300)),
        new Person("Bob", List.of(50, 150, 250, 350)),
        new Person("Charlie", List.of(75, 125, 175))
);

// Calculate the average transaction price for each person
Map<String, Double> averageTransactionPriceMap = persons.stream()
        .collect(Collectors.toMap(
                Person::name,
                person -> person.transactions().stream()
                        .mapToDouble(Integer::doubleValue)
                        .average()
                        .orElse(0.0)
        ));

// Print the average transaction price map
System.out.println("Average Transaction Price:");
averageTransactionPriceMap.forEach((name, average) -> System.out.println(name + ": " + average));

// Output:
// Average Transaction Price:
// Bob: 200.0
// Alice: 200.0
// Charlie: 125.0
```

We can sorted() during filtering. In next example we select top 3 Persons with higgest score:

```java
record Person(String name, int score) { }

List<Person> persons = Arrays.asList(new Person("Alice", 80), new Person("Bob", 90), new Person("Charlie", 70),
                                      new Person("David", 85), new Person("Eve", 75), new Person("Frank", 95),
                                      new Person("Grace", 82), new Person("Henry", 88), new Person("Ivy", 72),
                                      new Person("Jack", 89), new Person("Kate", 79), new Person("Luke", 91),
                                      new Person("Mary", 86), new Person("Nancy", 76), new Person("Oliver", 94));

// Filter persons with a score
// Sort by score in descending order
// Get the top 3 persons

List<Person> filteredPersons = persons.stream()
        .filter(person -> person.score() > 0)               
        .sorted(Comparator.comparingInt(Person::score).reversed()) 
        .limit(3)                                           
        .collect(Collectors.toList());

System.out.println(filteredPersons);

// Output:
// [Person[name=Frank, score=95], 
// Person[name=Oliver, score=94], 
// Person[name=Luke, score=91]]
```

Parallel Streams:
- Streams can be processed in parallel to take advantage of multi-core processors.
- Use the parallelStream() method to convert a stream into a parallel stream.

Example of using parallelStream() to previous example:

```java
// Use parallel stream
// Filter persons with a score
// Sort by score in descending order
// Get the top 3 persons

List<Person> selectedPersons = persons.parallelStream() 
        .filter(person -> person.score() > 0) 
        .sorted(Comparator.comparingInt(Person::score).reversed()) 
        .limit(3) 
        .collect(Collectors.toList());
```

So sorted can operates on a parallel stream also.

Java Stream API provides a functional and declarative way to process collections of data. By leveraging streams, developers can write concise and expressive code to perform complex operations on data sets. Understanding the basics of the Java Stream API is essential for writing efficient and readable code in modern Java applications.



