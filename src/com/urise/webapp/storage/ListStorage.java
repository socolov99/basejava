package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    protected Resume returnResumeByIndex(int indexResume) {
        return list.get(indexResume);
    }

    protected int findIndexResume(String uuid) {
        Resume resume = new Resume(uuid);
        return ((ArrayList<Resume>) list).indexOf(resume);
    }

    @Override
    protected void addResume(Resume resume, int indexResume) {
        list.add(resume);
    }

    @Override
    protected void saveProblemCheck(Resume resume, int indexResume) {
        if (indexResume >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void changeResume(Resume resume, int indexResume) {
        list.set(indexResume, resume);
    }

    @Override
    protected void removeResume(int indexResume) {
        list.remove(indexResume);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] array = new Resume[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    @Override
    public int size() {
        return list.size();
    }

}
