# Java: Mastering SOLID Principles


### Building Robust and Maintainable Software

### Introduction

Almost everyone likes to work on projects that start from scratch. However, not everyone succeeds in constructing a system that remains easily maintainable and adaptable after a year of development.Some, after a few months, make a second attempt, because they already know how to start correctly. It’s natural for the complexity of a system to increase as it grows. The success of developing such a system hinges on effectively managing its complexity. To aid in this endeavor, there exist design patterns, best practices, and, notably, design principles such as SOLID, GRASP, and DDD. This article aims to underscore the importance of SOLID as a fundamental component of a developer’s mindset, one that requires cultivation and refinement.


### Why do you need SOLID?

SOLID is a set of object-oriented programming principles introduced by Robert Martin (Uncle Bob) in 1995. Their idea is to avoid dependencies between code components. If there are a large number of dependencies, such code is difficult to maintain (spaghetti code).

Its main problems are:

- Rigidity: each change causes many other changes
- Fragility: changes in one part break the work of other parts
- Immobility: code cannot be reused outside of its context

- Single Responsibility Principle

Each class should have a clear purpose, focusing on doing one thing well. This doesn’t mean it can only have one method, but rather that all its methods should work together towards a single goal. If a method doesn’t fit with the main purpose of the class, it should be moved elsewhere.

Take the User class, for instance. Its job is to give information about a user, like their name, email, and subscription type for the service.

```java
import java.util.Date;

public enum SubscriptionTypes {STANDARD,VIP}

public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final SubscriptionTypes subscriptionType;
    private final Date subscriptionExpirationDate;

    public User(String firstName, String lastName, String email, SubscriptionTypes subscriptionType, Date subscriptionExpirationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.subscriptionType = subscriptionType;
        this.subscriptionExpirationDate = subscriptionExpirationDate;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public boolean hasExtraAccess() {
        Date now = new Date();
        return subscriptionType == SubscriptionTypes.VIP && subscriptionExpirationDate.after(now);
    }
}
```


Let’s take a look at the hasExtraAccess method. It decides if the user has unlimited access to content based on their subscription type. But hold on, should the User class be responsible for making such a determination? It seems like the User class has two distinct roles: providing user information and deducing content access based on the subscription. This goes against the Single Responsibility principle.

What are the drawbacks of having the hasExtraAccess method in the User class? It leads to confusion about subscription types across the program. Besides the User class, other classes like MediaLibrary and Player might also make decisions based on this data. Each class interprets the subscription type differently, resulting in a lack of consistency. If the rules for existing subscriptions change, all classes need to be updated because each one has its own set of rules for handling them.

Let’s remove the hasExtraAccess method from the User class and introduce a new class dedicated to handling subscriptions.

```java
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccessManager {
    public static boolean hasUnlimitedContentAccess(User user) {
        Date now = new Date();
        return user.getSubscriptionType() == SubscriptionTypes.VIP
                && user.getSubscriptionExpirationDate().after(now);
    }

    public static List<Movie> getBasicContent(List<Movie> movies) {
        return movies.stream()
                .filter(movie -> movie.getSubscriptionType() == SubscriptionTypes.STANDARD)
                .collect(Collectors.toList());
    }

    public static List<Movie> getPremiumContent(List<Movie> movies) {
        return movies.stream()
                .filter(movie -> movie.getSubscriptionType() == SubscriptionTypes.VIP)
                .collect(Collectors.toList());
    }

    public static List<Movie> getContentForUserWithBasicAccess(List<Movie> movies) {
        return getBasicContent(movies);
    }

    public static List<Movie> getContentForUserWithUnlimitedAccess(List<Movie> movies) {
        return movies;
    }
}
```

We consolidated all subscription-related rules into a single class. Any modifications to these rules will be confined to this class alone and will not impact others.

The Single Responsibility Principle extends beyond individual classes; it also applies to class modules, which should be narrowly specialized.

In addition to SOLID, there’s another set of software design principles called GRASP. Some of its principles align with those of SOLID. In the context of the Single Responsibility Principle, GRASP can be likened to:

- information expert (Information Expert) — an entity possessing comprehensive knowledge within a specific domain
- low coupling (Low Coupling) and high cohesion (High Cohesion) — components across different classes or modules ought to have minimal dependencies among them, while components within the same class or module should exhibit strong logical connections or close interaction with one another


### Open/Close Principle

Classes should be designed to allow for extension without requiring modification. When a class involves numerous branching or successive steps, and there is a likelihood of these steps increasing over time, it’s essential to structure the class in a manner that accommodates new branches or steps without necessitating modifications to the class itself.

Many of us have encountered lengthy chains of if-then-else or switch statements. Each time a new condition is introduced, we find ourselves adding yet another if-then-else statement, thereby altering the class. Similarly, a class executing a process with numerous successive steps may require modifications each time a new step is added. Such practices run counter to the Open/Closed Principle.

