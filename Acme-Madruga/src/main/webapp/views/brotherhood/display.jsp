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

<p><spring:message code="brotherhood.display.msg" /></p>

<spring:message code="actor.name" var="name"/>
<jstl:out value="${name}"/>:
<jstl:out value="${name1}"/>
<br>
<spring:message code="actor.middleName" var="middleName"/>
<jstl:out value="${middleName}"/>:
<jstl:out value="${middleName1}"/>
<br>
<spring:message code="actor.surname" var="surname"/>
<jstl:out value="${surname}"/>:
<jstl:out value="${surname1}"/>
<br>
<spring:message code="actor.photo" var="photo"/>
<jstl:out value="${photo}"/>:
<img src="${photoPic}" alt="Foto" width="10%" height="10%"/>
<br>
<spring:message code="actor.email" var="email"/>
<jstl:out value="${email}"/>:
<jstl:out value="${email1}"/>
<br>
<spring:message code="actor.phone" var="phoneNumber"/>
<jstl:out value="${phone}"/>:
<jstl:out value="${phone}"/>
<br>
<spring:message code="actor.address" var="address"/>
<jstl:out value="${address}"/>:
<jstl:out value="${address1}"/>
<br>
<spring:message code="actor.score" var="score"/>
<jstl:out value="${score}"/>:
<jstl:out value="${score1}"/>
<br>
<spring:message code="actor.spammer" var="spammer"/>
<jstl:out value="${spammer}"/>:
<jstl:out value="${spammer1}"/>
<br>
