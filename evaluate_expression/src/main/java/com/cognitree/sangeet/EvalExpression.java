package com.cognitree.sangeet;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

public class EvalExpression {
    private final Stack<String> opsBrackets; // to Store brackets and operators
    private final Stack<Double> number; // to Store the value from the string and calculated Values
    private final OperatorFactory operatorFactory; // to simulate Operator functions

    public EvalExpression() {
        this.opsBrackets = new Stack<>();
        this.number = new Stack<>();
        this.operatorFactory = new OperatorFactory();
    }

    public Double evaluateExpression(ExpressionParser expressionParser, Expression exp) {
        if (!expressionParser.expressionReady()) {
            return null;
        }

        String[] expression = exp.getFinalExpression();

        for (String evalChar : expression) {
            // Opening bracket simply get pushed
            if (evalChar.equals("(")) {
                this.opsBrackets.push(evalChar);
            }
            // If it's a closing bracket, evaluate the expression between this
            // and the last opening bracket
            else if (evalChar.equals(")")) {
                while (!this.opsBrackets.peek().equals("(")) {
                    if (!extractNumber()) {
                        return null;
                    }
                }

                this.opsBrackets.pop();
            }
            // Stores the number given to be used later
            else if (ExpressionParserUtil.checkInteger(evalChar)) {
                this.number.push(Double.parseDouble(evalChar));
            }
            // Operator and Brackets goes to same stack so as to
            // ease the process of BODMAS calculation.
            else if (!evalChar.equals(" ") && OperatorFactory.isOperator(evalChar)) {
                while (!opsBrackets.isEmpty() && hasPriority(evalChar, opsBrackets.peek())) {
                    if (!extractNumber()) return null;
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
        double firstNumber;
        double secondNumber;
        String operation;

        try {
            firstNumber = this.number.pop();
            secondNumber = this.number.pop();
            operation = this.opsBrackets.pop();
        } catch (EmptyStackException e) {
            System.out.println("Expression not verified properly");
            return false;
        }

        Operator operator = operatorFactory.getOperator(operation);

        if (operator != null) {
            Double[] newNumericalValue = operator.evaluate(new double[]{firstNumber, secondNumber});

            if (newNumericalValue == null) {
                return false;
            }

            this.number.addAll(Arrays.asList(newNumericalValue));
            return true;
        }

        return false;
    }

    // Helper function for checking the BODMAS rule.
    private boolean hasPriority(String currentOperation, String initialOperation) {
        if (initialOperation.equals("(") || initialOperation.equals(")") || currentOperation.equals("~")) {
            return false;
        }
        return (!currentOperation.equals("*") && !currentOperation.equals("/"))
                || (!(initialOperation.equals("+")) && (initialOperation.equals("-")));
    }
}
