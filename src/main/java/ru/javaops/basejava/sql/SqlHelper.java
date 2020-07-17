package ru.javaops.basejava.sql;

import ru.javaops.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T executeStatement(SqlExecutor<T> executor, String statement) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)
        ) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
