package com.andersenlab.bookstorage.command;

import com.andersenlab.bookstorage.dao.Dao;
import com.andersenlab.bookstorage.entity.Literature;
import com.andersenlab.bookstorage.entity.PublishingHouse;
import com.andersenlab.bookstorage.io.Printer;
import com.andersenlab.bookstorage.io.Reader;
import com.andersenlab.bookstorage.util.InfoHolder;

public abstract class CreateLiteratureCommand<E extends Literature> implements Command {

    private static final String MESSAGE_NO_PUBLISHING_HOUSE = "Publishing house not selected";

    protected static final int MAX_STRING_LENGTH = 128;

    protected Printer printer;
    protected Reader reader;

    protected Dao<E, Long> dao;

    protected PublishingHouse publishingHouse;

    public CreateLiteratureCommand(Printer printer, Reader reader, Dao<E, Long> dao) {
        this.printer = printer;
        this.reader = reader;
        this.dao = dao;
    }

    @Override
    public final void execute() {
        publishingHouse = InfoHolder.getInstance().getCurPublishingHouse();
        if (publishingHouse == null) {
            printer.print(MESSAGE_NO_PUBLISHING_HOUSE);
            return;
        }

        createLiterature();
    }

    abstract void createLiterature();
}
