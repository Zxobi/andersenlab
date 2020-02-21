package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.dao.Dao;
import com.andersenlab.patternsSample.entity.PublishingHouse;
import com.andersenlab.patternsSample.io.Reader;

public class CreatePublishingHouseCommand implements Command{

    private static final String MESSAGE_ENTER_NAME = "Enter publishing house name";
    private static final int MAX_STRING_LENGTH = 128;

    private Reader reader;

    private Dao<PublishingHouse, Long> dao;

    public CreatePublishingHouseCommand(Reader reader, Dao<PublishingHouse, Long> dao) {
        this.reader = reader;
        this.dao = dao;
    }

    @Override
    public void execute() {
        String publishingHouseName = reader.getString(MESSAGE_ENTER_NAME, MAX_STRING_LENGTH);
        dao.create(new PublishingHouse(publishingHouseName));
    }
}
