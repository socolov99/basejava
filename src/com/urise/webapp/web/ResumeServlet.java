package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(type, new SingleLineSection(value));
                    case QUALIFICATIONS, ACHIEVEMENT -> resume.addSection(type, new BulletedListSection(value.split("\\n")));
                }
            } else {
                resume.getSections().remove(type);
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
        switch (Objects.requireNonNull(action)) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                resume = Resume.emptyResume();
                removeNullsFromSections(resume);
                break;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                removeNullsFromSections(resume);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
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

    private void removeNullsFromSections(Resume resume) {
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
                    if (section == null) {
                        section = new OrganizationListSection(new Organization("", "", null));
                    }
                    break;
            }
            resume.addSection(type, section);
        }
    }
}
