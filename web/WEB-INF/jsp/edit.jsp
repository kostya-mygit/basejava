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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="isNew" value="${isNew}">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" value="${resume.fullName}" size="50"></dd>
        </dl>
        <h3>Контакты:</h3>
        <dl>
            <c:forEach var="contactType" items="${contactTypes}">
                <jsp:useBean id="contactType" type="ru.javaops.basejava.model.ContactType"/>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType}" value="${resume.getContact(contactType)}" size="50"></dd>
            </c:forEach>
        </dl>
        <h3>Секции:</h3>
        <dl>
            <c:forEach var="sectionType" items="${sectionTypes}">
                <jsp:useBean id="sectionType" type="ru.javaops.basejava.model.SectionType"/>
                <dt>${sectionType.title}</dt>
                <br>
                <c:choose>
                    <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                        <dd><input type="text" name="${sectionType}" value="${resume.getSection(sectionType).content}"
                                   size="50"></dd>
                    </c:when>
                    <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                        <c:forEach var="item" items="${resume.getSection(sectionType).items}">
                            <dd><input type="text" name="${sectionType}" value="${item}" size="50"></dd>
                        </c:forEach>
                    </c:when>
                    <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                        <c:forEach var="organization" items="${resume.getSection(sectionType).organizations}">
                            <jsp:useBean id="organization" type="ru.javaops.basejava.model.Organization"/>
                            <c:forEach var="position" items="${organization.positions}" varStatus="status">
                                <c:choose>
                                    <c:when test="${status.first}">
                                        <dd><input type="text" name="${sectionType}" value="${organization.homePage.name}" size="50">
                                            <input type="text" name="${sectionType}" value="${organization.homePage.url}" size="50"></dd>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="${sectionType}" value="${organization.homePage.name}">
                                            <input type="hidden" name="${sectionType}" value="${organization.homePage.url}">
                                    </c:otherwise>
                                </c:choose>
                                <jsp:useBean id="position" type="ru.javaops.basejava.model.Organization.Position"/>
                                <dd><input type="text" name="${sectionType}" value="${position.startDate}" size="22">
                                    <input type="text" name="${sectionType}" value="${position.endDate}" size="22">
                                    <input type="text" name="${sectionType}" value="${position.title}" size="50"></dd>
                                <c:choose>
                                    <c:when test="${not empty position.description}">
                                        <dd><input type="text" name="${sectionType}" value="${position.description}"
                                                   size="107"></dd>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="${sectionType}" value="${position.description}">
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
        </dl>
        <br>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
