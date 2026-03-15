package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.exception.DatabaseException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {

    private final LinkedBlockingQueue<Connection> availableConnections;
    private final Set<Connection> allConnections = Collections.synchronizedSet(new HashSet<>());
    private final AtomicInteger activeConnections = new AtomicInteger(0);

    private final String url;
    private final String username;
    private final String password;
    private final int maxPoolSize;
    private final int connectionTimeoutMs;

    public ConnectionPool() {
        this.url = ApplicationConfig.getProperty("db.url", "jdbc:h2:mem:quantitymeasurement;DB_CLOSE_DELAY=-1");
        this.username = ApplicationConfig.getProperty("db.username", "sa");
        this.password = ApplicationConfig.getProperty("db.password", "");
        this.maxPoolSize = ApplicationConfig.getIntProperty("db.pool.maxSize", 10);
        int initialPoolSize = ApplicationConfig.getIntProperty("db.pool.initialSize", 2);
        this.connectionTimeoutMs = ApplicationConfig.getIntProperty("db.pool.connectionTimeoutMs", 5000);

        this.availableConnections = new LinkedBlockingQueue<>(maxPoolSize);

        initializePool(initialPoolSize);
    }

    private void initializePool(int initialPoolSize) {
        int targetSize = Math.min(initialPoolSize, maxPoolSize);
        for (int i = 0; i < targetSize; i++) {
            Connection connection = createPhysicalConnection();
            allConnections.add(connection);
            availableConnections.offer(connection);
        }
    }

    public Connection getConnection() {
        try {
            Connection connection = availableConnections.poll();

            if (connection == null) {
                synchronized (allConnections) {
                    if (allConnections.size() < maxPoolSize) {
                        connection = createPhysicalConnection();
                        allConnections.add(connection);
                    }
                }
            }

            if (connection == null) {
                connection = availableConnections.poll(connectionTimeoutMs, TimeUnit.MILLISECONDS);
            }

            if (connection == null) {
                throw new DatabaseException("Could not acquire database connection from pool");
            }

            activeConnections.incrementAndGet();
            return wrapConnection(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DatabaseException("Interrupted while waiting for database connection", e);
        }
    }

    public String getStatistics() {
        int total = allConnections.size();
        int active = activeConnections.get();
        int idle = availableConnections.size();

        return "total=" + total + ", active=" + active + ", idle=" + idle + ", max=" + maxPoolSize;
    }

    public void close() {
        synchronized (allConnections) {
            for (Connection connection : allConnections) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                    // Best effort cleanup while shutting down.
                }
            }

            allConnections.clear();
            availableConnections.clear();
            activeConnections.set(0);
        }
    }

    private Connection createPhysicalConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create database connection", e);
        }
    }

    private Connection wrapConnection(Connection physicalConnection) {
        return (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                (proxy, method, args) -> {
                    String methodName = method.getName();

                    if ("close".equals(methodName)) {
                        releaseConnection(physicalConnection);
                        return null;
                    }

                    if ("isClosed".equals(methodName)) {
                        return false;
                    }

                    return method.invoke(physicalConnection, args);
                }
        );
    }

    private void releaseConnection(Connection connection) {
        activeConnections.decrementAndGet();
        availableConnections.offer(connection);
    }
}