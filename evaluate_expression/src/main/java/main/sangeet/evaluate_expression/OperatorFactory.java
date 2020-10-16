package main.sangeet.evaluate_expression;

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
            } else if (op.equalsIgnoreCase("~")) {
                return new NegateOperator();
            }
        }

        return null;
    }

    static HashSet<Character> operators = Stream.of('+', '-', '*', '/', '~').collect(Collectors.toCollection(HashSet::new));

    // Static function to provide validation of operators.
    static boolean isOperator(char letter) {
        return operators.contains(letter);
    }

    static boolean isOperator(char letter, int index, char[] arrayExpression) {
        return letter == '-' &&
                index < arrayExpression.length - 1 &&
                index > 0 &&
                isOperator(arrayExpression[index - 1]) &&
                ExpressionParserUtil.checkInteger(String.valueOf(arrayExpression[index + 1]));
    }

    static boolean isOperator(String letter) {
        return letter.length() <= 1 && operators.contains(letter.charAt(0));
    }
}