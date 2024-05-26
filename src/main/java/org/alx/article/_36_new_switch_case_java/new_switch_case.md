# Java's Pattern Matching: Simplifying Conditional Logic and Type Checking

Pattern matching for instanceof is a feature introduced in Java 16 and refined in Java 17 as a preview feature in JEP 406. It simplifies the common pattern of checking an object's type and then casting it to that type. Let's dive into some examples to understand how it works.

![image](source/title.jpeg)

### Before Pattern Matching

In this example, after instance of pattern we need to declare variable and then to use it:

```java
public static void printValue(Object o) {
    if (o instanceof String) {
        String str = (String) o;
        System.out.println("String: " + str);
    } else if (o instanceof Integer) {
        Integer i = (Integer) o;
        System.out.println("Integer: " + i);
    }
}
```

### Pattern Matching (Java 16)

In this example, if obj is an instance of String, the pattern String s matches, and s is automatically assigned the value of obj casted to String. This eliminates the need for an explicit cast and improves code readability.

```java
public static void printValueUsingPatternMatching(Object obj) {
    if (obj instanceof Integer i) {
        System.out.println("Integer: " + i);
    } else if (obj instanceof Long l) {
        System.out.println("Long: " + l);
    } else if (obj instanceof Double d) {
        System.out.println("Double: " + d);
    } else if (obj instanceof String s) {
        System.out.println("String: " + s);
    }
}
```

### Pattern Matching with Switch (Java 17)

Pattern matching for instanceof is further enhanced in Java 17 when used with switch expressions for few different types.

Switch statements are often considered suitable for such tasks, but their capability to handle types and comparisons is quite limited. For instance, matching a constant value often requires a cumbersome chain of if-else blocks. It's worth noting that this feature has already been implemented in other languages like Scala and Kotlin. However, Java has now adopted this construction, offering the ability to work with any type, pattern matching in case sections, and null handling within cases. Consequently, the lengthy list of if-else statements can now be transformed into a more elegant and concise construction:

```java
public static String getFormatStringUsingSwitchPatternMatching(Object obj) {
    return switch (obj) {
        case Integer i -> String.format("Integer: %d", i);
        case Long l -> String.format("Long: %d", l);
        case Double d -> String.format("Double: %f", d);
        case String s -> String.format("String: %s", s);
        default -> "Unknown";
    };
}
```

Here, the switch expression checks the type of obj. If it's a String, the pattern String s matches, and s is bound to the value of obj as a String, allowing us to directly call methods on s.

### Pattern Matching with Records (Java 17)

Pattern matching also works well with records, a feature introduced in Java 14.

```java
record Point(int x, int y) {}

Point p = new Point(10, 20);

if (p instanceof Point point) {
    System.out.println("X: " + point.x + ", Y: " + point.y);
}
```

In this example, if p is an instance of Point, the pattern Point point matches, and point is automatically assigned the value of p casted to Point, giving us access to its components x and y.
Pattern matching for instanceof simplifies code, making it more concise and readable, especially in scenarios where type checking and casting are common.

### Null handling

Also need to mention null handling. Before we NullPointerException, if checked object was null. To correctly handling nulls we need to make some preprocessing for null check like this:

```java
public static void nullHandlingOutsideSwitch(Object o) {
    if (o == null) {
        System.out.println("Null as input");
    } else {
        switch (o) {
            case String s -> System.out.println("String: " + s);
            case Integer i -> System.out.println("Integer: " + i);
            default -> System.out.println("Other data:" + o);
        }
    }
}
```

But for now we can handle this in own case:

```java
public static void nullHandlingWithSwitch(Object o) {
    switch (o) {
        case null -> System.out.println("Null as input");
        case String s -> System.out.println("String: " + s);
        case Integer i -> System.out.println("Integer: " + i);
        default -> System.out.println("Other data:" + o);
    }
}
```

# Guarded Pattern

Besides incorporating conditional logic within the case labels, using parenthesized patterns allows for grouping them together. We can streamline our boolean expressions by employing parentheses for conducting additional checks.

```java
public static int getIntegerUsingGuardPattern(Object o) {
    return switch (o) {
        case Integer i && i > 1 -> i;
        case null, default -> 0;
    };
}
```

Also we can add more conditions, and group them in parentheses:

```java
public static int integerCheckUsingParenthesizedPatterns(Object o) {
    return switch (o) {
        case Integer i && (0 < i && i < 100) -> i;
        case null, default -> 0;
    };
}
```

# Cover All Values

Here's an example demonstrating how the Java compiler checks type coverage when using pattern matching in a switch statement:

```java
public static void coveringNotAllStates(Object obj) {
    switch (obj) {
        case String s -> System.out.println("String: " + s);
    }
}
```

In this example, the switch statement accepts any object (Object obj), but it only covers the case where the object is a String. If the object is not a String, there are no cases to handle it.

When you compile this code, the Java compiler will issue a warning because the switch statement does not cover all possible types of the input object. This helps catch potential bugs at compile time, ensuring that all possible cases are handled.

One of possible ways to fix this:

```java
public static void coveringAllStates(Object obj) {
    switch (obj) {
        case String s -> System.out.println("String: " + s);
        case Integer i -> System.out.println("Integer: " + i);
        case Long l -> System.out.println("Long: " + l);
        case Double d -> System.out.println("Double: " + d);
        default -> System.out.println("Unknown type");
    }
}
```

In this corrected example, the switch statement covers all possible types of the input object obj. It includes cases for String, Integer, Long, and Double. Additionally, there's a default case to handle any other types that are not explicitly covered. This ensures proper type coverage and avoids compilation warnings.

# Order subclasses correctly

Illustration the importance of ordering subclasses correctly in a switch statement with pattern matching:

```java
public static void orderingSubclasses(Object obj) {
    switch (obj) {
        case CharSequence cs -> System.out.println("CharSequence: " + cs);
        case String s -> System.out.println("String: " + s);
        default -> System.out.println("Unknown type");
    }
}
```

In this example, we have a switch statement that handles objects of type Object. We attempt to match the input object against two cases: CharSequence and String. However, String is a subclass of CharSequence.

When we run this code, it will print "CharSequence: Hello". Despite the fact that the input object "Hello" is a String, it is matched by the CharSequence case because it appears first in the switch statement.

Thus, it's crucial to order the cases correctly when dealing with subclasses in a switch statement with pattern matching to ensure that the most specific cases are matched.

# Conclusion

In Java, the introduction of pattern matching for switch statements brings a powerful tool for handling complex conditional logic and type checking. This feature allows developers to write more concise and readable code by combining type checking and extraction into a single operation.

One key aspect to consider when using pattern matching in switch statements is the importance of ordering cases correctly, especially when dealing with subclasses. The order of cases determines the priority of matching, and placing more specific cases before general ones ensures that the most appropriate case is selected.

Additionally, pattern matching in switch statements helps improve code safety and maintainability by providing compile-time checks for exhaustiveness and type coverage. This helps catch potential bugs early in the development process and ensures that all possible cases are handled appropriately.

Overall, pattern matching in switch statements enhances the expressiveness and robustness of Java code, making it a valuable addition to the language for developers working with complex conditional logic and type hierarchies.

You can find examples at [Github](https://github.com/alxkm/articles/tree/master/src/main/java/org/alx/article/_36_new_switch_case_java).