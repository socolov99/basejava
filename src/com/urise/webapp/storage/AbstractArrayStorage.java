package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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

    protected void updateResume(Resume r, Integer index) {
        storage[index] = r;
    }

    @Override
    public List<Resume> getStorageCopyList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, storageSize));
    }

    @Override
    protected void addResume(Resume r, Integer index) {
        if (storageSize == STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r, index);
            storageSize++;
        }
    }

    @Override
    public void removeResume(Integer index) {
        fillDeletedElement(index);
        storage[storageSize - 1] = null;
        storageSize--;
    }

    public Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    protected abstract Integer getSearchKey(String uuid);
}
