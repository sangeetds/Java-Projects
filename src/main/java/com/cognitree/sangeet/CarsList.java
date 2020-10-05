package com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;

// Class for list of Car and to handle operations
// on them
public class CarsList {
    ArrayList<Car> allCars;
    HashSet<String> originSet;

    public CarsList(ArrayList<Car> allCars) {
        this.allCars = allCars;
        this.originSet = new HashSet<>();
    }

    public void getCarsList(Scanner scan) {
        int numberOfCars;
        String origin;

        // Decided not to go with a loop that keeps on continuing
        // based on user's input
//        while (true) {
        System.out.println("Enter number of cars for which you want the details");
        numberOfCars = scan.nextInt();

        if (numberOfCars < 0) {
            System.out.println("Not a valid number:");
            return;
        }

        System.out.println("Enter origin:");
        origin = scan.next();

        if (!originSet.contains(origin)) {
            System.out.println("Origin not found.");
            return;
        }

        printCarList(numberOfCars, origin, allCars);

//            System.out.println("Please Enter 'Y' or 'Yes' to continue");
//            String loopOver = scan.next();
//
//            if (!(loopOver.equals("Y") || loopOver.equals("Yes"))) {
//                break;
//            }
//        }
    }

    // Final function to provide the list of Cars
    public void printCarList(int numberOfCars, String origin, ArrayList<Car> carList) {
        // Filters list according to Origin provided
        List<Car> originCars = carList
                .stream()
                .filter(car -> car.origin.equals(origin))
                .collect(Collectors.toList());

        // Gets the average.
        double originRelativeSum = originCars.stream().mapToDouble(car -> car.horsePower).sum();
        double originRelativeAverage = originRelativeSum / originCars.size();

        // Gets the final list to be displayed.
        List<Car> finalCarList = originCars
                .stream()
                .filter(car -> car.horsePower > originRelativeAverage)
                .sorted(new CarComparator())
                .limit(numberOfCars)
                .collect(Collectors.toList());

        finalCarList.forEach(System.out::println);
    }

    // Processes the text file. Takes the liberty of asssuming the file
    // has text splitted by ','(commas) and are arranged in 'CarName,
    // Horsepower, and Origin', one car per line
    public void process(String line) {
        String[] carDetail = line.split(",");

        if (carDetail.length < 3) {
            throw new Error("Details for cars are not proper.");
        }

        try {
            Car newCar = new Car(carDetail[0].trim(), parseDouble(carDetail[2].trim()), carDetail[1].trim());
            this.allCars.add(newCar);
            this.originSet.add(carDetail[1]);
        } catch (NumberFormatException e) {
            // System.out.println("Detail for " + carDetail[0] + " is not valid. Not processed");
            // System.out.print("");
            int c = 0;
        }
    }
}
