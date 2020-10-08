package com.cognitree.sangeet.evaluate_expression;

public class MultiplyOperator implements Operations {

    public MultiplyOperator() {
        Operations.addOperation('+');
    }

    @Override
    public Integer applyOperation(int firstNumber, int secondNumber) {
        return firstNumber * secondNumber;
    }
}
