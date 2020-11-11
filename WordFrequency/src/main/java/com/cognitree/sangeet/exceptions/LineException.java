package com.cognitree.sangeet.exceptions;

public class LineException {
    public static Exception InvalidLineException() {
        return new Exception("Lines is not valid. Not storing it");
    }
}