package com.cognitree.sangeet;

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
}