package ru.javaops.basejava.web;

import ru.javaops.basejava.Config;
import ru.javaops.basejava.model.*;
import ru.javaops.basejava.storage.Storage;
import ru.javaops.basejava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.javaops.basejava.util.HtmlUtil.isEmpty;

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

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final boolean isNew = (uuid == null || uuid.length() == 0);
        Resume r;
        if (isNew) {
            r = new Resume(fullName);
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
                r = storage.get(uuid);
                break;
            case "add":
                r = Resume.EMPTY;
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    Section section = r.getSection(sectionType);
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENTS:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationsSection organizationsSection = (OrganizationsSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (organizationsSection != null) {
                                for (Organization organization : organizationsSection.getOrganizations()) {
                                    List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Organization.Position.EMPTY);
                                    emptyFirstPositions.addAll(organization.getPositions());
                                    emptyFirstOrganizations.add(new Organization(organization.getHomePage(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationsSection(emptyFirstOrganizations);
                            break;
                    }
                    r.setSection(sectionType, section);
                }
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
            if (isEmpty(value)) {
                r.getContacts().remove(contactType);
            } else {
                r.setContact(contactType, value);
            }
        }
    }

    private void addSections(HttpServletRequest request, Resume r) {
        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            final String[] values = request.getParameterValues(sectionType.name());
            if (isEmpty(value) && values.length < 2) {
                r.getSections().remove(sectionType);
            } else {
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.setSection(sectionType, new TextSection(value));
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        r.setSection(sectionType, new ListSection(value.split("\\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(sectionType.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!isEmpty(name)) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String strCountIndex = sectionType.name() + i;
                                String[] startDates = request.getParameterValues(strCountIndex + "startDate");
                                String[] endDates = request.getParameterValues(strCountIndex + "endDate");
                                String[] titles = request.getParameterValues(strCountIndex + "title");
                                String[] descriptions = request.getParameterValues(strCountIndex + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!isEmpty(titles[j])) {
                                        positions.add(new Organization.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        r.setSection(sectionType, new OrganizationsSection(organizations));
                        break;
                }
            }
        }
    }
}
