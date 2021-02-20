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
                        dos.writeUTF(organization.getHomePage().getUrl());
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

            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new SingleLineSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(sectionType, new BulletedListSection(readList(dis, dis::readUTF)));
                    case EXPERIENCE, EDUCATION -> resume.addSection(sectionType, new OrganizationListSection(readList(dis, () ->
                            new Organization(dis.readUTF(), dis.readUTF(), readList(dis, () ->
                                    new Organization.Experience(dis.readUTF(), LocalDate.of(dis.readInt(), dis.readInt(),
                                            dis.readInt()), LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt()), dis.readUTF())
                            )))));
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
