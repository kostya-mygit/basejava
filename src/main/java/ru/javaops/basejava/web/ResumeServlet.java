package ru.javaops.basejava.web;

import ru.javaops.basejava.Config;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage; // = Config.getInstance().getStorage();

    @Override
    public void init() throws ServletException {
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String tableHeaders = "<table>\n" +
                "  <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>Full Name</th> \n" +
                "  </tr>\n";
        StringBuilder sb = new StringBuilder(tableHeaders);

        List<Resume> resumes = storage.getAllSorted();
        for (Resume r : resumes) {
            sb.append("  <tr>\n" +
                    "    <td>" + r.getUuid() + "</td>\n" +
                    "    <td>" + r.getFullName() + "</td>\n" +
                    "  </tr>\n");
        }

        sb.append("</table>");

        response.getWriter().write(sb.toString());
    }
}
