package ru.javaops.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveStorageFull() {
        for (int i = 4; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume("Full Name" + i));
        }
        assertEquals(STORAGE_LIMIT, storage.size());
    }

    @Test
    void saveStorageOverflow() {
        try {
            for (int i = 4; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("Full Name" + i));
            }
        } catch (StorageException e) {
            Assertions.fail();
        }
        Exception exception = assertThrows(StorageException.class, () -> storage.save(new Resume("dummy")));
        String expectedMessage = "Unable to save the resume. The storage is full.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}