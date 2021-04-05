package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (uuid == null || uuid.length() == 0) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if ((value == null || value.trim().length() == 0) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(type, new SingleLineSection(value));
                    case QUALIFICATIONS, ACHIEVEMENT -> resume.addSection(type, new BulletedListSection(value.split("\\n")));
                    case EDUCATION, EXPERIENCE -> {
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (value != null && value.trim().length() != 0) {
                                List<Organization.Experience> experiences = new ArrayList<>();
                                String expIndex = type.name() + i;
                                String[] titles = request.getParameterValues(expIndex + "title");
                                String[] startDates = request.getParameterValues(expIndex + "startDate");
                                String[] endDates = request.getParameterValues(expIndex + "endDate");
                                String[] descriptions = request.getParameterValues(expIndex + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (titles[j] != null && titles[j].trim().length() != 0) {
                                        experiences.add(new Organization.Experience(titles[j], DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(name, urls[i]), experiences));
                            }
                        }
                        resume.addSection(type, new OrganizationListSection(organizations));
                    }
                }
            }
        }
        if (uuid == null || uuid.length() == 0) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        }
        Resume resume;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "add" -> {
                resume = Resume.emptyResume();
            }
            case "view" -> resume = storage.get(uuid);
            case "edit" -> {
                resume = storage.get(uuid);
                utilEmptySections(resume);
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        if (action.equals("view")) {
            request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);
        }
    }

    private void utilEmptySections(Resume resume) {
        for (SectionType type : SectionType.values()) {
            AbstractSection section = resume.getSection(type);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (section == null) {
                        section = new SingleLineSection("");
                    }
                    break;
                case QUALIFICATIONS:
                case ACHIEVEMENT:
                    if (section == null) {
                        section = new BulletedListSection("");
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    OrganizationListSection organizationListSection = (OrganizationListSection) section;
                    List<Organization> emptyOrganizations = new ArrayList<>();
                    emptyOrganizations.add(new Organization("", "", new Organization.Experience()));
                    if (organizationListSection != null) {
                        for (Organization organization : organizationListSection.getOrganizationList()) {
                            List<Organization.Experience> emptyExperiences = new ArrayList<>();
                            emptyExperiences.add(new Organization.Experience());
                            emptyExperiences.addAll(organization.getExperienceList());
                            emptyOrganizations.add(new Organization(organization.getHomePage(), emptyExperiences));
                        }
                    }
                    section = new OrganizationListSection(emptyOrganizations);
                    break;
            }
            resume.addSection(type, section);
        }
    }
}
