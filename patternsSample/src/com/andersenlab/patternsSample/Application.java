package com.andersenlab.patternsSample;

import com.andersenlab.patternsSample.command.*;
import com.andersenlab.patternsSample.entity.PublishingHouse;
import com.andersenlab.patternsSample.io.ConsolePrinter;
import com.andersenlab.patternsSample.io.ConsoleReader;
import com.andersenlab.patternsSample.io.Printer;
import com.andersenlab.patternsSample.io.Reader;
import com.andersenlab.patternsSample.util.InfoHolder;

import java.util.HashMap;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        Printer printer = new ConsolePrinter();
        Reader reader = new ConsoleReader(printer);

        Map<String, Command> commands = new HashMap<>();
        commands.put("Create publishing house", new CreatePublishingHouseCommand(reader));
        commands.put("Create book", new CreateBookCommand(printer, reader));
        commands.put("Create magazine", new CreateMagazineCommand(printer, reader));
        commands.put("Get publishing hoses", new GetPublishingHousesCommand(printer));
        commands.put("Get literatures", new GetLiteratureCommand(printer));
        commands.put("Select publishing house", new SelectPublishingHouseCommand(printer, reader));

        Object[] keys = commands.keySet().toArray();
        for(;;) {
            PublishingHouse publishingHouse = InfoHolder.getInstance().getCurPublishingHouse();
            if (publishingHouse != null) printer.print(String.format("Current publishing house: %s", publishingHouse));

            for (int i = 1; i <= keys.length; i++) {
                printer.print(String.format("%d - %s", i, keys[i - 1]));
            }

            int pickedIndex = reader.getInt("Enter action index", 1, keys.length);
            commands.get(keys[pickedIndex - 1]).execute();
        }
    }
}
