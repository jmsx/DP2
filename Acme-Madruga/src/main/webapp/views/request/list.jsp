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
.pendingPeriodPassed {
	color: grey;
}

.ACCEPTED {
	color: green;
}

.REJECTED {
	color: orange;
}
</style>

<jstl:choose>
	<jstl:when test="${empty applications }">
		<spring:message code="application.no.list" />
	</jstl:when>
	<jstl:otherwise>
		<display:table name="applications" id="row"
			requestURI="application/${rol}/list.do" pagesize="5"
			class="displaytag">

			<security:authorize access="hasRole('CUSTOMER')">
				<display:column>
					<a href="application/customer/edit.do?applicationId=${row.id}">
						<spring:message code="application.display" />
					</a>
				</display:column>
			</security:authorize>

			<jstl:set value="${row.status} " var="colorStyle" />
			<jsp:useBean id="currentDate" class="java.util.Date" />
			<fmt:formatDate var="now" value="${currentDate}"
				pattern="dd/MM/yyyy HH:mm" />
			<jstl:if
				test="${(row.status eq 'PENDING') and (now > row.task.endDate)}">
				<jstl:set value="pendingPeriodPassed" var="colorStyle" />
			</jstl:if>

			<display:column property="moment" titleKey="application.moment"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

			<display:column property="status" titleKey="application.status"
				class="${colorStyle}" />

			<display:column property="task.ticker"
				titleKey="application.task.ticker" />

			<security:authorize access="hasRole('HANDYWORKER')">
				<display:column>
					<a
						href="application/handyWorker/display.do?applicationId=${row.id}">
						<spring:message code="application.display" />
					</a>
				</display:column>
			</security:authorize>


		</display:table>
	</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:choose>
		<jstl:when test="${theresTasksAvailable}">
			<input type="button" name="create"
				value="<spring:message code="application.create" />"
				onclick="javascript: relativeRedir('application/handyWorker/create.do');" />
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="application.create.no" />
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>

<jstl:if test="${noCards}">
	<spring:message code="application.creditCard.no" />
</jstl:if>