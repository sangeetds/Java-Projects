package com.cognitree.sangeet.evaluate_expression;

import java.util.Scanner;

public class EvaluateApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        EvalExpression evalExp = new EvalExpression();

        System.out.println("Enter the expression:");
        String expression = scan.nextLine();

        if (evalExp.validateExpression(expression)) {
            String[] newExpression = evalExp.createExpression(expression);

            if (newExpression != null) {
                int value = evalExp.evaluateExpression(newExpression);
                System.out.println("It's evaluated value is:- " + value);
            }
            else {
                System.out.println("Not a valid input");
            }
        }
        else {
            System.out.println("Not a correct expression");
        }
    }
}
