package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final String name;
    private final List<Experience> experienceList;

    public Organization(String name, List<Experience> experienceList) {
        this.name = name;
        this.experienceList = experienceList;
    }

    public String getName() {
        return name;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(name, that.name))
            return false;
        return Objects.equals(experienceList, that.experienceList);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (experienceList != null ? experienceList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + "\n" + experienceList + "\n";
    }
}
