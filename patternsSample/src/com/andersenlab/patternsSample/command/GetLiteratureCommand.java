package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.util.InfoHolder;
import com.andersenlab.patternsSample.io.Printer;

import java.util.stream.Collectors;

public class GetLiteratureCommand implements Command {

    private static final String MESSAGE_EMPTY = "Nothing here yet";

    private Printer printer;

    public GetLiteratureCommand(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void execute() {
        String literature = InfoHolder.getInstance()
                .getPublishingHouses()
                .stream()
                .map(
                        publishingHouses -> publishingHouses.getLiteratures()
                                .stream()
                                .map(Object::toString)
                                .collect(Collectors.joining("\n"))
                ).collect(Collectors.joining("\n"));

        printer.print(literature.isEmpty() ? MESSAGE_EMPTY : literature);
    }
}
