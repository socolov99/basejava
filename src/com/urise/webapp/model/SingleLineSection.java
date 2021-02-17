package com.urise.webapp.model;

import java.util.Objects;

public class SingleLineSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String singleLine;

    public SingleLineSection() {
    }

    public SingleLineSection(String singleLine) {
        Objects.requireNonNull(singleLine, "singleLine must not be null");
        this.singleLine = singleLine;
    }

    public String getSingleLine() {
        return singleLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleLineSection that = (SingleLineSection) o;

        return Objects.equals(singleLine, that.singleLine);
    }

    @Override
    public int hashCode() {
        return singleLine != null ? singleLine.hashCode() : 0;
    }

    @Override
    public String toString() {
        return singleLine;
    }
}
