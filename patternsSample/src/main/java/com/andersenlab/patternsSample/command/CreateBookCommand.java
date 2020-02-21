package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.dao.Dao;
import com.andersenlab.patternsSample.entity.Book;
import com.andersenlab.patternsSample.io.Printer;
import com.andersenlab.patternsSample.io.Reader;

public class CreateBookCommand extends CreateLiteratureCommand<Book> {

    private static final String MESSAGE_ENTER_TITLE = "Enter book title";
    private static final String MESSAGE_ENTER_AUTHOR = "Enter book author";

    public CreateBookCommand(Printer printer, Reader reader, Dao<Book, Long> dao) {
        super(printer, reader, dao);
    }

    @Override
    void createLiterature() {
        String title = reader.getString(MESSAGE_ENTER_TITLE, MAX_STRING_LENGTH);
        String author = reader.getString(MESSAGE_ENTER_AUTHOR, MAX_STRING_LENGTH);

        dao.create(publishingHouse.publishBook(title, author));
    }
}
