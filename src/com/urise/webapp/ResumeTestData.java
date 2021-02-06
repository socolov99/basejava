package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.YearMonth;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Grigory Kislin");

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");

        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        AbstractSection objectiveData = new SingleLineAbstractSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        AbstractSection personalData = new SingleLineAbstractSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        List<String> achievements = new LinkedList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        AbstractSection achievementData = new BulletedListAbstractSection(achievements);

        List<String> qualifications = new LinkedList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        AbstractSection qualificationData = new BulletedListAbstractSection(qualifications);


        List<Organization> workOrganizations = new LinkedList<>();

        YearMonth start_work1_experience1 = YearMonth.of(2013, 10);
        YearMonth end_work1_experience1 = null;
        Experience work1_experience1 = new Experience("Автор проекта.", start_work1_experience1, end_work1_experience1, "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        List<Experience> work1_experiences = new ArrayList<>();
        work1_experiences.add(work1_experience1);
        Organization work1 = new Organization("Java Online Projects", work1_experiences);

        YearMonth start_work2_experience1 = YearMonth.of(2012, 4);
        YearMonth end_work2_experience1 = YearMonth.of(2014, 10);
        Experience work2_experience1 = new Experience("Java архитектор", start_work2_experience1, end_work2_experience1, "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        List<Experience> work2_experiences = new ArrayList<>();
        work2_experiences.add(work2_experience1);
        Organization work2 = new Organization("RIT Center", work2_experiences);

        workOrganizations.add(work1);
        workOrganizations.add(work2);
        AbstractSection experienceData = new OrganizationListAbstractSection(workOrganizations);


        List<Organization> educationalOrganizations = new LinkedList<>();

        YearMonth start_education1_experience1 = YearMonth.of(2013, 3);
        YearMonth end_education1_experience1 = YearMonth.of(2013, 5);
        Experience education1_experience1 = new Experience("\"Functional Programming Principles in Scala\" by Martin Odersky\n", start_education1_experience1, end_education1_experience1, "");
        List<Experience> education1_experiences = new ArrayList<>();
        education1_experiences.add(education1_experience1);
        Organization education1 = new Organization("Coursera", education1_experiences);

        YearMonth start_education2_experience1 = YearMonth.of(1993, 9);
        YearMonth end_education2_experience1 = YearMonth.of(1996, 7);
        Experience education2_experience1 = new Experience("Аспирантура (программист С, С++)", start_education2_experience1, end_education2_experience1, "");
        YearMonth start_education2_experience2 = YearMonth.of(1987, 9);
        YearMonth end_education2_experience2 = YearMonth.of(1993, 7);
        Experience education2_experience2 = new Experience("Инженер (программист Fortran, C)", start_education2_experience2, end_education2_experience2, "");
        List<Experience> education2_experiences = new ArrayList<>();
        education2_experiences.add(education2_experience1);
        education2_experiences.add(education2_experience2);
        Organization education2 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", education2_experiences);

        educationalOrganizations.add(education1);
        educationalOrganizations.add(education2);

        AbstractSection educationData = new OrganizationListAbstractSection(educationalOrganizations);


        sections.put(SectionType.OBJECTIVE, objectiveData);
        sections.put(SectionType.PERSONAL, personalData);
        sections.put(SectionType.ACHIEVEMENT, achievementData);
        sections.put(SectionType.QUALIFICATIONS, qualificationData);
        sections.put(SectionType.EXPERIENCE, experienceData);
        sections.put(SectionType.EDUCATION, educationData);

        resume.setContacts(contacts);
        resume.setSections(sections);

        System.out.println(resume.getFullName());
        System.out.println();
        System.out.println(ContactType.PHONE + ": " + resume.getContact(ContactType.PHONE));
        System.out.println(ContactType.SKYPE + ": " + resume.getContact(ContactType.SKYPE));
        System.out.println(ContactType.EMAIL + ": " + resume.getContact(ContactType.EMAIL));
        System.out.println("————————————————————————————————————————————————————————————————————————");
        System.out.println(SectionType.OBJECTIVE.getTitle());
        System.out.println(resume.getSection(SectionType.OBJECTIVE) + "\n");
        System.out.println(SectionType.PERSONAL.getTitle());
        System.out.println(resume.getSection(SectionType.PERSONAL) + "\n");
        System.out.println(SectionType.ACHIEVEMENT.getTitle());
        System.out.println(resume.getSection(SectionType.ACHIEVEMENT) + "\n");
        System.out.println(SectionType.QUALIFICATIONS.getTitle());
        System.out.println(resume.getSection(SectionType.QUALIFICATIONS) + "\n");
        System.out.println(SectionType.EXPERIENCE.getTitle());
        System.out.println(resume.getSection(SectionType.EXPERIENCE) + "\n");
        System.out.println(SectionType.EDUCATION.getTitle());
        System.out.println(resume.getSection(SectionType.EDUCATION) + "\n");

    }
}
