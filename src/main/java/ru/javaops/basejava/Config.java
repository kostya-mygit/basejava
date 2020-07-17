package ru.javaops.basejava;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private static final String PATH_PROPERTIES = "./config/resumes.properties";
    private Properties properties = new Properties();
    private String storageDir;

    private Config() {
        try (InputStream is = new FileInputStream(PATH_PROPERTIES)) {
            properties.load(is);
            storageDir = properties.getProperty("storage.dir");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PATH_PROPERTIES);
        }
    }

    public static Config getINSTANCE() {
        return INSTANCE;
    }

    public String getStorageDir() {
        return storageDir;
    }
}
