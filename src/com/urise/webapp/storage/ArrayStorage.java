package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, int indexResume) {
        storage[numberOfResume] = resume;
        numberOfResume++;
    }

    protected void movingArrayLeft(int indexResume) {
        storage[indexResume] = storage[numberOfResume - 1];
    }

    protected int findIndexResume(String uuid) {
        for (int i = 0; i < numberOfResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
