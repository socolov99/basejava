package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) {
        Object key = findKey(uuid);
        if (isExist(key)) {
            return getByKey(key);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void save(Resume resume) {
        Object key = findKey(resume.getUuid());
        if (isExist(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
        addResume(resume, key);
    }

    @Override
    public void update(Resume resume) {
        Object key = findKey(resume.getUuid());
        if (isExist(key)) {
            changeResume(resume, key);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        Object key = findKey(uuid);
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

    //return resume by it's index
    protected abstract Resume getByKey(Object indexResume);

    //return resume's key if it exists in storage (else return negative value)
    protected abstract Object findKey(String uuid);

}
