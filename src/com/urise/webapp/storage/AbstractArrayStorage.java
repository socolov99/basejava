package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected final int STORAGE_CAPACITY = 5;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int numberOfResume = 0;

    @Override
    public Resume get(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume >= 0) {
            return storage[indexResume];
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void save(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        if (indexResume >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else if (numberOfResume == STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insert(resume, indexResume);
            numberOfResume++;
        }
    }

    @Override
    public void update(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        if (indexResume >= 0) {
            storage[indexResume] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume >= 0) {
            movingArrayLeft(indexResume);
            storage[numberOfResume - 1] = null;
            numberOfResume--;
        } else {
            throw new NotExistStorageException(uuid);
        }
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
    public int size() {
        return numberOfResume;
    }

    //insert resume to array
    protected abstract void insert(Resume resume, int indexResume);

    //move array elements to the left side
    protected abstract void movingArrayLeft(int indexResume);

    //return resume's index if it exists in storage (else return negative value)
    protected abstract int findIndexResume(String uuid);
}
