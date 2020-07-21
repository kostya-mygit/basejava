package ru.javaops.basejava.storage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.javaops.basejava.Config;
import ru.javaops.basejava.ResumeTestData;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }

    @BeforeAll
    static void beforeAll() {
        RESUME_1.getSections().clear();
        RESUME_2.getSections().clear();
        RESUME_3.getSections().clear();
    }

    @AfterAll
    static void afterAll() {
        RESUME_1 = ResumeTestData.createResume1(UUID_1, "Anton A");
        RESUME_2 = ResumeTestData.createResume2(UUID_2, "Alex A");
        RESUME_3 = ResumeTestData.createResume3(UUID_3, "Alex A");
        RESUME_4 = ResumeTestData.createResume4(UUID_4, "Full Name");
    }
}