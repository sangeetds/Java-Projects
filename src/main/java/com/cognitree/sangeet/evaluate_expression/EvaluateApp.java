package com.cognitree.sangeet.evaluate_expression;

import java.util.Scanner;

public class EvaluateApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Expressions evalExp = new Expressions();

        System.out.println("Enter the expression:");
        String expression = scan.nextLine();

        int value = evalExp.evaluateExpression(expression);
        System.out.println(value);
    }
}