Now we will explore idea how we can expand a class functionality without modifying it.

```java
// Interface defining a shape
interface Shape {
    double area();
}

// Concrete implementation of Shape for a rectangle
class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}

// Concrete implementation of Shape for a circle
class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

// Utility class to calculate total area of shapes
class AreaCalculator {
    public static double calculateTotalArea(Shape[] shapes) {
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            totalArea += shape.area();
        }
        return totalArea;
    }
}

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[] {
            new Rectangle(5, 4),
            new Circle(3)
        };

        double totalArea = AreaCalculator.calculateTotalArea(shapes);
        System.out.println("Total area of shapes: " + totalArea);
    }
}
```

In this example, the AreaCalculator class can calculate the total area of various shapes without modifying its implementation. If we want to add a new shape, such as a triangle, we can create a new class implementing the Shape interface without modifying any existing code. This demonstrates the Open/Closed Principle, where classes should be open for extension (new shapes can be added) but closed for modification (existing code does not need to change).

### Liskov Substitution Principle

When substituting an object of the base class with an object of its derived class, the program should maintain proper functionality.

If methods inherited from the parent class are overridden, the updated behavior should seamlessly complement the existing behavior of the base class. To illustrate a breach of this principle.


```java
class NumberAdder {
    public int add(int a, int b) {
        return a + b;
    }
}

class ExtendedNumberAdder extends NumberAdder {
    @Override
    public int add(int a, int b) {
        throw new UnsupportedOperationException("This operation is not supported");
    }
}
```

There’s also the possibility that the behavior of the parent method clashes with the logic of the child classes. Take a look at the following vehicle hierarchy.

```java
class Vehicle {
    void accelerate() {
        // implementation
    }

    void slowDown() {
        // implementation
    }

    void turn(int angle) {
        // implementation
    }
}

class Car extends Vehicle {
}

class Bus extends Vehicle {
}

// Train class
class Train extends Vehicle {
    // Overridden method
    @Override
    void turn(int angle) {
        // implementation specific to trains
    }
}
```

In the Train class, the turn(int angle) method is overridden from the base class Vehicle, providing a specific implementation for trains. This demonstrates the extensibility of the code without conflicting with the behavior of other vehicle types.

To correct the situation and adhere to the Liskov substitution principle, we can introduce two new parent classes: FreeDirectionalVehicle and BidirectionalVehicle. These classes will define the specific movement capabilities of vehicles and ensure that subclasses adhere to those capabilities.

Here’s how you can refactor the code:


```java
// Parent class for vehicles with free directional movement
class FreeDirectionalVehicle extends Vehicle {
    // Methods for free directional movement
    void turnLeft() {
        // implementation
    }

    void turnRight() {
        // implementation
    }
}

// Parent class for vehicles with bidirectional movement
class BidirectionalVehicle extends Vehicle {
    // Methods for bidirectional movement
    void moveForward() {
        // implementation
    }

    void moveBackward() {
        // implementation
    }
}

// Car class
class Car extends FreeDirectionalVehicle {
    // Car-specific methods and properties
}

// Bus class
class Bus extends FreeDirectionalVehicle {
    // Bus-specific methods and properties
}

// Train class
class Train extends BidirectionalVehicle {
    // Train-specific methods and properties
}
```

Furthermore, the subclass should not introduce any preconditions or postconditions around the method execution. For instance:

```java
import java.io.FileWriter;
import java.io.IOException;

class Logger {
    void log(String text) {
        System.out.println(text);
    }
}

class FileLogger extends Logger {
    private final String path;

    FileLogger(String path) {
        this.path = path;
    }

    @Override
    void log(String text) {
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class HttpLogger extends Logger {
    private final String ip;
    private final int port;

    HttpLogger(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    void log(String text) {
        // implementation
    }

    void openConnection() {
        // implementation
    }

    void closeConnection() {
        // implementation
    }
}
```


In this hierarchy, replacing FileLogger objects of the parent class with HttpLogger objects won’t be straightforward due to the additional steps required before and after calling the log method, such as calling openConnection and closeConnection. This introduces additional conditions for the log method call, violating the Liskov substitution principle.

To address this issue, we can encapsulate the openConnection and closeConnection methods as private. Within the log method of the TcpLogger class, we can handle the logging process, including periodically opening a connection, sending log files, and closing the connection. Additionally, we should ensure that all logs are sent before the program terminates. In case of a program crash, logs can be sent during the next program launch.

### Interface Segregation Principle

Having numerous specialized interfaces is preferable to having a single generic one. When using a single common interface, there’s a possibility of encountering scenarios where a derived class cannot logically inherit certain methods.

Let’s consider an example where we have a common interface Shape and two derived classes Circle and Rectancle. The Shape interface has a method calculateArea(), but it's not applicable for all shapes.

```java
interface Shape {
    double calculateArea();
}

class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

class Square implements Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    // Not applicable for all shapes, violating the principle
    @Override
    public double calculateArea() {
        return side * side;
    }
}
```

