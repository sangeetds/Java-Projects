package main.sangeet.evaluate_expression;

import java.util.List;
import java.util.Scanner;

public class EvaluateApp {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        EvalExpression evalExp = new EvalExpression();

        System.out.println("Enter the expression (Don't name any variable as 'confirm') :");
        String exp = scan.nextLine();

        Expression expression = new Expression(exp);
        ExpressionParser parsedExp = new ExpressionParser(expression);

        System.out.println("Your expression is:- " + expression.getExpression().trim());

        // Hands off the responsibility to ParseExpression which takes input
        if (!calculateValue(evalExp, parsedExp, expression, scan)) {
            return;
        }

        while (true) {
            // If validation and initial values have been taken, it asks the user
            // to change values or not
            if (parsedExp.valuesReady() && parsedExp.expressionReady()) {
                System.out.println("Do you want to change any variables value?: (Enter Y for yes)");
                String change = scan.nextLine();

                if (!change.equalsIgnoreCase("y")) {
                    break;
                }

                while (true) {
                    System.out.println("Enter the variable to be changed or enter 'confirm' to confirm changes");
                    String newInput = scan.nextLine();

                    if (parsedExp.containsVariable(newInput)) {
                        System.out.println("Enter the value:");
                        String newValue = scan.nextLine();

                        parsedExp.setVariable(newInput, newValue);
                    } else {
                        if (!newInput.toLowerCase().equals("confirm")) {
                            System.out.println("Variable Doesn't exists. Showing current value");
                        }

                        calculateValue(evalExp, parsedExp, expression, scan);
                        break;
                    }
                }
            } else break;
        }
    }

    // Checks if validation has been done and then only proceeds. Breaks in case of wrong number values
    private static boolean calculateValue(EvalExpression evalExp, ExpressionParser parseExp, Expression expression, Scanner scan) {
        if (!parseExp.expressionReady()) {
            System.out.println("Error: Expression has not been validated");
            return false;
        }

        List<String> modifiedExpression = parseExp.createExpression(expression);
        getValues(modifiedExpression, parseExp, scan, expression);
        Double value = evalExp.evaluateExpression(parseExp, expression);

        if (value == null) {
            System.out.println("Error: Expression has not been validated");
            return false;
        }

        System.out.println("It's evaluated value is:- " + value);
        return true;
    }

    private static void getValues(List<String> modifiedExpression, ExpressionParser parseExp, Scanner scan, Expression expression) {

        for (int index = 0; index < modifiedExpression.size(); index++) {
            String variable = modifiedExpression.get(index);

            if (Character.isLetter(variable.charAt(0))) {
                String value;

                if (!parseExp.containsVariable(variable)) {
                    System.out.println("Enter value for " + variable);
                    value = scan.nextLine();
                    parseExp.setVariable(variable, value);
                } else {
                    value = parseExp.getVariable(variable);
                }

                modifiedExpression.set(index, value);
            }
        }

//        System.out.println("Your expression with values is:- " + String.join("", modifiedExpression));

        parseExp.setValuesReady();
        parseExp.setExpression(expression, modifiedExpression);
    }
}

