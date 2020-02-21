package com.andersenlab.bookstorage.io;

public class ConsolePrinter implements Printer {
    @Override
    public void print(String toPrint) {
        System.out.println(toPrint);
    }
}