In this example, the calculateArea() method is appropriate for shapes like circles and rectangles but not for squares, as the concept of a square's area is slightly different. Therefore, having a single common interface leads to a violation of the principle.

To solve this, we can divide the Shape interface into two specialized interfaces: AreaCalculatable and Shape.

```java
interface AreaCalculatable {
    double calculateArea();
}

interface Shape {
    // Other shape-related methods
}

class Circle implements AreaCalculatable, Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements AreaCalculatable, Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

class Square implements AreaCalculatable, Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }
}
```

By doing this, we ensure that the calculateArea() method is only present in the AreaCalculatable interface, and only shapes that need to calculate their area will implement this interface. Other shape-related methods can still be included in the Shape interface. This approach adheres to the principle of having many specialized interfaces rather than one common one.

### Dependency Inversion Principle

These statements can be summarized as follows:

- High-level modules should not be dependent on low-level modules. Both should rely on abstractions.
- Abstractions should not rely on implementation details. Instead, implementation details should depend on abstractions.

Now, let’s examine the code that contradicts these principles:

Suppose we have a NotificationService class responsible for sending notifications, and a PaymentService class responsible for processing payments. Initially, these classes have a direct dependency on each other:

```java
public class NotificationService {
    public void sendNotification(String message) {
        // Logic to send notification
        System.out.println("Notification sent: " + message);
    }
}

public class PaymentService {
    private NotificationService notificationService;

    public PaymentService() {
        this.notificationService = new NotificationService(); // Direct dependency
    }

    public void processPayment(double amount) {
        // Logic to process payment
        System.out.println("Payment processed: $" + amount);
        notificationService.sendNotification("Payment of $" + amount + " processed successfully");
    }
}

public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();
        paymentService.processPayment(100.0);
    }
}
```

In this example, the PaymentService directly instantiates a NotificationService, creating a tight coupling between the two classes.

To adhere to the Dependency Inversion Principle, we introduce an interface NotificationSender that both NotificationService and PaymentService will depend on:


```java
public interface NotificationSender {
    void sendNotification(String message);
}

public class NotificationService implements NotificationSender {
    @Override
    public void sendNotification(String message) {
        // Logic to send notification
        System.out.println("Notification sent: " + message);
    }
}

public class PaymentService {
    private NotificationSender notificationSender;

    public PaymentService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender; // Dependency injection via constructor
    }

    public void processPayment(double amount) {
        // Logic to process payment
        System.out.println("Payment processed: $" + amount);
        notificationSender.sendNotification("Payment of $" + amount + " processed successfully");
    }
}

public class Main {
    public static void main(String[] args) {
        NotificationSender notificationSender = new NotificationService();
        PaymentService paymentService = new PaymentService(notificationSender);
        paymentService.processPayment(100.0);
    }
}
```

Now, both NotificationService and PaymentService depend on the NotificationSender interface rather than concrete implementations. This allows for greater flexibility, as we can easily swap out implementations of NotificationSender without modifying PaymentService, adhering to the Dependency Inversion Principle.

In the refactored example, dependency on abstractions is achieved through the use of the NotificationSender interface. Both the NotificationService and PaymentService classes depend on this interface rather than concrete implementations.

Specifically, in the PaymentService class, the dependency on NotificationSender is injected via the constructor:

```java
public class PaymentService {
    private NotificationSender notificationSender;

    public PaymentService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender; // Dependency injection via constructor
    }

    // Other methods...
}
```

By accepting NotificationSender as a constructor parameter, the PaymentService class doesn’t depend on a specific implementation of the NotificationSender, but rather any implementation that adheres to the NotificationSender interface.

This approach allows the PaymentService class to remain agnostic of the actual implementation details of the NotificationSender, enabling easier maintenance, testing, and future changes. Thus, the classes depend on abstractions rather than concrete implementations, adhering to the Dependency Inversion Principle.

### Summary

As the system evolves, its complexity naturally grows. It’s crucial to manage this complexity effectively to prevent even seemingly minor feature additions from becoming prohibitively expensive. Certain issues tend to recur frequently during development. To address these challenges, a set of design principles has been established. Adhering to these principles helps prevent the system’s complexity from spiraling out of control. Among the most fundamental of these principles are the SOLID principles.

In summary, the SOLID principles provide a foundational framework for writing maintainable, scalable, and robust software systems. By adhering to these principles, developers can design code that is easier to understand, modify, and extend over time. The principles emphasize the importance of single responsibility, open-closed behavior, Liskov substitution, interface segregation, and dependency inversion. By following these guidelines, software engineers can create codebases that are resilient to change, promote code reuse, and facilitate collaboration among team members. Robert Martin’s contributions to the field of software development, particularly through his books “Clean Code” and “Clean Coder,” have been instrumental in popularizing these principles and guiding developers towards writing cleaner, more efficient code.

