package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementDatabaseRepository.class);
    private static final String INSERT_SQL =
            "INSERT INTO quantity_measurement (operation, input_data, result_data, error_flag) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL =
            "SELECT operation, input_data, result_data, error_flag FROM quantity_measurement ORDER BY id";
    private static final String SELECT_BY_OPERATION_SQL =
            "SELECT operation, input_data, result_data, error_flag FROM quantity_measurement WHERE operation = ? ORDER BY id";
    private static final String SELECT_BY_TYPE_SQL =
            "SELECT operation, input_data, result_data, error_flag FROM quantity_measurement WHERE LOWER(input_data) LIKE ? ORDER BY id";
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM quantity_measurement";
    private static final String DELETE_ALL_SQL = "DELETE FROM quantity_measurement";
    private final ConnectionPool connectionPool;

    public QuantityMeasurementDatabaseRepository() {
        this(new ConnectionPool());
    }

    public QuantityMeasurementDatabaseRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        logger.info("Initializing database schema");
        ensureSchema();
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        executeUpdate(INSERT_SQL, statement -> {
            statement.setString(1, entity.getOperation());
            statement.setString(2, entity.getInput());
            statement.setString(3, entity.getResult());
            statement.setBoolean(4, entity.isError());
        }, "Failed to save measurement");
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return queryMeasurements(SELECT_ALL_SQL, null, "Failed to fetch all measurements");
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        if (operation == null) {
            return List.of();
        }
        return queryMeasurements(
                SELECT_BY_OPERATION_SQL,
                statement -> statement.setString(1, operation),
                "Failed to fetch measurements by operation"
        );
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        if (measurementType == null) {
            return List.of();
        }
        return queryMeasurements(
                SELECT_BY_TYPE_SQL,
                statement -> statement.setString(1, "%" + measurementType.toLowerCase() + "%"),
                "Failed to fetch measurements by type"
        );
    }

    @Override
    public long getTotalCount() {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getLong(1) : 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch total measurement count", e);
        }
    }

    @Override
    public void deleteAll() {
        executeUpdate(DELETE_ALL_SQL, null, "Failed to delete all measurements");
    }

    @Override
    public String getPoolStatistics() {
        return connectionPool.getStatistics();
    }

    @Override
    public void releaseResources() {
        connectionPool.close();
        logger.info("Released database repository resources");
    }

    private List<QuantityMeasurementEntity> mapResults(ResultSet resultSet) throws SQLException {
        List<QuantityMeasurementEntity> entities = new ArrayList<>();
        while (resultSet.next()) {
            entities.add(new QuantityMeasurementEntity(
                    resultSet.getString("operation"),
                    resultSet.getString("input_data"),
                    resultSet.getString("result_data"),
                    resultSet.getBoolean("error_flag")
            ));
        }
        return entities;
    }

    private List<QuantityMeasurementEntity> queryMeasurements(
            String sql,
            StatementBinder binder,
            String errorMessage
    ) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                return mapResults(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(errorMessage, e);
        }
    }

    private void executeUpdate(String sql, StatementBinder binder, String errorMessage) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(statement);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(errorMessage, e);
        }
    }

    private void ensureSchema() {
        String schemaSql = readSchemaSql();
        if (schemaSql.isBlank()) {
            return;
        }
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : Arrays.stream(schemaSql.split(";"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList()) {
                try {
                    statement.execute(sql);
                } catch (SQLException e) {
                    if (isIgnorableDdlIssue(sql, e)) {
                        logger.debug("Skipping non-critical DDL issue: {}", e.getMessage());
                        continue;
                    }
                    throw e;
                }
            }
            logger.info("Database schema initialized");
        } catch (SQLException e) {
            throw new DatabaseException("Failed to initialize database schema", e);
        }
    }

    private boolean isIgnorableDdlIssue(String sql, SQLException e) {
        String normalizedSql = sql.toLowerCase(Locale.ROOT);
        if (!normalizedSql.startsWith("create index")) {
            return false;
        }

        String message = e.getMessage() == null ? "" : e.getMessage().toLowerCase(Locale.ROOT);
        return message.contains("already exists") || message.contains("duplicate") || message.contains("exists");
    }

    private String readSchemaSql() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db/schema.sql")) {
            if (inputStream == null) {
                return "";
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DatabaseException("Failed to read schema.sql", e);
        }
    }

    @FunctionalInterface
    private interface StatementBinder {
        void bind(PreparedStatement statement) throws SQLException;
    }
}