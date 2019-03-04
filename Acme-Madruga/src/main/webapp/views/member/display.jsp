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
<script src="https://unpkg.com/jspdf@latest/dist/jspdf.min.js"></script>
<script>
	var doc = new jsPDF()
	
	
	doc.save('doc.pdf')
</script>


<acme:display code="actor.name" value="${member.name}"/>
<spring:message code="actor.photo"/>:<br>
<img src="${member.photo}" alt="<spring:message code="member.alt.image"/>" width="20%" height="20%"/>
<br>
<acme:display code="actor.middleName" value="${member.middleName}"/>
<acme:display code="actor.surname" value="${member.surname}"/>
<acme:display code="actor.email" value="${member.email}"/>
<acme:display code="actor.phone" value="${member.phone}"/>
<acme:display code="actor.email" value="${member.email}"/>
<acme:display code="actor.address" value="${member.address}"/>
<acme:display code="actor.score" value="${member.score}"/>

<jstl:choose>
	<jstl:when test="${member.spammer}">
		<spring:message code="actor.spammer"/>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="actor.spammer.no"/>
	</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:button url="/member/list.do" name="back" code="member.back"/>
</security:authorize>