package com.andersenlab.patternsSample.io;

import com.andersenlab.patternsSample.io.Printer;

public class ConsolePrinter implements Printer {
    @Override
    public void print(String toPrint) {
        System.out.println(toPrint);
    }
}
