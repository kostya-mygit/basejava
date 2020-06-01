package ru.javaops.basejava;

import ru.javaops.basejava.model.*;

import java.util.*;

public class ResumeTestData {

    private static Resume resume;

    static {
        resume = new Resume("uuid1", "Григорий Кислин");

        Map<ContactType, Contact> contacts = new LinkedHashMap<>();
        resume.setContacts(contacts);
        contacts.put(ContactType.PHONE, new Contact(ContactType.PHONE, "+7(921) 855-0482"));
        contacts.put(ContactType.SKYPE, new Contact(ContactType.SKYPE, "skype:grigory.kislin"));
        contacts.put(ContactType.EMAIL, new Contact(ContactType.EMAIL, "mailto:gkislin@yandex.ru"));
        contacts.put(ContactType.GITHUB, new Contact(ContactType.GITHUB, "https://github.com/gkislin"));
        contacts.put(ContactType.STACKOVERFLOW, new Contact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin"));
        contacts.put(ContactType.HOMEPAGE, new Contact(ContactType.HOMEPAGE, "http://gkislin.ru"));

        Map<SectionType, Section<?>> sections = new LinkedHashMap<>();
        resume.setSections(sections);
        sections.put(SectionType.OBJECTIVE, new Section<>(SectionType.OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        sections.put(SectionType.PERSONAL, new Section<>(SectionType.PERSONAL, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        sections.put(SectionType.ACHIEVEMENTS, new Section<>(SectionType.ACHIEVEMENTS, achievements));

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

        sections.put(SectionType.QUALIFICATIONS, new Section<>(SectionType.QUALIFICATIONS, qualifications));

        List<Entry> experience = new ArrayList<>();
        experience.add(new Entry("Java Online Projects", "10/2013", "Сейчас", "Автор проекта. Создание, организация и проведение Java онлайн проектов и стажировок."));
        experience.add(new Entry("Wrike", "10/2014", "01/2016", "Старший разработчик (backend). Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experience.add(new Entry("RIT Center", "04/2012", "10/2014", "Java архитектор. Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        experience.add(new Entry("Luxoft (Deutsche Bank)", "12/2010", "04/2012", "Ведущий программист. Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        experience.add(new Entry("Yota", "06/2008", "12/2010", "Ведущий специалист. Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        experience.add(new Entry("Enkata", "03/2007", "06/2008", "Разработчик ПО. Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        experience.add(new Entry("Siemens AG", "01/2005", "02/2007", "Разработчик ПО. Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        experience.add(new Entry("Alcatel", "01/2005", "02/2007", "Разработчик ПО. Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));

        sections.put(SectionType.EXPERIENCE, new Section<>(SectionType.EXPERIENCE, experience));

        List<Entry> education = new ArrayList<>();
        education.add(new Entry("Coursera", "03/2013", "05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        education.add(new Entry("Luxoft", "03/2011", "04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        education.add(new Entry("Siemens AG", "01/2005", "04/2005", "3 месяца обучения мобильным IN сетям (Берлин)"));
        education.add(new Entry("Alcatel", "09/1997", "03/1998", "6 месяцев обучения цифровым телефонным сетям (Москва)"));
        education.add(new Entry("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "09/1993", "07/1996", "Аспирантура (программист С, С++)"));
        education.add(new Entry("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "09/1987", "07/1993", "Инженер (программист Fortran, C)"));
        education.add(new Entry("Заочная физико-техническая школа при МФТИ", "09/1984", "06/1987", "Закончил с отличием"));

        sections.put(SectionType.EDUCATION, new Section<>(SectionType.EDUCATION, education));
    }

    public static void main(String[] args) {
        System.out.println(resume.getUuid());
        System.out.println(resume.getFullName());
        System.out.println();
        for (Map.Entry<ContactType, Contact> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, Section<?>> entry : resume.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": ");
            System.out.println(entry.getValue());
        }

        Map<ContactType, Contact> contacts = resume.getContacts();
        Contact contactSkype = contacts.get(ContactType.SKYPE);
        System.out.println(contactSkype.getContent());

        Map<SectionType, Section<?>> sections = resume.getSections();
        Section<?> sectionPersonal = sections.get(SectionType.PERSONAL);
        System.out.println(sectionPersonal.getContent());
        Section<?> sectionEducation = sections.get(SectionType.EDUCATION);
        System.out.println(sectionEducation.getContent());
    }
}
