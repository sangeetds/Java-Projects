package com.cognitree.sangeet;

public class ExpressionParserUtil {
    public static boolean checkInteger(String evalChar) {
        try {
            Double.parseDouble(evalChar);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
