<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="areas" requestURI="${requestURI}" id="row">

	<!-- Action links -->



	<spring:message code="area.edit.header" var="editHeader"></spring:message>
	<display:column title="${editHeader}" sortable="false">
		<security:authorize access="hasRole('ADMIN')">
			<acme:link url="area/administrator/edit.do?areaId=${row.id}"
				code="area.edit" />
		</security:authorize>
	</display:column>


	<spring:message code="area.display.header" var="displayHeader"></spring:message>
	<display:column title="${displayHeader}" sortable="false">
		<acme:link url="area/administrator/display.do?areaId=${row.id}"
			code="area.display" />
	</display:column>


	<!-- Attributes of the positions -->

	<spring:message code="area.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

</display:table>

<!-- Action links -->

<security:authorize access="hasRole('ADMIN')">
	<div>
		<a href="area/administrator/create.do"> <spring:message
				code="area.create" />
		</a>
	</div>
</security:authorize>
