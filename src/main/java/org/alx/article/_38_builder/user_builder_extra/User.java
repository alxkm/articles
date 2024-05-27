package org.alx.article._38_builder.user_builder_extra;

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
}