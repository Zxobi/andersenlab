package com.andersenlab.patternsSample.dao;

import com.andersenlab.patternsSample.db.DbManager;
import com.andersenlab.patternsSample.entity.Book;
import com.andersenlab.patternsSample.entity.PublishingHouse;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDao extends AbstractJDBCDao<Book, Long> {

    private static final String TYPE_BOOK = "book";

    private static final String STATEMENT_GET_BOOK_ALL = "SELECT l.id, l.title, l.publish_date, l.author, p.id, p.name " +
            "FROM Literature l " +
            "JOIN Publishing_house p ON (l.publishing_house_id = p.id)" +
            "WHERE l.type = ?;";
    private static final String STATEMENT_GET_BOOK_BY_ID = "SELECT l.id, l.title, l.publish_date, l.author, p.id, p.name " +
            "FROM Literature l " +
            "JOIN Publishing_house p ON (l.publishing_house_id = p.id) " +
            "WHERE l.id = ? AND l.type = ?;";
    private static final String STATEMENT_CREATE_BOOK = "INSERT INTO Literature(type, title, author, publish_date, publishing_house_id) " +
            "VALUES(?, ?, ?, ?, ?);";

    public BookDao(DbManager dbManager) {
        super(dbManager);
    }

    @Override
    protected String getGetAllSQL() {
        return STATEMENT_GET_BOOK_ALL;
    }

    @Override
    protected String getGetByIdSQL() {
        return STATEMENT_GET_BOOK_BY_ID;
    }

    @Override
    protected String getInsertIntoSQL() {
        return STATEMENT_CREATE_BOOK;
    }

    @Override
    protected void buildGetAllStatement(PreparedStatement statement) throws SQLException {
        statement.setString(1, TYPE_BOOK);
    }

    @Override
    protected void buildGetByIdStatement(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void buildInsertIntoStatement(PreparedStatement statement, Book book) throws SQLException {
        statement.setString(1, TYPE_BOOK);
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getAuthor());
        statement.setDate(4, new Date(book.getPublishDate().getTime()));
        statement.setLong(5, book.getPublishingHouse().getId());
    }

    @Override
    protected void applyGeneratedKeys(Book book, ResultSet generatedKeys) throws SQLException {
        book.setId(generatedKeys.getLong(1));
    }

    @Override
    protected Book buildEntity(ResultSet resultSet) throws SQLException {
        return new Book.Builder().setId(resultSet.getLong(1))
                .setTitle(resultSet.getString(2))
                .setPublishDate(resultSet.getDate(3))
                .setAuthor(resultSet.getString(4))
                .setPublishingHouse(new PublishingHouse(
                        resultSet.getLong(5),
                        resultSet.getString(6)
                )).build();
    }
}
