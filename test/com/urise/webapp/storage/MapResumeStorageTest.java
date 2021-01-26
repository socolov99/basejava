package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    protected void ArraysEquals(Resume[] resumes) {
        Assert.assertArrayEquals(resumes, storage.getAllSorted().toArray());
    }
}