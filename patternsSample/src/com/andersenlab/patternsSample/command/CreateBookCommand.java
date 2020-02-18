package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.util.InfoHolder;
import com.andersenlab.patternsSample.io.Printer;
import com.andersenlab.patternsSample.io.Reader;
import com.andersenlab.patternsSample.entity.PublishingHouse;

public class CreateBookCommand extends CreateLiteratureCommand {

    private static final String MESSAGE_ENTER_TITLE = "Enter book title";
    private static final String MESSAGE_ENTER_AUTHOR = "Enter book author";

    public CreateBookCommand(Printer printer, Reader reader) {
        super(printer, reader);
    }

    @Override
    void createLiterature() {
        String title = reader.getString(MESSAGE_ENTER_TITLE, MAX_STRING_LENGTH);
        String author = reader.getString(MESSAGE_ENTER_AUTHOR, MAX_STRING_LENGTH);

        publishingHouse.publishBook(title, author);
    }
}
