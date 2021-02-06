package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private final String experience;
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String description;

    public Experience(String experience, YearMonth startDate, YearMonth endDate, String description) {
        Objects.requireNonNull(startDate,"startDate must not be null");
        Objects.requireNonNull(endDate,"endDate must not be null");
        this.experience = experience;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
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
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return startDate + " – " + endDate + ".\t\t" + experience + "\n" + description;
    }
}
