package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private Storage storage;
    private static final String htmlStringFirst = "" +
            "<html>\n" +
            "<head>\n" +
            "    <title>Resume table</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table border='3' cellpadding='5'>\n" +
            "    <tr>\n" +
            "        <th>UUID</th>\n" +
            "        <th>Имя</th>\n" +
            "        <th>Телефон</th>\n" +
            "        <th>Skype</th>\n" +
            "        <th>Mail</th>\n" +
            "    </tr>\n";
    private static final String htmlStringLast = "" +
            "</table>\n" +
            "</body>\n" +
            "</html>\n";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(htmlStringFirst);
        for (Resume resume : storage.getAllSorted()) {
            response.getWriter().write("<tr>\n" +
                    "<td>" + resume.getUuid() + "</td>\n" +
                    "<td>" + resume.getFullName() + "</td>\n" +
                    "<td>" + resume.getContact(ContactType.PHONE) + "</td>\n" +
                    "<td>" + resume.getContact(ContactType.SKYPE) + "</td>\n" +
                    "<td>" + resume.getContact(ContactType.MAIL) + "</td>\n" +
                    "</tr>\n");
        }
        response.getWriter().write(htmlStringLast);
    }
}
