package com.cognitree.sangeet.evaluate_expression;

import java.util.Stack;

public class EvalExpression {
    Stack<Character> opsBrackets; // to Store brackets and operators
    Stack<Integer> number; // to Store the value from the string and calculated Values

    public EvalExpression() {
        this.opsBrackets = new Stack<>();
        this.number = new Stack<>();
    }

    public int evaluateExpression(String expression) {
        int index = 0;

        while (index < expression.length()) {
            char ch = expression.charAt(index);

            // Opening bracket simply get pushed
            if (ch == '(') {
                this.opsBrackets.push(ch);
            }
            // If it's a closing bracket, evaluate the expression between this
            // and the last opening bracket
            else if (ch == ')') {
                while (this.opsBrackets.peek() != '(') {
                    extractNumber();
                }

                this.opsBrackets.pop();
            }
            // Stores the number given to be used later
            else if (Character.isDigit(ch)) {
                StringBuilder numericalValue = new StringBuilder();

                while (index < expression.length() &&
                        Character.isDigit(expression.charAt(index)))
                {
                    numericalValue.append(expression.charAt(index));
                    index++;
                }

                this.number.add(Integer.parseInt(numericalValue.toString()));
                index--;
            }
            // Operator and Brackets goes to same stack so as to
            // ease the process of BODMAS calculation.
            else if (isOperator(ch)) {
                while (!opsBrackets.isEmpty() && hasPriority(ch, opsBrackets.peek())) {
                    extractNumber();
                }

                opsBrackets.push(ch);
            }
            // The only other character that it accepted right now is a space.
            else if (ch != ' '){
                System.out.println("Input contains bad element:-" + ch + ". Ignoring");
            }

            index++;
        }

        while (!opsBrackets.isEmpty()) {
            extractNumber();
        }

        return number.pop();
    }

    // Helper function which gets the last two number and the operation to be done
    // on them.
    private void extractNumber() {
        int firstNumber = this.number.pop();
        int secondNumber = this.number.pop();
        int operator = this.opsBrackets.pop();

        int newNumericalValue = applyOperation(operator, firstNumber, secondNumber);

        this.number.push(newNumericalValue);
    }

    // Helper function for checking the BODMAS rule.
    private boolean hasPriority(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') || ((op2 != '+') && (op2 != '-'));
    }

    // Simple operation helper function
    private int applyOperation(int operator, int firstNumber, int secondNumber) {
        switch (operator) {
            case '*':
                return firstNumber * secondNumber;
            case '+':
                return firstNumber + secondNumber;
            case '-':
                return secondNumber - firstNumber;
            case '/':
                if (firstNumber == 0) {
                    throw new UnsupportedOperationException("Cannot Divide by zero");
                }
                return secondNumber / firstNumber;
            default:
                return 0;
        }
    }

    // Helper function to check whether the character is an operator or not
    private boolean isOperator(char ch) {
        return ch == '*' || ch == '/' || ch == '+' || ch == '-';
    }
}
