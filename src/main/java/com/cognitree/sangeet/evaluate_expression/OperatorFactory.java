package com.cognitree.sangeet.evaluate_expression;

public class OperatorFactory {
    public Operations getOperator(String op) {
        if (Operations.isOperator(op)) {
            if (op.equalsIgnoreCase("+")) {
                return new PlusOperator();
            }
            else if (op.equalsIgnoreCase("-")) {
                return new SubtractOperator();
            }
            else if (op.equalsIgnoreCase("*")) {
                return new MultiplyOperator();
            }
            else if (op.equalsIgnoreCase("/")) {
                return new DivisionOperator();
            }
        }

        return null;
    }
}