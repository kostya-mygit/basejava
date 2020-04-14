package ru.javaops.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javaops.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    void save() {
        Resume r = new Resume("uuid0");
        storage.save(r);
        assertArrayEquals(new Resume[]{r, RESUME_1, RESUME_2, RESUME_3}, storage.getAll());
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertArrayEquals(new Resume[]{RESUME_2, RESUME_3}, storage.getAll());
    }
}