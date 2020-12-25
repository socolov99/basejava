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

    public void save(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        if (indexResume >= 0) {
            System.out.println("ERROR: \"" + resume.getUuid() + "\" already exists");
        } else if (numberOfResume == STORAGE_CAPACITY) {
            System.out.println("ERROR: storage is full");
        } else {
            insert(resume, indexResume);
        }
    }

    //insert resume to array
    protected abstract void insert(Resume resume, int indexResume);

    public void update(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        if (indexResume >= 0) {
            storage[indexResume].setUuid(resume.getUuid());
        } else {
            System.out.println("ERROR: \"" + resume.getUuid() + "\" wasn't found");
        }
    }

    public void delete(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume >= 0) {
            movingArrayLeft(indexResume);
            storage[numberOfResume - 1] = null;
            numberOfResume--;
        } else {
            System.out.println("ERROR: \"" + uuid + "\" wasn't found");
        }
    }

    //move array elements to the left side
    protected abstract void movingArrayLeft(int indexResume);

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
