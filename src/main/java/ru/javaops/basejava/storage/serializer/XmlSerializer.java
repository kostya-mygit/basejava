package ru.javaops.basejava.storage.serializer;

import ru.javaops.basejava.model.*;
import ru.javaops.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerializer implements Serializer {
    private XmlParser xmlParser;

    public XmlSerializer() {
        xmlParser = new XmlParser(Resume.class, Organization.class, Link.class, OrganizationsSection.class,
                TextSection.class, ListSection.class, Organization.Position.class);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, writer);
        }
    }
}
