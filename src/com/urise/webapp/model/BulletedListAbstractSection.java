package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class BulletedListAbstractSection extends AbstractSection {
    private final List<String> bulletedList;

    public BulletedListAbstractSection(List<String> bulletedList) {
        this.bulletedList = bulletedList;
    }

    public List<String> getBulletedList() {
        return bulletedList;
    }

    @Override
    public String toString() {
        return bulletedList.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletedListAbstractSection that = (BulletedListAbstractSection) o;

        return Objects.equals(bulletedList, that.bulletedList);
    }

    @Override
    public int hashCode() {
        return bulletedList != null ? bulletedList.hashCode() : 0;
    }
}
