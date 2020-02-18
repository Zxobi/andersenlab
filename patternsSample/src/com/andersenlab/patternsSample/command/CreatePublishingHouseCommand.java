package com.andersenlab.patternsSample.command;

import com.andersenlab.patternsSample.util.InfoHolder;
import com.andersenlab.patternsSample.io.Reader;
import com.andersenlab.patternsSample.entity.PublishingHouse;

public class CreatePublishingHouseCommand implements Command{

    private static final String MESSAGE_ENTER_NAME = "Enter publishing house name";
    private static final int MAX_STRING_LENGTH = 128;

    private Reader reader;

    public CreatePublishingHouseCommand(Reader reader) {
        this.reader = reader;
    }

    @Override
    public void execute() {
        String publishingHouseName = reader.getString(MESSAGE_ENTER_NAME, MAX_STRING_LENGTH);
        InfoHolder.getInstance().addPublishingHouse(new PublishingHouse(publishingHouseName));
    }
}
