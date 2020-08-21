<%@ page import="ru.javaops.basejava.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javaops.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<table>
    <tr>
        <th>Имя</th>
        <th>Email</th>
    </tr>
    <%
        for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
    %>
    <tr>
        <td>
            <a href=resumes?uuid=<%=resume.getUuid()%>><%=resume.getFullName()%></a>
        </td>
        <td>
            <%=resume.getContact(ContactType.EMAIL)%>
        </td>
    </tr>
    <%
        }
    %>
</table>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
