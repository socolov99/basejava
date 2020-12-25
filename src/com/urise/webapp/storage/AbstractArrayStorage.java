package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected final int STORAGE_CAPACITY = 5;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int numberOfResume = 0;

    public Resume get(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume != -1) {
            return storage[indexResume];
        }
        System.out.println("ERROR: \"" + uuid + "\" wasn't found");
        return null;
    }

    //return resume's index if it exists in storage (else return negative value)
    protected abstract int findIndexResume(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, numberOfResume, null);
        numberOfResume = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberOfResume);
    }

    public int size() {
        return numberOfResume;
    }

}
