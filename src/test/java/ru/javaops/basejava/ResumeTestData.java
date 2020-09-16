package ru.javaops.basejava;

import ru.javaops.basejava.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static final String UUID_1 = "a751ceb9-c3bf-44e9-a073-a0c130eee359";
    public static final String UUID_2 = "ba18773e-5b32-41c1-bfa7-9eb225495998";
    public static final String UUID_3 = "c04c0f94-6126-460a-85a2-840adc38866d";
    public static final String UUID_4 = "df34b72f-07ec-4086-808d-abd2ce972955";
    public static final String UUID_5 = "fg09k56f-e9ec-1278-b051-usa45tr72523";

    public static Resume RESUME_1;
    public static Resume RESUME_2;
    public static Resume RESUME_3;
    public static Resume RESUME_4;
    public static Resume RESUME_5;

    static {
        RESUME_1 = createResume1(UUID_1, "Anton A");
        RESUME_2 = createResume2(UUID_2, "Alex A");
        RESUME_3 = createResume3(UUID_3, "Alex A");
        RESUME_4 = createResume4(UUID_4, "Full Name");
        RESUME_5 = createGK(UUID_5, "Григорий Кислин");
    }

    public static Resume createResume1(String uuid, String fullName) {
        Resume resume1 = new Resume(uuid, fullName);

        resume1.setContact(ContactType.PHONE, "11111");
        resume1.setContact(ContactType.EMAIL, "email1@mail.ru");

        resume1.setSection(SectionType.PERSONAL, new TextSection("Personal"));
        resume1.setSection(SectionType.OBJECTIVE, new TextSection("Objective"));
        resume1.setSection(SectionType.ACHIEVEMENTS, new ListSection("Achievement1", "Achievement2"));
        resume1.setSection(SectionType.QUALIFICATIONS, new ListSection("Qualification1", "Qualification2"));

        List<Organization> experience = new ArrayList<>();
        experience.add(new Organization("Organization2", null, new Organization.Position(2016, Month.FEBRUARY, "Software Designer", "Description")));
        experience.add(new Organization("Organization1", null, new Organization.Position(2010, Month.SEPTEMBER, 2016, Month.JANUARY, "Software Designer", "Description")));
        resume1.setSection(SectionType.EXPERIENCE, new OrganizationsSection(experience));

        List<Organization> education = new ArrayList<>();
        education.add(new Organization("Organization1", null, new Organization.Position(2004, Month.SEPTEMBER, 2010, Month.JANUARY, "University", null)));
        resume1.setSection(SectionType.EDUCATION, new OrganizationsSection(education));
        return resume1;
    }

    public static Resume createResume2(String uuid, String fullName) {
        Resume resume2 = new Resume(uuid, fullName);

        resume2.setContact(ContactType.PHONE, "22222");
        resume2.setContact(ContactType.EMAIL, "email2@gmail.com");
        resume2.setContact(ContactType.SKYPE, "skype2");
        resume2.setContact(ContactType.GITHUB, "https://github.com/github2");
        resume2.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/22222");
        resume2.setContact(ContactType.HOME_PAGE, "https://homepage2.ru");

        resume2.setSection(SectionType.PERSONAL, new TextSection("Personal"));
        resume2.setSection(SectionType.OBJECTIVE, new TextSection("Objective"));
        resume2.setSection(SectionType.ACHIEVEMENTS, new ListSection("Achievement1", "Achievement2", "Achievement3", "Achievement4"));
        resume2.setSection(SectionType.QUALIFICATIONS, new ListSection("Qualification1", "Qualification2", "Qualification3", "Qualification4"));

        List<Organization> experience = new ArrayList<>();
        experience.add(new Organization("Organization3", "https://organization3.com", new Organization.Position(2018, Month.FEBRUARY, "Software Designer", "Description")));
        experience.add(new Organization("Organization2", null, new Organization.Position(2015, Month.MARCH, 2018, Month.JANUARY, "Software Designer", "Description")));
        experience.add(new Organization("Organization1", null, new Organization.Position(2010, Month.JUNE, 2015, Month.FEBRUARY, "Software Designer", "Description"),
                new Organization.Position(2005, Month.SEPTEMBER, 2010, Month.MAY, "QA Automation Engineer", "Description")));
        resume2.setSection(SectionType.EXPERIENCE, new OrganizationsSection(experience));

        List<Organization> education = new ArrayList<>();
        education.add(new Organization("Organization2", null, new Organization.Position(2015, Month.JANUARY, 2015, Month.MARCH, "Courses", null)));
        education.add(new Organization("Organization1", null, new Organization.Position(1999, Month.SEPTEMBER, 2005, Month.JUNE, "University", null)));
        resume2.setSection(SectionType.EDUCATION, new OrganizationsSection(education));

        return resume2;
    }

    public static Resume createResume3(String uuid, String fullName) {
        Resume resume3 = new Resume(uuid, fullName);

        resume3.setContact(ContactType.PHONE, "33333");
        resume3.setContact(ContactType.EMAIL, "email3@mail.ru");

        resume3.setSection(SectionType.PERSONAL, new TextSection("Personal"));
        resume3.setSection(SectionType.OBJECTIVE, new TextSection("Objective"));
        resume3.setSection(SectionType.ACHIEVEMENTS, new ListSection("Achievement1"));
        resume3.setSection(SectionType.QUALIFICATIONS, new ListSection("Qualification1"));

        List<Organization> experience = new ArrayList<>();
        experience.add(new Organization("Organization1", "https://organization1.ru", new Organization.Position(2018, Month.SEPTEMBER, "QA Automation Engineer", "Description")));
        resume3.setSection(SectionType.EXPERIENCE, new OrganizationsSection(experience));

        List<Organization> education = new ArrayList<>();
        education.add(new Organization("Organization1", null, new Organization.Position(2014, Month.SEPTEMBER, 2018, Month.JANUARY, "University", null)));
        resume3.setSection(SectionType.EDUCATION, new OrganizationsSection(education));

        return resume3;
    }

    public static Resume createResume4(String uuid, String fullName) {
        Resume resume4 = new Resume(uuid, fullName);

        resume4.setContact(ContactType.PHONE, "44444");
        resume4.setContact(ContactType.EMAIL, "email4@mail.ru");

        return resume4;
    }

    public static Resume createGK(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "grigory.kislin");
        resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.setContact(ContactType.HOME_PAGE, "http://gkislin.ru");

        resume.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        List<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resume.setSection(SectionType.ACHIEVEMENTS, new ListSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)");
        qualifications.add("Python: Django");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");

        resume.setSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        List<Organization> experience = new ArrayList<>();
        experience.add(new Organization("Java Online Projects", null, new Organization.Position(2013, Month.OCTOBER, "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.")));
        experience.add(new Organization("Wrike", null, new Organization.Position(2014, Month.OCTOBER, 2016, Month.JANUARY, "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));
        experience.add(new Organization("RIT Center", null, new Organization.Position(2012, Month.APRIL, 2014, Month.OCTOBER, "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")));
        experience.add(new Organization("Luxoft (Deutsche Bank)", null, new Organization.Position(2010, Month.DECEMBER, 2012, Month.APRIL, "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")));
        experience.add(new Organization("Yota", null, new Organization.Position(2008, Month.JUNE, 2010, Month.DECEMBER, "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")));
        experience.add(new Organization("Enkata", null, new Organization.Position(2007, Month.MARCH, 2008, Month.JUNE, "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).")));
        experience.add(new Organization("Siemens AG", null, new Organization.Position(2005, Month.JANUARY, 2007, Month.FEBRUARY, "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")));
        experience.add(new Organization("Alcatel", null, new Organization.Position(2005, Month.JANUARY, 2007, Month.FEBRUARY, "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")));

        resume.setSection(SectionType.EXPERIENCE, new OrganizationsSection(experience));

        List<Organization> education = new ArrayList<>();
        education.add(new Organization("Coursera", null, new Organization.Position(2013, Month.MARCH, 2013, Month.MAY, "\"Functional Programming Principles in Scala\" by Martin Odersky", null)));
        education.add(new Organization("Luxoft", null, new Organization.Position(2011, Month.MARCH, 2011, Month.APRIL, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null)));
        education.add(new Organization("Siemens AG", null, new Organization.Position(2005, Month.JANUARY, 2005, Month.APRIL, "3 месяца обучения мобильным IN сетям (Берлин)", null)));
        education.add(new Organization("Alcatel", null, new Organization.Position(1997, Month.SEPTEMBER, 1998, Month.MARCH, "6 месяцев обучения цифровым телефонным сетям (Москва)", null)));
        education.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", null, new Organization.Position(1993, Month.SEPTEMBER, 1996, Month.JULY, "Аспирантура (программист С, С++)", null), new Organization.Position(1987, Month.SEPTEMBER, 1993, Month.JULY, "Инженер (программист Fortran, C)", null)));
        education.add(new Organization("Заочная физико-техническая школа при МФТИ", null, new Organization.Position(1984, Month.SEPTEMBER, 1987, Month.JUNE, "Закончил с отличием", null)));

        resume.setSection(SectionType.EDUCATION, new OrganizationsSection(education));

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = createGK("uuid1", "Григорий Кислин");

        System.out.println(resume.getUuid());
        System.out.println(resume.getFullName());
        System.out.println();
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": ");
            System.out.println(entry.getValue());
        }
        System.out.println();

        String contactSkype = resume.getContact(ContactType.SKYPE);
        System.out.println(contactSkype);
        Section sectionPersonal = resume.getSection(SectionType.PERSONAL);
        System.out.println(sectionPersonal);
        Section sectionEducation = resume.getSection(SectionType.EDUCATION);
        System.out.println(sectionEducation);
    }
}
