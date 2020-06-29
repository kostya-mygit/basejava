package ru.javaops.basejava.storage.serializer;

import ru.javaops.basejava.model.*;
import ru.javaops.basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {
    private final static String EMPTY_VALUE = "-";

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String contact = dis.readUTF();
                resume.addContact(contactType, contact);
            }
            int sectionsSize = dis.readInt();
            for (int index = 0; index < sectionsSize; index++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    }
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        int itemsSize = dis.readInt();
                        List<String> items = new ArrayList<>();
                        for (int i = 0; i < itemsSize; i++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(items));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int organizationsSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        for (int i = 0; i < organizationsSize; i++) {
                            String name = dis.readUTF();
                            String readUtfUrl = dis.readUTF();
                            String url = readUtfUrl.equals(EMPTY_VALUE) ? null : readUtfUrl;
                            Link homePage = new Link(name, url);
                            int positionsSize = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int j = 0; j < positionsSize; j++) {
                                LocalDate startDate = LocalDate.parse(dis.readUTF(), DateUtil.ISO_LOCAL_DATE_FORMATTER);
                                LocalDate endDate = LocalDate.parse(dis.readUTF(), DateUtil.ISO_LOCAL_DATE_FORMATTER);
                                String title = dis.readUTF();
                                String readUtfDescription = dis.readUTF();
                                String description = readUtfDescription.equals(EMPTY_VALUE) ? null : readUtfDescription;
                                positions.add(new Organization.Position(startDate, endDate, title, description));
                            }
                            organizations.add(new Organization(homePage, positions));
                            resume.addSection(sectionType, new OrganizationsSection(organizations));
                        }
                        break;
                }
            }
            return resume;
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            dos.writeInt(r.getContacts().size());
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(r.getSections().size());
            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.toString());
                Section section = entry.getValue();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        List<String> items = ((ListSection) section).getItems();
                        int itemsSize = items.size();
                        dos.writeInt(itemsSize);
                        for (int i = 0; i < itemsSize; i++) {
                            dos.writeUTF(items.get(i));
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = ((OrganizationsSection) section).getOrganizations();
                        int organizationsSize = organizations.size();
                        dos.writeInt(organizationsSize);
                        for (int i = 0; i < organizationsSize; i++) {
                            Organization organization = organizations.get(i);
                            Link homePage = organization.getHomePage();
                            dos.writeUTF(homePage.getName());
                            dos.writeUTF(homePage.getUrl() == null ? EMPTY_VALUE : homePage.getUrl());
                            List<Organization.Position> positions = organization.getPositions();
                            int positionsSize = positions.size();
                            dos.writeInt(positionsSize);
                            for (int j = 0; j < positionsSize; j++) {
                                Organization.Position position = positions.get(j);
                                dos.writeUTF(DateUtil.ISO_LOCAL_DATE_FORMATTER.format(position.getStartDate()));
                                dos.writeUTF(DateUtil.ISO_LOCAL_DATE_FORMATTER.format(position.getEndDate()));
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription() == null ? EMPTY_VALUE : position.getDescription());
                            }
                        }
                        break;
                }
            }
        }
    }
}
