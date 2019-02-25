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

<p><spring:message code="member.edit.msg" /></p>
<jstl:if test="${alert}">
	<script>
		alert('holq2');
	</script>
</jstl:if>

<form:form modelAttribute="actorForm" action="member/edit.do" method="POST">

	<acme:textbox code="member.edit.userAccountuser" path="userAccountuser" />
	<acme:textbox code="member.edit.userAccountpassword" path="userAccountpassword" />

	<acme:textbox code="member.edit.name" path="name" />
	<acme:textbox code="member.edit.middleName" path="middleName" />
	<acme:textbox code="member.edit.surname" path="surname" />
	<acme:textbox code="member.edit.photo" path="photo" />
	<acme:textbox code="member.edit.email" path="email" />
	<acme:textbox code="member.edit.phone" path="phone" />
	<acme:textbox code="member.edit.address" path="address" />
	<acme:submit code="member.edit.submit" name="submit"/>
</form:form>