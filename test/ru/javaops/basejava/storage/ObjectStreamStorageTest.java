package ru.javaops.basejava.storage;

import java.io.File;

class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(new File("./storage")));
    }
}