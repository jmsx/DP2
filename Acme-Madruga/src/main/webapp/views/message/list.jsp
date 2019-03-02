<%--
 * list.jsp
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

<p><spring:message code="message.list" /></p>

<input type="button" class="btn btn-danger" name="back"
       value="<spring:message code="general.cancel" />"
       onclick="relativeRedir('folder/list.do');"/>
<br>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" keepStatus="true"
               name="m" requestURI="${requestURI}" id="row">

    <!-- Attributes -->

    <security:authorize access="hasAnyRole('ADMIN')">
        <display:column>
            <input type="button" class="btn btn-danger" name="deleteMessage"
                   value="<spring:message code="general.delete" />"
                   onclick="relativeRedir('message/delete.do?messageId=${row.id}&folderId=${folder.id}');"/>
        </display:column>
    </security:authorize>

	<display:column property="subject" titleKey="message.subject" />
	<display:column property="sender" value="name" titleKey="message.sender" />
	<display:column property="recipients" value="name" titleKey="message.recipients" />
	<display:column property="priority" titleKey="message.priority" />
	
	<display:column>
            <input type="button" class="btn btn-danger" name="moveMessage"
                   value="<spring:message code="general.move" />"
                   onclick="relativeRedir('message/move.do?messageId=${row.id}&folderId=${folder.id}');"/>
        </display:column>
	
</display:table>
