package org.alx.article._10_introduction_to_stream_api;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamApiExample {
    private static List<Person> persons = Arrays.asList(new Person("Alice", 80), new Person("Bob", 90), new Person("Charlie", 70),
            new Person("David", 85), new Person("Eve", 75), new Person("Frank", 95),
            new Person("Grace", 82), new Person("Henry", 88), new Person("Ivy", 72),
            new Person("Jack", 89), new Person("Kate", 79), new Person("Luke", 91),
            new Person("Mary", 86), new Person("Nancy", 76), new Person("Oliver", 94));


    public static void main(String[] args) {
        filterExample();
        mapExample();
        reduceExample();
        sortedExample();
        parallelStreamExample();
    }

    private static void filterExample() {
        // Create a list of integers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Filter even numbers
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)       // Filter out odd numbers
                .collect(Collectors.toList()); // Collect into a list

        // Print the filtered numbers
        System.out.println("Even numbers: " + evenNumbers);

        //Output: Even numbers: [2, 4, 6, 8, 10]
    }

    public static void mapExample() {
        //Create a list of Person records
        List<Person> persons = Arrays.asList(
                new Person("Alice"),
                new Person("Bob"),
                new Person("Charlie")
        );

        // Map Person records to their names
        List<String> names = persons.stream()
                .map(Person::getName)             // Map Person records to their names
                .collect(Collectors.toList()); // Collect the names into a list

        // Print the list of names
        System.out.println("Names: " + names);

        //Output: Names: [Alice, Bob, Charlie]


        // Person record

    }

    public static void reduceExample() {
        // Create a list of integers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Use reduce to calculate the sum of all integers in the list
        // Initial value is 0, BinaryOperator adds two integers
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);


        // Print the sum
        System.out.println("Sum: " + sum);
        //Output: Sum: 15
    }

    public static void pipelinesExample() {
        // Create a list of persons with their transactions
        List<Person> persons = List.of(
                new Person("Alice", List.of(100, 200, 300)),
                new Person("Bob", List.of(50, 150, 250, 350)),
                new Person("Charlie", List.of(75, 125, 175))
        );

        // Calculate the average transaction price for each person
        Map<String, Double> averageTransactionPriceMap = persons.stream()
                .collect(Collectors.toMap(
                        Person::getName,
                        person -> person.getTransactions().stream()
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
    }

    public static void sortedExample() {

        // Filter persons with a score
        // Sort by score in descending order
        // Get the top 3 persons

        List<Person> filteredPersons = persons.stream()
                .filter(person -> person.getScore() > 0)
                .sorted(Comparator.comparingInt(Person::getScore).reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(filteredPersons);

        // Output:
        // [Person[name=Frank, score=95],
        // Person[name=Oliver, score=94],
        // Person[name=Luke, score=91]]
    }

    public static void parallelStreamExample() {
        // Use parallel stream
        // Filter persons with a score
        // Sort by score in descending order
        // Get the top 3 persons

        List<Person> selectedPersons = persons.parallelStream()
                .filter(person -> person.getScore() > 0)
                .sorted(Comparator.comparingInt(Person::getScore).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

}
