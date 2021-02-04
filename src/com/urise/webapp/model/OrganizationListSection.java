package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationListSection extends Section {
    private final List<Organization> organizationList;

    public OrganizationListSection(List<Organization> organizationList) {
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

        OrganizationListSection that = (OrganizationListSection) o;

        return Objects.equals(organizationList, that.organizationList);
    }

    @Override
    public int hashCode() {
        return organizationList != null ? organizationList.hashCode() : 0;
    }
}
