# Java Recursive Generics

![image](source/title.jpg)

Recursive generics, also known as self-referential generics, are a powerful feature in Java that allow classes and methods to reference themselves with a generic type parameter. This enables more flexible and expressive APIs, particularly in scenarios requiring method chaining and fluent interfaces. Here's a deeper look into why recursive generics are useful and when they are typically needed:

Here's a few practical examples of using recursive generics to implement a type-safe builder pattern for creating complex objects.


### Benefits of Recursive Generics

1. Fluent APIs and Method Chaining: Recursive generics are essential for creating fluent APIs, which are designed to be readable and intuitive by allowing method calls to be chained together. Each method returns this (or a subclass), enabling successive method calls on the same object.
2. Type Safety: Recursive generics ensure that method chaining maintains type safety. By returning the correct subclass type, they prevent errors where a superclass method is inadvertently returned, breaking the chain.
3. Inheritance and Covariance: When extending classes that use recursive generics, the subclass can maintain the fluent interface without losing type information. This is particularly useful in builders and other design patterns that require method chaining.
4. Enhanced Readability and Maintainability: Fluent interfaces and builders created with recursive generics make the code more readable and easier to maintain by clearly defining the sequence of operations and their dependencies.

# Use Cases for Recursive Generics

- Builder Pattern: The builder pattern often uses recursive generics to allow for a fluent API that can construct complex objects step by step.

```java
public class Builder<T extends Builder<T>> {
    private String field;
    
    public T setField(String field) {
        this.field = field;
        return self();
    }
    
    protected T self() {
        return (T) this;
    }
    
    public MyObject build() {
        return new MyObject(this);
    }
}

public class MyObject {
    private final String field;
    
    private MyObject(Builder<?> builder) {
        this.field = builder.field;
    }
}
```

- Fluent APIs: Libraries and frameworks often use recursive generics to provide a fluent API for configuring and using complex systems.

```java
public class FluentBuilder<T extends FluentBuilder<T>> {
    public T doSomething() {
        // Do something
        return self();
    }
    protected T self() {
        return (T) this;
    }
}
```

- Type-Safe Enumerations: Recursive generics can enforce type constraints in enumeration classes and other scenarios where the type hierarchy needs to be preserved.

# Type-Safe Builder Pattern with Recursive Generics

Imagine we are building a type-safe builder for a Person class, where we want to ensure that certain properties are set before the object can be created.

### Person Class

```java
public class Person {
    private final String firstName;
    private final String lastName;
    private final int age;

    private Person(Builder<?> builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
    }

    public static Builder<?> builder() {
        return new PersonBuilder();
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    public static class Builder<T extends Builder<T>> {
        private String firstName;
        private String lastName;
        private int age;

        public T firstName(String firstName) {
            this.firstName = firstName;
            return self();
        }

        public T lastName(String lastName) {
            this.lastName = lastName;
            return self();
        }

        public T age(int age) {
            this.age = age;
            return self();
        }

        protected T self() {
            return (T) this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    private static class PersonBuilder extends Builder<PersonBuilder> {
        @Override
        protected PersonBuilder self() {
            return this;
        }
    }
}
```


### Usage example:

```java
public class RecursiveGenericsExample {
    public static void main(String[] args) {
        Person person = Person.builder()
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .build();

        System.out.println(person);
    }
}
```

# What is going on:

### Person Class:

* The Person class has private fields firstName, lastName, and age, which are set through the builder.
* The constructor is private and only accessible through the Builder.

### Builder Class:

* The nested Builder class uses recursive generics to ensure type safety. The type parameter T extends Builder<T>, allowing methods to return the correct type for method chaining.
* Methods like firstName, lastName, and age, set the respective fields and return self(), which ensures the return type is the builder itself.
* The self method is crucial here as it returns the correct type (T), allowing fluent method calls.

### PersonBuilder Class:

* The PersonBuilder extends the Builder class with the concrete type PersonBuilder. It overrides the self method to return this, ensuring the builder methods return the correct type.

### Usage:

* The Person.builder() method returns a new instance of PersonBuilder.
* The builder methods are called in a fluent style, setting the desired properties.
* Finally, the build method is called to create a Person  object.

This pattern ensures that the builder is type-safe and enforces correct usage through recursive generics. You can extend this pattern further to enforce even more constraints, such as requiring certain fields to be set before building the object.

# Recursive Generics Query Builder

