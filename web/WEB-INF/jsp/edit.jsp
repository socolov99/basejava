<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.BulletedListSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page import="com.urise.webapp.model.OrganizationListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" scope="request" type="com.urise.webapp.model.Resume"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input required type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <h3>${type.title}</h3>
            <c:if test="${type=='OBJECTIVE'||type=='PERSONAL'}">
                <textarea name='${type}' cols="120"><%=section%></textarea>
            </c:if>
            <c:if test="${type=='ACHIEVEMENT'||type=='QUALIFICATIONS'}">
                <textarea name='${type}' cols="120"
                          rows="12"><%=String.join("\n", ((BulletedListSection) section).getBulletedList())%></textarea>
            </c:if>
            <c:if test="${type=='EXPERIENCE'||type=='EDUCATION'}">
                <c:forEach var="org" items="<%=((OrganizationListSection) section).getOrganizationList()%>"
                           varStatus="experienceNumber">
                    <dl>
                        <dt>Название организации:</dt>
                        <dd><input type="text" name='${type}' size=100 value="${org.homePage.name}"></dd>
                    </dl>
                    <dl>
                        <dt>Сайт организации:</dt>
                        <dd><input type="text" name='${type}url' size=100 value="${org.homePage.url}"></dd>
                        </dd>
                    </dl>
                    <br>
                    <div style="margin-left: 30px">
                        <c:forEach var="pos" items="${org.experienceList}">
                            <jsp:useBean id="pos" type="com.urise.webapp.model.Organization.Experience"/>
                            <dl>
                                <dt>Начальная дата:</dt>
                                <dd>
                                    <input type="text" name="${type}${experienceNumber.index}startDate" size=10
                                           value="<%=DateUtil.format(pos.getStartDate())%>" placeholder="MM/yyyy">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Конечная дата:</dt>
                                <dd>
                                    <input type="text" name="${type}${experienceNumber.index}endDate" size=10
                                           value="<%=DateUtil.format(pos.getEndDate())%>" placeholder="MM/yyyy">
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name='${type}${experienceNumber.index}title' size=75
                                           value="${pos.experience}">
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><textarea name="${type}${experienceNumber.index}description" rows=5
                                              cols=75>${pos.description}</textarea></dd>
                            </dl>
                        </c:forEach>
                    </div>
                </c:forEach>
            </c:if>
        </c:forEach>

        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

