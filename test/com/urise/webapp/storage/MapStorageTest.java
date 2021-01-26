package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    protected void ArraysEquals(Resume[] resumes) {
        Assert.assertArrayEquals(resumes, storage.getAllSorted().toArray());
    }
}