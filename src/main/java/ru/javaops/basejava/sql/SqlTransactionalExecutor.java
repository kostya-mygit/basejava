package ru.javaops.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransactionalExecutor<T> {
    T execute(Connection connection) throws SQLException;
}
