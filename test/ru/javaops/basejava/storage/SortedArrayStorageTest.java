package ru.javaops.basejava.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

class SortedArrayStorageTest extends AbstractArrayStorageTest {
    @BeforeAll
    static void init() {
        storage = new SortedArrayStorage();
    }

    @Test
    void save() {
        Resume r = new Resume("uuid0");
        storage.save(r);
        assertArrayEquals(new Resume[]{r, r1, r2, r3}, storage.getAll());
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertArrayEquals(new Resume[]{r2, r3}, storage.getAll());
    }
}