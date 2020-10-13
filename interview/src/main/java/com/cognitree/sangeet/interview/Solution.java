package com.cognitree.sangeet.interview;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        CarsList carList = new CarsList(new ArrayList<>());

        // Reads file and only proceed when the input file name is
        // correct.
        if (readFile(scan, carList)) {
            carList.getCarsList(scan);
        }
    }

    private static boolean readFile(Scanner scan, CarsList carList) {
        System.out.println("Enter the proper file name:");
        String filename = scan.nextLine();

        // Error handling so that wrong file name does not creates an error.
        // Assumed that the file is in the same location as the java file
        // and the user is providing the full name of the file.
        // The zip file contains "cars_input1.txt" which is to be typed
        // whole in the propmt when asked.
        try (Stream<String> lines = Files.lines(Paths.get(filename), Charset.defaultCharset()).skip(1)) {
            lines.forEachOrdered(carList::process);
        } catch (IOException e) {
            System.out.println("Not a valid file name");
            return false;
        }

        return true;
    }
}
