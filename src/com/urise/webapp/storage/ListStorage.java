package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public Resume get(String uuid) {
        Iterator<Resume> iterator = list.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return r;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void save(Resume resume) {
        Iterator<Resume> iterator = list.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid() == resume.getUuid()) {
                throw new ExistStorageException(resume.getUuid());
            }
        }
        list.add(resume);
    }

    @Override
    public void update(Resume resume) {
        boolean isException = true;
        Iterator<Resume> iterator = list.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(resume.getUuid())) {
                r = resume;
                isException = false;
            }
        }
        if (isException) {
            throw new NotExistStorageException(resume.getUuid());
        }

    }

    @Override
    public void delete(String uuid) {
        boolean isException = true;
        Iterator<Resume> iterator = list.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                iterator.remove();
                isException = false;
            }
        }
        if (isException) {
            throw new NotExistStorageException(uuid);
        }
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
