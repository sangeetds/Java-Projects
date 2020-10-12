package com.cognitree.sangeet.evaluate_expression;

public class PlusOperator implements Operator {
    @Override
    public Double evaluate(double firstNumber, double secondNumber) {
        return firstNumber + secondNumber;
    }
}
