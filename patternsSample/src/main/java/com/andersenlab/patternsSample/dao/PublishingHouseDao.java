package com.andersenlab.patternsSample.dao;

import com.andersenlab.patternsSample.db.DbManager;
import com.andersenlab.patternsSample.entity.PublishingHouse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublishingHouseDao extends AbstractJDBCDao<PublishingHouse, Long> {

    private static final String STATEMENT_GET_PUBLISHING_HOUSE_ALL = "SELECT id, name " +
            "FROM Publishing_house ";
    private static final String STATEMENT_GET_PUBLISHING_HOUSE_BY_ID = "SELECT id, name " +
            "FROM Publishing_house " +
            "WHERE id = ?;";
    private static final String STATEMENT_CREATE_PUBLISHING_HOUSE = "INSERT INTO publishing_house(name) values(?)";


    public PublishingHouseDao(DbManager dbManager) {
        super(dbManager);
    }

    @Override
    protected String getGetAllSQL() {
        return STATEMENT_GET_PUBLISHING_HOUSE_ALL;
    }

    @Override
    protected String getGetByIdSQL() {
        return STATEMENT_GET_PUBLISHING_HOUSE_BY_ID;
    }

    @Override
    protected String getInsertIntoSQL() {
        return STATEMENT_CREATE_PUBLISHING_HOUSE;
    }

    @Override
    protected void buildGetAllStatement(PreparedStatement statement) {

    }

    @Override
    protected void buildGetByIdStatement(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void buildInsertIntoStatement(PreparedStatement statement, PublishingHouse publishingHouse) throws SQLException {
        statement.setString(1, publishingHouse.getName());
    }

    @Override
    protected void applyGeneratedKeys(PublishingHouse publishingHouse, ResultSet generatedKeys) throws SQLException {
        publishingHouse.setId(generatedKeys.getLong(1));
    }

    @Override
    protected PublishingHouse buildEntity(ResultSet resultSet) throws SQLException {
        return new PublishingHouse(resultSet.getLong(1), resultSet.getString(2));
    }
}
