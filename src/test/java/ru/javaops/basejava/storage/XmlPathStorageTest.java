package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.XmlSerializer;

class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlSerializer()));
    }
}