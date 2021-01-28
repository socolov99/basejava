package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_CAPACITY = 5;
    protected Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int storageSize = 0;

    public int size() {
        return storageSize;
    }

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    @Override
    protected void updateResume(Resume r, Object index) {
        storage[(Integer) index] = r;
    }

    @Override
    public List<Resume> getStorageCopyList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, storageSize));
    }

    @Override
    protected void addResume(Resume r, Object index) {
        if (storageSize == STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r, (Integer) index);
            storageSize++;
        }
    }

    @Override
    public void removeResume(Object index) {
        fillDeletedElement((Integer) index);
        storage[storageSize - 1] = null;
        storageSize--;
    }

    public Resume getResume(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    protected abstract Integer getSearchKey(String uuid);
}
