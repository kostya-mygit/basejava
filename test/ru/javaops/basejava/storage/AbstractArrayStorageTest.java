package ru.javaops.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javaops.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";

    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;

    static {
        RESUME_1 = new Resume(UUID_1);
        RESUME_2 = new Resume(UUID_2);
        RESUME_3 = new Resume(UUID_3);
    }

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void saveAlreadyExist() {
        Exception exception = assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
        String expectedMessage = "The resume with uuid uuid1 already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void saveStorageFull() {
        for (int i = 3; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        assertEquals(STORAGE_LIMIT, storage.size());
    }

    @Test
    void saveStorageOverflow() {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assertions.fail();
        }
        Exception exception = assertThrows(StorageException.class, () -> storage.save(new Resume()));
        String expectedMessage = "Unable to save the resume. The storage is full.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void update() {
        Resume r = new Resume("uuid1");
        storage.update(r);
        assertArrayEquals(new Resume[]{r, RESUME_2, RESUME_3}, storage.getAll());
    }

    @Test
    public void updateNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy")));
        String expectedMessage = "The resume with uuid dummy doesn't exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_2, storage.get(UUID_2));
        assertEquals(RESUME_3, storage.get(UUID_3));
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
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3}, storage.getAll());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}