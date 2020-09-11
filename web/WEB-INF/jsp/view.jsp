<%@ page import="ru.javaops.basejava.model.ContactType" %>
<%@ page import="ru.javaops.basejava.model.SectionType" %>
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
    <br>
    <c:forEach var="contactType" items="${contactTypes}">
        <jsp:useBean id="contactType" type="ru.javaops.basejava.model.ContactType"/>
        <p><b>${contactType.title}</b>:&nbsp;
            <c:choose>
                <c:when test="${contactType == ContactType.EMAIL}">
                    <a href="mailto:${resume.getContact(contactType)}">${resume.getContact(contactType)}</a>
                </c:when>
                <c:when test="${contactType == ContactType.SKYPE}">
                    <a href="skype:${resume.getContact(contactType)}">${resume.getContact(contactType)}</a>
                </c:when>
                <c:when test="${contactType == ContactType.HOME_PAGE || contactType == ContactType.GITHUB ||
                contactType == ContactType.STACKOVERFLOW}">
                    <a href="${resume.getContact(contactType)}">${resume.getContact(contactType)}</a>
                </c:when>
                <c:otherwise>
                    ${resume.getContact(contactType)}
                </c:otherwise>
            </c:choose></p>
    </c:forEach>
    <br>
    <c:forEach var="sectionType" items="${sectionTypes}">
        <jsp:useBean id="sectionType" type="ru.javaops.basejava.model.SectionType"/>
        <h3>${sectionType.title}:</h3>
        <c:choose>
            <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                ${resume.getSection(sectionType).content}
            </c:when>
            <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                <p>
                <c:forEach var="item" items="${resume.getSection(sectionType).items}" varStatus="status">
                    ${item}${not status.last ? ", " : ""}
                </c:forEach>
            </c:when>
            <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                <p>
                <c:forEach var="organization" items="${resume.getSection(sectionType).organizations}">
                    <jsp:useBean id="organization" type="ru.javaops.basejava.model.Organization"/>
                    <p>
                    <c:choose>
                        <c:when test="${empty organization.homePage.url}">
                            <b>${organization.homePage.name}</b>
                        </c:when>
                        <c:otherwise>
                            <b><a href="${organization.homePage.url}">${organization.homePage.name}</a></b>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="position" items="${organization.positions}">
                        <jsp:useBean id="position" type="ru.javaops.basejava.model.Organization.Position"/>
                        <p>${position.startDate} ${position.endDate} ${position.title}
                        <p>${position.description}
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
