package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.sql.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String statement = "DELETE FROM resume";
        sqlHelper.executeStatement(ps -> {
            ps.executeUpdate();
            return null;
        }, statement);
    }

    @Override
    public void save(Resume r) {
        String statement = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
        sqlHelper.executeStatement(ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(r.getUuid());
                } else {
                    throw new SQLException(e);
                }
            }
            return null;
        }, statement);
    }

    @Override
    public void update(Resume r) {
        String statement = "UPDATE resume r SET full_name = ? WHERE r.uuid = ?";
        sqlHelper.executeStatement(ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        }, statement);
    }

    @Override
    public Resume get(String uuid) {
        String statement = "SELECT * FROM resume r WHERE r.uuid = ?";
        return sqlHelper.executeStatement(ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }, statement);
    }

    @Override
    public void delete(String uuid) {
        String statement = "DELETE FROM resume r WHERE r.uuid = ?";
        sqlHelper.executeStatement(ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, statement);
    }

    @Override
    public List<Resume> getAllSorted() {
        String statement = "SELECT * FROM resume r ORDER BY r.full_name, r.uuid";
        return sqlHelper.executeStatement(ps -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        }, statement);
    }

    @Override
    public int size() {
        String statement = "SELECT count(*) FROM resume";
        return sqlHelper.executeStatement(ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }, statement);
    }
}
