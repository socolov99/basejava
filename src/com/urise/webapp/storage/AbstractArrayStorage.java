package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected int numberOfResume = 0;
    protected static final int STORAGE_CAPACITY = 5;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected void changeResume(Resume resume, Object indexResume) {
        storage[(Integer) indexResume] = resume;
    }

    @Override
    protected void removeResume(Object indexResume) {
        movingArrayLeft((Integer) indexResume);
        storage[numberOfResume - 1] = null;
        numberOfResume--;
    }

    @Override
    protected Resume getResumeBySearchKey(Object indexResume) {
        return storage[(Integer) indexResume];
    }

    @Override
    protected void addResume(Resume resume, Object indexResume) {
        if (numberOfResume == STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insert(resume, (Integer) indexResume);
        numberOfResume++;
    }

    @Override
    public int size() {
        return numberOfResume;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, numberOfResume, null);
        numberOfResume = 0;
    }

    @Override
    public abstract List<Resume> getAllSorted();

    @Override
    protected abstract Integer getSearchKey(String uuid);

    //insert resume to array
    protected abstract void insert(Resume resume, int indexResume);

    //move array elements to the left side
    protected abstract void movingArrayLeft(int indexResume);

}
