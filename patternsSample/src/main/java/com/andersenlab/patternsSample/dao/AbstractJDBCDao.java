package com.andersenlab.patternsSample.dao;

import com.andersenlab.patternsSample.db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJDBCDao<E, PK> implements Dao<E, PK> {

    protected DbManager dbManager;

    public AbstractJDBCDao(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public E getById(PK primaryKey) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getGetByIdSQL())){
            buildGetByIdStatement(preparedStatement, primaryKey);
            ResultSet resultSet = preparedStatement.executeQuery();

            E entity = null;
            if (resultSet.next()) {
                entity = buildEntity(resultSet);
            }
            resultSet.close();

            return entity;
        } catch (SQLException ex) {
            throw new RuntimeException("Error getting entity by id", ex);
        }
    }

    @Override
    public List<E> getAll() {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getGetAllSQL())){
            buildGetAllStatement(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<E> entities = buildEntities(resultSet);
            resultSet.close();

            return entities;
        } catch (SQLException ex) {
            throw new RuntimeException("Error getting all entities", ex);
        }
    }

    @Override
    public E create(E entity) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     getInsertIntoSQL(), Statement.RETURN_GENERATED_KEYS
             )){
            buildInsertIntoStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                applyGeneratedKeys(entity, generatedKeys);
            }
            generatedKeys.close();

            return entity;
        } catch (SQLException ex) {
            throw new RuntimeException("Error while inserting book to literature table", ex);
        }
    }

    protected List<E> buildEntities(ResultSet resultSet) throws SQLException {
        List<E> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(buildEntity(resultSet));
        }

        return list;
    }

    protected abstract String getGetAllSQL();
    protected abstract String getGetByIdSQL();
    protected abstract String getInsertIntoSQL();

    protected abstract void buildGetAllStatement(PreparedStatement statement) throws SQLException;
    protected abstract void buildGetByIdStatement(PreparedStatement statement, PK id) throws SQLException;
    protected abstract void buildInsertIntoStatement(PreparedStatement statement, E entity) throws SQLException;

    protected abstract void applyGeneratedKeys(E entity, ResultSet generatedKeys) throws SQLException;

    protected abstract E buildEntity(ResultSet resultSet) throws SQLException;
}
