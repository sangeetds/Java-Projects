package com.cognitree.sangeet.evaluate_expression;

import java.util.*;

public class EvalExpression {
    private final Stack<String> opsBrackets; // to Store brackets and operators
    private final Stack<Integer> number; // to Store the value from the string and calculated Values
    private final OperatorFactory operatorFactory; // to simulate Operator functions

    public EvalExpression() {
        this.opsBrackets = new Stack<>();
        this.number = new Stack<>();
        this.operatorFactory = new OperatorFactory();
    }

    public Integer evaluateExpression(ParseExpression parseExpression) {
        if (!parseExpression.expressionReady()) {
            return null;
        }

        String[] expression = parseExpression.getExpression();

        for (String evalChar: expression) {
            // Opening bracket simply get pushed
            if (evalChar.equals("(")) {
                this.opsBrackets.push(evalChar);
            }
            // If it's a closing bracket, evaluate the expression between this
            // and the last opening bracket
            else if (evalChar.equals(")")) {
                while (!this.opsBrackets.peek().equals("(")) {
                    if (!extractNumber()) break;
                }

                this.opsBrackets.pop();
            }
            // Stores the number given to be used later
            else if (ParseExpressionUtil.checkInteger(evalChar)) {
                this.number.push(Integer.parseInt(evalChar));
            }
            // Operator and Brackets goes to same stack so as to
            // ease the process of BODMAS calculation.
            else if (!evalChar.equals(" ") && Operations.isOperator(evalChar)) {
                while (!opsBrackets.isEmpty() && hasPriority(evalChar, opsBrackets.peek())) {
                    if (extractNumber()) break;
                }

                opsBrackets.push(evalChar);
            }
        }

        while (!opsBrackets.isEmpty()) {
            extractNumber();
        }

        if (number.isEmpty()) {
            return null;
        }

        return number.pop();
    }

    // Helper function which gets the last two number and the operation to be done
    // on them.
    private boolean extractNumber() {
        int firstNumber = this.number.pop();
        int secondNumber = this.number.pop();
        String operation = this.opsBrackets.pop();
        Operations operator = operatorFactory.getOperator(operation);

        if (operator == null) {
            return false;
        }
        else {
            Integer newNumericalValue = operator.applyOperation(firstNumber, secondNumber);

            if (newNumericalValue == null) {
                return false;
            }

            this.number.push(newNumericalValue);
            return true;
        }
    }

    // Helper function for checking the BODMAS rule.
    private boolean hasPriority(String currentOperation, String initialOperation) {
        if (initialOperation.equals("(") || initialOperation.equals(")")) {
            return false;
        }
        return (!currentOperation.equals("*") && !currentOperation.equals("/"))
                || (!(initialOperation.equals("+")) && (initialOperation.equals("-")));
    }
}
