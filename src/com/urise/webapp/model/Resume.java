package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    // full name
    private final String fullName;
    //contacts
    private Map<ContactType,String> contacts = new EnumMap<>(ContactType.class);
    //sections
    private Map<SectionType,Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this( UUID.randomUUID().toString(),fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setContacts(Map<ContactType,String> contacts ){
        this.contacts=contacts;
    }

    public void setSections(Map<SectionType,Section> sections){
        this.sections=sections;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + " – " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int compareResult = fullName.compareTo(o.fullName);
        return compareResult != 0 ? compareResult : uuid.compareTo(o.uuid);
    }
}