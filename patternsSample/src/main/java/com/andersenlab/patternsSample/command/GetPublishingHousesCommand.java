package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.dao.Dao;
import com.andersenlab.patternsSample.entity.PublishingHouse;
import com.andersenlab.patternsSample.io.Printer;

import java.util.List;
import java.util.stream.Collectors;

public class GetPublishingHousesCommand implements Command {

    private static final String MESSAGE_EMPTY = "No publishing houses yet";

    private Printer printer;

    private Dao<PublishingHouse, Long> dao;

    public GetPublishingHousesCommand(Printer printer, Dao<PublishingHouse, Long> dao) {
        this.printer = printer;
        this.dao = dao;
    }

    @Override
    public void execute() {
        List<PublishingHouse> publishingHouses = dao.getAll();
        printer.print(
                publishingHouses.isEmpty() ?
                        MESSAGE_EMPTY :
                        publishingHouses.stream().map(Object::toString).collect(Collectors.joining("\n"))
        );
    }
}
