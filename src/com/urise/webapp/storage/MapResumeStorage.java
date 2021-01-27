package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<Resume>(map.values());
        Collections.sort(list);
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void removeResume(Object searchKey) {
        map.values().removeIf(resume -> resume.equals(searchKey));
    }

    @Override
    protected void changeResume(Resume resume, Object searchKey) {
        removeResume(searchKey);
        addResume(resume, searchKey);
    }

    @Override
    protected void addResume(Resume resume, Object searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResumeBySearchKey(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsValue((Resume) searchKey);
    }
}
