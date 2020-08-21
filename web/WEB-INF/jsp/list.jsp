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
<table>
    <tr>
        <th>Имя</th>
        <th>Email</th>
    </tr>
    <c:forEach items="${resumes}" var="resume">
        <jsp:useBean id="resume" class="ru.javaops.basejava.model.Resume"/>
        <tr>
            <td><a href=resumes?uuid=${resume.uuid}>${resume.fullName}</a></td>
            <td>${resume.getContact(ContactType.EMAIL)}</td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
