package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, int indexResume) {
        indexResume = -indexResume - 1;
        System.arraycopy(storage, indexResume, storage, indexResume + 1, numberOfResume - indexResume);
        storage[indexResume] = resume;
    }

    @Override
    protected void movingArrayLeft(int indexResume) {
        System.arraycopy(storage, indexResume + 1, storage, indexResume, numberOfResume - 1 - indexResume);
    }

    @Override
    protected Integer findKey(String uuid) {
        Resume searchKeyResume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, numberOfResume, searchKeyResume);
    }
}
