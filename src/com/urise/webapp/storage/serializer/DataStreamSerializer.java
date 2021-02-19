package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeWithException(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();
            writeWithException(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((SingleLineSection) section).getSingleLine());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        writeWithException(dos, ((BulletedListSection) section).getBulletedList(), dos::writeUTF);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        writeWithException(dos, ((OrganizationListSection) section).getOrganizationList(), organization -> {
                            dos.writeUTF(organization.getHomePage().getUrl());
                            dos.writeUTF(organization.getName());
                            writeWithException(dos, organization.getExperienceList(), experience -> {
                                dos.writeUTF(experience.getExperience());
                                dos.writeInt(experience.getStartDate().getYear());
                                dos.writeInt(experience.getStartDate().getMonth().getValue());
                                dos.writeInt(experience.getStartDate().getDayOfMonth());
                                dos.writeInt(experience.getEndDate().getYear());
                                dos.writeInt(experience.getEndDate().getMonth().getValue());
                                dos.writeInt(experience.getEndDate().getDayOfMonth());
                                dos.writeUTF(experience.getDescription());
                            });
                        });
                    }
                    default -> throw new StorageException("Not existed sectionType");
                }
            });
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
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new SingleLineSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int bulletedListSize = dis.readInt();
                        List<String> bulletedList = new ArrayList<>();
                        for (int j = 0; j < bulletedListSize; j++) {
                            bulletedList.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new BulletedListSection(bulletedList));
                    }
                    case EXPERIENCE, EDUCATION -> {
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
                    }
                    default -> throw new StorageException("Not existed sectionType");
                }
            }
            return resume;
        }
    }

    private interface Writer<T> {
        void writeElement(T t) throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.writeElement(element);
        }
    }
}
