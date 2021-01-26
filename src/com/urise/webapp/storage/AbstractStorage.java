package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) {
        Object key = getSearchKey(uuid);
        if (isExist(key)) {
            return getResumeBySearchKey(key);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void save(Resume resume) {
        Object key = getSearchKey(resume.getUuid());
        if (isExist(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
        addResume(resume, key);
    }

    @Override
    public void update(Resume resume) {
        Object key = getSearchKey(resume.getUuid());
        if (isExist(key)) {
            changeResume(resume, key);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        Object key = getSearchKey(uuid);
        if (isExist(key)) {
            removeResume(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public abstract int size();

    //check if resume already exists in storage
    protected abstract boolean isExist(Object key);

    //remove resume by it's index
    protected abstract void removeResume(Object indexResume);

    //change resume by it's index
    protected abstract void changeResume(Resume resume, Object indexResume);

    //add resume to the storage
    protected abstract void addResume(Resume resume, Object indexResume);

    //return resume by it's search key
    protected abstract Resume getResumeBySearchKey(Object indexResume);

    //return resume's search key if it exists in storage
    protected abstract Object getSearchKey(String uuid);


}
