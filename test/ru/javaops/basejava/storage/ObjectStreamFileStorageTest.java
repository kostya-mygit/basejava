package ru.javaops.basejava.storage;

import java.io.File;

class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(new File("./storage"), new ObjectStreamStrategy()));
    }
}