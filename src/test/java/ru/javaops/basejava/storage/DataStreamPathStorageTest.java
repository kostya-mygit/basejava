package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.DataStreamSerializer;

class DataStreamPathStorageTest extends AbstractStorageTest {

    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}