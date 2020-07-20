package ru.javaops.basejava;

import ru.javaops.basejava.storage.SqlStorage;
import ru.javaops.basejava.storage.Storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private static final String PATH_PROPERTIES = "./config/resumes.properties";
    private final String storageDir;
    private final Storage storage;

    private Config() {
        try (InputStream is = new FileInputStream(PATH_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = properties.getProperty("storage.dir");
            storage = new SqlStorage(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PATH_PROPERTIES);
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public String getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
