package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.util.InfoHolder;
import com.andersenlab.patternsSample.io.Printer;
import com.andersenlab.patternsSample.entity.PublishingHouse;

import java.util.List;
import java.util.stream.Collectors;

public class GetPublishingHousesCommand implements Command {

    private static final String MESSAGE_EMPTY = "No publishing houses yet";

    private Printer printer;

    public GetPublishingHousesCommand(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void execute() {
        List<PublishingHouse> publishingHouses = InfoHolder.getInstance().getPublishingHouses();
        printer.print(
                publishingHouses.isEmpty() ?
                        MESSAGE_EMPTY :
                        publishingHouses.stream().map(Object::toString).collect(Collectors.joining("\n"))
        );
    }
}
