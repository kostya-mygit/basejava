package ru.javaops.basejava.web;

import ru.javaops.basejava.Config;
import ru.javaops.basejava.model.*;
import ru.javaops.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage; // = Config.getInstance().getStorage();

    @Override
    public void init() throws ServletException {
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String isNew = request.getParameter("isNew");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume r;
        if ("true".equals(isNew)) {
            r = new Resume(uuid, fullName);
            addContacts(request, r);
            addSections(request, r);
            storage.save(r);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
            addContacts(request, r);
            addSections(request, r);
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r = null;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume("New Name");
                request.setAttribute("isNew", "true");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.setAttribute("contactTypes", ContactType.values());
        request.setAttribute("sectionTypes", SectionType.values());
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }

    private void addContacts(HttpServletRequest request, Resume r) {
        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (isNotEmpty(value)) {
                r.addContact(contactType, value);
            } else {
                r.getContacts().remove(contactType);
            }
        }
    }

    private void addSections(HttpServletRequest request, Resume r) {
        for (SectionType sectionType : SectionType.values()) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    String content = request.getParameter(sectionType.name());
                    if (isNotEmpty(content)) {
                        r.addSection(sectionType, new TextSection(content));
                    } else {
                        r.getSections().remove(sectionType);
                    }
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    List<String> items = new ArrayList<>();
                    String[] itemsArr = request.getParameterValues(sectionType.name());
                    for (String item : itemsArr) {
                        if (isNotEmpty(item)) {
                            items.add(item);
                        }
                    }
                    if (items.size() != 0) {
                        r.addSection(sectionType, new ListSection(items));
                    } else {
                        r.getSections().remove(sectionType);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    String[] organizationsArr = request.getParameterValues(sectionType.name());
                    for (int i = 0; i < organizationsArr.length; i += 6) {
                        String name = organizationsArr[i];
                        String url = organizationsArr[i + 1];
                        String startDate = organizationsArr[i + 2];
                        String endDate = organizationsArr[i + 3];
                        String title = organizationsArr[i + 4];
                        String description = organizationsArr[i + 5];
                        if (isNotEmpty(name) && isNotEmpty(startDate) && isNotEmpty(endDate) && isNotEmpty(title)) {
                            organizations.add(new Organization(name, url,
                                    new Organization.Position(LocalDate.parse(startDate), LocalDate.parse(endDate), title, description)));
                        }
                    }
                    if (organizations.size() != 0) {
                        r.addSection(sectionType, new OrganizationsSection(organizations));
                    } else {
                        r.getSections().remove(sectionType);
                    }
                    break;
            }
        }
    }

    private boolean isNotEmpty(String value) {
        return value != null && value.trim().length() != 0;
    }


}
