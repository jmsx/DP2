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




<spring:message code="actor.name" var="name"/>
<jstl:out value="${name}"/>:
<jstl:out value="${brotherhood.name}"/>
<br>
<spring:message code="actor.middleName" var="middleName"/>
<jstl:out value="${middleName}"/>:<jstl:out value="${brotherhood.middleName}"/>
<br>
<spring:message code="actor.surname" var="surname"/>
<jstl:out value="${surname}"/>:
<jstl:out value="${brotherhood.surname}"/>
<br>
<spring:message code="actor.photo" var="photo"/>
<jstl:out value="${photo}"/>:
<img src="${brotherhood.photo}" alt="Foto" width="10%" height="10%"/>
<br>
<spring:message code="actor.email" var="email"/>
<jstl:out value="${email}"/>:
<jstl:out value="${brotherhood.email}"/>
<br>
<spring:message code="actor.phone" var="phone"/>
<jstl:out value="${phone}"/>:
<jstl:out value="${brotherhood.phone}"/>
<br>
<spring:message code="actor.address" var="address"/>
<jstl:out value="${address}"/>:
<jstl:out value="${brotherhood.address}"/>
<br>
<spring:message code="actor.score" var="score"/>
<jstl:out value="${score}"/>:
<jstl:out value="${brotherhood.score}"/>
<br>
<spring:message code="actor.spammer" var="spammer"/>
<jstl:out value="${spammer}"/>:
<jstl:out value="${brotherhood.spammer}"/>
<br>
<br>

<security:authorize access="hasRole('MEMBER')">
	<acme:button url="procession/member/list.do" name="back" code="procession.back"/>
</security:authorize>


