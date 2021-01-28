package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void updateResume(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void addResume(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void removeResume(Object resume) {
        map.remove(((Resume) resume).getUuid());
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

