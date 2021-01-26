package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    protected boolean isExist(Object key) {
        return (int) key >= 0;
    }

    @Override
    protected Resume getResumeBySearchKey(Object indexResume) {
        return list.get((int) indexResume);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid);
        return list.indexOf(resume);
    }

    @Override
    protected void addResume(Resume resume, Object indexResume) {
        list.add(resume);
    }

    @Override
    protected void changeResume(Resume resume, Object indexResume) {
        list.set((int) indexResume, resume);
    }

    @Override
    protected void removeResume(Object indexResume) {
        list.remove((int) indexResume);
    }

    @Override
    public void clear() {
        list.clear();
    }

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    public List<Resume> getAllSorted() {
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

}
