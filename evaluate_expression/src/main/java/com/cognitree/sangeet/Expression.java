package com.cognitree.sangeet;

import java.util.List;

final public class Expression {
    private final String exp;
    private String[] expWithValues;

    public Expression(String expression) {
        this.exp = expression.replaceAll("\\s+", " ").trim();
    }

    public String getExpression() {
        return exp;
    }

    public String[] getFinalExpression() {
        return expWithValues;
    }

    public void setFinalExpression(List<String> numericalExpression) {
        expWithValues = numericalExpression.toArray(new String[0]);
    }
}
