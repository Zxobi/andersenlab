package com.andersenlab.bookstorage.io;

import java.util.Scanner;

public class ConsoleReader implements Reader {
    private static final String MESSAGE_TEMPLATE_INT_OUT_OF_BOUND = "input val should be in range [%d;%d]";
    private static final String MESSAGE_TEMPLATE_LENGTH_OUT_OF_BOUND = "input string length should be in range [%d;%d]";

    private Printer printer;
    private Scanner scanner;

    public ConsoleReader(Printer printer) {
        this.printer = printer;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int getInt(String message, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min > max");
        }

        printer.print(message);
        for(;;) {
            int input = scanner.nextInt();
            if (input >= min && input <= max) {
                scanner.nextLine();
                return input;
            }
            printer.print(String.format(MESSAGE_TEMPLATE_INT_OUT_OF_BOUND, min, max));
        }
    }

    @Override
    public String getString(String message, int maxLength) {
        if (maxLength < 1) {
            throw new IllegalArgumentException("maxLength < 1");
        }

        printer.print(message);
        for (;;) {
            String input = scanner.nextLine();
            if (!input.isEmpty() && input.length() <= maxLength) {
                return input;
            }

            printer.print(String.format(MESSAGE_TEMPLATE_LENGTH_OUT_OF_BOUND, 1, maxLength));
        }
    }

}
