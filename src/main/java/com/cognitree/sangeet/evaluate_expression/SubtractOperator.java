package com.cognitree.sangeet.evaluate_expression;

public class SubtractOperator implements Operator {

    @Override
    public Double evaluate(double firstNumber, double secondNumber) {
        return secondNumber - firstNumber;
    }
}
