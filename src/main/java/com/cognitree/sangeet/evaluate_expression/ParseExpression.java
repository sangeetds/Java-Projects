package com.cognitree.sangeet.evaluate_expression;

import java.util.*;

public class ParseExpression {
    private final HashMap<String, String> variableMap;
    private boolean validFlag;
    private boolean expressionFlag;
    private String[] expression;

    public ParseExpression() {
        this.variableMap = new HashMap<>();
        this.validFlag = false;
        this.expressionFlag = false;
    }

    // Checks the content first and then goes for bracket testing and then
    // saves the expression. The thinking behind saving expression was
    // not let user mix a different expression with a different validated
    // expression
    public boolean validateExpression(String expression) {
        for (char letter: expression.toCharArray()) {
            if (Character.isLetterOrDigit(letter) || isBlankOrBracket(letter) || letter == '_') {
                continue;
            }
            else if (!Operations.isOperator(letter)) {
                System.out.println(letter + " shouldn't be here and is causing problems.");
                return false;
            }
        }

        if (validateBrackets(expression)) {
            setExpressionValid();
            return true;
        }

        System.out.println("Brackets are not Valid");
        return false;
    }

    private void setExpressionValid() {
        this.expressionFlag = true;
    }

    // For validating the brackets
    private boolean validateBrackets(String expression) {
        Stack<Character> brackets = new Stack<>();

        for (char letter: expression.toCharArray()) {
            if (letter == '(') {
                brackets.push(letter);
            }
            if (letter == ')') {
                if (brackets.isEmpty()) {
                    return false;
                }

                brackets.pop();
            }
        }

        return brackets.isEmpty();
    }

    private boolean isBlankOrBracket(char letter) {
        return (letter == ' ') || (letter == '(') || (letter == ')');
    }

    // It creates a new array, where it changes the variables to number
    public boolean createExpression(String expression) {
        Scanner scan = new Scanner(System.in);
        char[] tempExpression = expression.toCharArray();
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

                String tempStr = tempStrBuilder.toString();

                String value = variableMap.get(tempStr);

                if (value == null) {
                    System.out.println("Enter the value for variable " + tempStrBuilder.toString() + ":-");
                    value = scan.nextLine();

                    if (!ParseExpressionUtil.checkInteger(value)) {
                        return false;
                    }
                }

                variableMap.put(tempStr, value);

                numericalExpression.add(value);
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
            else if (isBlankOrBracket(currCh) || Operations.isOperator(currCh)) {
                numericalExpression.add(String.valueOf(currCh));
            }

            index++;
        }

        System.out.println("Your expression with values is:- " + String.join("", numericalExpression));

        setValuesReady();
        setExpression(numericalExpression);
        return true;
    }

    public String[] getExpression() {
        return this.expression;
    }

    private void setExpression(List<String> numericalExpression) {
        this.expression = numericalExpression.toArray(new String[0]);
    }

    private void setValuesReady() {
        this.validFlag = true;
    }

    public boolean containsVariable(String newInput) {
        return variableMap.containsKey(newInput);
    }

    public boolean valuesReady() {
        return validFlag;
    }

    public boolean expressionReady() {
        return expressionFlag;
    }

    public void changeVariable(String newInput, String newValue) {
        variableMap.put(newInput, newValue);
    }
}
