package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, int indexResume) {
        storage[numberOfResume] = resume;
    }

    @Override
    protected void movingArrayLeft(int indexResume) {
        storage[indexResume] = storage[numberOfResume - 1];
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < numberOfResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected List<Resume> storageAsList() {
        return Arrays.asList(storage).subList(0, numberOfResume);
    }
}
