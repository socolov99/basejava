package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void updateResume(Resume r, Object uuid) {
        map.put((String) uuid, r);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return map.containsKey((String) uuid);
    }

    @Override
    protected void addResume(Resume r, Object uuid) {
        map.put((String) uuid, r);
    }

    @Override
    protected Resume getResume(Object uuid) {
        return map.get((String) uuid);
    }

    @Override
    protected void removeResume(Object uuid) {
        map.remove((String) uuid);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getStorageCopyList() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
