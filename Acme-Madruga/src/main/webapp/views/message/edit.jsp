<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<%--
  ~ Copyright © 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
  --%>

<form:form action="message/edit" modelAttribute="message">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="sender"/>
    <form:hidden path="moment"/>
    <form:hidden path="sender"/>
    <form:hidden path="tags"/>
    <form:hidden path="priority"/>
    


    <acme:textbox path="recipients" code="message.receiver"/>
    <br/>

    <acme:textbox path="subject" code="message.subject"/>
    <br/>

    <acme:textarea path="body" code="message.body"/>
    <br/>


    <!--<form:label path="priority">
        <spring:message code="message.priority"/>:
    </form:label>
    <form:select path="priority" code="message.priority">
        <form:options/>
    </form:select>
    <br/>-->


    <!---------------------------- BOTONES -------------------------->


    <button name="save" type="submit" class="button2">
        <spring:message code="general.send"/>
    </button>

    <input type="button" class="btn btn-danger" name="cancel"
           value="<spring:message code="general.cancel" />"
           onclick="relativeRedir('folder/list.do');"/>

</form:form>