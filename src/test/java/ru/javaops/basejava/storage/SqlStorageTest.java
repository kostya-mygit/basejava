package ru.javaops.basejava.storage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.Config;
import ru.javaops.basejava.ResumeTestData;
import ru.javaops.basejava.model.SectionType;

import static org.junit.jupiter.api.Assertions.assertNull;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }

    @BeforeAll
    static void beforeAll() {
        RESUME_1.getSections().remove(SectionType.EXPERIENCE);
        RESUME_1.getSections().remove(SectionType.EDUCATION);
        RESUME_2.getSections().remove(SectionType.EXPERIENCE);
        RESUME_2.getSections().remove(SectionType.EDUCATION);
        RESUME_3.getSections().remove(SectionType.EXPERIENCE);
        RESUME_3.getSections().remove(SectionType.EDUCATION);
    }

    @AfterAll
    static void afterAll() {
        RESUME_1 = ResumeTestData.createResume1(UUID_1, "Anton A");
        RESUME_2 = ResumeTestData.createResume2(UUID_2, "Alex A");
        RESUME_3 = ResumeTestData.createResume3(UUID_3, "Alex A");
    }

    @Override
    @Test
    void resumeGetExperience() {
        assertNull(RESUME_1.getSection(SectionType.EXPERIENCE));
    }

    @Override
    @Test
    void resumeGetEducation() {
        assertNull(RESUME_1.getSection(SectionType.EDUCATION));
    }
}