package com.cognitree.sangeet.evaluate_expression;

public class PlusOperator implements Operations {

    public PlusOperator() {
        Operations.addOperation('+');
    }

    @Override
    public Integer applyOperation(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }
}
