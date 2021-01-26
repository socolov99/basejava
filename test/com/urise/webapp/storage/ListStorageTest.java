package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    protected void ArraysEquals(Resume[] resumes) {
        Assert.assertArrayEquals(resumes, storage.getAllSorted().toArray());
    }
}
