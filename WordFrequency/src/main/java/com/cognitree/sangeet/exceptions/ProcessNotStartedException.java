package com.cognitree.sangeet.exceptions;

public class ProcessNotStartedException extends Exception {
    public ProcessNotStartedException() {
        super("No data has been stored. Please process the data");
    }
}