package com.andersenlab.bookstorage;

import com.andersenlab.bookstorage.command.*;
import com.andersenlab.bookstorage.dao.Dao;
import com.andersenlab.bookstorage.dao.jpa.EntityManagerProvider;
import com.andersenlab.bookstorage.dao.jpa.GenericJPADao;
import com.andersenlab.bookstorage.dao.jpa.HibernateEntityManagerProvider;
import com.andersenlab.bookstorage.entity.Book;
import com.andersenlab.bookstorage.entity.Magazine;
import com.andersenlab.bookstorage.entity.PublishingHouse;
import com.andersenlab.bookstorage.io.ConsolePrinter;
import com.andersenlab.bookstorage.io.ConsoleReader;
import com.andersenlab.bookstorage.io.Printer;
import com.andersenlab.bookstorage.io.Reader;
import com.andersenlab.bookstorage.util.InfoHolder;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        EntityManagerProvider entityManagerProvider = new HibernateEntityManagerProvider();
        Dao<Book, Long> bookDao = new GenericJPADao<>(Book.class, entityManagerProvider);
        Dao<Magazine, Long> magazineDao = new GenericJPADao<>(Magazine.class, entityManagerProvider);
        Dao<PublishingHouse, Long> publishingHouseDao = new GenericJPADao<>(PublishingHouse.class, entityManagerProvider);

        Printer printer = new ConsolePrinter();
        Reader reader = new ConsoleReader(printer);

        Map<String, Command> commands = new LinkedHashMap<>();
        commands.put("Create publishing house", new CreatePublishingHouseCommand(reader, publishingHouseDao));
        commands.put("Select publishing house", new SelectPublishingHouseCommand(printer, reader, publishingHouseDao));
        commands.put("Create book", new CreateBookCommand(printer, reader, bookDao));
        commands.put("Create magazine", new CreateMagazineCommand(printer, reader, magazineDao));
        commands.put("Get publishing houses", new GetPublishingHousesCommand(printer, publishingHouseDao));
        commands.put("Get literatures", new GetLiteratureCommand(printer, Arrays.asList(bookDao, magazineDao)));

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
