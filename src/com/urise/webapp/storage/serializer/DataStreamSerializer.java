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
                    case ACHIEVEMENT, QUALIFICATIONS -> writeWithException(dos, ((BulletedListSection) section).getBulletedList(), dos::writeUTF);
                    case EDUCATION, EXPERIENCE -> writeWithException(dos, ((OrganizationListSection) section).getOrganizationList(), organization -> {
                        dos.writeUTF(organization.getName());
                        if (organization.getHomePage().getUrl() == null) {
                            dos.writeUTF(" ");
                        } else {
                            dos.writeUTF(organization.getHomePage().getUrl());
                        }
                        writeWithException(dos, organization.getExperienceList(), experience -> {
                            dos.writeUTF(experience.getExperience());
                            writeLocalDate(dos, experience.getStartDate());
                            writeLocalDate(dos, experience.getEndDate());
                            if (experience.getDescription() == null) {
                                dos.writeUTF(" ");
                            } else {
                                dos.writeUTF(experience.getDescription());
                            }
                        });
                    });
                    default -> throw new StorageException("Not existed sectionType");
                }
            });
        }

    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
        dos.writeInt(localDate.getDayOfMonth());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new SingleLineSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(sectionType, new BulletedListSection(readList(dis, dis::readUTF)));
                    case EXPERIENCE, EDUCATION -> resume.addSection(sectionType, new OrganizationListSection(readList(dis, () -> {
                        Organization organization = new Organization(dis.readUTF(), dis.readUTF(), readList(dis, () -> {
                                    Organization.Experience experience = new Organization.Experience(dis.readUTF(), readLocalDate(dis), readLocalDate(dis), dis.readUTF());
                                    if (experience.getDescription().equals(" ")) {
                                        experience.setDescription(null);
                                    }
                                    return experience;
                                }
                        ));
                        if (organization.getHomePage().getUrl().equals(" ")) {
                            organization.setHomePage(null);
                        }
                        return organization;
                    })));
                    default -> throw new StorageException("Not existed sectionType");
                }
            });
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

    private interface ActionWithElement {
        void action() throws IOException;
    }

    private interface Reader<T> {
        T readElement() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readElement());
        }
        return list;
    }

    private void readWithException(DataInputStream dis, ActionWithElement actionWithElement) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            actionWithElement.action();
        }
    }
}
