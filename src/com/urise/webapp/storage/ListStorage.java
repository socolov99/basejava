package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> list = new ArrayList<>();

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
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected void updateResume(Resume r, Integer searchKey) {
        list.set(searchKey, r);
    }

    @Override
    protected void addResume(Resume r, Integer searchKey) {
        list.add(r);
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    protected void removeResume(Integer searchKey) {
        list.remove(searchKey.intValue());
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