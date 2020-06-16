package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.ObjectStreamSerializer;

import java.io.File;

class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(new File("./storage"), new ObjectStreamSerializer()));
    }
}