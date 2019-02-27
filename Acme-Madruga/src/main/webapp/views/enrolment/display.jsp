<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<spring:message code="enrolment.member" var="member"/>
<jstl:out value="${member}"/>:
<jstl:out value="${enrolment.member}"/>
<br>

<spring:message code="enrolment.brotherhood" var="brotherhood"/>
<jstl:out value="${brotherhood}"/>:
<jstl:out value="${enrolment.brotherhood}"/>
<br>

<spring:message code="enrolment.moment" var="moment"/>
<jstl:out value="${moment}"/>:
<jstl:out value="${enrolment.moment}"/>
<br>

<jstl:choose>
	<jstl:when test="${enrolment.enrolled}">
		<spring:message code="enrolment.position" var="position"/>
		<jstl:out value="${position}"/>:
		<jstl:out value="${enrolment.position}"/>
		<br>
	</jstl:when>

	<jstl:otherwise> 
	<security:authorize access="hasRole('BROTHERHOOD')">
		<a href="enrolment/member/edit.do">
			<spring:message code="enrolment.member.position.enrole" />
		</a>
	</security:authorize>
	</jstl:otherwise>
</jstl:choose>


<spring:message code="enrolment.dropOut" var="dropOut"/>
<jstl:out value="${dropOut}"/>:
<jstl:out value="${enrolment.dropOut}"/>
<br>

<security:authorize access="hasRole('MEMBER')">
	<acme:button url="brotherhood/list.do" name="back" code="member.back"/>
</security:authorize>

<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:button url="member/list.do" name="back" code="member.back"/>
</security:authorize>

