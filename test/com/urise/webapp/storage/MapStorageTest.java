package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Test
    public void getAll() throws Exception {
        Resume[] resumes = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertEquals(3, storage.size());
        ArraysEquals(resumes);
    }

    @Override
    protected void ArraysEquals(Resume [] resumes){
        Resume[] resumesFromStorage = storage.getAll();
        Arrays.sort(resumesFromStorage);
        Assert.assertArrayEquals(resumes, resumesFromStorage);
    }
}