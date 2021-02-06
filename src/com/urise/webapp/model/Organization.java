package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final String organizationName;
    private final List<Experience> experienceList;

    public Organization(String organizationName, List<Experience> experienceList) {
        this.organizationName = organizationName;
        this.experienceList = experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(organizationName, that.organizationName))
            return false;
        return Objects.equals(experienceList, that.experienceList);
    }

    @Override
    public int hashCode() {
        int result = organizationName != null ? organizationName.hashCode() : 0;
        result = 31 * result + (experienceList != null ? experienceList.hashCode() : 0);
        return result;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    @Override
    public String toString() {
        return organizationName + "\n" + experienceList + "\n";
    }
}
