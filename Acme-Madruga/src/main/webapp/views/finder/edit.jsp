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


<form:form action="finder/member/edit.do" modelAttribute="finder" method="POST">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<br/>
    <acme:textbox path="keyword" code="finder.keyword"/>
    <br/>
    <acme:textbox path="areaName" code="finder.areaName"/>
	<br/>
    <acme:textbox path="maxDate" code="finder.maxDate"/>
    <br/>
    <acme:textbox path="minDate" code="finder.minDate"/>
    <br/>


	<br>
	<input type="submit" name="save" value="<spring:message code="finder.search" />" />
	<input type="submit" name="clear" value="<spring:message code="finder.clear" />" />
		
</form:form>