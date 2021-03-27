<%@ page import="com.urise.webapp.model.SingleLineSection" %>
<%@ page import="com.urise.webapp.model.BulletedListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationListSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
    </c:forEach>
</section>
<section>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>

            <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
            <c:if test="${type=='OBJECTIVE'||type=='PERSONAL'}">
                <tr>
                    <td colspan="2">
                        <%=((SingleLineSection) section).getSingleLine()%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${type=='ACHIEVEMENT'||type=='QUALIFICATIONS'}">
                <tr>
                    <td colspan="2">
                        <ul >
                            <c:forEach var="item" items="<%=((BulletedListSection)section).getBulletedList()%>">
                                <li>${item}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:if>
            <c:if test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <c:forEach var="org" items="<%=((OrganizationListSection) section).getOrganizationList()%>">
                    <tr>
                        <td colspan="2">
                            <h3>${org.name}</h3>
                        </td>
                    </tr>
                    <c:forEach var="experience" items="${org.experienceList}">
                        <jsp:useBean id="experience" type="com.urise.webapp.model.Organization.Experience"/>
                        <tr>
                            <td width="20%" style="vertical-align: top"><%=DateUtil.format(experience.getStartDate()) + " - " + DateUtil.format(experience.getEndDate())%>
                            </td>
                            <td style="vertical-align: top"><b>${experience.experience}</b><br>${experience.description}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:if>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

