package org.alx.article._35_abstraction_extensibility_cohesion.extensibility;

public class InterfacesExample {
    interface Printable {
        void print();
    }

    class Document implements Printable {
        public void print() {
            // Implementation for printing Document
        }
    }

    class Report implements Printable {
        public void print() {
            // Implementation for printing Report
        }
    }
}
