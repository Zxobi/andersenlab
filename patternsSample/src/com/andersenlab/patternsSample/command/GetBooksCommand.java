package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.util.InfoHolder;
import com.andersenlab.patternsSample.io.Printer;

import java.util.stream.Collectors;

public class GetBooksCommand implements Command {

    private static final String MESSAGE_EMPTY = "No books yet";

    private Printer printer;

    public GetBooksCommand(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void execute() {
        String books = InfoHolder.getInstance()
                .getPublishingHouses()
                .stream()
                .map(
                        publishingHouses -> publishingHouses.getBooks()
                                .stream()
                                .map(Object::toString)
                                .collect(Collectors.joining("\n"))
                ).collect(Collectors.joining("\n"));

        printer.print(books.isEmpty() ? MESSAGE_EMPTY : books);
    }
}
