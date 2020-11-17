package com.cognitree.sangeet.exceptions;

public class EmptyWordException {
    public static Exception SearchWordInvalidException() {
        return new Exception("Search word has not been set or is invalid. Please set the word correctly.");
    }
}
