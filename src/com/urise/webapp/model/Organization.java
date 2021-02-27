package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private String name;
    private List<Experience> experienceList;

    public Organization() {
    }

    public Organization(String name, String url, List<Experience> experienceList) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.homePage = new Link(name, url);
        this.experienceList = experienceList;
    }

    public String getName() {
        return name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Link getHomePage() {
        return homePage;
    }

    public void setHomePage(String url) {
        this.homePage = new Link(name,url);
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Serializable {
        private static final long serialVersionUID = 1L;

        private String experience;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String description;

        public Experience() {
        }

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

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Experience that = (Experience) o;
            return experience.equals(that.experience) && startDate.equals(that.startDate) &&
                    endDate.equals(that.endDate) && Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(experience, startDate, endDate, description);
        }

        @Override
        public String toString() {
            return startDate + " – " + endDate + ".\t\t" + experience + "\n" + description;
        }
    }
}
