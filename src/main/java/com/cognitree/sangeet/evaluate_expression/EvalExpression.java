package com.cognitree.sangeet.evaluate_expression;

import java.util.*;

public class EvalExpression {
    Stack<String> opsBrackets; // to Store brackets and operators
    Stack<Integer> number; // to Store the value from the string and calculated Values

    public EvalExpression() {
        this.opsBrackets = new Stack<>();
        this.number = new Stack<>();
    }

    public int evaluateExpression(String[] expression) {

        for (String evalChar: expression) {
            // Opening bracket simply get pushed
            if (evalChar.equals("(")) {
                this.opsBrackets.push(evalChar);
            }
            // If it's a closing bracket, evaluate the expression between this
            // and the last opening bracket
            else if (evalChar.equals(")")) {
                while (!this.opsBrackets.peek().equals("(")) {
                    extractNumber();
                }

                this.opsBrackets.pop();
            }
            // Stores the number given to be used later
            else if (checkInteger(evalChar)) {
                this.number.push(Integer.parseInt(evalChar));
            }
            // Operator and Brackets goes to same stack so as to
            // ease the process of BODMAS calculation.
            else if (isOperator(evalChar)) {
                while (!opsBrackets.isEmpty() && hasPriority(evalChar, opsBrackets.peek())) {
                    extractNumber();
                }

                opsBrackets.push(evalChar);
            }
            // The only other character that it accepted right now is a space.
//            else if () {
//                System.out.println("Input contains bad element:-" + evalChar + ". Ignoring");
//            }
        }

        while (!opsBrackets.isEmpty()) {
            extractNumber();
        }

        return number.pop();
    }

    private boolean checkInteger(String evalChar) {
        try {
            Integer.parseInt(evalChar);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Helper function which gets the last two number and the operation to be done
    // on them.
    private void extractNumber() {
        int firstNumber = this.number.pop();
        int secondNumber = this.number.pop();
        String operator = this.opsBrackets.pop();

        int newNumericalValue = applyOperation(operator, firstNumber, secondNumber);

        this.number.push(newNumericalValue);
    }

    // Helper function for checking the BODMAS rule.
    private boolean hasPriority(String currentOperation, String initialOperation) {
        if (initialOperation.equals("(") || initialOperation.equals(")")) {
            return false;
        }
        return (!currentOperation.equals("*") && !currentOperation.equals("/"))
                || (!(initialOperation.equals("+")) && (initialOperation.equals("-")));
    }

    // Simple operation helper function
    private int applyOperation(String operator, int firstNumber, int secondNumber) {
        switch (operator) {
            case "*":
                return firstNumber * secondNumber;
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return secondNumber - firstNumber;
            case "/":
                if (firstNumber == 0) {
                    throw new UnsupportedOperationException("Cannot Divide by zero");
                }
                return secondNumber / firstNumber;
            default:
                return 0;
        }
    }

    // Helper function to check whether the character is an operator or not
    private boolean isOperator(String ch) {
        return ch.equals("*") || ch.equals("/") || ch.equals("+") || ch.equals("-");
    }

    public boolean validateExpression(String expression) {
        for (char letter: expression.toCharArray()) {
            if (!(Character.isLetterOrDigit(letter) || isBlankorBracket(letter) || isOperator(String.valueOf(letter)))) {
                return false;
            }
        }

        return validateBrackets(expression);
    }

    private boolean validateBrackets(String expression) {
        Stack<Character> brackets = new Stack<>();

        for (char letter: expression.toCharArray()) {
            if (letter == '(') {
                brackets.push(letter);
            }
            if (letter == ')') {
                if (brackets.isEmpty()) {
                    return false;
                }

                brackets.pop();
            }
        }

        return brackets.isEmpty();
    }

    private boolean isBlankorBracket(char letter) {
        return (letter == ' ') || (letter == '(') || (letter == ')');
    }

    public String[] createExpression(String expression) {
        Scanner scan = new Scanner(System.in);
        String[] numericalExpression = expression.split("");

        for (int i = 0; i < numericalExpression.length; i++) {
            char isLetter = numericalExpression[i].charAt(0);

            if (Character.isLetter(isLetter)) {
                System.out.println("Please provide the value for: " + isLetter);
                String value = scan.nextLine();

                if (checkInteger(value)) {
                    numericalExpression[i] = value;
                }
                else {
                    return null;
                }
            }
        }

        System.out.println("Your expression is:- " + String.join("", numericalExpression));

        return numericalExpression;
    }
}
