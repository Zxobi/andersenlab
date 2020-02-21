package com.andersenlab.bookstorage.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbManager {

    Connection getConnection() throws SQLException;

}
