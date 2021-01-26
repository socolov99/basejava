package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

import java.util.Arrays;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    protected void ArraysEquals(Resume[] resumes) {
        Resume[] resumesFromStorage = storage.getAll();
        Arrays.sort(resumesFromStorage);
        Assert.assertArrayEquals(resumes, resumesFromStorage);
    }
}