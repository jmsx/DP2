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

<p><spring:message code="member.display.msg" /></p>


<spring:message code="member.display.msg"/>: 
	<a href="member/display.do?memberId=${id}">	</a>
<br/>

<spring:message code="actor.name" var="name"/>
<jstl:out value="${name}"/>:
<jstl:out value="${member.name}"/>
<br>
<spring:message code="actor.middleName" var="middleName"/>
<jstl:out value="${middleName}"/>:
<jstl:out value="${member.middleName}"/>
<br>
<spring:message code="actor.surname" var="surname"/>
<jstl:out value="${surname}"/>:
<jstl:out value="${member.surname}"/>
<br>
<spring:message code="actor.photo" var="photo"/>
<jstl:out value="${photo}"/>:
<img src="${member.photo}" alt="Foto" width="10%" height="10%"/>
<br>
<spring:message code="actor.email" var="email"/>
<jstl:out value="${email}"/>:
<jstl:out value="${member.email}"/>
<br>
<spring:message code="actor.phone" var="phoneNumber"/>
<jstl:out value="${phone}"/>:
<jstl:out value="${member.phone}"/>
<br>
<spring:message code="actor.address" var="address"/>
<jstl:out value="${address}"/>:
<jstl:out value="${member.address}"/>
<br>
<spring:message code="actor.score" var="score"/>
<jstl:out value="${score}"/>:
<jstl:out value="${member.score}"/>
<br>
<spring:message code="actor.spammer" var="spammer"/>
<jstl:out value="${spammer}"/>:
<jstl:out value="${member.spammer}"/>
<br>
