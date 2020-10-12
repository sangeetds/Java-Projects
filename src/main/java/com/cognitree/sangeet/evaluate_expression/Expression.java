package com.cognitree.sangeet.evaluate_expression;

import java.util.List;

public class Expression {
    private final String exp;
    private String[] expWithValues;

    public Expression(String expression) {
        this.exp = expression;
    }

    String getExpression() {
        return exp;
    }

    String[] getFinalExpression() {
        return expWithValues;
    }

    void setFinalExpression(List<String> numericalExpression) {
        expWithValues = numericalExpression.toArray(new String[0]);
    }
}
