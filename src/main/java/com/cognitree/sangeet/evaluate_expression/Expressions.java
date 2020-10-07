package com.cognitree.sangeet.evaluate_expression;

import java.util.Stack;

public class Expressions {
    int value;
    Stack<Character> opsBrackets;
    Stack<Integer> number;

    public Expressions() {
        this.value = 0;
        this.opsBrackets = new Stack<>();
        this.number = new Stack<>();
    }

    public int evaluateExpression(String expression) {
        int index = 0;

        while (index < expression.length()) {
            char ch = expression.charAt(index);

            if (ch == '(') {
                this.opsBrackets.push(ch);
            }
            else if (ch == ')') {
                while (this.opsBrackets.peek() != '(') {
                    extractNumber();
                }

                this.opsBrackets.pop();
            }
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
            else if (isOperand(ch)) {
                while (!opsBrackets.isEmpty() && hasPriority(ch, opsBrackets.peek())) {
                    extractNumber();
                }

                opsBrackets.push(ch);
            }
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

    private void extractNumber() {
        int firstNumber = this.number.pop();
        int secondNumber = this.number.pop();
        int operator = this.opsBrackets.pop();

        int newNumericalValue = applyOperation(operator, firstNumber, secondNumber);

        this.number.push(newNumericalValue);
    }

    private boolean hasPriority(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') || ((op2 != '+') && (op2 != '-'));
    }

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

    private boolean isOperand(char ch) {
        return ch == '*' || ch == '/' || ch == '+' || ch == '-';
    }
}
