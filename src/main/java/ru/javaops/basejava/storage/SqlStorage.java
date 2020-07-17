package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM resume")
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")
        ) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(r.getUuid());
            } else {
                throw new StorageException(e);
            }
        }
    }

    @Override
    public void update(Resume r) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE resume r SET full_name = ? WHERE r.uuid = ?")
        ) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")
        ) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM resume r WHERE r.uuid = ?")
        ) {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r ORDER BY r.full_name, r.uuid")
        ) {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT count(*) FROM resume")
        ) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
