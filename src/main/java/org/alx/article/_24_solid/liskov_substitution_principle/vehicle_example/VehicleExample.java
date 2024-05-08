package org.alx.article._24_solid.liskov_substitution_principle.vehicle_example;

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

public class VehicleExample {

}
