package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sqlCommand) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T execute(String sqlCommand, SqlStatement<T> sqlStatement) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            return sqlStatement.execute(preparedStatement);
        } catch (SQLException e) {
            throw analyzeSqlException(e);
        }
    }


    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw analyzeSqlException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private StorageException analyzeSqlException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(e);
        } else {
            return new StorageException(e);
        }
    }

}