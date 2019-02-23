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

<jstl:if test="${not empty rol}">
	<jstl:set var="rolURL" value="/${rol}" />
</jstl:if>

<display:table name="processions" id="row"
	requestURI="procession${rolURL}/list.do" pagesize="5"
	class="displaytag">

	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column>
			<a href="procession/brotherhood/edit.do?processionId=${row.id}">
				<spring:message code="procession.edit" />
			</a>
		</display:column>
	</security:authorize>

	<display:column titleKey="procession.brotherhood">
		<a href="brotherhood/display.do?brotherhoodId=${row.brotherhood.id}">
			<jstl:out value="${row.brotherhood.title}" />
		</a>
	</display:column>

	<display:column property="ticker" titleKey="procession.ticker" />

	<acme:dataTableColumn code="procession.moment" property="moment" />

	<display:column property="description"
		titleKey="procession.description" />


	<display:column>
		<a href="procession${rolURL}/display.do?processionId=${row.id}"> <spring:message
				code="procession.display" />
		</a>
	</display:column>


	<security:authorize access="hasRole('MEMBER')">

		<jstl:set var="ctrl" value="0" />
		<jstl:forEach var="r" items="${memberProcessions}">
			<jstl:if test="${r eq row}">
				<jstl:set var="ctrl" value="1" />
			</jstl:if>
		</jstl:forEach>
		<display:column>
			<jstl:choose>
				<jstl:when test="${ctrl == 0}">
					<a href="request/member/create.do?processionId=${row.id}"> <spring:message
							code="procession.apply" />
					</a>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="procession.applied" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">
	<a href="procession/brotherhood/create.do"> <spring:message
			code="procession.create" />
	</a>
</security:authorize>