package com.cognitree.sangeet.evaluate_expression;

public class DivisionOperator implements Operations {

    public DivisionOperator() {
        Operations.addOperation('+');
    }

    @Override
    public Integer applyOperation(int firstNumber, int secondNumber) {
        if (firstNumber == 0) {
            System.out.println("Don't carry division by 0");
            return null;
        }

        return firstNumber / secondNumber;
    }
}
