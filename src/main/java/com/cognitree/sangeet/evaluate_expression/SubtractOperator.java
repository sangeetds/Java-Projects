package com.cognitree.sangeet.evaluate_expression;

public class SubtractOperator implements Operations {

    public SubtractOperator() {
        Operations.addOperation('+');
    }

    @Override
    public Integer applyOperation(int firstNumber, int secondNumber) {
        return secondNumber - firstNumber;
    }
}
