package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationListAbstractSection extends AbstractSection {
    private final List<Organization> organizationList;

    public OrganizationListAbstractSection(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public String toString() {
        return organizationList.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationListAbstractSection that = (OrganizationListAbstractSection) o;

        return Objects.equals(organizationList, that.organizationList);
    }

    @Override
    public int hashCode() {
        return organizationList != null ? organizationList.hashCode() : 0;
    }
}
