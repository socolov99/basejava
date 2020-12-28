package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_CAPACITY;

public abstract class AbstractArrayStorageTest {

    static private final String UUID_1 = "uuid1";
    static private final String UUID_2 = "uuid2";
    static private final String UUID_3 = "uuid3";
    static private final String UUID_NEW = "NewResumeUUID";
    static private final Resume RESUME_1 = new Resume(UUID_1);
    static private final Resume RESUME_2 = new Resume(UUID_2);
    static private final Resume RESUME_3 = new Resume(UUID_3);
    static private final Resume RESUME_NEW = new Resume(UUID_NEW);

    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("Dummy");
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_NEW);
        Assert.assertEquals(4,storage.size());
        Assert.assertEquals(RESUME_NEW, storage.get("NewResumeUUID"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < STORAGE_CAPACITY; i++) {
                storage.save(new Resume("u" + i));
            }
        } catch (StorageException storageException) {
            Assert.fail("storage isn't overflow yet");
        }
        storage.save(new Resume("uOverFlow"));
    }

    @Test
    public void update() throws Exception {
        storage.update(RESUME_1);
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_NEW);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
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
        Resume[] resumes = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertEquals(3,storage.size());
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }
}