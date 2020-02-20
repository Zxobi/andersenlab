package com.andersenlab.patternsSample.dao;

import com.andersenlab.patternsSample.db.DbManager;
import com.andersenlab.patternsSample.entity.Book;
import com.andersenlab.patternsSample.entity.Magazine;
import com.andersenlab.patternsSample.entity.PublishingHouse;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MagazineDao extends AbstractJDBCDao<Magazine, Long> {
    private static final String TYPE_MAGAZINE = "magazine";

    private static final String STATEMENT_GET_MAGAZINE_ALL = "SELECT l.id, l.title, l.publish_date, l.serial_num, p.id, p.name " +
            "FROM Literature l " +
            "JOIN Publishing_house p ON (l.publishing_house_id = p.id) " +
            "WHERE l.type = ?;";
    private static final String STATEMENT_GET_MAGAZINE_BY_ID = "SELECT l.id, l.title, l.publish_date, l.serial_num, p.id, p.name " +
            "FROM Literature l " +
            "JOIN Publishing_house p ON (l.publishing_house_id = p.id) " +
            "WHERE l.id = ? AND l.type = ?;";
    private static final String STATEMENT_CREATE_MAGAZINE = "INSERT INTO Literature(type, title, serial_num, publish_date, publishing_house_id) " +
            "VALUES(?, ?, ?, ?, ?);";

    public MagazineDao(DbManager dbManager) {
        super(dbManager);
    }

    @Override
    protected String getGetAllSQL() {
        return STATEMENT_GET_MAGAZINE_ALL;
    }

    @Override
    protected String getGetByIdSQL() {
        return STATEMENT_GET_MAGAZINE_BY_ID;
    }

    @Override
    protected String getInsertIntoSQL() {
        return STATEMENT_CREATE_MAGAZINE;
    }

    @Override
    protected void buildGetAllStatement(PreparedStatement statement) throws SQLException {
        statement.setString(1, TYPE_MAGAZINE);
    }

    @Override
    protected void buildGetByIdStatement(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void buildInsertIntoStatement(PreparedStatement statement, Magazine magazine) throws SQLException {
        statement.setString(1, TYPE_MAGAZINE);
        statement.setString(2, magazine.getTitle());
        statement.setInt(3, magazine.getSerialNumber());
        statement.setDate(4, new Date(magazine.getPublishDate().getTime()));
        statement.setLong(5, magazine.getPublishingHouse().getId());
    }

    @Override
    protected void applyGeneratedKeys(Magazine magazine, ResultSet generatedKeys) throws SQLException {
        magazine.setId(generatedKeys.getLong(1));
    }

    @Override
    protected Magazine buildEntity(ResultSet resultSet) throws SQLException {
        return Magazine.newBuilder().setId(resultSet.getLong(1))
                .setTitle(resultSet.getString(2))
                .setPublishDate(resultSet.getDate(3))
                .setSerialNumber(resultSet.getInt(4))
                .setPublishingHouse(new PublishingHouse(
                        resultSet.getLong(5),
                        resultSet.getString(6)
                )).build();
    }
}
