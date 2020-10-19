package com.cognitree.sangeet;

import java.util.Iterator;

public class DoubleStack<T, E> {
    private Object[] arrayStack;
    private int length;
    private int forwardIndex;
    private int backwardIndex;

    public DoubleStack(int initialSize) {
        this.arrayStack = new Object[initialSize];
        this.length = initialSize;
        this.forwardIndex = -1;
        this.backwardIndex = initialSize;
    }

    public DoubleStack() {
        this(10);
    }

    public boolean isBothStackEmpty() {
        return this.forwardIndex < 0 && this.backwardIndex >= this.length;
    }

    public boolean isFirstStackEmpty() {
        return this.forwardIndex < 0;
    }

    public boolean isSecondStackEmpty() {
        return this.backwardIndex >= this.length;
    }

    public T firstStackPeek() {
        @SuppressWarnings("unchecked") final T firstStackElement = (T) this.arrayStack[forwardIndex];

        return firstStackElement;
    }

    public E secondStackPeek() {
        @SuppressWarnings("unchecked") final E secondStackElement = (E) this.arrayStack[backwardIndex];

        return secondStackElement;
    }

    public void firstStackPush(T value) {
        if (forwardIndex + 1 == backwardIndex) upSize();

        this.forwardIndex++;

        this.arrayStack[forwardIndex] = value;
    }

    public void secondStackPush(E value) {
        if (forwardIndex + 1 == backwardIndex) upSize();

        this.backwardIndex--;

        this.arrayStack[backwardIndex] = value;
    }

    public T firstStackPop() {
        @SuppressWarnings("unchecked") final T firstStackElement = (T) this.arrayStack[this.forwardIndex];

        this.forwardIndex--;

        return firstStackElement;
    }

    public T secondStackPop() {
        @SuppressWarnings("unchecked") final T secondStackElement = (T) this.arrayStack[this.backwardIndex];

        this.backwardIndex++;

        return secondStackElement;
    }

    private void upSize() {
        int newLength = this.length * 2;
        copyElements(newLength);
    }

    private void copyElements(int newLength) {
        Object[] tempArrayStack = new Object[newLength];

        int index = 0;
        while (index <= this.forwardIndex) {
            tempArrayStack[index] = this.arrayStack[index];
            index++;
        }

        index = newLength - 1;

        while (index >= newLength - this.backwardIndex) {
            tempArrayStack[index] = this.arrayStack[index - this.length];
            index--;
        }

        this.backwardIndex = newLength - (this.length - this.backwardIndex);
        this.length = newLength;
        this.arrayStack = tempArrayStack;
    }

    public Iterator<T> getFirstStackIterator() {

        return new FirstStackIterator<>();
    }

    public Iterator<E> getSecondStackIterator() {

        return new SecondStackIterator<>();
    }

    public Iterable<T> getFirstStackIterable() {

        return new FirstStackIterator<>();
    }

    public Iterable<E> getSecondStackIterable() {

        return new SecondStackIterator<>();
    }

    private class FirstStackIterator<A> implements Iterator<A>, Iterable<A> {
        int iteratorIndex = 0;

        @Override
        public boolean hasNext() {
            return iteratorIndex <= forwardIndex;
        }

        @Override
        public A next() {
            @SuppressWarnings("unchecked") final A stackElement = (A) arrayStack[iteratorIndex];

            this.iteratorIndex++;

            return stackElement;
        }

        @Override
        public Iterator<A> iterator() {
            return this;
        }
    }

    private class SecondStackIterator<B> implements Iterator<B>, Iterable<B> {
        int iteratorIndex = length - 1;

        @Override
        public boolean hasNext() {
            return this.iteratorIndex >= backwardIndex;
        }

        @Override
        public B next() {
            @SuppressWarnings("unchecked") final B stackElement = (B) arrayStack[iteratorIndex];

            this.iteratorIndex--;

            return stackElement;
        }

        @Override
        public Iterator<B> iterator() {
            return this;
        }
    }
}