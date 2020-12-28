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

    static private final String UUID1 = "uuid1";
    static private final String UUID2 = "uuid2";
    static private final String UUID3 = "uuid3";
    static private final Resume resume1 = new Resume(UUID1);
    static private final Resume resume2 = new Resume(UUID2);
    static private final Resume resume3 = new Resume(UUID3);
    static private final Resume newResume = new Resume("NewResumeUUID");

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
        int sizeBeforeSave = storage.size();
        storage.save(newResume);
        Assert.assertNotEquals(sizeBeforeSave,storage.size());
        Assert.assertEquals(newResume, storage.get("NewResumeUUID"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(resume1);
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
        storage.update(resume1);
        Assert.assertEquals(resume1, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("Dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int sizeBeforeDelete = storage.size();
        storage.delete(UUID1);
        Assert.assertNotEquals(sizeBeforeDelete, storage.size());
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
        Resume[] resumes = {resume1, resume2, resume3};
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }
}