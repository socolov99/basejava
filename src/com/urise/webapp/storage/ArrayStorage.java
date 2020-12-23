package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_CAPACITY = 5;
    private Resume[] storage = new Resume[STORAGE_CAPACITY];
    private int numberOfRes = 0;

    //check if resume with uuid exists in storage
    private boolean checkResumePresent(Resume r, String uuid) {
        return r.getUuid() == uuid;
    }

    //clear the storage
    public void clear() {
        numberOfRes = 0;
        Arrays.fill(storage, null);
    }

    //add new resume if it doesn't exist in storage and if storage is not full
    public void save(Resume r) {
        for (int i = 0; i < numberOfRes; i++) {
            if (checkResumePresent(storage[i], r.getUuid())) {
                System.out.println("ERROR: \"" + r.getUuid() + "\" already exists");
                return;
            }
        }
        if (numberOfRes == STORAGE_CAPACITY) {
            System.out.println("ERROR: storage is full");
        } else {
            storage[numberOfRes] = r;
            numberOfRes++;
        }
    }

    //rewrite the resume if it exists in storage
    public void update(Resume r) {
        for (int i = 0; i < numberOfRes; i++) {
            if (checkResumePresent(storage[i], r.getUuid())) {
                storage[numberOfRes] = r;
                return;
            }
        }
        System.out.println("ERROR: \"" + r.getUuid() + "\" wasn't found");
    }

    //return the resume if it exists in storage
    public Resume get(String uuid) {
        for (int i = 0; i < numberOfRes; i++) {
            if (checkResumePresent(storage[i], uuid)) {
                return storage[i];
            }
        }
        System.out.println("ERROR: \"" + uuid + "\" wasn't found");
        return null;
    }

    //delete the resume if it exists in storage
    public void delete(String uuid) {
        for (int i = 0; i < numberOfRes; i++) {
            if (checkResumePresent(storage[i], uuid)) {
                storage[i] = storage[numberOfRes - 1];
                storage[numberOfRes - 1] = null;
                numberOfRes--;
                return;
            }
        }
        System.out.println("ERROR: \"" + uuid + "\" wasn't found");
    }

    //return array of resumes
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberOfRes);
    }

    //return the number of resumes in storage
    public int size() {
        return numberOfRes;
    }
}
