package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.dao.Dao;
import com.andersenlab.patternsSample.entity.Literature;
import com.andersenlab.patternsSample.io.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetLiteratureCommand implements Command {

    private static final String MESSAGE_EMPTY = "Nothing here yet";

    private Printer printer;

    List<Dao<? extends Literature, Long>> daoList;

    public GetLiteratureCommand(Printer printer, List<Dao<? extends Literature, Long>> daoList) {
        this.printer = printer;
        this.daoList = daoList;
    }

    @Override
    public void execute() {
        List<Literature> literature = new ArrayList<>();
        for (Dao<? extends Literature, Long> dao : daoList) {
            literature.addAll(dao.getAll());
        }

        printer.print(
                literature.isEmpty() ?
                        MESSAGE_EMPTY :
                        literature.stream().map(Object::toString).collect(Collectors.joining("\n"))
        );
    }
}
