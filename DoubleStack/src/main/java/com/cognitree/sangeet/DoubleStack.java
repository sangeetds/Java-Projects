package com.cognitree.sangeet;

import java.util.Iterator;

public class DoubleStack<T, E> {
    private Object[] arrayStack;
    public int length;
    public int firstIndex;
    public int secondIndex;

    public DoubleStack(int initialSize) {
        this.arrayStack = new Object[initialSize];
        this.length = initialSize;
        this.firstIndex = -1;
        this.secondIndex = initialSize;
    }

    public DoubleStack() {
        this(10);
    }

    public boolean isBothStackEmpty() {
        return this.firstIndex < 0 && this.secondIndex >= this.length;
    }

    public boolean isFirstStackEmpty() {
        return this.firstIndex < 0;
    }

    public boolean isSecondStackEmpty() {
        return this.secondIndex >= this.length;
    }

    public T firstStackPeek() {
        @SuppressWarnings("unchecked") final T firstStackElement = (T) this.arrayStack[firstIndex];

        return firstStackElement;
    }

    public E secondStackPeek() {
        @SuppressWarnings("unchecked") final E secondStackElement = (E) this.arrayStack[secondIndex];

        return secondStackElement;
    }

    public void firstStackPush(T value) {
        if (firstIndex + 1 == secondIndex) upSize();

        this.firstIndex++;

        this.arrayStack[firstIndex] = value;
    }

    public void secondStackPush(E value) {
        if (firstIndex + 1 == secondIndex) upSize();

        this.secondIndex--;

        this.arrayStack[secondIndex] = value;
    }

    public T firstStackPop() {
        @SuppressWarnings("unchecked") final T firstStackElement = (T) this.arrayStack[this.firstIndex];

        this.firstIndex--;

        return firstStackElement;
    }

    public T secondStackPop() {
        @SuppressWarnings("unchecked") final T secondStackElement = (T) this.arrayStack[this.secondIndex];

        this.secondIndex++;

        return secondStackElement;
    }

    private void upSize() {
        int newLength = this.length * 2;
        copyElements(newLength);
    }

    private void copyElements(int newLength) {
        Object[] tempArrayStack = new Object[newLength];

        int index = 0;
        while (index <= this.firstIndex) {
            tempArrayStack[index] = this.arrayStack[index];
            index++;
        }

        index = newLength - 1;

        while (index >= newLength - this.secondIndex) {
            tempArrayStack[index] = this.arrayStack[index - this.length];
            index--;
        }

        this.secondIndex = newLength - (this.length - this.secondIndex);
        this.length = newLength;
        this.arrayStack = tempArrayStack;
    }

    public Iterator<T> getForwardStackIterator() {

        return new ForwardStackIterator<>();
    }

    public Iterator<E> getBackwardStackIterator() {

        return new BackwardStackIterator<>();
    }

    public Iterable<T> getForwardStackIterable() {

        return new ForwardStackIterator<>();
    }

    public Iterable<E> getBackwardStackIterable() {

        return new BackwardStackIterator<>();
    }

    private class ForwardStackIterator<A> implements Iterator<A>, Iterable<A> {
        int iteratorIndex = 0;

        @Override
        public boolean hasNext() {
            return iteratorIndex <= firstIndex;
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

    private class BackwardStackIterator<B> implements Iterator<B>, Iterable<B> {
        int iteratorIndex = length - 1;

        @Override
        public boolean hasNext() {
            return this.iteratorIndex >= secondIndex;
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