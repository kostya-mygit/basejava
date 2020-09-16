<%@ page import="ru.javaops.basejava.model.ContactType" %>
<%@ page import="ru.javaops.basejava.model.SectionType" %>
<%@ page import="ru.javaops.basejava.util.HtmlUtil" %>
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
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя</dt>
            <dd><input type="text" name="fullName" value="${resume.fullName}" size="75" required></dd>
        </dl>
        <br>
        <dl>
            <c:forEach var="contactType" items="${contactTypes}">
                <jsp:useBean id="contactType" type="ru.javaops.basejava.model.ContactType"/>
                <dt>${contactType.title}</dt>
                <c:choose>
                    <c:when test="${contactType == ContactType.EMAIL}">
                        <dd><input type="email" name="${contactType}" value="${resume.getContact(contactType)}"
                                   size="75" required></dd>
                    </c:when>
                    <c:otherwise>
                        <dd><input type="text" name="${contactType}" value="${resume.getContact(contactType)}"
                                   size="75"></dd>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </dl>
        <br>
        <dl>
            <c:forEach var="sectionType" items="${sectionTypes}">
                <jsp:useBean id="sectionType" type="ru.javaops.basejava.model.SectionType"/>
                <h3>
                    <dt>${sectionType.title}</dt>
                </h3>
                <br>
                <c:choose>
                    <c:when test="${sectionType == SectionType.OBJECTIVE || sectionType == SectionType.PERSONAL}">
                        <dd><input type="text" name="${sectionType}" value="${resume.getSection(sectionType).content}"
                                   size="140"></dd>
                    </c:when>
                    <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                        <jsp:useBean id="htmlUtil" type="ru.javaops.basejava.util.HtmlUtil"
                                     class="ru.javaops.basejava.util.HtmlUtil"/>
                        <dd><textarea name="${sectionType}" cols="130"
                                      rows="5">${htmlUtil.formatListToString(resume.getSection(sectionType).items)}</textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                        <c:forEach var="organization" items="${resume.getSection(sectionType).organizations}"
                                   varStatus="counter">
                            <jsp:useBean id="organization" type="ru.javaops.basejava.model.Organization"/>
                            <dl>
                                <dt>Название учреждения:</dt>
                                <dd><input type="text" name="${sectionType}"
                                           value="${organization.homePage.name}" size="100"/></dd>
                            </dl>
                            <dl>
                                <dt>Сайт учреждения:</dt>
                                <dd><input type="text" name="${sectionType}url"
                                           value="${organization.homePage.url}" size="100"/></dd>
                            </dl>
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position" type="ru.javaops.basejava.model.Organization.Position"/>
                                <div>
                                    <dl>
                                        <dt>Начальная дата:</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}startDate"
                                                   value="${htmlUtil.formatDateToMMyyyy(position.startDate)}"
                                                   placeholder="MM/yyyy" size="20"/></dd>
                                    </dl>
                                    <dl>
                                        <dt>Конечная дата:</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}endDate"
                                                   value="${htmlUtil.formatDateToMMyyyy(position.endDate)}"
                                                   placeholder="MM/yyyy" size="20"/></dd>
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}title"
                                                   value="${position.title}" size=100></dd>
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}description"
                                                   value="${position.description}" size="100"/></dd>
                                    </dl>
                                </div>
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
