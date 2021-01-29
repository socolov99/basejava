package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void addResume(Resume r, SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract void removeResume(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void updateResume(Resume r, SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> getStorageCopyList();

    public void update(Resume r) {
        LOGGER.info("Update " + r);
        SK searchKey = getExistedSearchKey(r.getUuid());
        updateResume(r, searchKey);
    }

    public void save(Resume r) {
        LOGGER.info("Save " + r);
        SK searchKey = getNotExistedSearchKey(r.getUuid());
        addResume(r, searchKey);
    }

    public void delete(String uuid) {
        LOGGER.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        removeResume(searchKey);
    }

    public Resume get(String uuid) {
        LOGGER.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOGGER.warning("Resume " + uuid + " doesn't exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOGGER.warning("Resume " + uuid + " exists");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("Get all sorted");
        List<Resume> list = getStorageCopyList();
        Collections.sort(list);
        return list;
    }
}