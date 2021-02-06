package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

/**
 * Storage for Resumes interface
 */
public interface Storage {
    //clear the storage
    void clear();

    //add new resume if it doesn't exist in storage and if storage is not full
    void save(Resume resume);

    //rewrite the resume if it exists in storage
    void update(Resume resume);

    //return the resume if it exists in storage (else return null)
    Resume get(String uuid);

    //delete the resume if it exists in storage
    void delete(String uuid);

    //return sorted list of resumes
    List<Resume> getAllSorted();

    //return storage size
    int size();
}
