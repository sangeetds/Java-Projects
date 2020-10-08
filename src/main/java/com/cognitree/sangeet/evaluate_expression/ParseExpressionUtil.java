package com.cognitree.sangeet.evaluate_expression;

public class ParseExpressionUtil {
    public static boolean checkInteger(String evalChar) {
        try {
            Integer.parseInt(evalChar);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
