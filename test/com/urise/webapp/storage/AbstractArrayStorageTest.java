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

    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID1));
        storage.save(new Resume(UUID2));
        storage.save(new Resume(UUID3));
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
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(new Resume("uuid4"), storage.get("uuid4"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(new Resume(UUID1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        AbstractArrayStorage arrayStorage = createArrayStorage();
        int capacity = arrayStorage.STORAGE_CAPACITY;
        try {
            for (int i = 0; i < capacity; i++) {
                arrayStorage.save(new Resume("u" + i));
            }
        } catch (StorageException storageException) {
            Assert.fail("storage isn't overflow yet");
        }
        arrayStorage.save(new Resume("uOverFlow"));
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
        storage.delete(UUID1);
        storage.get(UUID1);
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