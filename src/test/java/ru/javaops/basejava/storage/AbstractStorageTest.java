package ru.javaops.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.Config;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.basejava.ResumeTestData.*;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = Config.getInstance().getStorageDir();

    protected Storage storage;

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
        storage.save(RESUME_4);
        assertEquals(List.of(RESUME_2, RESUME_3, RESUME_1, RESUME_4), storage.getAllSorted());
    }

    @Test
    void saveAlreadyExist() {
        Exception exception = assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
        String expectedMessage = "The resume with uuid " + UUID_1 + " already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void update() {
        Resume resume = new Resume(UUID_1, "New Full Name");
        resume.addContact(ContactType.PHONE, "41414");
        resume.addContact(ContactType.SKYPE, "new skype");
        resume.addContact(ContactType.EMAIL, "email4@gmail.com");
        storage.update(resume);
        assertEquals(List.of(RESUME_2, RESUME_3, resume), storage.getAllSorted());
    }

    @Test
    public void updateNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
        String expectedMessage = "The resume with uuid " + UUID_4 + " doesn't exist.";
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
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
        String expectedMessage = "The resume with uuid " + UUID_4 + " doesn't exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertEquals(List.of(RESUME_2, RESUME_3), storage.getAllSorted());
    }

    @Test
    void deleteNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
        String expectedMessage = "The resume with uuid " + UUID_4 + " doesn't exist.";
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
    void resumeGetContacts() {
        assertEquals("22222", RESUME_2.getContact(ContactType.PHONE));
        assertEquals("email2@gmail.com", RESUME_2.getContact(ContactType.EMAIL));
        assertEquals("skype2", RESUME_2.getContact(ContactType.SKYPE));
        assertEquals("https://github.com/github2", RESUME_2.getContact(ContactType.GITHUB));
        assertEquals("https://homepage2.ru", RESUME_2.getContact(ContactType.HOME_PAGE));
    }

    @Test
    void resumeGetPersonal() {
        Section personal = new TextSection("Personal");
        assertEquals(personal, RESUME_1.getSection(SectionType.PERSONAL));
    }

    @Test
    void resumeGetObjective() {
        Section objective = new TextSection("Objective");
        assertEquals(objective, RESUME_1.getSection(SectionType.OBJECTIVE));
    }

    @Test
    void resumeGetAchievements() {
        Section achievements = new ListSection("Achievement1", "Achievement2");
        assertEquals(achievements, RESUME_1.getSection(SectionType.ACHIEVEMENTS));
    }

    @Test
    void resumeGetQualifications() {
        Section qualifications = new ListSection("Qualification1", "Qualification2");
        assertEquals(qualifications, RESUME_1.getSection(SectionType.QUALIFICATIONS));
    }

    @Test
    void resumeGetExperience() {
        List<Organization> experience = new ArrayList<>();
        experience.add(new Organization("Organization2", null, new Organization.Position(2016, Month.FEBRUARY, "Software Designer", "Description")));
        experience.add(new Organization("Organization1", null, new Organization.Position(2010, Month.SEPTEMBER, 2016, Month.JANUARY, "Software Designer", "Description")));
        OrganizationsSection organizationsSection = new OrganizationsSection(experience);
        assertEquals(organizationsSection, RESUME_1.getSection(SectionType.EXPERIENCE));
    }

    @Test
    void resumeGetEducation() {
        List<Organization> education = new ArrayList<>();
        education.add(new Organization("Organization1", null, new Organization.Position(2004, Month.SEPTEMBER, 2010, Month.JANUARY, "University", null)));
        OrganizationsSection organizationsSection = new OrganizationsSection(education);
        assertEquals(organizationsSection, RESUME_1.getSection(SectionType.EDUCATION));
    }
}