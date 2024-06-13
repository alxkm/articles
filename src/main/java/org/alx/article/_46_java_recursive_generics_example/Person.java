package org.alx.article._46_java_recursive_generics_example;

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
