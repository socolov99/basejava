package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {
    //clear the storage
    public void clear();

    //add new resume if it doesn't exist in storage and if storage is not full
    public void save(Resume resume);

    //rewrite the resume if it exists in storage
    public void update(Resume resume);

    //return the resume if it exists in storage (else return null)
    public Resume get(String uuid);

    //delete the resume if it exists in storage
    public void delete(String uuid);

    //return array of resumes
    public Resume[] getAll();

    //return the number of resumes in storage
    public int size();
}
