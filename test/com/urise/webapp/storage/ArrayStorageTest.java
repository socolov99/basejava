package com.urise.webapp.storage;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Override
    protected AbstractArrayStorage createArrayStorage() {
        return new ArrayStorage();
    }
}