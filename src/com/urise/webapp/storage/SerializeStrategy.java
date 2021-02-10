package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface SerializeStrategy {

    public void doWrite(Resume r, OutputStream os) throws IOException;

    public Resume doRead(InputStream is) throws IOException;
}
