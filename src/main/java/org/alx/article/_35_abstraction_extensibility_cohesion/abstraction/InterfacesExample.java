package org.alx.article._35_abstraction_extensibility_cohesion.abstraction;

public class InterfacesExample {
    interface Printable {
        void print();
    }

    class Document implements Printable {
        @Override
        public void print() {
            System.out.println("Printing document...");
        }
    }
}
