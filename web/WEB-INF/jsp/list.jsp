<%@ page import="ru.javaops.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <a href="resume?action=add"><img src="img/add.png"></a>
    <p>
    <table id="allResumes">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="resume" items="${resumes}">
            <jsp:useBean id="resume" class="ru.javaops.basejava.model.Resume"/>
            <tr>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                </td>
                <td>
                    <a href="mailto:${resume.getContact(ContactType.EMAIL)}">${resume.getContact(ContactType.EMAIL)}</a>
                </td>
                <td id="col3">
                    <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a>
                </td>
                <td id="col4">
                    <a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
