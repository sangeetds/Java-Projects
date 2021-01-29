package com.cognitree.sangeet.operator;

public class DivisionOperator extends Operator {

    @Override
    public Double[] evaluate(double[] operands) {
        if (operands[0] == 0) {
            System.out.println("Don't carry division by 0");
            return null;
        }

        return new Double[] { operands[1] / operands[0] };
    }
}
