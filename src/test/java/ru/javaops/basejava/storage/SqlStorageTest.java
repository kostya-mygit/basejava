package ru.javaops.basejava.storage;

import ru.javaops.basejava.Config;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}