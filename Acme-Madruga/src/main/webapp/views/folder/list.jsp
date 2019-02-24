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

<p><spring:message code="folder.list" /></p>

<%--<security:authorize access="hasAnyRole('ADMIN')">
    <div>
        <H3>
            <input type="button" class="btn btn-danger" name="createMessage"
                   value="<spring:message code="message.send" />"
                   onclick="relativeRedir('message/create.do');"/>
        </H3>
    </div>
</security:authorize> --%>

    <div>
        <input type="button" class="btn btn-danger" name="createFolder"
               value="Crear"
               onclick="relativeRedir('folder/create.do');"/>
    </div>


<%--<security:authorize access="hasRole('ADMIN')">
    <div>
        <input type="button" class="btn btn-danger" name="createFolder"
               value="<spring:message code="mezzage.createBroadcast" />"
               onclick="relativeRedir('mezzage/createBroadcast.do');"/>
    </div>
</security:authorize> --%>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" keepStatus="true"
               name="folders" requestURI="${requestURI}" id="row">
    <%-- <security:authorize access="hasAnyRole('ADMIN')">
    <display:column>
        <input type="button" class="btn btn-danger" name="open"
               value="<spring:message code="general.open" />"
               onclick="relativeRedir('folder/view.do?folderId=${row.id}');"/>

    </display:column>
    </security:authorize>--%>
    <!-- Attributes -->


    <spring:message var="title"/>
    <display:column property="name" title="${title}" sortable="true"/>

    <!--<security:authorize access="hasAnyRole('ADMIN')">
        <display:column>
            <input type="button" class="btn btn-danger" name="edit"
                   value="<spring:message code="general.edit" />"
                   onclick="relativeRedir('folder/edit.do?folderId=${row.id}');"/>

            <input type="button" class="btn btn-danger" name="edit"
                   value="<spring:message code="general.delete" />"
                   onclick="relativeRedir('folder/delete.do?folderId=${row.id}');"/>
        </display:column>

    </security:authorize>-->
</display:table>