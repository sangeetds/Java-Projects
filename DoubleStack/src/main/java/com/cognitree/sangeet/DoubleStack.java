package com.cognitree.sangeet;

public class DoubleStack<T> {
    private Object[] arrayStack;
    public int length;
    public int firstIndex;
    public int secondIndex;

    public DoubleStack() {
        this.arrayStack = new Object[2];
        this.length = 2;
        this.firstIndex = -1;
        this.secondIndex = 2;
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

    public T secondStackPeek() {
        @SuppressWarnings("unchecked") final T secondStackElement = (T) this.arrayStack[secondIndex];

        return secondStackElement;
    }

    public void firstStackPush(T value) {
        if (firstIndex + 1 == secondIndex) upSize();

        this.firstIndex++;

        this.arrayStack[firstIndex] = value;
    }

    public void secondStackPush(T value) {
        if (firstIndex + 1 == secondIndex) downSize();

        this.secondIndex--;

        this.arrayStack[secondIndex] = value;
    }

    public T firstStackPop() {
        @SuppressWarnings("unchecked") final T secondStackElement = (T) this.arrayStack[this.firstIndex];

        this.firstIndex--;

        return secondStackElement;
    }

    public T secondStackPop() {
        @SuppressWarnings("unchecked") final T secondStackElement = (T) this.arrayStack[secondIndex];

        this.secondIndex++;

        return secondStackElement;
    }

    private void upSize() {
        int newLength = this.length * 2;
        copyElements(newLength);
    }

    private void downSize() {
        int newLength = this.length / 2;
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