This is useful example, because it provides example of writing own DSL like syntax on Java. Such query builder will allow us to construct SQL-like queries in a type-safe manner using a fluent API.

We'll build a simple SQL-like query builder that supports SELECT, FROM, WHERE, and ORDER BY clauses. The builder will ensure that the order of the clauses is respected and provide type-safe method chaining.

### Query Builder Classes:

```java
public class QueryBuilder {
    
    public static SelectBuilder select(String... columns) {
        return new SelectBuilder(columns);
    }

    public static class SelectBuilder {
        private final String[] columns;

        private SelectBuilder(String[] columns) {
            this.columns = columns;
        }

        public FromBuilder from(String table) {
            return new FromBuilder(this, table);
        }

        public String[] getColumns() {
            return columns;
        }
    }

    public static class FromBuilder {
        private final SelectBuilder selectBuilder;
        private final String table;

        private FromBuilder(SelectBuilder selectBuilder, String table) {
            this.selectBuilder = selectBuilder;
            this.table = table;
        }

        public WhereBuilder where(String condition) {
            return new WhereBuilder(this, condition);
        }

        public OrderByBuilder orderBy(String... columns) {
            return new OrderByBuilder(this, columns);
        }

        public String getTable() {
            return table;
        }

        public SelectBuilder getSelectBuilder() {
            return selectBuilder;
        }
    }

    public static class WhereBuilder {
        private final FromBuilder fromBuilder;
        private final String condition;

        private WhereBuilder(FromBuilder fromBuilder, String condition) {
            this.fromBuilder = fromBuilder;
            this.condition = condition;
        }

        public OrderByBuilder orderBy(String... columns) {
            return new OrderByBuilder(fromBuilder, columns);
        }

        public String getCondition() {
            return condition;
        }

        public FromBuilder getFromBuilder() {
            return fromBuilder;
        }
    }

    public static class OrderByBuilder {
        private final FromBuilder fromBuilder;
        private final WhereBuilder whereBuilder;
        private final String[] columns;

        private OrderByBuilder(FromBuilder fromBuilder, String[] columns) {
            this.fromBuilder = fromBuilder;
            this.whereBuilder = null;
            this.columns = columns;
        }

        private OrderByBuilder(WhereBuilder whereBuilder, String[] columns) {
            this.fromBuilder = whereBuilder.getFromBuilder();
            this.whereBuilder = whereBuilder;
            this.columns = columns;
        }

        public String build() {
            StringBuilder query = new StringBuilder("SELECT ");

            String[] selectColumns = fromBuilder.getSelectBuilder().getColumns();
            query.append(String.join(", ", selectColumns));
            query.append(" FROM ").append(fromBuilder.getTable());

            if (whereBuilder != null) {
                query.append(" WHERE ").append(whereBuilder.getCondition());
            }

            if (columns.length > 0) {
                query.append(" ORDER BY ").append(String.join(", ", columns));
            }

            return query.toString();
        }
    }
}
```

### Usage example:
Here's how you can use this query builder to construct a SQL-like query:

```java
public class QueryBuilderExample {
    public static void main(String[] args) {
        String query = QueryBuilder.select("id", "name", "age")
                .from("users")
                .where("age > 18")
                .orderBy("name", "age")
                .build();

        System.out.println(query);
    }
}
```

### Let's take a look how it works:

### QueryBuilder Class:

* Provides a static method select to start building the query. This method returns a SelectBuilder.

### SelectBuilder Class:

* Initializes with the columns to select.
* Has a from method to specify the table, returning a FromBuilder.

### FromBuilder Class:

* Stores the table name and the previous SelectBuilder.
* Provides where and orderBy methods for further query building.
* The where method returns a WhereBuilder, while orderBy returns an OrderByBuilder.


### WhereBuilder Class:

* Stores the condition and the previous FromBuilder.
* Provides an orderBy method for adding ORDER BY clauses, returning an OrderByBuilder.


### OrderByBuilder Class:

* Stores the columns to order by and the previous builders (FromBuilder and optionally WhereBuilder).
* Provides a build method to construct the final query string.

This design ensures that the builder enforces the correct order of SQL clauses and provides a type-safe way to construct SQL-like queries.

# Conclusion

Recursive generics are a powerful tool in Java, enabling the creation of type-safe, fluent APIs and method chains. They are particularly useful in design patterns like the builder pattern, enhancing code readability, maintainability, and safety. By understanding and leveraging recursive generics, developers can create more expressive and robust APIs in their applications.
