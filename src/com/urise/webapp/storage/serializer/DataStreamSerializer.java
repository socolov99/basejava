package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(sectionType.name());
                if (sectionType.equals(SectionType.OBJECTIVE) || sectionType.equals(SectionType.PERSONAL)) {
                    dos.writeUTF(((SingleLineSection) section).getSingleLine());
                } else if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
                    List<String> bulletedList;
                    bulletedList = ((BulletedListSection) section).getBulletedList();
                    dos.writeInt(bulletedList.size());
                    for (String s : bulletedList) {
                        dos.writeUTF(s);
                    }
                } else if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)) {
                    List<Organization> list;
                    list = ((OrganizationListSection) section).getOrganizationList();
                    dos.writeInt(list.size());
                    for (Organization o : list) {
                        dos.writeUTF(o.getHomePage().getUrl());
                        dos.writeUTF(o.getName());
                        List<Organization.Experience> experienceList = o.getExperienceList();
                        dos.writeInt(experienceList.size());
                        for (Organization.Experience experience : experienceList) {
                            dos.writeUTF(experience.getExperience());
                            dos.writeInt(experience.getStartDate().getYear());
                            dos.writeInt(experience.getStartDate().getMonth().getValue());
                            dos.writeInt(experience.getStartDate().getDayOfMonth());
                            dos.writeInt(experience.getEndDate().getYear());
                            dos.writeInt(experience.getEndDate().getMonth().getValue());
                            dos.writeInt(experience.getEndDate().getDayOfMonth());
                            dos.writeUTF(experience.getDescription());
                        }
                    }
                } else {
                    throw new StorageException("Not existed sectionType");
                }
            }

        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                if (sectionType.equals(SectionType.OBJECTIVE) || sectionType.equals(SectionType.PERSONAL)) {
                    resume.addSection(sectionType, new SingleLineSection(dis.readUTF()));
                } else if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
                    int bulletedListSize = dis.readInt();
                    List<String> bulletedList = new ArrayList<>();
                    for (int j = 0; j < bulletedListSize; j++) {
                        bulletedList.add(dis.readUTF());
                    }
                    resume.addSection(sectionType, new BulletedListSection(bulletedList));
                } else if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)) {
                    int listSize = dis.readInt();
                    List<Organization> list = new ArrayList<>();
                    for (int j = 0; j < listSize; j++) {
                        String linkUrl = dis.readUTF();
                        String organizationName = dis.readUTF();
                        int experienceListSize = dis.readInt();
                        List<Organization.Experience> experienceList = new ArrayList<>();
                        for (int k = 0; k < experienceListSize; k++) {
                            String experience = dis.readUTF();
                            int startDateYear = dis.readInt();
                            int startDateMonth = dis.readInt();
                            int startDateDay = dis.readInt();
                            int endDateYear = dis.readInt();
                            int endDateMonth = dis.readInt();
                            int endDateDay = dis.readInt();
                            String description = dis.readUTF();
                            experienceList.add(new Organization.Experience(experience, LocalDate.of(startDateYear, startDateMonth, startDateDay),
                                    LocalDate.of(endDateYear, endDateMonth, endDateDay), description));
                        }
                        list.add(new Organization(organizationName, linkUrl, experienceList));
                    }
                    resume.addSection(sectionType, new OrganizationListSection(list));
                } else {
                    throw new StorageException("Not existed sectionType");
                }
            }
            return resume;
        }
    }
}
