package com.cognitree.sangeet;

import com.cognitree.sangeet.operator.Operator;
import com.cognitree.sangeet.operator.OperatorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

final public class ExpressionParser {
    private final HashMap<String, String> variableMap;
    private boolean isValueSet;
    private final boolean isExpSet;

    public ExpressionParser(Expression expression) throws Exception {
        if (!this.validateExpression(expression.getExpression())) {
            throw IllegalExpressionException();
        }

        this.variableMap = new HashMap<>();
        Operator op = new Operator() {
            @Override
            public Double[] evaluate(double[] operands) {
                return new Double[0];
            }
        };
        this.isValueSet = false;
        this.isExpSet = true;
    }

    private Exception IllegalExpressionException() {
        return new Exception("Not a valid Expression");
    }

    // Checks the content first and then goes for bracket testing and then
    // saves the expression. The thinking behind saving expression was
    // not let user mix a different expression with a different validated
    // expression
    private boolean validateExpression(String expression) {
        for (char letter : expression.replace(" ", "").toCharArray()) {
            if (!(Character.isLetterOrDigit(letter) || (letter == '(') || (letter == ')') || letter == '_' || OperatorFactory.isOperator(letter))) {
                System.out.println("'" + letter + "'" + " shouldn't be here and is causing problems.");
                return false;
            }
        }

        return validateBrackets(expression);
    }

    // For validating the brackets
    private boolean validateBrackets(String expression) {
        Stack<Character> brackets = new Stack<>();

        for (char letter : expression.toCharArray()) {
            if (letter == '(') {
                brackets.push(letter);
            }
            if (letter == ')') {
                if (brackets.isEmpty()) {
                    System.out.println("Brackets are not properly matched");
                    return false;
                }

                brackets.pop();
            }
        }

        return brackets.isEmpty();
    }

    // It creates a new array, where it changes the variables to number
    public List<String> createExpression(Expression expression) {
        char[] tempExpression = expression.getExpression().replace(" ", "").toCharArray();
        List<String> numericalExpression = new ArrayList<>();
        int index = 0;

        // Goes over the given string.
        while (index < tempExpression.length) {
            char currCh = tempExpression[index];

            // If it finds letter, it extracts the variable.
            if (Character.isLetter(currCh)) {
                StringBuilder tempStrBuilder = new StringBuilder();

                while (index < tempExpression.length &&
                        (Character.isLetterOrDigit(tempExpression[index]) || tempExpression[index] == '_')) {
                    tempStrBuilder.append(tempExpression[index]);
                    index++;
                }

                numericalExpression.add(tempStrBuilder.toString());
                index--;
            }
            // Extract out the number
            else if (Character.isDigit(currCh)) {
                StringBuilder tempStrBuilder = new StringBuilder();

                while (index < tempExpression.length &&
                        Character.isDigit(tempExpression[index])) {
                    tempStrBuilder.append(tempExpression[index]);
                    index++;
                }

                numericalExpression.add(tempStrBuilder.toString());
                index--;
            }
            // Extracts the bracket
            else if ((currCh == '(') || (currCh == ')')) {
                numericalExpression.add(String.valueOf(currCh));
            }
            else if (OperatorFactory.isOperator(currCh, index, tempExpression)) {
                numericalExpression.add("~");
            }
            else if (OperatorFactory.isOperator(currCh)) {
                numericalExpression.add(String.valueOf(currCh));
            }

            index++;
        }

        return numericalExpression;
    }

    public void setExpression(Expression expression, List<String> numericalExpression) {
        expression.setFinalExpression(numericalExpression);
    }

    public void setValuesReady() {
        this.isValueSet = true;
    }

    public boolean containsVariable(String newInput) {
        return variableMap.containsKey(newInput);
    }

    public boolean valuesReady() {
        return isValueSet;
    }

    public boolean expressionReady() {
        return isExpSet;
    }

    public void setVariable(String input, String value) {
        variableMap.put(input, value);
    }

    public String getVariable(String variable) {
        return variableMap.get(variable);
    }
}
