package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_CAPACITY = 5;
    private Resume[] storage = new Resume[STORAGE_CAPACITY];
    private int numberOfResume = 0;

    //return the resume with uuid if it exists in storage (else return null)
    private Resume findEqualResume(String uuid) {
        for (int i = 0; i < numberOfResume; i++) {
            if (storage[i].equals(new Resume(uuid))) {
                return storage[i];
            }
        }
        return null;
    }

    //clear the storage
    public void clear() {
        Arrays.fill(storage, 0, numberOfResume - 1, null);
        numberOfResume = 0;
    }

    //add new resume if it doesn't exist in storage and if storage is not full
    public void save(Resume resume) {
        if (findEqualResume(resume.getUuid()) != null) {
            System.out.println("ERROR: \"" + resume.getUuid() + "\" already exists");
        } else if (numberOfResume == STORAGE_CAPACITY) {
            System.out.println("ERROR: storage is full");
        } else {
            storage[numberOfResume] = resume;
            numberOfResume++;
        }
    }

    //rewrite the resume if it exists in storage
    public void update(Resume resume) {
        Resume updatingResume = findEqualResume(resume.getUuid());
        if (updatingResume != null) {
            updatingResume = resume;
        } else {
            System.out.println("ERROR: \"" + resume.getUuid() + "\" wasn't found");
        }
    }

    //return the resume if it exists in storage (else return null)
    public Resume get(String uuid) {
        Resume gettingResum = findEqualResume(uuid);
        if (gettingResum != null) {
            return gettingResum;
        } else {
            System.out.println("ERROR: \"" + uuid + "\" wasn't found");
            return null;
        }
    }

    //delete the resume if it exists in storage
    public void delete(String uuid) {
        Resume deletingResume = findEqualResume(uuid);
        if (deletingResume != null) {
            deletingResume.setUuid(storage[numberOfResume - 1].getUuid());
            storage[numberOfResume - 1] = null;
            numberOfResume--;
        } else {
            System.out.println("ERROR: \"" + uuid + "\" wasn't found");
        }
    }

    //return array of resumes
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberOfResume);
    }

    //return the number of resumes in storage
    public int size() {
        return numberOfResume;
    }
}
