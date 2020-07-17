package ru.javaops.basejava.storage;

import ru.javaops.basejava.Config;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.getINSTANCE().getProperties().getProperty("db.url"),
                Config.getINSTANCE().getProperties().getProperty("db.user"),
                Config.getINSTANCE().getProperties().getProperty("db.password")));
    }
}