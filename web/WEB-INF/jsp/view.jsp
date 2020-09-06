<%@ page import="ru.javaops.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<ru.javaops.basejava.model.ContactType, java.lang.String>"/>
        <p><b>${contactEntry.key.title}</b>:&nbsp;
            <c:choose>
                <c:when test="${contactEntry.key == ContactType.EMAIL}">
                    <a href="mailto:${contactEntry.value}">${contactEntry.value}</a>
                </c:when>
                <c:when test="${contactEntry.key == ContactType.SKYPE}">
                    <a href="skype:${contactEntry.value}">${contactEntry.value}</a>
                </c:when>
                <c:when test="${contactEntry.key == ContactType.HOME_PAGE || contactEntry.key == ContactType.GITHUB ||
                contactEntry.key == ContactType.STACKOVERFLOW}">
                    <a href="${contactEntry.value}">${contactEntry.value}</a>
                </c:when>
                <c:otherwise>
                    ${contactEntry.value}
                </c:otherwise>
            </c:choose></p>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
