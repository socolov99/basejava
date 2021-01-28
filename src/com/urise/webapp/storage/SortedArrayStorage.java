package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKeyResume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, numberOfResume, searchKeyResume, RESUME_COMPARATOR);
    }

    @Override
    public List<Resume> getAllSorted() {
        return storageAsList();
    }

    @Override
    protected List<Resume> storageAsList() {
        return Arrays.asList(storage).subList(0, numberOfResume);
    }
}
