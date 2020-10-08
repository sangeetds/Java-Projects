package com.cognitree.sangeet.evaluate_expression;

import java.util.Scanner;

public class EvaluateApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        EvalExpression evalExp = new EvalExpression();
        ParseExpression parsedExp = new ParseExpression();

        System.out.println("Enter the expression:");
        String expression = scan.nextLine();

        if (parsedExp.validateExpression(expression)) {
            System.out.println("Your expression is:- " + expression);

            calculateValue(evalExp, parsedExp, expression);

            while (true) {
                if (parsedExp.valuesReady() && parsedExp.expressionReady()) {
                    System.out.println("Do you want to change any variables value?: (Enter Y for yes)");
                    String change = scan.nextLine();

                    if (!change.toLowerCase().equals("y")) {
                        break;
                    }

                    while (true) {
                        System.out.println("Enter the variable to be changed or enter 'confirm' to confirm changes");
                        String newInput = scan.nextLine();

                        if (parsedExp.containsVariable(newInput)) {
                            System.out.println("Enter the value:");
                            String newValue = scan.nextLine();

                            parsedExp.changeVariable(newInput, newValue);
                        }
                        else {
                            if (!newInput.toLowerCase().equals("confirm")) {
                                System.out.println("Variable Doesn't exists. Showing current value");
                            }

                            calculateValue(evalExp, parsedExp, expression);
                            break;
                        }
                    }
                }
                else break;
            }
        } else {
            System.out.println("Not a valid expression");
        }
    }

    private static void calculateValue(EvalExpression evalExp, ParseExpression parseExp, String expression) {
        if (!parseExp.expressionReady()) {
            System.out.println("Error: Expression has not been validated");
            return;
        }

        if (parseExp.createExpression(expression)) {
            Integer value = evalExp.evaluateExpression(parseExp);

            if (value == null) {
                System.out.println("Error: Expression has not been validated");
                return;
            }

            System.out.println("It's evaluated value is:- " + value);
        } else {
            System.out.println("Not a valid input");
        }
    }
}
