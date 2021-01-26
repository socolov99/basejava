package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<Resume>(map.values());
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void removeResume(Object key) {
        map.remove((String) key);
    }

    @Override
    protected void changeResume(Resume resume, Object key) {
        map.put((String) key, resume);
    }

    @Override
    protected void addResume(Resume resume, Object key) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResumeBySearchKey(Object key) {
        return map.get((String) key);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object key) {
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            if (entry.getKey().equals((String) key)) {
                return true;
            }
        }
        return false;
    }
}
