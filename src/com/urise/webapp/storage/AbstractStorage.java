package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume >= 0) {
            return returnResumeByIndex(indexResume);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void save(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        saveProblemCheck(resume, indexResume);
        addResume(resume, indexResume);
    }

    @Override
    public void update(Resume resume) {
        int indexResume = findIndexResume(resume.getUuid());
        if (indexResume >= 0) {
            changeResume(resume, indexResume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int indexResume = findIndexResume(uuid);
        if (indexResume >= 0) {
            removeResume(indexResume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public abstract int size();

    //remove resume by it's index
    protected abstract void removeResume(int indexResume);

    //change resume by it's index
    protected abstract void changeResume(Resume resume, int indexResume);

    //throw exceptions if it is impossible to save resume
    protected abstract void saveProblemCheck(Resume resume, int indexResume);

    //add resume to the storage
    protected abstract void addResume(Resume resume, int indexResume);

    //return resume by it's index
    protected abstract Resume returnResumeByIndex(int indexResume);

    //return resume's index if it exists in storage (else return negative value)
    protected abstract int findIndexResume(String uuid);


}
