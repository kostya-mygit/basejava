package ru.javaops.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javaops.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ArrayStorageTest extends AbstractArrayStorageTest {
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Override
    @Test
    void save() {
        Resume r = new Resume("uuid0");
        storage.save(r);
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3, r}, storage.getAll());
    }

    @Override
    @Test
    void delete() {
        storage.delete(UUID_1);
        assertArrayEquals(new Resume[]{RESUME_3, RESUME_2}, storage.getAll());
    }
}