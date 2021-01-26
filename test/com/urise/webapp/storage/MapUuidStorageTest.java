package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    protected void ArraysEquals(Resume[] resumes) {
        Assert.assertArrayEquals(resumes, storage.getAllSorted().toArray());
    }
}