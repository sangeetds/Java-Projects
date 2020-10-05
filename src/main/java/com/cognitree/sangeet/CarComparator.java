package com.cognitree.sangeet;

import java.util.Comparator;

class CarComparator implements Comparator<Car> {
    public int compare(Car firstCar, Car secondCar){
        return secondCar.horsePower.compareTo(firstCar.horsePower);
    }
}