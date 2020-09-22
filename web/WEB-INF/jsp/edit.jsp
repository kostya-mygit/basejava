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
        <div class="container-edit">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <div class="row">
                <div class="col-25">
                    <label>Имя</label>
                </div>
                <div class="col-75">
                    <input type="text" name="fullName" value="${resume.fullName}" required>
                </div>
            </div>
        </div>

        <div class="container-edit">
            <c:forEach var="contactType" items="${contactTypes}">
                <jsp:useBean id="contactType" type="ru.javaops.basejava.model.ContactType"/>
                <div class="row">
                    <div class="col-25">
                        <label>${contactType.title}</label>
                    </div>
                    <div class="col-75">
                        <c:choose>
                            <c:when test="${contactType == ContactType.PHONE}">
                                <input type="tel" name="${contactType}" value="${resume.getContact(contactType)}"
                                       placeholder="900123456" pattern="[0-9]{10}">
                            </c:when>
                            <c:when test="${contactType == ContactType.EMAIL}">
                                <input type="email" name="${contactType}" value="${resume.getContact(contactType)}"
                                       required>
                            </c:when>
                            <c:otherwise>
                                <input type="text" name="${contactType}" value="${resume.getContact(contactType)}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:forEach var="sectionType" items="${sectionTypes}">
            <jsp:useBean id="sectionType" type="ru.javaops.basejava.model.SectionType"/>
            <div class="container-edit">
                <label>${sectionType.title}</label>
                <c:choose>
                    <c:when test="${sectionType == SectionType.OBJECTIVE || sectionType == SectionType.PERSONAL}">
                        <input type="text" name="${sectionType}"
                               value="${resume.getSection(sectionType).content}">
                    </c:when>
                    <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                        <jsp:useBean id="htmlUtil" type="ru.javaops.basejava.util.HtmlUtil"
                                     class="ru.javaops.basejava.util.HtmlUtil"/>
                        <textarea
                                name="${sectionType}">${htmlUtil.formatListToString(resume.getSection(sectionType).items)}</textarea>
                    </c:when>
                    <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                        <c:forEach var="organization" items="${resume.getSection(sectionType).organizations}"
                                   varStatus="counter">
                            <jsp:useBean id="organization" type="ru.javaops.basejava.model.Organization"/>
                            <div class="container-edit-2">
                                <div class="row">
                                    <div class="col-25">
                                        <label class="font-normal">Название учреждения</label>
                                    </div>
                                    <div class="col-75">
                                        <input type="text" name="${sectionType}" value="${organization.homePage.name}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-25">
                                        <label class="font-normal">Сайт учреждения</label>
                                    </div>
                                    <div class="col-75">
                                        <input type="text" name="${sectionType}url"
                                               value="${organization.homePage.url}">
                                    </div>
                                </div>
                                <c:forEach var="position" items="${organization.positions}" varStatus="status">
                                    <jsp:useBean id="position"
                                                 type="ru.javaops.basejava.model.Organization.Position"/>
                                    <div ${status.last ? 'class="container-edit-4"' : 'class="container-edit-3"'}>
                                        <div class="row">
                                            <div class="col-25-right">
                                                <label class="font-normal">Начальная дата</label>
                                            </div>
                                            <div class="col-25">
                                                <input type="text" name="${sectionType}${counter.index}startDate"
                                                       value="${htmlUtil.formatDateToMMyyyy(position.startDate)}"
                                                       placeholder="MM/yyyy">
                                            </div>
                                            <div class="col-25-right">
                                                <label class="font-normal">Конечная дата</label>
                                            </div>
                                            <div class="col-25">
                                                <input type="text" name="${sectionType}${counter.index}endDate"
                                                       value="${htmlUtil.formatDateToMMyyyy(position.endDate)}"
                                                       placeholder="MM/yyyy">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-25-right">
                                                <label class="font-normal">Должность</label>
                                            </div>
                                            <div class="col-75">
                                                <input type="text" name="${sectionType}${counter.index}title"
                                                       value="${position.title}">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-25-right">
                                                <label class="font-normal">Описание</label>
                                            </div>
                                            <div class="col-75">
                                                <input type="text" name="${sectionType}${counter.index}description"
                                                       value="${position.description}">
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </div>
        </c:forEach>

        <div class="row">
            <input type="submit" id="submit" value="Сохранить">
            <input type="button" id="cancel" value="Отменить" onclick="window.history.back()">
        </div>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
