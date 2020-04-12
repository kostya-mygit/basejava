package ru.javaops.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void clear() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void get() {
    }

    @Test
    public void getNotExist() throws Exception {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
        String expectedMessage = "The resume with uuid dummy doesn't exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}