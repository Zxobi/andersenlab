package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.entity.PublishingHouse;
import com.andersenlab.patternsSample.io.Printer;
import com.andersenlab.patternsSample.io.Reader;

public class CreateMagazineCommand extends CreateLiteratureCommand {

    private static final String MESSAGE_ENTER_TITLE = "Enter magazine title";
    private static final String MESSAGE_ENTER_SERIAL_NUMBER = "Enter magazine serial number";

    public CreateMagazineCommand(Printer printer, Reader reader) {
        super(printer, reader);
    }

    @Override
    void createLiterature() {
        String title = reader.getString(MESSAGE_ENTER_TITLE, MAX_STRING_LENGTH);
        int serialNumber = reader.getInt(MESSAGE_ENTER_SERIAL_NUMBER, 1, Integer.MAX_VALUE);

        publishingHouse.publishMagazine(title, serialNumber);
    }
}
