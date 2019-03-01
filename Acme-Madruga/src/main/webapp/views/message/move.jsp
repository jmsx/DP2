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
  
<p><spring:message code="general.move" /></p>

<form:form action="message/move.do" modelAttribute="m">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="moment"/>
    <form:hidden path="sender"/>
    
    <display:table pagesize="5" class="displaytag" keepStatus="true"
               name="m" requestURI="${requestURI}" id="row">

    <!-- Attributes -->

    <security:authorize access="hasAnyRole('ADMIN')">
    </security:authorize>

	<display:column property="subject" titleKey="message.subject" />
	<display:column property="sender" value="name" titleKey="message.sender" />
	<display:column property="recipients" value="name" titleKey="message.recipients" />
	<display:column property="priority" titleKey="message.priority" />
	<display:column>
		<acme:select items="${folders}" itemLabel="name" code="general.folders" path=""/>
	</display:column>
	
</display:table>

    <!---------------------------- BOTONES -------------------------->

	<input type="button" class="btn btn-danger" name="saveMove"
		value="<spring:message code="general.move" />"
           onclick="relativeRedir('message/saveMove.do');"/>

    <input type="button" class="btn btn-danger" name="cancel"
           value="<spring:message code="general.cancel" />"
           onclick="relativeRedir('folder/list.do');"/>

</form:form>