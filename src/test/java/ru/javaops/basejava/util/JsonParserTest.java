package ru.javaops.basejava.util;

import org.junit.jupiter.api.Test;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.model.Section;
import ru.javaops.basejava.model.TextSection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.javaops.basejava.ResumeTestData.RESUME_1;

class JsonParserTest {

    @Test
    void testResume() throws Exception {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_1, resume);
    }

    @Test
    void testSection() {
        Section expectedSection = new TextSection("Objective1");
        String json = JsonParser.write(expectedSection, Section.class);
        System.out.println(json);
        Section actualSection = JsonParser.read(json, Section.class);
        assertEquals(expectedSection, actualSection);
    }
}