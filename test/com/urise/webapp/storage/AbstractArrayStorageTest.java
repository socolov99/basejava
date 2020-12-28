package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {

    static private final String UUID1 = "uuid1";
    static private final String UUID2 = "uuid2";
    static private final String UUID3 = "uuid3";
    static private final Resume resume1 = new Resume(UUID1);
    static private final Resume resume2 = new Resume(UUID2);
    static private final Resume resume3 = new Resume(UUID3);

    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(new Resume(UUID1), storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("Dummy");
    }

    @Test
    public void save() throws Exception {
        int oldSize = storage.size();
        storage.save(new Resume("uuid4"));
        int newSize = storage.size();
        if (newSize - oldSize == 1) {
            Assert.assertEquals(new Resume("uuid4"), storage.get("uuid4"));
        } else {
            Assert.fail("the storage size hasn't increased");
        }

    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(new Resume(UUID1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {

        int capacity = ((AbstractArrayStorage) storage).STORAGE_CAPACITY;
        try {
            for (int i = storage.size(); i < capacity; i++) {
                storage.save(new Resume("u" + i));
            }
        } catch (StorageException storageException) {
            Assert.fail("storage isn't overflow yet");
        }
        storage.save(new Resume("uOverFlow"));
    }

    protected abstract AbstractArrayStorage createArrayStorage();

    @Test
    public void update() throws Exception {
        storage.update(new Resume(UUID1));
        Assert.assertEquals(new Resume(UUID1), storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("Dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int oldSize = storage.size();
        storage.delete(UUID1);
        int newSize = storage.size();
        if (oldSize - newSize == 1) {
            storage.get(UUID1);
        } else {
            Assert.fail("the storage size hasn't decreased");
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExit() throws Exception {
        storage.delete("Dummy");
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Deprecated
    @Test
    public void getAll() throws Exception {
        Resume[] resumes = {new Resume(UUID1), new Resume(UUID2), new Resume(UUID3)};
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }
}