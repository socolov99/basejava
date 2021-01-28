package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return getResumeBySearchKey(searchKey);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        addResume(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            changeResume(resume, searchKey);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            removeResume(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = storageAsList();
        Collections.sort(list);
        return list;
    }

    protected abstract List<Resume> storageAsList();

    //check if resume already exists in storage
    protected abstract boolean isExist(Object searchKey);

    //remove resume by it's index
    protected abstract void removeResume(Object searchKey);

    //change resume by it's index
    protected abstract void changeResume(Resume resume, Object searchKey);

    //add resume to the storage
    protected abstract void addResume(Resume resume, Object searchKey);

    //return resume by it's search key
    protected abstract Resume getResumeBySearchKey(Object searchKey);

    //return resume's search key if it exists in storage
    protected abstract Object getSearchKey(String uuid);


}
