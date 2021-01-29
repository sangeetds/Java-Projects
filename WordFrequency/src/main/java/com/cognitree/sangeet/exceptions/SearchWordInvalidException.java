package com.cognitree.sangeet.exceptions;

public class SearchWordInvalidException extends Exception {
    public SearchWordInvalidException() {
        super("Search word has not been found in the file provided or is invalid. Please set the word correctly.");
    }
}
