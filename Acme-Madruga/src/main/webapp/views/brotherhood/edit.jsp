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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p><spring:message code="brotherhood.edit.msg" /></p>

<form:form modelAttribute="actorForm" action="brotherhood/edit.do" method="POST">
	<acme:textbox code="brotherhood.edit.userAccountuser" path="userAccountuser" />
	<acme:textbox code="brotherhood.edit.userAccountpassword" path="userAccountpassword" />

	<acme:textbox code="brotherhood.edit.name" path="name" />
	<acme:textbox code="brotherhood.edit.middleName" path="middleName" />
	<acme:textbox code="brotherhood.edit.surname" path="surname" />
	<acme:textbox code="brotherhood.edit.photo" path="photo" />
	<acme:textbox code="brotherhood.edit.email" path="email" />
	<acme:textbox code="brotherhood.edit.phone" path="phone" />
	<acme:textbox code="brotherhood.edit.address" path="address" />
	<acme:submit code="brotherhood.edit.submit" name="submit"/>
</form:form>