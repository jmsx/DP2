<%--
 * action-1.jsp
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

<p><spring:message code="brotherhood.register.msg" /></p>

<form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="photo"/>
    <form:hidden path="userAccount.authorities"/>

    <acme:textbox path="name" code="actor.name"/>
    <acme:textbox path="surname" code="actor.surname"/>
    <acme:textbox path="middleName" code="actor.middleName"/>
    <acme:textbox path="email" code="actor.email"/>
    <acme:textbox path="address" code="actor.address"/>
    <acme:textbox path="phone" code="actor.phone"/>


<br/>
    <h1><spring:message code="actor.userAccount"/></h1>
    <br>
    <form:label path="userAccount.username">
        <spring:message code="actor.username"/>:
    </form:label>
    <form:input path="userAccount.username"/>
    <form:errors cssClass="error" path="userAccount.username"/>
    <br/>
    <br>
    <form:label path="userAccount.password">
        <spring:message code="actor.password"/>:
    </form:label>
    <form:password path="userAccount.password"/>
    <form:errors cssClass="error" path="userAccount.password"/>
    <br/>
    <br/>
    <spring:message code="actor.terms" var="terms11"/>
    <h3></h3>
    <a href="http://ourdisclaimer.com/?i=AcmeOverflow.,Inc."><jstl:out value="${terms11}"/></a>
    <br/>
    <br/>