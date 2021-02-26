package com.urise.webapp.exception;

import java.sql.SQLException;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exists", uuid);
    }

    public ExistStorageException(SQLException sqlException) {
        super(sqlException);
    }
}