package ru.javaops.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javaops.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

abstract class AbstractArrayStorageTest {
    protected static Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";

    protected static final Resume r1 = new Resume(UUID_1);
    protected static final Resume r2 = new Resume(UUID_2);
    protected static final Resume r3 = new Resume(UUID_3);

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void saveAlreadyExist() {
        Exception exception = assertThrows(ExistStorageException.class, () -> storage.save(r1));
        String expectedMessage = "The resume with uuid uuid1 already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void saveStorageFull() {
        for (int i = 3; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        assertEquals(10000, storage.size());
    }

    @Test
    void saveStorageOverflow() {
        for (int i = 3; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        Exception exception = assertThrows(StorageException.class, () -> storage.save(new Resume()));
        String expectedMessage = "Unable to save the resume. The storage is full.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void update() {
        Resume r = new Resume("uuid1");
        storage.update(r);
        assertArrayEquals(new Resume[]{r, r2, r3}, storage.getAll());
    }

    @Test
    public void updateNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy")));
        String expectedMessage = "The resume with uuid dummy doesn't exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void get() {
        assertEquals(r1, storage.get(UUID_1));
    }

    @Test
    public void getNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
        String expectedMessage = "The resume with uuid dummy doesn't exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void deleteNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
        String expectedMessage = "The resume with uuid dummy doesn't exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getAll() {
        assertArrayEquals(new Resume[]{r1, r2, r3}, storage.getAll());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}