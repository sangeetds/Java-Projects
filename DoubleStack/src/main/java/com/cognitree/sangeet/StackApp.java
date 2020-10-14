package com.cognitree.sangeet;

public class StackApp {
    public static void main(String[] args) {
        DoubleStack<Integer, String> doubleStack = new DoubleStack<>();
        doubleStack.firstStackPush(2);
        doubleStack.firstStackPush(2);
        doubleStack.firstStackPush(2);
        doubleStack.firstStackPush(2);
        doubleStack.secondStackPush("String");
        System.out.println(doubleStack.firstStackPop());
        System.out.println(doubleStack.firstStackPop());
        System.out.println(doubleStack.firstStackPop());
        System.out.println(doubleStack.firstStackPop());
        System.out.println(doubleStack.secondStackPop());
        System.out.println(doubleStack.isFirstStackEmpty());
        System.out.println(doubleStack.isSecondStackEmpty());
    }
}
