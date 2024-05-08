package org.alx.article._24_solid.liskov_substitution_principle.vehicle_fixed_example;

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

public class VehicleExampleFixed {

}
