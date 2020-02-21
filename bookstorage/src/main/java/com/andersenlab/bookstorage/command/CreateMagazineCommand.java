package com.andersenlab.bookstorage.command;

import com.andersenlab.bookstorage.dao.Dao;
import com.andersenlab.bookstorage.entity.Magazine;
import com.andersenlab.bookstorage.io.Printer;
import com.andersenlab.bookstorage.io.Reader;

public class CreateMagazineCommand extends CreateLiteratureCommand<Magazine> {

    private static final String MESSAGE_ENTER_TITLE = "Enter magazine title";
    private static final String MESSAGE_ENTER_SERIAL_NUMBER = "Enter magazine serial number";

    public CreateMagazineCommand(Printer printer, Reader reader, Dao<Magazine, Long> dao) {
        super(printer, reader, dao);
    }

    @Override
    void createLiterature() {
        String title = reader.getString(MESSAGE_ENTER_TITLE, MAX_STRING_LENGTH);
        int serialNumber = reader.getInt(MESSAGE_ENTER_SERIAL_NUMBER, 1, Integer.MAX_VALUE);

        dao.create(publishingHouse.publishMagazine(title, serialNumber));
    }
}
