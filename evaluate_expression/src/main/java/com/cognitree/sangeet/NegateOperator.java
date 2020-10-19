package com.cognitree.sangeet;

public class NegateOperator extends Operator{
    @Override
    public Double[] evaluate(double[] operands) {
        return new Double[]{ -operands[0], operands[1] };
    }
}
