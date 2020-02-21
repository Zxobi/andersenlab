package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.dao.Dao;
import com.andersenlab.patternsSample.entity.PublishingHouse;
import com.andersenlab.patternsSample.io.Printer;
import com.andersenlab.patternsSample.io.Reader;
import com.andersenlab.patternsSample.util.InfoHolder;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SelectPublishingHouseCommand implements Command {

    private static final String MESSAGE_EMPTY = "No publishing houses yet";
    private static final String MESSAGE_ENTER_PUBLISHING_HOUSE = "Enter publishing house index";

    private Printer printer;
    private Reader reader;

    Dao<PublishingHouse, Long> dao;

    public SelectPublishingHouseCommand(Printer printer, Reader reader, Dao<PublishingHouse, Long> dao) {
        this.printer = printer;
        this.reader = reader;
        this.dao = dao;
    }

    @Override
    public void execute() {
        List<PublishingHouse> publishingHouses = dao.getAll();
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
