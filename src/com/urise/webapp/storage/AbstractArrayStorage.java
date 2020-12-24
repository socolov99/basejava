package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected final int STORAGE_CAPACITY = 5;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int numberOfResume = 0;

    //return the resume if it exists in storage (else return null)
    public Resume get(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume != -1) {
            return storage[indexResume];
        }
        System.out.println("ERROR: \"" + uuid + "\" wasn't found");
        return null;
    }

    protected abstract int findIndexResume(String uuid);

}
