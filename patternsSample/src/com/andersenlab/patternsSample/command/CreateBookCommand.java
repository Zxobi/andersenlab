package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.util.InfoHolder;
import com.andersenlab.patternsSample.io.Printer;
import com.andersenlab.patternsSample.io.Reader;
import com.andersenlab.patternsSample.entity.PublishingHouse;

public class CreateBookCommand implements Command {

    private static final String MESSAGE_NO_PUBLISHING_HOUSE = "Publishing house not selected";
    private static final String MESSAGE_ENTER_TITLE = "Enter book title";
    private static final String MESSAGE_ENTER_AUTHOR = "Enter book author";
    private static final int MAX_STRING_LENGTH = 128;

    private Printer printer;
    private Reader reader;

    public CreateBookCommand(Printer printer, Reader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    @Override
    public void execute() {
        PublishingHouse publishingHouse = InfoHolder.getInstance().getCurPublishingHouse();
        if (publishingHouse == null) {
            printer.print(MESSAGE_NO_PUBLISHING_HOUSE);
            return;
        }

        String title = reader.getString(MESSAGE_ENTER_TITLE, MAX_STRING_LENGTH);
        String author = reader.getString(MESSAGE_ENTER_AUTHOR, MAX_STRING_LENGTH);

        publishingHouse.publishBook(title, author);
    }
}
