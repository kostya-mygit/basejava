package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.ObjectStreamSerializer;

class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}