package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        if (indexResume >= 0) {
            System.out.println("ERROR: \"" + resume.getUuid() + "\" already exists");
        } else if (numberOfResume == STORAGE_CAPACITY) {
            System.out.println("ERROR: storage is full");
        } else {
            indexResume = -indexResume - 1;
            System.arraycopy(storage, indexResume, storage, indexResume + 1, numberOfResume - indexResume);
            storage[indexResume] = resume;
            numberOfResume++;
        }
    }

    @Override
    public void update(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        if (indexResume >= 0) {
            storage[indexResume].setUuid(resume.getUuid());
        } else {
            System.out.println("ERROR: \"" + resume.getUuid() + "\" wasn't found");
        }
    }

    @Override
    public void delete(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume >= 0) {
            System.arraycopy(storage, indexResume + 1, storage, indexResume, numberOfResume - 1 - indexResume);
            storage[numberOfResume - 1] = null;
            numberOfResume--;
        } else {
            System.out.println("ERROR: \"" + uuid + "\" wasn't found");
        }
    }

    @Override
    protected int findIndexResume(String uuid) {
        Resume searchKeyResume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, numberOfResume, searchKeyResume);
    }
}
