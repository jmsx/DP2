
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<style type="text/css">
.PENDING {
	background-color: #d9d9d9;
}

.APPROVED {
	background-color: green;
}

.REJECTED {
	background-color: orange;
}
</style>

<jstl:if test="${not empty rol}">
	<jstl:set var="rolURL" value="/${rol}" />
</jstl:if>

<form:form action="request${rolURL}/edit.do" modelAttribute="request">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment"/>
	<form:hidden path="status"/>
	
	<security:authorize access="hasRole('BROTHERHOOD')">
			<jstl:if test="${request.status eq 'APPROVED'}">
				<form:hidden path="explanation"/>
				<acme:display code="request.suggested.row" value="${suggestedRow}"/>
				<acme:textbox code="request.row" path="row"/>
				<br />
				<acme:display code="request.suggested.column" value="${suggestedColumn}"/>
				<acme:textbox code="request.column" path="column"/>	
				<br />
			</jstl:if>
			<jstl:if test="${request.status eq 'REJECTED'}">
				<form:hidden path="row"/>
				<form:hidden path="column"/>
				<acme:textbox code="request.explanation" path="explanation"/>
				<br />
			</jstl:if>
			<br />
			<acme:submit name="save" code="request.save"/>
	</security:authorize>
	
	<acme:button url="request${rolURL}/list.do" name="cancel" code="request.cancel"/>

</form:form>