package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (findIndexResume(resume.getUuid()) != -1) {
            System.out.println("ERROR: \"" + resume.getUuid() + "\" already exists");
        } else if (numberOfResume == STORAGE_CAPACITY) {
            System.out.println("ERROR: storage is full");
        } else {
            storage[numberOfResume] = resume;
            numberOfResume++;
        }
    }

    @Override
    public void update(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        if (indexResume != -1) {
            storage[indexResume].setUuid(resume.getUuid());
        } else {
            System.out.println("ERROR: \"" + resume.getUuid() + "\" wasn't found");
        }
    }

    @Override
    public void delete(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume != -1) {
            storage[indexResume] = storage[numberOfResume - 1];
            storage[numberOfResume - 1] = null;
            numberOfResume--;
        } else {
            System.out.println("ERROR: \"" + uuid + "\" wasn't found");
        }
    }

    //return resume's index if it exists in storage (else return -1)
    protected int findIndexResume(String uuid) {
        for (int i = 0; i < numberOfResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
