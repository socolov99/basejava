package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword)  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }


    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
                    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    addContactsToContactTable(resume, connection);
                    addSectionsToSectionTable(resume, connection);
                    return null;
                }
        );
    }


    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            String uuid = resume.getUuid();
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name =? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                ps.execute();
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("DELETE  FROM contact WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ps.execute();
            }

            try (PreparedStatement ps = connection.prepareStatement("DELETE  FROM section WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ps.execute();
            }

            addContactsToContactTable(resume, connection);
            addSectionsToSectionTable(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContactToResume(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSectionToResume(rs, r);
                }
            }

            return r;
        });
    }


    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name,uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContactToResume(rs, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addSectionToResume(rs, resume);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void addContactToResume(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSectionToResume(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("type"));
            if (sectionType == SectionType.OBJECTIVE || sectionType == SectionType.PERSONAL) {
                resume.addSection(sectionType, new SingleLineSection(value));
            } else if (sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS) {
                String[] list = value.split("\n");
                resume.addSection(sectionType, new BulletedListSection(list));
            }
            //TODO OrganizationListSection
        }
    }

    private void addContactsToContactTable(Resume resume, Connection connection) throws SQLException {
        Map<ContactType, String> contacts = resume.getContacts();
        if (!contacts.isEmpty()) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : contacts.entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void addSectionsToSectionTable(Resume resume, Connection connection) throws SQLException {
        Map<SectionType, AbstractSection> sections = resume.getSections();
        if (!sections.isEmpty()) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<SectionType, AbstractSection> e : sections.entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    if (e.getKey() == SectionType.OBJECTIVE || e.getKey() == SectionType.PERSONAL) {
                        ps.setString(3, String.valueOf(e.getValue()));
                    } else if (e.getKey() == SectionType.ACHIEVEMENT || e.getKey() == SectionType.QUALIFICATIONS) {
                        ps.setString(3, String.join("\n", ((BulletedListSection) e.getValue()).getBulletedList()));
                    }
                    //TODO OrganizationListSection
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }
}
