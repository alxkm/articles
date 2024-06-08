# Builder Pattern Variations and Best Practices

If you are not using Lombok for some reason, or you are interested Builder pattern in java, welcome here. This article could explore various variations and extensions of the Builder Pattern beyond the basic implementation. It could cover topics such as nested builders, step builders, telescoping builders, and the use of method chaining for fluent interfaces. It could also discuss best practices for designing builder classes and composing complex object hierarchies.

![image](source/title.jpeg)

The Builder Pattern is a creational design pattern that provides a flexible solution for constructing complex objects. It allows us to separate the construction of an object from its representation, thereby enabling the creation of different object configurations using the same construction process. While the basic implementation of the Builder Pattern is straightforward, there are several variations and best practices that developers should be aware of to maximize its effectiveness. In this article, we will explore various Builder Pattern variations and discuss best practices for their use in Java.

Let's describe such class User:

```java
public class User {
    private final String userId;
    private final String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
```

With only two fields currently, employing the builder remains advantageous to avoid parameter-order confusion in constructors and to facilitate initializing one or both fields separately at different intervals. Consider the complexities when dealing with a lot of fields. To prevent field duplication in the builder class, a nested class is introduced. This inner class can access its parent class's private fields directly and expose them. By making the inner class constructor private and removing the final modifier from the fields, we streamline the process.

```java
public class User {
    private String userId;
    private String name;

    private User() {
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public static Builder newBuilder() {
        return new User().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setUserId(String userId) {
            User.this.userId = userId;

            return this;
        }

        public Builder setName(String name) {
            User.this.name = name;

            return this;
        }

        public User build() {
            return User.this;
        }

    }

    public static void main(String[] args) {
        User user = User.newBuilder().setUserId("123").setName("John").build();
    }
}
```

The constructor of the builder is private to prevent the possibility of creating a builder and modifying the fields of an already existing object by accessing the Account instance. The build method merely returns a fully constructed object, allowing for validation of required fields before instantiation. The finishing step involves incorporating it into the method for creating a builder instance.

Consider adding a new field or modifying the token field type in both scenarios. As the number of fields grows, the contrast in code size and readability will become more pronounced. Let's contrast the example from the article I referenced earlier in this discussion (I've adjusted it to ensure the examples' styles match).

Let's add different fields and another variant to organize builder code:


```java
public class User {
    private String userId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String streetAddress;
    private String city;
    private String state;

    public User(String userId, String lastName, String firstName,
                String middleName, String streetAddress, String city, String state) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
    }

    public User() {
    }

    public User userId(String userId) {
        this.userId = userId;
        return this;
    }

    public User lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public User streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public User city(String city) {
        this.city = city;
        return this;
    }

    public User state(String state) {
        this.state = state;
        return this;
    }

    public User build() {
        return new User(userId, lastName, firstName, middleName, streetAddress, city, state);
    }
}
```

And creation such object using such pattern looks better than using standard way:

```java
public static void main(String[] args) {
        User user = new User()
                .userId("123")
                .lastName("Doe")
                .firstName("John")
                .middleName("Robert")
                .streetAddress("123 Main St")
                .city("Anytown")
                .state("NY")
                .build();
    }
```

Using of a builder pattern to address limitations when dealing with final fields and constructors in object creation.

The description suggests that due to the inability to use the final keyword with a builder, or to create constructors with a large number of parameters, the builder pattern offers a solution. It explains that the builder pattern allows for the creation of objects with mutable fields during the building process, but once the object is constructed, the fields become immutable.

In essence, the builder pattern provides flexibility during object construction by allowing for the setting of individual fields in a fluent manner, while still ensuring immutability once the object is fully constructed. This approach allows for cleaner, more maintainable code while adhering to principles of immutability where desired.

You can find some examples at [Github](https://github.com/alxkm/articles/tree/master/src/main/java/org/alx/article/_38_builder).