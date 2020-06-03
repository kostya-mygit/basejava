package ru.javaops.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.ResumeTestData;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractStorageTest {
    protected Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";

    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "B B");
        RESUME_2 = new Resume(UUID_2, "A A");
        RESUME_3 = new Resume(UUID_3, "A A");
        RESUME_4 = ResumeTestData.create(UUID_4, "Григорий Кислин");
    }

    public AbstractStorageTest(Storage storage) {
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
    void save() {
        Resume r = new Resume("uuid0", "Full Name");
        storage.save(r);
        assertEquals(List.of(RESUME_2, RESUME_3, RESUME_1, r), storage.getAllSorted());
    }

    @Test
    void saveAlreadyExist() {
        Exception exception = assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
        String expectedMessage = "The resume with uuid uuid1 already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void update() {
        Resume r = new Resume("uuid1", "Full Name");
        storage.update(r);
        assertEquals(List.of(RESUME_2, RESUME_3, r), storage.getAllSorted());
    }

    @Test
    public void updateNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("uuid0", "dummy")));
        String expectedMessage = "The resume with uuid uuid0 doesn't exist.";
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
    void delete() {
        storage.delete(UUID_1);
        assertEquals(List.of(RESUME_2, RESUME_3), storage.getAllSorted());
    }

    @Test
    void deleteNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
        String expectedMessage = "The resume with uuid dummy doesn't exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getAllSorted() {
        assertEquals(List.of(RESUME_2, RESUME_3, RESUME_1), storage.getAllSorted());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }

    @Test
    void resumeGetGithub() {
        String github = "https://github.com/gkislin";
        assertEquals(github, RESUME_4.getContact(ContactType.GITHUB));
    }

    @Test
    void resumeGetObjective() {
        Section objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        assertEquals(objective, RESUME_4.getSection(SectionType.OBJECTIVE));
    }
}