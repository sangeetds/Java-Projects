package com.cognitree.sangeet;

public class MultiplyOperator extends Operator {

    @Override
    public Double[] evaluate(double[] operands) {
        return new Double[]{ operands[1] * operands[0] };
    }
}
