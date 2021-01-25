package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected int numberOfResume = 0;
    protected static final int STORAGE_CAPACITY = 5;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];

    @Override
    protected void changeResume(Resume resume, int indexResume) {
        storage[indexResume] = resume;
    }

    @Override
    protected void removeResume(int indexResume) {
        movingArrayLeft(indexResume);
        storage[numberOfResume - 1] = null;
        numberOfResume--;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, numberOfResume, null);
        numberOfResume = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberOfResume);
    }

    @Override
    protected Resume getByIndex(int indexResume) {
        return storage[indexResume];
    }

    @Override
    protected void addResume(Resume resume, int indexResume) {
        if (numberOfResume == STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insert(resume, indexResume);
        numberOfResume++;
    }

    @Override
    public int size() {
        return numberOfResume;
    }

    //insert resume to array
    protected abstract void insert(Resume resume, int indexResume);

    //move array elements to the left side
    protected abstract void movingArrayLeft(int indexResume);

}
