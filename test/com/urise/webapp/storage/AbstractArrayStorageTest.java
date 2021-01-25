package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_CAPACITY;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < STORAGE_CAPACITY; i++) {
                storage.save(new Resume("uuid" + (i + 1)));
            }
        } catch (StorageException storageException) {
            Assert.fail("storage isn't overflow yet");
        }
        storage.save(new Resume("uOverFlow"));
    }

    @Override
    public void ArraysEquals(Resume [] resumes){
        Assert.assertArrayEquals(resumes, storage.getAll());
    }
}