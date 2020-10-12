package com.cognitree.sangeet.evaluate_expression;

public class DivisionOperator implements Operator {

    @Override
    public Double evaluate(double firstNumber, double secondNumber) {
        if (firstNumber == 0) {
            System.out.println("Don't carry division by 0");
            return null;
        }

        return firstNumber / secondNumber;
    }
}
