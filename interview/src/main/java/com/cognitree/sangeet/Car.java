package com.cognitree.sangeet;

// Class to hold the properties of the given car
public class Car {
    String name;
    String origin;
    Double horsePower;

    public Car(String name, Double horsePower, String origin) {
        this.name = name;
        this.horsePower = horsePower;
        this.origin = origin;
    }

    @Override
    public String toString() {
        return name + "," + horsePower.toString() + "," + origin;
    }
}
