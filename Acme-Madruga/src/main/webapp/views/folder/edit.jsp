<%--
 * edit.jsp
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

<p><spring:message code="folder.edit" /></p>

    

<form:form action="folder/edit.do" modelAttribute="folder">

	<form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="isSystemFolder"/>
    <form:hidden path="actor"/>
    <form:hidden path="messages"/>

    <acme:textbox path="name" code="folder.name"/>
    <br/>

    <!---------------------------- BOTONES -------------------------->

 	<button name="save" type="submit" class="button2">
        <spring:message code="general.save"/>
    </button>
    <input type="button" class="btn btn-danger" name="cancel"
           value="<spring:message code="general.cancel" />"
           onclick="relativeRedir('folder/list.do');"/>



</form:form>