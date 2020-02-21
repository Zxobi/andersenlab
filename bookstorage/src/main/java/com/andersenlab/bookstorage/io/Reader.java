package com.andersenlab.bookstorage.io;

public interface Reader {

    int getInt(String message, int min, int max);

    String getString(String message, int maxLength);

}
