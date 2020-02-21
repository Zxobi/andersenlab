package com.andersenlab.patternsSample.io;

public class ConsolePrinter implements Printer {
    @Override
    public void print(String toPrint) {
        System.out.println(toPrint);
    }
}
