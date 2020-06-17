package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.JsonSerializer;

class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonSerializer()));
    }
}