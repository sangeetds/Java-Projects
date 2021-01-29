package com.cognitree.sangeet.operator;

public class NegateOperator extends Operator{
    @Override
    public Double[] evaluate(double[] operands) {
        return new Double[]{ -operands[0], operands[1] };
    }
}
