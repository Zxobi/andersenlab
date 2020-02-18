package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.util.InfoHolder;
import com.andersenlab.patternsSample.io.Printer;
import com.andersenlab.patternsSample.io.Reader;
import com.andersenlab.patternsSample.entity.PublishingHouse;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SelectPublishingHouseCommand implements Command {

    private static final String MESSAGE_EMPTY = "No publishing houses yet";
    private static final String MESSAGE_ENTER_PUBLISHING_HOUSE = "Enter publishing house index";

    private Printer printer;
    private Reader reader;

    public SelectPublishingHouseCommand(Printer printer, Reader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    @Override
    public void execute() {
        List<PublishingHouse> publishingHouses = InfoHolder.getInstance().getPublishingHouses();
        if (publishingHouses.isEmpty()) {
            printer.print(MESSAGE_EMPTY);
            return;
        }

        AtomicInteger index = new AtomicInteger(1);
        printer.print(
                publishingHouses.stream().map(
                        publishingHouse -> index.getAndIncrement() + " - " + publishingHouse.getName()
                ).collect(Collectors.joining("\n"))
        );

        int publishingHouseIndex = reader.getInt(MESSAGE_ENTER_PUBLISHING_HOUSE, 1, index.get() - 1);

        InfoHolder.getInstance().setCurPublishingHouse(publishingHouses.get(publishingHouseIndex - 1));
    }
}
