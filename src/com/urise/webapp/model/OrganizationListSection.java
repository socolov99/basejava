package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationListSection extends AbstractSection {
    private final List<Organization> organizationList;

    public OrganizationListSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationListSection(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "organizationList must not be null");
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
