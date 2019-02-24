<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<style type="text/css">
.PENDING {
	color: grey;
}

.ACCEPTED {
	color: green;
}

.REJECTED {
	color: orange;
}
</style>

<jstl:if test="${not empty rol}">
	<jstl:set var="rolURL" value="/${rol}" />
</jstl:if>

<jstl:choose>
	<jstl:when test="${empty requests }">
		<spring:message code="request.no.list" />
	</jstl:when>
	<jstl:otherwise>
		<display:table name="requests" id="row"
			requestURI="request${rolURL}/list.do" pagesize="5"
			class="displaytag">

			<security:authorize access="hasRole('BROTHERHOOD')">
				<display:column>
					<a href="request/brotherhood/edit.do?requestId=${row.id}">
						<spring:message code="request.display" />
					</a>
				</display:column>
			</security:authorize>

			<jstl:set value="${row.status} " var="colorStyle" />

			<acme:dataTableColumn code="request.moment" property="moment"/>

			<display:column property="status" titleKey="request.status"
				class="${colorStyle}" />

			<display:column property="task.ticker"
				titleKey="request.task.ticker" />

			<security:authorize access="hasRole('MEMBER')">
				<display:column>
					<a
						href="request/member/display.do?requestId=${row.id}">
						<spring:message code="request.display" />
					</a>
				</display:column>
			</security:authorize>


		</display:table>
	</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('MEMBER')">
	<jstl:choose>
		<jstl:when test="${theresProcessionAvailable}">
			<input type="button" name="create"
				value="<spring:message code="request.create" />"
				onclick="javascript: relativeRedir('request/member/create.do');" />
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="request.create.no" />
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>