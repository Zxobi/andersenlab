package com.andersenlab.patternsSample.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DbManager implements DbManager {

    private static final String STATEMENT_CREATE_TABLE_PUBLISH_HOUSE =
            "CREATE TABLE Publishing_house(" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255)" +
                    ");";
    private static final String STATEMENT_CREATE_TABLE_LITERATURE =
            "CREATE TABLE Literature(" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "publishing_house_id INT," +
                    "title VARCHAR(255)," +
                    "type VARCHAR(15)," +
                    "publish_date DATE," +
                    "serial_num INT," +
                    "author VARCHAR(255)," +
                    "FOREIGN KEY (publishing_house_id) REFERENCES publishing_house(id)," +
                    "CHECK (type IN ('book', 'magazine'))" +
                    ");";

    private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    public H2DbManager() {
        try {
            createSchema();
        } catch (SQLException ex) {
            throw new RuntimeException("Error initializing database", ex);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void createSchema() throws SQLException{
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()
        ){
            statement.addBatch(STATEMENT_CREATE_TABLE_PUBLISH_HOUSE);
            statement.addBatch(STATEMENT_CREATE_TABLE_LITERATURE);
            statement.executeBatch();
        }
    }
}
