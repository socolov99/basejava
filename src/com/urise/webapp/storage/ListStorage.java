package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void updateResume(Resume r, Object searchKey) {
        list.set((Integer) searchKey, r);
    }

    @Override
    protected void addResume(Resume r, Object searchKey) {
        list.add(r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    protected void removeResume(Object searchKey) {
        list.remove(((Integer) searchKey).intValue());
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> getStorageCopyList() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }
}