package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final String name;
    private final List<Experience> experienceList;

    public Organization(String name, String url, List<Experience> experienceList) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.homePage = new Link(name, url);
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

        if (!Objects.equals(homePage, that.homePage)) return false;
        if (!name.equals(that.name)) return false;
        return Objects.equals(experienceList, that.experienceList);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (experienceList != null ? experienceList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + "\n" + homePage + "\n" + experienceList + "\n";
    }
}
