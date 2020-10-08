package com.cognitree.sangeet.evaluate_expression;


import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Operations {
    HashSet<Character> operators = Stream.of('+', '-', '*', '/').collect(Collectors.toCollection(HashSet::new));

    static boolean isOperator(char letter) {
        if (!operators.contains(letter)) {
            System.out.println("We do not support this " + letter + " operation yet");
        }

        return operators.contains(letter);
    }

    static boolean isOperator(String letter) {
        if (letter.length() > 1) return false;

        if (!operators.contains(letter.charAt(0))) {
            System.out.println("We do not support this " + letter + " operation yet");
        }

        char charLetter = letter.charAt(0);

        return operators.contains(charLetter);
    }

    // Simple operation helper function
    Integer applyOperation(int firstNumber, int secondNumber);

    static void addOperation(char newOperation) {
        operators.add(newOperation);
    }
}
