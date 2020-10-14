package com.cognitree.sangeet;

import java.util.Iterator;

public class StackApp {
    public static void main(String[] args) {
        DoubleStack<Integer, String> doubleStack = new DoubleStack<>();
        doubleStack.firstStackPush(2);
        doubleStack.firstStackPush(2);
        doubleStack.firstStackPush(2);
        doubleStack.firstStackPush(2);
        doubleStack.secondStackPush("String");

        Iterator<Integer> f = doubleStack.getForwardStackIterator();

        while (f.hasNext()) {
            System.out.println(f.next());
        }

        Iterator<String> g = doubleStack.getBackwardStackIterator();

        while (g.hasNext()) {
            System.out.println(g.next());
        }

        for (int c: doubleStack.getForwardStackIterable()) {
            System.out.println(c);
        }

        for (String s: doubleStack.getBackwardStackIterable()) {
            System.out.println(s);
        }
    }
}
