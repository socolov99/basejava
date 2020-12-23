package com.urise.webapp.model;

import java.util.Objects;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume() {
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return this.getUuid().equals(resume.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
