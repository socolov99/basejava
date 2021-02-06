package com.urise.webapp.model;

import java.util.Objects;

public class SingleLineAbstractSection extends AbstractSection {
    private final String singleLine;

    public SingleLineAbstractSection(String singleLine) {
        this.singleLine = singleLine;
    }

    public String getSingleLine() {
        return singleLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleLineAbstractSection that = (SingleLineAbstractSection) o;

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
