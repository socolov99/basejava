package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BulletedListSection extends AbstractSection {
    private final List<String> bulletedList;

    public BulletedListSection(String... strings) {
        this(Arrays.asList(strings));
    }

    public BulletedListSection(List<String> bulletedList) {
        Objects.requireNonNull(bulletedList, "bulletedList must not be null");
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

        BulletedListSection that = (BulletedListSection) o;

        return Objects.equals(bulletedList, that.bulletedList);
    }

    @Override
    public int hashCode() {
        return bulletedList != null ? bulletedList.hashCode() : 0;
    }
}
