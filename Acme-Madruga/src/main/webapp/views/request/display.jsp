<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${not empty rol}">
	<jstl:set var="rolURL" value="/${rol}" />
</jstl:if>

<jstl:if test="${not empty request.member.middleName}">
	<jstl:set var="middlename" value=" ${request.member.middleName}" />
</jstl:if>

<acme:display code="request.procession.title" value="${request.procession.title}"/>
<acme:display code="request.member.name" value="${request.member.name}${middlename} ${request.member.surname}"/>
<acme:display code="request.moment" value="${request.moment}"/>
<acme:display code="request.status" value="${request.status}"/>

<jstl:choose>
	<jstl:when test="${request.status eq 'REJECTED'}">
		<acme:display code="request.explanation" value="${request.explanation}"/>
	</jstl:when>
	<jstl:when test="${request.status eq 'APPROVED'}">
		<acme:display code="request.row" value="${request.row}"/>
		<acme:display code="request.column" value="${request.column}"/>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="request.status.pending"/>
		</br>
	</jstl:otherwise>
</jstl:choose>

<acme:button name="back" code="request.back" url="request${rolURL}/list.do"/>

