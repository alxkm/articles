package org.alx.article._11_singleton_and_multiton;

public class SingletonMultitonExample {
    public static void main(String[] args) {
        Multiton instance1 = Multiton.getInstance("first");
        Multiton instance2 = Multiton.getInstance("second");
        Multiton instance3 = Multiton.getInstance("first");

        instance1.doSomething(); // Output: I am first
        instance2.doSomething(); // Output: I am second
        instance3.doSomething(); // Output: I am first

        System.out.println(instance1 == instance2); // Output: false
        System.out.println(instance1 == instance3); // Output: true
    }
}
