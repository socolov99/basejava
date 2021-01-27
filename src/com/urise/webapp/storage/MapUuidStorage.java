package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
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
        map.remove((String) searchKey);
    }

    @Override
    protected void changeResume(Resume resume, Object searchKey) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected void addResume(Resume resume, Object searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResumeBySearchKey(Object searchKey) {
        return map.get((String) searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            if (entry.getKey().equals((String) searchKey)) {
                return true;
            }
        }
        return false;
    }
}
