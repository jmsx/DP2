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



<acme:display code="actor.name" value="${brotherhood.name}"/>
<spring:message code="actor.photo"/>:<br>
<img src="${brotherhood.photo}" alt="Foto" width="20%" height="20%"/>
<br>
<acme:display code="actor.middleName" value="${brotherhood.middleName}"/>
<acme:display code="actor.surname" value="${brotherhood.surname}"/>
<acme:display code="actor.email" value="${brotherhood.email}"/>
<acme:display code="actor.phone" value="${brotherhood.phone}"/>
<acme:display code="actor.email" value="${brotherhood.email}"/>
<acme:display code="actor.address" value="${brotherhood.address}"/>
<acme:display code="actor.score" value="${brotherhood.score}"/>

<jstl:choose>
	<jstl:when test="${brotherhood.spammer}">
		<spring:message code="actor.spammer"/>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="actor.spammer.no"/>
	</jstl:otherwise>
</jstl:choose>
<br><br>
<security:authorize access="hasRole('MEMBER')">
	<acme:button url="procession/member/list.do" name="back" code="procession.back"/>
</security:authorize>


