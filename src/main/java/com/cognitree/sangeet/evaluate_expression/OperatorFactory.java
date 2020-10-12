package com.cognitree.sangeet.evaluate_expression;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperatorFactory {
    public Operator getOperator(String op) {
        if (isOperator(op)) {
            if (op.equalsIgnoreCase("+")) {
                return new PlusOperator();
            } else if (op.equalsIgnoreCase("-")) {
                return new SubtractOperator();
            } else if (op.equalsIgnoreCase("*")) {
                return new MultiplyOperator();
            } else if (op.equalsIgnoreCase("/")) {
                return new DivisionOperator();
            }
        }

        return null;
    }

    static HashSet<Character> operators = Stream.of('+', '-', '*', '/').collect(Collectors.toCollection(HashSet::new));

    // Static function to provide validation of operators.
    static boolean isOperator(char letter) {
        if (!operators.contains(letter)) {
            System.out.println("We do not support this " + letter + " operation yet");
        }

        return operators.contains(letter);
    }

    static boolean isOperator(String letter) {
        if (letter.length() > 1) return false;

        if (!operators.contains(letter.charAt(0))) {
            return false;
        }

        return true;
    }
}