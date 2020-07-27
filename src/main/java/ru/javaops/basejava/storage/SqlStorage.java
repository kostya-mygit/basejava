package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.*;
import ru.javaops.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeStatement("DELETE FROM resume",
                ps -> {
                    ps.executeUpdate();
                    return null;
                });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecuteStatement(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                try {
                    ps.executeUpdate();
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23505")) throw new ExistStorageException(r.getUuid());
                    else throw new SQLException(e);
                }
            }
            insertContacts(connection, r);
            insertSections(connection, r);
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecuteStatement(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume r SET full_name = ? WHERE r.uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(connection, r);
            deleteSections(connection, r);
            insertContacts(connection, r);
            insertSections(connection, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecuteStatement(connection -> {
            Resume resume = null;
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                addAllContactsToResume(connection, resume);
                addAllSectionsToResume(connection, resume);
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeStatement("DELETE FROM resume r WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecuteStatement(connection -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r ORDER BY r.full_name, r.uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    map.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            addAllContactsToResume(connection, map);
            addAllSectionsToResume(connection, map);
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeStatement("SELECT count(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    private void insertContacts(Connection connection, Resume r) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection connection, Resume r) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM contact c WHERE c.resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        }
    }

    private void addContactToResume(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addAllContactsToResume(Connection connection, Map<String, Resume> map) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume resume = map.get(rs.getString("resume_uuid"));
                addContactToResume(rs, resume);
            }
        }
    }

    private void addAllContactsToResume(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact c WHERE c.resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContactToResume(rs, resume);
            }
        }
    }

    private void insertSections(Connection connection, Resume r) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                ps.setString(1, r.getUuid());
                ps.setString(2, type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, ((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        String strSection = String.join("\n", ((ListSection) section).getItems());
                        ps.setString(3, strSection);
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Connection connection, Resume r) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM section s WHERE s.resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        }
    }

    private void addSectionToResume(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            Section section = null;
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    section = new TextSection(value);
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    List<String> items = Arrays.asList(value.split("\n"));
                    section = new ListSection(items);
                    break;
            }
            r.addSection(type, section);
        }
    }

    private void addAllSectionsToResume(Connection connection, Map<String, Resume> map) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume resume = map.get(rs.getString("resume_uuid"));
                addSectionToResume(rs, resume);
            }
        }
    }

    private void addAllSectionsToResume(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addSectionToResume(rs, resume);
            }
        }
    }
}
