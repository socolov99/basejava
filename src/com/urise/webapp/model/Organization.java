package com.urise.webapp.model;

import java.time.LocalDate;
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
        return name + "\n" + experienceList + "\n";
    }

    public static class Experience {
        private final String experience;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String description;

        public Experience(String experience, LocalDate startDate, LocalDate endDate, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            this.experience = experience;
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getExperience() {
            return experience;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Experience that = (Experience) o;

            if (!Objects.equals(experience, that.experience)) return false;
            if (!Objects.equals(startDate, that.startDate)) return false;
            if (!Objects.equals(endDate, that.endDate)) return false;
            return Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            int result = experience != null ? experience.hashCode() : 0;
            result = 31 * result + startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return startDate + " – " + endDate + ".\t\t" + experience + "\n" + description;
        }
    }
}
